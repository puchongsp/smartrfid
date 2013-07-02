package sushil.luc.gui;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.Customer;
import sushil.luc.smartrfid.R;
import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketManagerAssembler;
import sushil.luc.ticket.TicketService;
import sushil.luc.ticket.TicketStatus;
import sushil.luc.utils.DateUtil;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TicketsFragment extends Fragment {
	 
	private ListView TicketList;
	private static String MyLog ="TicketsFragment";
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	
    	 View view = inflater.inflate(R.layout.tickets_fragment, container, false);
    	
    	this.TicketList = (ListView) view.findViewById(R.id.TicketList);
    	
    	fillTickets2List(container);
    	
    	return view;
    }

    
    private void fillTickets2List (ViewGroup parent)
    {
    	List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    	
    	String KEY_LABEL ="Big Text";
    	String KEY_HELP ="Help Text";
    	
    	// -- list item hash re-used
    	Map<String, String> group;
    	
    	TicketManagerAssembler assembler = new TicketManagerAssembler();
    	
    	List<Ticket> alltickets = assembler.fetchTickets();
    	
    	for (int i =0; i<alltickets.size();i++)
    	{
        	group = new HashMap<String, String>();
        	
        	Ticket tmp = alltickets.get(i);
        	
        	Customer c = tmp.getTicketCustomer();
        	List<Item> items = tmp.getItems();
        	
        	int collected = 0;
        	
        	for (Item tmpItem :items)
        	{
        		if (tmpItem.getStatus().equals(ItemStatus.Collected))
        			collected++;
        	}	
        	
        	String deliverydate = DateUtil.DatetoString(tmp.getDeliveryDate());
        	
        	group.put( KEY_LABEL, "Ticket "+ tmp.getTicketID() );
        	group.put( KEY_HELP, "Items "+collected+"/"+items.size()+" | "+deliverydate+" | Client "+c.getName() );


        	groupData.add(group);
    	}
    	
    	// -- create an adapter, takes care of binding hash objects in our list to actual row views
    	SimpleAdapter adapter = new SimpleAdapter( parent.getContext(), groupData, android.R.layout.simple_list_item_2, 
    	                                                   new String[] { KEY_LABEL, KEY_HELP },
    	                                                   new int[]{ android.R.id.text1, android.R.id.text2 } );
    	TicketList.setAdapter(adapter);
    	
    	colorListItems(alltickets);
    }
    
    private void colorListItems(List<Ticket> alltickets)
    {
    	Log.d(MyLog, "");
    	
    	for (int i =0; i<TicketList.getChildCount();i++)
    	{
    		Log.d(MyLog, "inside loop");
    		View tmp =TicketList.getChildAt(i);
    		
    		TicketStatus status = alltickets.get(i).getStatus();

    		switch (status)
    		{
    			case Closed:
    				tmp.setBackgroundResource(Color.GREEN);
    				Log.d(MyLog, "Closed == Green");
    				break;
    			case InProgress:
    				tmp.setBackgroundResource(Color.YELLOW);
    				Log.d(MyLog, "InProgress == Yellow");
    				break;
    			case Open:
    				tmp.setBackgroundResource(Color.RED);
    				Log.d(MyLog, "Open == RED");
    				break;
    		}
    		
    		tmp.refreshDrawableState();
    	}
    	
    	TicketList.refreshDrawableState();
    }
}
