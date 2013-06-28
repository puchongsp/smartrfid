package sushil.luc.ticket;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.Client;

public class Ticket {

	private long TicketID;
	private Client TicketClient;
	private List<Item> Items;
	private TicketStatus Status;
	private Date CreationDate;
	
	public Ticket()
	{
		this.Items = new LinkedList<Item>();
		fetchAllItems();
	}
	
	private void fetchAllItems()
	{
		
	}
	
	/**
	 * Checks if the given RFID is contained in this ticket 
	 * @param check
	 * @return true if the RFID is contained, false if not
	 */
	private boolean checkItem(String check)
	{
		boolean result = false;
		
		int i =0;
		while (i<this.Items.size() && !result)
		{
			if (this.Items.get(i).getRFID().equals(check))
			{
				result =true;
				this.Items.get(i).setStatus(ItemStatus.COLLECTED);
			}
			i++;
		}
		
		return result;
	}
	

	/**
	 * Checks if the given RFID is contained in the current Itemlist of the Ticket
	 * @param RFID
	 */
	public void checkRFIDInTicket (String RFID)
	{
		if (checkItem(RFID))
		{
			System.out.println("Positive Feedback");
		}
		else
		{
			System.out.println("Negative Feedback");
		}
	}
	
	
	public long getTicketID() {

		return TicketID;
	}

	public void setTicketID(Long ticketID) {
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
