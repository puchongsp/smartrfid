package sushil.luc.ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.Customer;

public class Ticket {

    /*
     * to map models vars with fields in json
     */
    public static final String _ID = "id";
    public static final String _CUSTOMER = "customer";
    public static final String _ITEMS = "items";
    public static final String _STATUS = "status";
    public static final String _CREATED_DATE = "created_date";
    public static final String _DELIVERY_DATE = "delivery_date";


	private String TicketID;
	private Customer TicketCustomer;
	private List<Item> Items;
	private TicketStatus Status;
	private Date CreationDate;
	private Date DeliveryDate;
	
	public Ticket()
	{
		this.Items = new ArrayList<Item>();
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
			String rfid = this.Items.get(i).getRFID();
			if (rfid!=null && rfid.equals(check))
			{
				result =true;
				this.Items.get(i).setStatus(ItemStatus.Collected);
			}
			i++;
		}
		
		return result;
	}
	

	/**
	 * Checks if the given RFID is contained in the current Itemlist of the Ticket
	 * @param RFID
	 */
	public boolean checkRFIDInTicket (String RFID)
	{
		boolean res= checkItem(RFID);
		if (res)
		{
			calcTicketStatus ();
		}
		
		return res;
	}
	
	
	private void calcTicketStatus ()
	{
		boolean res =true;
		for(int i=0; i<this.Items.size();i++)
		{
			Item tmp = this.Items.get(i);
			if (tmp.getStatus().equals(ItemStatus.Collected))
			{
				res = res && true;
			}
			else
			{
				res = res && false;
			}
		}
		
		if (res)
		{
			this.setStatus(TicketStatus.Closed);
		}
	}
	public String getTicketID() {

		return TicketID;
	}

	public void setTicketID(String ticketID) {
		TicketID = ticketID;
	}

	public Customer getTicketCustomer() {
		return TicketCustomer;
	}

	public void setTicketCustomer(Customer ticketCustomer) {
		TicketCustomer = ticketCustomer;
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


	public Date getDeliveryDate() {
		return DeliveryDate;
	}


	public void setDeliveryDate(Date deliveryDate) {
		DeliveryDate = deliveryDate;
	}
	
}
