package sushil.luc.ticket;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sushil.luc.dtos.OrderDTO;
import sushil.luc.dtos.TicketDTO;
import sushil.luc.gui.MainActivity;
import sushil.luc.gui.TicketsFragment;
import sushil.luc.network.Callback;
import sushil.luc.network.NetworkHandler;
import sushil.luc.network.SimpleGetTask;

public class TicketService {

  //  private final String URL = "http://rfidproject.azurewebsites.net/api/orders/query?limit=5";

    private final String URL = MainActivity.HOST_URL + "/api/orders/query.php?limit=5";

    public static List<Ticket> Tickets;


	public TicketService()
	{
		if (Tickets!=null)
		{

		}
		else
		{
            Tickets = new ArrayList<Ticket>();
		}
	}


	public List<Ticket> fetchLocalTickets()
	{
		return Tickets;
	}
    /**
     * This function will fetch json data from exposed REST webservice
     * and return after converting it to Ticket model
     * @return
     */
	public List<Ticket> fetchAllTickets(final Context context) {
        Log.i("TS:", "Fetching tickets");
        
        final int limit = 5;
        
        if (Tickets.size()< limit)
        {
            Tickets.clear();
        final NetworkHandler networkHandler = NetworkHandler.getInstance();
        networkHandler.setContext(context);

        // First get order then get ticket by id
        final List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
        //final List<TicketDTO> ticketDTOList = new ArrayList<TicketDTO>();

        try {
            Log.i("TS:","url = "+URL);
            networkHandler.readList(URL,OrderDTO[].class, new Callback<List<OrderDTO>>() {
                @Override
                public void callback(final List<OrderDTO> myOrderDTOList) {
                    Log.i("TS:", "size of orders = "+myOrderDTOList.size());
                    orderDTOList.addAll(myOrderDTOList);

                    for(final OrderDTO orderDto:orderDTOList) {

                      //  String ticketsUrl = "http://rfidproject.azurewebsites.net/api/tickets/query?limit="+limit+"&skip=0&orderBy=0&filters=0&addRfids=1&identifiers="+orderDto.getIdentifier(); //identifier id dticketid

                        String ticketsUrl = MainActivity.HOST_URL + "/api/tickets/query.php?limit="+limit+"&identifiers="+orderDto.getIdentifier(); //identifier id dticketid
                        Log.i("TS",ticketsUrl);
                        try {
                            networkHandler.read(ticketsUrl,TicketDTO.class, new Callback<TicketDTO>() {
                                @Override
                                public void callback(final TicketDTO myTicketDTO) {
                                	if (myTicketDTO.getItems()!=null)
                                		Log.i("TS:", "size of items in ticket = "+myTicketDTO.getItems().size());
                                    //ticketDTOList.add(myTicketDTO);
                                    // map each orderdto,ticketdto to Tickets model
                                    if(!doesTicketExist(myTicketDTO)) {
                                        Tickets.add(new Ticket(myTicketDTO, orderDto));
                                        TicketsFragment.updateView();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(context, "Could not connect. Please check your connection.", Toast.LENGTH_LONG).show();
                        }          
                    }     
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Could not connect. Please check your connection.", Toast.LENGTH_LONG).show();
        }
        }

        return Tickets;
	}

/*	
	public void saveToRemote(Ticket t)
	{
		int i =0;
		boolean found=false;
		while ( (i< Tickets.size()) && !(found))
		{
			if (Tickets.get(i).getTicketID().equals(t.getTicketID()))
				found =true;
			else
				i++;
		}
		if (found)
		{
			Tickets.set(i, t);
		}
	}*/

    public boolean doesTicketExist(TicketDTO myTicketDTO) {
        boolean ticketExists = false;
        for(Ticket ticket : Tickets) {
            if(ticket.getTicketID().equals(String.valueOf(myTicketDTO.getIdentifier()))){
                ticketExists = true;
                break;
            }
        }
        return ticketExists;
    }
    
    /**
     * Sets all the Items from the Ticket to picked up
     * @return false if there was a problem, true if everything was fine
     */
    /*public boolean ticketCollected (Ticket t, Context context)
    {
    	final NetworkHandler networkHandler = NetworkHandler.getInstance();
        networkHandler.setContext(context);
        
        boolean res = networkHandler.updateTicketFullyCollected(t);
        
    	return res;
    }*/
    
    public void saveToRemote(Ticket t)
    {
    	SimpleGetTask gettask;
    	switch (t.getStatus())
    	{
		    	case Open:
		    		 gettask = new SimpleGetTask(t, SimpleGetTask.TicketAvailable);
		    		gettask.execute();
		    		break;
		    	case Checked:
		    		gettask = new SimpleGetTask(t, SimpleGetTask.TicketChecked);
		    		gettask.execute();
		    		break;
		    	case Closed:
		    		gettask = new SimpleGetTask(t, SimpleGetTask.TicketReturned);
		    		gettask.execute();
		    		break;
		    	case Staged:
		    		gettask = new SimpleGetTask(t, SimpleGetTask.TicketStaged);
		    		gettask.execute();
		    		break;
    	}
    }

}
