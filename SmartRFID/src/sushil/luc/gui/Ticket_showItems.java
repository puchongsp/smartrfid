package sushil.luc.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.RFIDActivity;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.smartrfid.R;
import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketManagerAssembler;
import sushil.luc.ticket.TicketStatus;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Ticket_showItems extends UgroKitActivity{
	
	private ListView ItemList;
	private int positioninListview;
	private Ticket currentTicket;
	private Context myparent;
	private TicketManagerAssembler assembler ;
	
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
			this.ItemList = (ListView)findViewById(R.id.ItemsList);
			
			assembler = new TicketManagerAssembler(myparent);
			
			currentTicket = TicketManagerAssembler.ticketlist.get(positioninListview);
			
			fillItems2List();
			
			this.ItemList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//Toast.makeText(myparent, "Hallo", Toast.LENGTH_LONG).show();
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
        	
        	group.put( KEY_LABEL,  tmp.getItemName() );
        	group.put( KEY_HELP, "Location "+tmp.getWarehouseLocation() );


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
		super.mHandler.modeTicketItemScan(true, currentTicket, this);
	}
	
	/**
	 * Save the ticket as soon as the worker wants to leave the screen
	 */
	public void onDestroy()
	{
		assembler.saveTicket(currentTicket);
		super.onDestroy();
	}
	/**
	 * Checks if there are any items left which need to be collected
	 */
	public void evalTicket()
	{
		List<Item> items =currentTicket.getItems();
		
		boolean close =true;
		boolean partial =false;
		
		for (int i= 0; i<items.size();i++)
		{
			if (items.get(i).getStatus().equals(ItemStatus.Collected))
			{
				close = close && true;
				partial = partial || true;
			}
			else
			{
				close = close && false;
				partial = partial || false;
			}
		}
		
		// set the result to the ticket
		if (close)
			currentTicket.setStatus(TicketStatus.Closed);
		else
		{
			if(partial)
				currentTicket.setStatus(TicketStatus.InProgress);
			else
				currentTicket.setStatus(TicketStatus.Open);
		}
	}
}
