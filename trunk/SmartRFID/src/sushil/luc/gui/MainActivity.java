package sushil.luc.gui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.LinkedList;
import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.msc.RFIDActivity;
import sushil.luc.smartrfid.R;

public class MainActivity extends RFIDActivity {
	
	public static Context appContext;
	private static String log ="MainActivity";
	private ActionBar actionbar;
	private final String TicketsTabName ="Tickets";
	private final String NewItemsTabName ="Tag new Items";
	private final String ReturnItemsTabName ="Return Items";
	private final String ItemInfoTabName ="Item Info";
	private ItemService service;
	
	private TicketsFragment ticketsFragment;
	private NewItemFragment newItemsFragment;
	private ReturnItemFragment returnItemsFragment;
	private ItemInfoFragment itemInfoFragment;
	
	private MyTabsListener<TicketsFragment> TabListenerTickets;
	private MyTabsListener<NewItemFragment> TabListenerNewItems;
	private MyTabsListener<ReturnItemFragment> TabListenerReturnItems;
	private MyTabsListener<ItemInfoFragment> TabListenerItemInfo;

	

	protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 
	 service= new ItemService();
	 
     // setup action bar for tabs
	 actionbar = getActionBar();
	 actionbar.removeAllTabs();
     if (actionbar.getTabCount() == 0)
     {
    	 actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
         
         ActionBar.Tab TicketsTab = actionbar.newTab().setText(TicketsTabName);
         ActionBar.Tab NewItemsTab = actionbar.newTab().setText(NewItemsTabName);       
         ActionBar.Tab ReturnItemsTab = actionbar.newTab().setText(ReturnItemsTabName);
         ActionBar.Tab ItemInfoTab = actionbar.newTab().setText(ItemInfoTabName);
         
         TabListenerTickets = new MyTabsListener<TicketsFragment>(this, TicketsTabName, TicketsFragment.class);
         TicketsTab.setTabListener(TabListenerTickets);
        
         
         TabListenerNewItems = new MyTabsListener<NewItemFragment>(this, NewItemsTabName, NewItemFragment.class);
         NewItemsTab.setTabListener(TabListenerNewItems);
        
         
         TabListenerReturnItems = new MyTabsListener<ReturnItemFragment>(this, ReturnItemsTabName, ReturnItemFragment.class);
         ReturnItemsTab.setTabListener(TabListenerReturnItems);
         
         
         TabListenerItemInfo = new MyTabsListener<ItemInfoFragment>(this, ItemInfoTabName, ItemInfoFragment.class);
         ItemInfoTab.setTabListener(TabListenerItemInfo);
         
         
         actionbar.addTab(TicketsTab);
         actionbar.addTab(NewItemsTab);
         actionbar.addTab(ReturnItemsTab);
         actionbar.addTab(ItemInfoTab);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onNewIntent(Intent intent) {
		returnItemsFragment = TabListenerReturnItems.getFragment();
		ticketsFragment = TabListenerTickets.getFragment();
		newItemsFragment = TabListenerNewItems.getFragment();
		itemInfoFragment = TabListenerItemInfo.getFragment();
		
		Tab currenttab = actionbar.getSelectedTab();
		if (currenttab.getText().equals(ItemInfoTabName))
		{
			Log.d(log, "onNewIntent");
			TagId = getTagId(intent);
			Log.d(log, TagId);
			Item i = service.getItemInfo(TagId);
			List<String> iteminfo = new LinkedList<String>();
			
	    	if (i ==null)
	    	{
	    		iteminfo.add("");
	    		iteminfo.add("No Item found for the scanned RFID");
	    		iteminfo.add("");
	    		iteminfo.add("");
	    		iteminfo.add("");
	    	}
	    	else
	    	{
	    		iteminfo.add(i.getItemID());
	    		iteminfo.add(i.getItemName());
	    		iteminfo.add(i.getRFID());
	    		iteminfo.add(i.getStatus().toString());
	    		iteminfo.add(i.getWarehouseLocation());
	    	}
			
	    	
			itemInfoFragment.displayInfo(iteminfo.get(0),iteminfo.get(1),iteminfo.get(2),iteminfo.get(3),iteminfo.get(4));
        } else if (currenttab.getText().equals(ReturnItemsTabName)) {
            Log.d(log, "Return Items");
            TagId = getTagId(intent);
            Log.d(log, TagId);
            Item item = service.getItemInfo(TagId);
            if(item != null) {
                ((ReturnItemFragment)returnItemsFragment).returnItem(item);
            }
        }
	}

}
