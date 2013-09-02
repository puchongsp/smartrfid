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

    int LIMIT = 10;
    private final String URL = MainActivity.HOST_URL + "/api/orders/query.php?limit="+LIMIT ;

    public static List<Ticket> Tickets;


    /**
     * Create a new instance of the Ticketservice
     */
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

/**
 * get all the open local tickets
 * @return
 */
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
        
        final int limit = LIMIT;
        
        if (Tickets.size()< limit)
        {
            Tickets.clear();
        final NetworkHandler networkHandler = NetworkHandler.getInstance();
        networkHandler.setContext(context);

        // First get order then get ticket by id
        final List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();

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

/**
 * Checks if the given dto file is already on in our local ticket list
 * @param myTicketDTO
 * @return
 */
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
     * Saves the Status of the ticket to the database
     * @param t
     */
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
