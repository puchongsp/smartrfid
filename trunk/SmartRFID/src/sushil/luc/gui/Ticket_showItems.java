package sushil.luc.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.RFIDActivity;
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

public class Ticket_showItems extends RFIDActivity{
	
	private ListView ItemList;
	private int positioninListview;
	private Ticket currentTicket;
	private Context myparent;
	private TicketManagerAssembler assembler ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
			
			fillItems2List(this);
			
			this.ItemList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//Toast.makeText(myparent, "Hallo", Toast.LENGTH_LONG).show();
					Intent i = new Intent(myparent,ShowItemDetails.class);
					i.putExtra("positionInItemListview", position);
					i.putExtra("positionInTicketList", positioninListview);
					startActivity(i);
				}
			});
		}
	}
	
	private void fillItems2List (Context con)
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
    	MyItemListAdapter adapter = new MyItemListAdapter( con, groupData, android.R.layout.simple_list_item_2, 
    	                                                   new String[] { KEY_LABEL, KEY_HELP },
    	                                                   new int[]{ android.R.id.text1, android.R.id.text2 } , allItems);
    	ItemList.setAdapter(adapter);
    }
	
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		boolean result =currentTicket.checkRFIDInTicket(super.TagId);
		
		if (result)
		{
			evalTicket();
			fillItems2List(this);
		}
		else
		{
			Toast.makeText(this, "Item not in the Ticket", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void onDestroy()
	{
		assembler.saveTicket(currentTicket);
		super.onDestroy();
	}
	
	private void evalTicket()
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