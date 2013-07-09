package sushil.luc.ticket;

import android.content.Context;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import sushil.luc.item.Item;

public class TicketManagerAssembler implements TicketManager{
	
	public static List<Ticket> ticketlist;
	
    private Context context;
    private TicketService ticketserv;

    public TicketManagerAssembler(Context context){
        this.context = context;
        ticketserv = new TicketService();
    }

	@Override
	public List<Ticket> orderTickets(List<Ticket> alltickets) {
		Comparator<Ticket> comp = new Comparator<Ticket>() {			
			@Override
			public int compare(Ticket lhs, Ticket rhs) {
				return lhs.getDeliveryDate().compareTo(rhs.getDeliveryDate());
			}
		};
    	
    	Collections.sort(alltickets,comp);
    	return alltickets;
	}


    public  List<Ticket> fetchTickets()
    {
    	List<Ticket> alltickets = ticketserv.fetchAllTickets(context);
    	
    	List<Ticket> tmp = new LinkedList<Ticket>();
    	
    	for (int i = 0;i<alltickets.size();i++)
    	{
    		Ticket t = alltickets.get(i);
    		if (!t.getStatus().equals(TicketStatus.Closed))
    			tmp.add(t);
    	}
    	
    	ticketlist = orderTickets(tmp);
    	
    	return ticketlist;
    }
    
    public List<Item> getShortestRoute(Ticket t)
    {
    	return t.getItems();
    }
    
    public void saveTicket (Ticket t)
    {
    	this.ticketserv.saveToRemote(t);
    }

}