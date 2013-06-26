package sushil.luc.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sushil.luc.database.RemoteDBService;
import sushil.luc.msc.Client;
import sushil.luc.utils.DateUtil;

public class TicketService {

	public TicketService()
	{

	}
	
	public List<Ticket> fetchAllTickets()
	{
        RemoteDBService dbService = new RemoteDBService();
        String sql = "SELECT * FROM tickets WHERE status = 0 and date = '26-06-2013'";


        //List<Ticket> tickets = (List<Ticket>)(List<?>)dbService.select(sql);
        List<Ticket> tickets = new ArrayList<Ticket>();
        List<HashMap<String,String>> rawTickets = dbService.select(sql);
        for(HashMap<String, String> rawTicket : rawTickets) {
            tickets.add(convertToTicket(rawTicket));
        }

        return tickets;
	}
	
	public void saveToRemote(int TicketID)
	{
		
	}

    private Ticket convertToTicket(HashMap<String, String> map) {
        Ticket ticket = new Ticket();
        ticket.setTicketID(Long.valueOf(map.get("id")));
        ticket.setStatus(TicketStatus.valueOf(map.get("status")));
        ticket.setTicketClient(new Client()); //TODO: shall we merge Client info into the ticket itself, just for simplification
        ticket.setCreationDate(DateUtil.stringToDate(map.get("created_date")));

        return ticket;
    }
}
