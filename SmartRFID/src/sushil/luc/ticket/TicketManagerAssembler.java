package sushil.luc.ticket;

import android.content.Context;

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
	/**
	 * Order the Tickets according to their deliverydate. The tickets with the soonest delivery date will be first
	 */
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

	/**
	 * Fetches all the open tickets. Local or from the database according to the local parameter
	 * @param local
	 * @return
	 */
    public  List<Ticket> fetchTickets(boolean local)
    {
    	List<Ticket> alltickets;

    	if (local)
    		alltickets = ticketserv.fetchLocalTickets();
    	else
    		alltickets = ticketserv.fetchAllTickets(context);
    	
    	List<Ticket> tmp = new LinkedList<Ticket>();
    	
    	//  check if the tickets are really open
    	for (int i = 0;i<alltickets.size();i++)
    	{
    		Ticket t = alltickets.get(i);
    		if (t.getStatus().equals(TicketStatus.Open) )
    			tmp.add(t);
    	}
    	
    	// order tickets by deliverydate
    	ticketlist = orderTickets(tmp);
    	
    	return ticketlist;
    }
    
    public List<Item> getShortestRoute(Ticket t)
    {
    	// Not possible since the exacat locations in the warehouse is not maintained
    	return t.getItems();
    }
    
    /**
     * Save the ticket to the database
     * @param t
     */
    public void saveTicket (Ticket t)
    {
    	// update the database
    	ticketserv.saveToRemote(t);
    	
    	// remove the ticket from the local list
        if (t.getStatus().equals(TicketStatus.Checked))
            TicketService.Tickets.remove(t);
    }

}
