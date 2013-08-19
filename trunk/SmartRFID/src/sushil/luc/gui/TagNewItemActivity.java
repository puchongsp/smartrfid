package sushil.luc.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.msc.RFIDActivity;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.smartrfid.R;
import sushil.luc.utils.DateUtil;

/**
 * Enable RFID and scan
 * set rfid to the item if detected
 */
public class TagNewItemActivity extends UgroKitActivity {

    private TextView Item_ID;
    private TextView Item_Name;
    private TextView Item_RFID;
    private TextView Item_Location;
    private TextView Item_Status;
    private TextView Item_Scanning;
	private TextView Item_Category;
	private TextView Item_SubCategory;
	private TextView Item_CreationDate;
	private TextView Item_Type;
	private TextView Item_InventoryTotal;
	private TextView Item_InventoryOnHand;
	private TextView Item_InventoryOut;
    private Item item;

    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_tag_item_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position",-1);
        }

        ItemService itemService = new ItemService();
      //TODO connect to database
        List<Item> newItems = itemService.getNewItems("TagNewItemActivity");

        item = newItems.get(position);

        Item_ID = (TextView) findViewById(R.id.item_id);
        Item_Name = (TextView) findViewById(R.id.item_name);
        Item_RFID = (TextView) findViewById(R.id.item_rfid);
        Item_Location = (TextView) findViewById(R.id.item_location);
        Item_Status = (TextView) findViewById(R.id.item_status);
        Item_Scanning = (TextView) findViewById(R.id.item_scanning);
		Item_Category= (TextView) findViewById(R.id.item_categorie);
		Item_SubCategory= (TextView) findViewById(R.id.item_subcategorie);
		Item_CreationDate= (TextView) findViewById(R.id.item_creationdate);
		Item_Type= (TextView) findViewById(R.id.item_type);
		Item_InventoryTotal= (TextView) findViewById(R.id.item_inventorytotal);
		Item_InventoryOnHand= (TextView) findViewById(R.id.item_inventoryonhand);
		Item_InventoryOut= (TextView) findViewById(R.id.item_inventoryout);
        
        
        Item_ID.setText("Item ID : "+item.getItemID());
        Item_Name.setText("Itemname : "+item.getItemName());
        Item_RFID.setText("RFID :");
        Item_Location.setText("Location : "+item.getWarehouseLocation());
        if (item.getStatus()!=null)
        	Item_Status.setText("Status : "+item.getStatus().toString());
        else
        	Item_Status.setText("Status : ");
        Item_Scanning.setText("Scanning ...");
        Item_Category.setText("Category : "+item.getCategory().toString());
		Item_SubCategory.setText("SubCategory : "+item.getSubCategory().toString());
		Item_CreationDate.setText("Creation Date : "+DateUtil.formatDate(item.getCreationDate().toString()));
		Item_Type.setText("Type : "+item.getType().toString());
		Item_InventoryTotal.setText("Inventory Total : "+item.getInventoryTotal().toString());
		Item_InventoryOnHand.setText("Inventory on Hand : "+item.getInventoryOnHand().toString());
		Item_InventoryOut.setText("Inventory out : "+item.getInventoryOut().toString());
    }
/**
 * start the Rfid scan and set the correct Handler modes
 */
   public void onResume()
   {
	   super.onResume();
	   
	   super.StartInventory();
	   Log.d("TagNewItemAc", "onResume");
	   super.mHandler.modeNewItem(true, this, item);
   }
    
  /*  public void onNewIntent(Intent intent) {
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

    }*/
    
    public void updateView ()
    {
    	 Item_Scanning.setText("Item tagged Successfully!");
         Item_RFID.setText("RFID :"+item.getRFID());
    }
}
