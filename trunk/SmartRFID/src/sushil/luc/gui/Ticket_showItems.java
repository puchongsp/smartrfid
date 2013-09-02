package sushil.luc.gui;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ugrokit.api.Ugi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sushil.luc.item.Item;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.smartrfid.R;
import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketManagerAssembler;

public class Ticket_showItems extends UgroKitActivity{
	
	private ListView ItemList;
	private int positioninListview;
	private Ticket currentTicket;
	private Context myparent;
	private TicketManagerAssembler assembler ;
	private ActionBar actionbar;
    private MyItemListAdapter adapter;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ticket_show_items);
		
		myparent = this;
		
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
			
			// get the selected ticket
			currentTicket = TicketManagerAssembler.ticketlist.get(positioninListview);
			
			// update the item list
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
    	
    	Map<String, String> group;
    	
    	// order the items according to their position in the warehouse
    	List<Item> allItems = assembler.getShortestRoute(currentTicket);
    	
    	for (int i =0; i<allItems.size();i++)
    	{
    		Item tmp = allItems.get(i);
    		
    		group = new HashMap<String, String>();
        	
        	group.put( KEY_LABEL,  tmp.getItemID() );
        	group.put( KEY_HELP, "Desc: "+tmp.getItemName() );

        	groupData.add(group);
    	}
    	
    	// a item status sensitif adapter. Maps status to color
    	adapter = new MyItemListAdapter( this, groupData, android.R.layout.simple_list_item_2,
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
		super.mHandler.modeTicketItemScan(true, currentTicket, this);

        if(adapter!=null)
            adapter.notifyDataSetChanged();
	}
	
	/**
	 * Save the ticket as soon as the worker wants to leave the screen
	 */
	public void onDestroy()
	{
        // check ticket again and update database
        currentTicket.calcTicketStatus();
        assembler.saveTicket(currentTicket);
        
        // stop all modes
        super.StopInventory();
		super.stopAllModes();
		super.calculateStatus();
		

		super.onDestroy();
	}
	
	public void onPause()
	{
		super.StopInventory();
		super.stopAllModes();
		super.calculateStatus();
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
