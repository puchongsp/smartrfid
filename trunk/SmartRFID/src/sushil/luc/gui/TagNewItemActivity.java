package sushil.luc.gui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.msc.RFIDActivity;
import sushil.luc.smartrfid.R;

/**
 * Enable RFID and scan
 * set rfid to the item if detected
 */
public class TagNewItemActivity extends RFIDActivity {

    private TextView Item_ID;
    private TextView Item_Name;
    private TextView Item_RFID;
    private TextView Item_Location;
    private TextView Item_Status;
    private TextView Item_Scanning;
    private Item item;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_item_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position",-1);
        }

        ItemService itemService = new ItemService();
        List<Item> newItems = itemService.getNewItems();

        item = newItems.get(position);

        Item_ID = (TextView) findViewById(R.id.item_id);
        Item_Name = (TextView) findViewById(R.id.item_name);
        Item_RFID = (TextView) findViewById(R.id.item_rfid);
        Item_Location = (TextView) findViewById(R.id.item_location);
        Item_Status = (TextView) findViewById(R.id.item_status);
        Item_Scanning = (TextView) findViewById(R.id.item_scanning);

        Item_ID.setText("Item ID : "+item.getItemID());
        Item_Name.setText("Itemname : "+item.getItemName());
        Item_RFID.setText("RFID :");
        Item_Location.setText("Location : "+item.getWarehouseLocation());
        Item_Status.setText("Status : "+item.getStatus().toString());
        Item_Scanning.setText("Scanning ...");
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //check if RFID already exists
        // make RFId lookup here
        //boolean result = rfidLookup(TagId);
        ItemService itemService = new ItemService();
        Item lookupItem = itemService.getItemInfo(TagId);

        // lookupItem null means the rfid is not yet assigned to any item yet
        if (lookupItem == null) {
            item.setRFID(TagId);
            Item_Scanning.setText("Item tagged Successfully!");
            Item_RFID.setText("RFID :"+TagId);
        }
        else {
            Toast.makeText(this, "RFID is already assigned", Toast.LENGTH_LONG).show();
        }

    }
}
