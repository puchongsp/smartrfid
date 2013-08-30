package sushil.luc.gui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.Customer;
import sushil.luc.smartrfid.R;
import sushil.luc.ticket.Ticket;
import sushil.luc.ticket.TicketManagerAssembler;
import sushil.luc.utils.DateUtil;


public class TicketsFragment extends Fragment {
	 
	private static ListView TicketList;
	private static String MyLog ="TicketsFragment";
	private static Context myparent;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	View view = inflater.inflate(R.layout.tickets_fragment, container, false);
    	
    	myparent = container.getContext();

    	TicketList = (ListView) view.findViewById(R.id.TicketList);
    	
    	
    	TicketList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				

                Intent i = new Intent(myparent,Ticket_showItems.class);
				i.putExtra("Position_InList", position);
				startActivity(i);

			}
		});

    	return view;
    }
    
    public void onResume()
    {
    	super.onResume();
    	fillTickets2List(myparent);
    }

    
    private void fillTickets2List (Context parent)
    {
    	TicketList.setAdapter(null);
    	
    	List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    	
    	String KEY_LABEL ="Big Text";
    	String KEY_HELP ="Help Text";
    	
    	// -- list item hash re-used
    	Map<String, String> group;
    	
    	TicketManagerAssembler assembler = new TicketManagerAssembler(getActivity().getApplicationContext());
    	
    	List<Ticket> alltickets = assembler.fetchTickets(false);
    	
    	for (int i =0; i<alltickets.size();i++)
    	{
        	group = new HashMap<String, String>();
        	
        	Ticket tmp = alltickets.get(i);
        	
        	Customer c = tmp.getTicketCustomer();
        	List<Item> items = tmp.getItems();
        	
        	int collected = 0;
        	
        	for (Item tmpItem :items)
        	{
        		if (tmpItem.getStatus()!=null && tmpItem.getStatus().equals(ItemStatus.Checked))
        			collected++;
        	}	
        	
        	String deliverydate = DateUtil.DatetoString(tmp.getDeliveryDate());
        	
        	group.put( KEY_LABEL, "Ticket "+ tmp.getTicketID() );
        	group.put( KEY_HELP, "Items "+collected+"/"+items.size()+" | "+deliverydate+" | Client "+c.getName() );


        	groupData.add(group);
    	}
    	
    	// -- create an adapter, takes care of binding hash objects in our list to actual row views
    	MyTicketListAdapter adapter = new MyTicketListAdapter( parent, groupData, android.R.layout.simple_list_item_2, 
    	                                                   new String[] { KEY_LABEL, KEY_HELP },
    	                                                   new int[]{ android.R.id.text1, android.R.id.text2 } , alltickets);
    	TicketList.setAdapter(adapter);
    }
    
    public static void updateView()
    {
    	TicketList.setAdapter(null);
    	
    	List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    	
    	String KEY_LABEL ="Big Text";
    	String KEY_HELP ="Help Text";
    	
    	// -- list item hash re-used
    	Map<String, String> group;
    	
    	TicketManagerAssembler assembler = new TicketManagerAssembler(myparent);
    	
    	List<Ticket> alltickets = assembler.fetchTickets(true);
    	Log.d("Ticketsfragment", ""+alltickets.size());
    	
    	for (int i =0; i<alltickets.size();i++)
    	{
        	group = new HashMap<String, String>();
        	
        	Ticket tmp = alltickets.get(i);
        	
        	Customer c = tmp.getTicketCustomer();
        	List<Item> items = tmp.getItems();
        	
        	int collected = 0;
        	
        	for (Item tmpItem :items)
        	{
        		if (tmpItem.getStatus()!=null && tmpItem.getStatus().equals(ItemStatus.Checked))
        			collected++;
        	}	
        	
        	String deliverydate = DateUtil.DatetoString(tmp.getDeliveryDate());
        	
        	group.put( KEY_LABEL, "Ticket "+ tmp.getTicketID() );
        	group.put( KEY_HELP, "Items "+collected+"/"+items.size()+" | "+deliverydate+" | Client "+c.getName() );


        	groupData.add(group);
    	}
    	
    	// -- create an adapter, takes care of binding hash objects in our list to actual row views
    	MyTicketListAdapter adapter = new MyTicketListAdapter( myparent, groupData, android.R.layout.simple_list_item_2, 
    	                                                   new String[] { KEY_LABEL, KEY_HELP },
    	                                                   new int[]{ android.R.id.text1, android.R.id.text2 } , alltickets);
    	TicketList.setAdapter(adapter);
    }

}
