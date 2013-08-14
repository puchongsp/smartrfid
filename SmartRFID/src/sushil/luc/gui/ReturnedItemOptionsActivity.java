package sushil.luc.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.smartrfid.R;

/**
 * Created by sushil on 7/9/13.
 */
public class ReturnedItemOptionsActivity extends Activity{

    private TextView Item_ID;
    private TextView Item_Name;
    private TextView Item_RFID;
    private TextView Item_Location;
    private TextView Item_Status;
    private Item item;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.show_returned_item_options);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position",-1);
        }

        ItemService itemService = new ItemService();
      //TODO connect to database
        List<Item> returnedItems = itemService.getReturnedItems();

        item = returnedItems.get(position);

        Item_ID = (TextView) findViewById(R.id.item_id);
        Item_Name = (TextView) findViewById(R.id.item_name);
        Item_RFID = (TextView) findViewById(R.id.item_rfid);
        Item_Location = (TextView) findViewById(R.id.item_location);
        Item_Status = (TextView) findViewById(R.id.item_status);

        Item_ID.setText("Item ID : " + item.getItemID());
        Item_Name.setText("Itemname : " + item.getItemName());
        Item_RFID.setText("RFID :" + item.getRFID());
        Item_Location.setText("Location : " + item.getWarehouseLocation());
        Item_Status.setText("Status : " + item.getStatus().toString());

        Button btnRepair = (Button) findViewById(R.id.btn_send_to_repair);
        btnRepair.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println ("Repair");
                ItemService itemService = new ItemService();
              //TODO connect to database
                itemService.sendToRepair(item);
                finish();
            }
        });

        Button btnToWarehouse = (Button) findViewById(R.id.btn_send_to_warehouse);
        btnToWarehouse.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemService itemService = new ItemService();
              //TODO connect to database
                itemService.sendToWarehouse(item);
                finish();
            }
        });
    }
}
