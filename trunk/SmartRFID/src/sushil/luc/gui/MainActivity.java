package sushil.luc.gui;

import sushil.luc.smartrfid.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.Menu;

public class MainActivity extends Activity {
	
	public static Context appContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		appContext =this;
		//ActionBar gets initiated
        ActionBar actionbar = getActionBar();
      //Tell the ActionBar we want to use Tabs.
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      //initiating both tabs and set text to it.
        ActionBar.Tab TicketsTab = actionbar.newTab().setText("Tickets");
        ActionBar.Tab NewItemsTab = actionbar.newTab().setText("Tag new Items");       
        ActionBar.Tab ReturnItemsTab = actionbar.newTab().setText("Return Items");
 
     //create the two fragments we want to use for display content
        Fragment TicketsFragment = new TicketsFragment();
        Fragment NewItemsFragment = new NewItemFragment();
        Fragment ReturnItemsFragment = new ReturnItemFragment();
 
    //set the Tab listener. Now we can listen for clicks.
        
        TicketsTab.setTabListener(new MyTabsListener(TicketsFragment));
        NewItemsTab.setTabListener(new MyTabsListener(NewItemsFragment));
        ReturnItemsTab.setTabListener(new MyTabsListener(ReturnItemsFragment));
 
   //add the two tabs to the actionbar       
        actionbar.addTab(TicketsTab);
        actionbar.addTab(NewItemsTab);
        actionbar.addTab(ReturnItemsTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
