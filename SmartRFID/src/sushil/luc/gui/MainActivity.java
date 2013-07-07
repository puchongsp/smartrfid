package sushil.luc.gui;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		appContext =this;
		//ActionBar gets initiated
        actionbar = getActionBar();
      //Tell the ActionBar we want to use Tabs.
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      //initiating both tabs and set text to it.
        ActionBar.Tab TicketsTab = actionbar.newTab().setText(TicketsTabName);
        ActionBar.Tab NewItemsTab = actionbar.newTab().setText(NewItemsTabName);       
        ActionBar.Tab ReturnItemsTab = actionbar.newTab().setText(ReturnItemsTabName);
        ActionBar.Tab ItemInfoTab = actionbar.newTab().setText(ItemInfoTabName);
 
     //create the two fragments we want to use for display content
        Fragment TicketsFragment = new TicketsFragment();
        Fragment NewItemsFragment = new NewItemFragment();
        Fragment ReturnItemsFragment = new ReturnItemFragment();
        Fragment ItemInfoFragment = new ItemInfoFragment();
 
    //set the Tab listener. Now we can listen for clicks.
        
        TicketsTab.setTabListener(new MyTabsListener(TicketsFragment));
        NewItemsTab.setTabListener(new MyTabsListener(NewItemsFragment));
        ReturnItemsTab.setTabListener(new MyTabsListener(ReturnItemsFragment));
        ItemInfoTab.setTabListener(new MyTabsListener(ItemInfoFragment));
 
   //add the two tabs to the actionbar       
        actionbar.addTab(TicketsTab);
        actionbar.addTab(NewItemsTab);
        actionbar.addTab(ReturnItemsTab);
        actionbar.addTab(ItemInfoTab);
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
			
		}
	}

}
