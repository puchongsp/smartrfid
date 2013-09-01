package sushil.luc.gui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ugrokit.api.Ugi;
import com.ugrokit.api.UgiInventoryDelegate;
import com.ugrokit.api.UgiTag;

import java.util.LinkedList;
import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.UgroKitActivity;

public class MainActivity extends UgroKitActivity implements
Ugi.ConnectionStateListener,
UgiInventoryDelegate.InventoryTagChangedListener{


    public static String HOST_URL = "http://sushilshilpakar.com.np/smartrfid";
    static MainActivity instance;

    //public static String HOST_URL = "http://rfidproject.azurewebsites.net";
    //public static String HOST_URL = "http://70.125.157.25";

	public static Context appContext;
	private static String log ="MainActivity";
	private ActionBar actionbar;
	public static  final String TicketsTabName ="Tickets";
	public static final String NewItemsTabName ="Tag new Items";
	public static final String ReturnItemsTabName ="Return Items";
	public static final String ItemInfoTabName ="Item Info";
	public static final String RepairItemTabName ="Repair Items";
	private ItemService service;
	//public static String currentStatus;
	
	private TicketsFragment ticketsFragment;
	private NewItemFragment newItemsFragment;
	private ReturnItemFragment returnItemsFragment;
	private ItemInfoFragment itemInfoFragment;
	private RepairItemFragment repairItemFragment;
	
	private MyTabsListener<TicketsFragment> TabListenerTickets;
	private MyTabsListener<NewItemFragment> TabListenerNewItems;
	private MyTabsListener<ReturnItemFragment> TabListenerReturnItems;
	private MyTabsListener<ItemInfoFragment> TabListenerItemInfo;
	private MyTabsListener<RepairItemFragment> TabListenerRepairItem;



    public static MainActivity getInstance() {
        return instance;
    }

/**
 * init the Tabs 
 */
	public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 service= new ItemService();
	 instance=this;
     // setup action bar for tabs
	 actionbar = getActionBar();
	 actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
	 actionbar.setSubtitle(currentStatus);
	 actionbar.removeAllTabs();
     if (actionbar.getTabCount() == 0)
     {
    	 actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
         
         ActionBar.Tab TicketsTab = actionbar.newTab().setText(TicketsTabName);
         ActionBar.Tab NewItemsTab = actionbar.newTab().setText(NewItemsTabName);       
         ActionBar.Tab ReturnItemsTab = actionbar.newTab().setText(ReturnItemsTabName);
         ActionBar.Tab ItemInfoTab = actionbar.newTab().setText(ItemInfoTabName);
         ActionBar.Tab RepairItemTab = actionbar.newTab().setText(RepairItemTabName);
         
         TabListenerTickets = new MyTabsListener<TicketsFragment>(this, TicketsTabName, TicketsFragment.class);
         TicketsTab.setTabListener(TabListenerTickets);
        
         
         TabListenerNewItems = new MyTabsListener<NewItemFragment>(this, NewItemsTabName, NewItemFragment.class);
         NewItemsTab.setTabListener(TabListenerNewItems);
        
         
         TabListenerReturnItems = new MyTabsListener<ReturnItemFragment>(this, ReturnItemsTabName, ReturnItemFragment.class);
         ReturnItemsTab.setTabListener(TabListenerReturnItems);
         
         
         TabListenerItemInfo = new MyTabsListener<ItemInfoFragment>(this, ItemInfoTabName, ItemInfoFragment.class);
         ItemInfoTab.setTabListener(TabListenerItemInfo);
         
         TabListenerRepairItem = new MyTabsListener<RepairItemFragment>(this, RepairItemTabName, RepairItemFragment.class);
         RepairItemTab.setTabListener(TabListenerRepairItem);
         
         
         actionbar.addTab(TicketsTab);
         actionbar.addTab(NewItemsTab);
         actionbar.addTab(ReturnItemsTab);
         actionbar.addTab(ItemInfoTab);
         actionbar.addTab(RepairItemTab);
     }

     if (savedInstanceState != null)
     {
    	 actionbar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
     }
	}

	@Override
 	protected void onSaveInstanceState(Bundle outState)
 	{
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
 	}
	
	@Override
	/**
	 * If the connection from the ugrokit changes, this methode gives feedback to the user
	 */
	public void connectionStateChanged(Ugi.ConnectionStates connectionState) {
		/*if (connectionState == Ugi.ConnectionStates.NOT_CONNECTED) {
			Toast.makeText(this, "Not Connected + RFID started "+Ugi.singleton().getInStartInventory(), Toast.LENGTH_SHORT).show();
		} else if (connectionState == Ugi.ConnectionStates.CONNECTING) {
			Toast.makeText(this, "Connecting + RFID started "+Ugi.singleton().getInStartInventory(), Toast.LENGTH_SHORT).show();
		} else if (connectionState == Ugi.ConnectionStates.INCOMPATIBLE_READER) {
			Toast.makeText(this, "Incompatible + RFID started "+Ugi.singleton().getInStartInventory(), Toast.LENGTH_SHORT).show();
			
		} else { 
			if (connectionState == Ugi.ConnectionStates.CONNECTED)
			{
				Toast.makeText(this, "Connected + RFID started "+Ugi.singleton().getInStartInventory(), Toast.LENGTH_SHORT).show();
			}
		}*/
		// update the Status
		super.calculateStatus();
		notifiySatusUpdate();
	}


    public void updateIteminfo(Item i) {
        Tab currenttab = actionbar.getSelectedTab();
        if (currenttab.getText().equals(ItemInfoTabName)) {
            List<String> iteminfo = new LinkedList<String>();

            if (i!=null && i.getItemID()==null) {
                // if the tag is not yet known yet
                iteminfo.add("");
                iteminfo.add("No Item found for the scanned RFID");
                iteminfo.add("");
                iteminfo.add("");
                iteminfo.add("");
                iteminfo.add("");
                iteminfo.add("");
                iteminfo.add("");
                iteminfo.add("");
                iteminfo.add("");
                iteminfo.add("");
                iteminfo.add("");

            } else {
                // the item is known. Get the data
                iteminfo.add(String.valueOf(i.getItemID()));
                iteminfo.add(i.getItemName());
                iteminfo.add(i.getRFID());
                iteminfo.add(String.valueOf(i.getStatus()));
                iteminfo.add(i.getWarehouseLocation());
                iteminfo.add(i.getCategory());
                iteminfo.add(i.getSubCategory());
                iteminfo.add(i.getCreationDate());
                iteminfo.add(i.getType());
                iteminfo.add(i.getInventoryTotal());
                iteminfo.add(i.getInventoryOnHand());
                iteminfo.add(i.getInventoryOut());
            }
            // tell the view to display the data
            itemInfoFragment.displayInfo(iteminfo.get(0),iteminfo.get(1),iteminfo.get(2),iteminfo.get(3),iteminfo.get(4),
                    iteminfo.get(5),iteminfo.get(6),iteminfo.get(7),iteminfo.get(8),iteminfo.get(9),iteminfo.get(10), iteminfo.get(11) );
        }
        
        if (currenttab.getText().equals(ReturnItemsTabName))
        {
        	if(i!=null && i.getItemID() != null && i.getStatus().equals(ItemStatus.Staged)) {
          	  // if we found some infos for the tag, tell the view
              ((ReturnItemFragment)returnItemsFragment).returnItem(i);
             }
            else
            {
               if ( !i.getStatus().equals(ItemStatus.Staged))
                    Toast.makeText(this,"Item was not rented to a client",Toast.LENGTH_SHORT).show();
                else
                   Toast.makeText(this,"RFID tag not connected to an Item",Toast.LENGTH_SHORT).show();
            }
        }
        
        if (currenttab.getText().equals(RepairItemTabName))
        {
        	//Log.d("MainActivity", "before");
            ///Log.d("MainActivity", i.getStatus().toString());
            if (i !=null && i.getStatus().equals(ItemStatus.Returned))
            {
            	repairItemFragment.setItem(i);
                Log.d("MainActivity", "inside");
            	super.StopInventory();
            }
        }
    }
	@Override
	/**
	 * This methode is called as soon as the ugrokit discovers a new Tag. If found the same tag twice immediately after each other, this function is not called
	 * Has to be handled here, because Tabs implement Fragment and we need an activity
	 */
	public void inventoryTagChanged(UgiTag tag, boolean count) {
		
		returnItemsFragment = TabListenerReturnItems.getFragment();
		//ticketsFragment = TabListenerTickets.getFragment();
		//newItemsFragment = TabListenerNewItems.getFragment();
		itemInfoFragment = TabListenerItemInfo.getFragment();
		repairItemFragment = TabListenerRepairItem.getFragment();
		
		Log.d(log, "inventoryTagChanged");
		
		String currentTagId=null;
		
		Tab currenttab = actionbar.getSelectedTab();
		// if the iteminfo tab is currently open
		if (currenttab.getText().equals(ItemInfoTabName))
		{
			currentTagId = tag.getEpc().toString();
			
			// search information about the new tag
			Log.d(log, currentTagId);
			
			service.fetchItemFromRfid(tag, this, null);
            /*
                This method calls updateItemInfo();
             */
        }

			// if the current tab is the Return Item tab
		if (currenttab.getText().equals(ReturnItemsTabName)) {
            Log.d(log, "Return Items");
            // collect infos for the scanned tag
            currentTagId = tag.getEpc().toString();
            Log.d(log, currentTagId);
        
            // this calls updateItemInfo()
            service.fetchItemFromRfid(tag, this, null);
            
           /* Item item = service.fetchItemFromRfid(currentTagId, this);
          
            if(item != null) {
            	  // if we found some infos for the tag, tell the view
                ((ReturnItemFragment)returnItemsFragment).returnItem(item);
            }*/
        }
		
		if (currenttab.getText().equals(RepairItemTabName))
			{
			 	Log.d(log, "Repair Items");
	            // collect infos for the scanned tag
	            currentTagId = tag.getEpc().toString();
	            Log.d(log, currentTagId);
	            
	            service.fetchItemFromRfid(tag, this, null);
	            /*
	            Item item = service.fetchItemFromRfid(currentTagId, this);
	            
	            
	            if (item !=null && item.getStatus().equals(ItemStatus.Repair))
	            {
	            	repairItemFragment.setItem(item);
	            	super.StopInventory();
	            }*/
	            	
			}
	}
	/**
	 * Update the status bar
	 */
	public void notifiySatusUpdate()
	{
		if (actionbar!=null)
			actionbar.setSubtitle(currentStatus);
	}
}
