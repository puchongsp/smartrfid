package sushil.luc.gui;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ugrokit.api.Ugi;

import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
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
    private ActionBar actionbar;

    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_tag_item_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position",-1);
        }
        
        if (position == -1)
        {
        	finish();
        }
        
        // init the action bar and assign the current status
		 actionbar = getActionBar();
		 actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
		 actionbar.setSubtitle(currentStatus);
        
        ItemService itemService = new ItemService();

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
        
        this.updateView(item);
       
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
   
   public void onPause()
   {
	   super.onPause();
	   
	   super.StopInventory();
	   super.mHandler.modeNewItem(false,null, null);
   }
    
    
    public void updateView ()
    {
    	 Item_Scanning.setText("Item tagged Successfully!");
         Item_RFID.setText("RFID :"+item.getRFID());
    }

       
       private void updateView (Item currentItem)
       {
    	   item = currentItem;
    	   Item_ID.setText("Item ID : "+item.getItemID());
           Item_Name.setText("Itemname : "+item.getItemName());
           Item_RFID.setText("RFID :");
           Item_Location.setText("Location : "+item.getWarehouseLocation());
           if (item.getStatus()!=null)
           {
        	   Item_Status.setText("Status : "+item.getStatus().toString());
           }
           else
           {
           		Item_Status.setText("Status : ");
           }
           	Item_Scanning.setText("Scanning ...");
           	if (item.getCategory()!=null)
           		Item_Category.setText("Category : "+item.getCategory().toString());
           	else
           		Item_Category.setText("Category : ");
           	if (item.getSubCategory()!=null)
           		Item_SubCategory.setText("SubCategory : "+item.getSubCategory().toString());
           	else
           		Item_SubCategory.setText("SubCategory : ");
           	if (item.getCreationDate()!=null)
           		Item_CreationDate.setText("Creation Date : "+DateUtil.formatDate(item.getCreationDate().toString()));
           	else
           		Item_CreationDate.setText("Creation Date : ");
           	if(item.getType()!=null)
           		Item_Type.setText("Type : "+item.getType().toString());
           	else
           		Item_Type.setText("Type : ");
           	if(item.getInventoryTotal()!=null)
           		Item_InventoryTotal.setText("Inventory Total : "+item.getInventoryTotal().toString());
           	else
           		Item_InventoryTotal.setText("Inventory Total : ");
           	if (item.getInventoryOnHand()!=null)
           		Item_InventoryOnHand.setText("Inventory on Hand : "+item.getInventoryOnHand().toString());
           	else
           		Item_InventoryOnHand.setText("Inventory on Hand : ");
           	if (item.getInventoryOut()!=null)
           		Item_InventoryOut.setText("Inventory out : "+item.getInventoryOut().toString());
           	else
           		Item_InventoryOut.setText("Inventory out : ");
       }
       
       @Override
		/**
		 * If the connection from the ugrokit changes, this methode gives feedback to the user
		 */
		public void connectionStateChanged(Ugi.ConnectionStates connectionState) {
			super.connectionStateChanged(connectionState);
			// update the Status
			super.calculateStatus();
			notifiySatusUpdate();
		}
		
		/**
		 * Update the status bar
		 */
		public void notifiySatusUpdate()
		{
			if (actionbar!=null)
				actionbar.setSubtitle(currentStatus);
		}
		
		public void onDestroy()
		{
			super.onDestroy();
			super.StopInventory();
			super.mHandler.modeNewItem(false,null, null);
			super.calculateStatus();
		}

}
