package sushil.luc.ticket;

import android.content.Context;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TicketManagerAssembler implements TicketManager{

    Context context;

    private TicketManagerAssembler(){

    }

    public TicketManagerAssembler(Context context){
        this.context = context;
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
    	TicketService ticketserv = new TicketService();
    	
    	List<Ticket> alltickets = ticketserv.fetchAllTickets(context);
    	
    	return orderTickets(alltickets);
    }

}
