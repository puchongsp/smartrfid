package sushil.luc.ticket;

import java.sql.Date;
import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.msc.Client;

public class Ticket {

	private int TicketID;
	private Client TicketClient;
	private List<Item> Items;
	private TicketStatus Status;
	private Date CreationDate;
	
	public Ticket()
	{
		
	}
	
	private void fetchAllItems()
	{
		
	}
	
	private void PlanIndoorRoute()
	{
		
	}
	
	public int getTicketID() {
		return TicketID;
	}

	public void setTicketID(int ticketID) {
		TicketID = ticketID;
	}

	public Client getTicketClient() {
		return TicketClient;
	}

	public void setTicketClient(Client ticketClient) {
		TicketClient = ticketClient;
	}

	public List<Item> getItems() {
		return Items;
	}

	public void setItems(List<Item> items) {
		Items = items;
	}

	public TicketStatus getStatus() {
		return Status;
	}

	public void setStatus(TicketStatus status) {
		Status = status;
	}

	public Date getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(Date creationDate) {
		CreationDate = creationDate;
	}
	
}
