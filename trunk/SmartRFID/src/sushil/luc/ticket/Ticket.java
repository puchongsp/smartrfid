package sushil.luc.ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sushil.luc.dtos.CustomerDTO;
import sushil.luc.dtos.ItemDTO;
import sushil.luc.dtos.OrderDTO;
import sushil.luc.dtos.TicketDTO;
import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.Customer;

public class Ticket {

    /*
     * to map models vars with fields in json
     */
  /*  public static final String _ID = "id";
    public static final String _CUSTOMER = "customer";
    public static final String _ITEMS = "items";
    public static final String _STATUS = "status";
    public static final String _CREATED_DATE = "created_date";
    public static final String _DELIVERY_DATE = "delivery_date";*/


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

    public Ticket(TicketDTO ticketDTO, OrderDTO orderDTO){
        this.Items = new ArrayList<Item>();
        this.TicketID = String.valueOf(ticketDTO.getIdentifier());

        CustomerDTO customerDto = orderDTO.getCustomer();
        Customer customer = new Customer(customerDto, orderDTO.getInfo());

        this.TicketCustomer = customer;
        
        // Call the method  calcTicketStatus()
        //this.Status = TicketStatus.Open; // Setting default ticket status;
        
        this.CreationDate = new Date();
        this.DeliveryDate = new Date();

        List<ItemDTO> itemDtos = ticketDTO.getItems();
        
        if (itemDtos!=null)
        {
	        for(ItemDTO itemDto:itemDtos){
	            Item tmp = new Item(itemDto);
	            Items.add(tmp);
	        }
        }
        
        calcTicketStatus();
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
				this.Items.get(i).setStatus(ItemStatus.Checked);
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
	
	
	public void calcTicketStatus ()
	{
		boolean res =true;
	//	boolean onecollected =false;
		for(int i=0; i<this.Items.size();i++)
		{
			Item tmp = this.Items.get(i);
			if (tmp.getStatus().equals(ItemStatus.Checked))
			{
				res = res && true;
	//			onecollected =true;
			}
			else
			{
				res = res && false;
			}
		}
		
		if (res)
		{
			this.setStatus(TicketStatus.Checked);
		}
		else
			//if (onecollected)
			//	this.setStatus(TicketStatus.InProgress);
			//else
				this.setStatus(TicketStatus.Open);
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
