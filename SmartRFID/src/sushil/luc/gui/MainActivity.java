package sushil.luc.gui;

import java.util.LinkedList;
import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemService;
import sushil.luc.msc.RFIDActivity;
import sushil.luc.smartrfid.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends RFIDActivity {
	
	public static Context appContext;
	private static String log ="MainActivity";
	private ActionBar actionbar;
	private final String TicketsTabName ="Tickets";
	private final String NewItemsTabName ="Tag new Items";
	private final String ReturnItemsTabName ="Return Items";
	private final String ItemInfoTabName ="Item Info";
	private ItemService service;
	
	private Fragment ticketsFragment;
	private Fragment newItemsFragment;
	private Fragment returnItemsFragment;
	private ItemInfoFragment itemInfoFragment;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		appContext =this;
		//ActionBar gets initiated
        actionbar = getActionBar();
      //Tell the ActionBar we want to use Tabs.
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      //initiating the tabs and set text to it.
        ActionBar.Tab TicketsTab = actionbar.newTab().setText(TicketsTabName);
        ActionBar.Tab NewItemsTab = actionbar.newTab().setText(NewItemsTabName);       
        ActionBar.Tab ReturnItemsTab = actionbar.newTab().setText(ReturnItemsTabName);
        ActionBar.Tab ItemInfoTab = actionbar.newTab().setText(ItemInfoTabName);
 
     //create the fragments we want to use for display content
        ticketsFragment = new TicketsFragment();
        newItemsFragment = new NewItemFragment();
        returnItemsFragment = new ReturnItemFragment();
        itemInfoFragment = new ItemInfoFragment();
 
    //set the Tab listener. Now we can listen for clicks.
        
        TicketsTab.setTabListener(new MyTabsListener(ticketsFragment));
        NewItemsTab.setTabListener(new MyTabsListener(newItemsFragment));
        ReturnItemsTab.setTabListener(new MyTabsListener(returnItemsFragment));
        ItemInfoTab.setTabListener(new MyTabsListener(itemInfoFragment));
 
   //add the tabs to the actionbar       
        actionbar.addTab(TicketsTab);
        actionbar.addTab(NewItemsTab);
        actionbar.addTab(ReturnItemsTab);
        actionbar.addTab(ItemInfoTab);
        
        
        service = new ItemService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onNewIntent(Intent intent) {

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
		}
	}

}
