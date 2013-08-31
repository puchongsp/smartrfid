package sushil.luc.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ugrokit.api.Ugi;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.RFIDActivity;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.smartrfid.R;
import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketManagerAssembler;
import sushil.luc.ticket.TicketService;
import sushil.luc.ticket.TicketStatus;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Ticket_showItems extends UgroKitActivity{
	
	private ListView ItemList;
	private int positioninListview;
	private Ticket currentTicket;
	private Context myparent;
	private TicketManagerAssembler assembler ;
//	private ItemHistory itemHistory;
	private ActionBar actionbar;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ticket_show_items);
		
		myparent = this;
	//	itemHistory = ItemHistory.getInstance();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			positioninListview = extras.getInt("Position_InList",-1);
		 }
		
		if (positioninListview == -1)
			finish();
		else
		{
			 // init the action bar and assign the current status
			 actionbar = getActionBar();
			 actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
			 actionbar.setSubtitle(currentStatus);
			
			this.ItemList = (ListView)findViewById(R.id.ItemsList);
			
			assembler = new TicketManagerAssembler(myparent);
			
			currentTicket = TicketManagerAssembler.ticketlist.get(positioninListview);
			
			fillItems2List();
			
			this.ItemList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
					Intent i = new Intent(myparent, ShowItemDetails.class);
					i.putExtra("positionInItemListview", position);
					i.putExtra("positionInTicketList", positioninListview);
					startActivity(i);
				}
			});
		}
	}
	/**
	 * update the list with the items. Updates the colors
	 */
	public void fillItems2List ()
    {
		ItemList.setAdapter(null);
		
		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    	
    	String KEY_LABEL ="Big Text";
    	String KEY_HELP ="Help Text";
    	
    	// -- list item hash re-used
    	Map<String, String> group;
    	
    	List<Item> allItems = assembler.getShortestRoute(currentTicket);
    	
    	for (int i =0; i<allItems.size();i++)
    	{
    		Item tmp = allItems.get(i);
    		
    		group = new HashMap<String, String>();
        	
        	group.put( KEY_LABEL,  tmp.getItemID() );
        	group.put( KEY_HELP, "Desc: "+tmp.getItemName() );

        	groupData.add(group);
    	}
    	
    	// -- create an adapter, takes care of binding hash objects in our list to actual row views
    	MyItemListAdapter adapter = new MyItemListAdapter( this, groupData, android.R.layout.simple_list_item_2, 
    	                                                   new String[] { KEY_LABEL, KEY_HELP },
    	                                                   new int[]{ android.R.id.text1, android.R.id.text2 } , allItems);
    	ItemList.setAdapter(adapter);
    }
	
	/**
	 * Activate the rfid scanner and det the correct handler mode
	 */
	public void onResume()
	{
		super.onResume();
		super.StartInventory();
		//TODO keep changes local->done
		super.mHandler.modeTicketItemScan(true, currentTicket, this);
	}
	
	/**
	 * Save the ticket as soon as the worker wants to leave the screen
	 */
	public void onDestroy()
	{
		super.StopInventory();
		super.stopAllModes();
		super.calculateStatus();
		
		// check ticket again and update database
		currentTicket.calcTicketStatus();
		assembler.saveTicket(currentTicket);
		super.onDestroy();
	}
	
	public void onPause()
	{
		super.StopInventory();
		super.stopAllModes();
		super.calculateStatus();
		//assembler.saveTicket(currentTicket);
		super.onPause();
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


	
	
}
