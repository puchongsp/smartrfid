package sushil.luc.ticket;

import com.ugrokit.api.UgiTag;

import java.util.ArrayList;
import java.util.List;

import sushil.luc.dtos.CustomerDTO;
import sushil.luc.dtos.ItemDTO;
import sushil.luc.dtos.OrderDTO;
import sushil.luc.dtos.TicketDTO;
import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.Customer;

public class Ticket {

    private int id;
	private String TicketID;
	private Customer TicketCustomer;
	private List<Item> Items;
	private TicketStatus Status;
	private String CreationDate;
	private String DeliveryDate;
	
	/**
	 * creates a new ticket from the given DTO files
	 * @param ticketDTO
	 * @param orderDTO
	 */
    public Ticket(TicketDTO ticketDTO, OrderDTO orderDTO){
        this.Items = new ArrayList<Item>();
        this.id = ticketDTO.getId();
        this.TicketID = String.valueOf(ticketDTO.getIdentifier());

        CustomerDTO customerDto = orderDTO.getCustomer();
        Customer customer = new Customer(customerDto, orderDTO.getInfo());

        this.TicketCustomer = customer;
        
        this.CreationDate = orderDTO.getInfo().getCreationDate();
        this.DeliveryDate = orderDTO.getInfo().getDeliveryDate();

        List<ItemDTO> itemDtos = ticketDTO.getItems();
        
        if (itemDtos!=null)
        {
	        for(ItemDTO itemDto:itemDtos){
	            Item tmp = new Item(itemDto);
	            Items.add(tmp);
	        }
        }
        
        // calc the status
        if (orderDTO.getStatus().isCheckedOut()==1 && orderDTO.getStatus().isReturned()==1 && orderDTO.getStatus().isStaged()==1)
            this.Status= TicketStatus.Closed;
        else
            if(orderDTO.getStatus().isStaged()==1)
                this.Status=TicketStatus.Staged;
            else if (orderDTO.getStatus().isCheckedOut()==1)
                this.Status = TicketStatus.Checked;
            else
                this.Status = TicketStatus.Open;
      
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
	
	/**
	 * Sets the ticketstatus according to the statues of the items of the ticket
	 */
	public void calcTicketStatus ()
	{
		boolean res =true;
		for(int i=0; i<this.Items.size();i++)
		{
			Item tmp = this.Items.get(i);
			if (tmp.getStatus().equals(ItemStatus.Checked))
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
			this.setStatus(TicketStatus.Checked);
		}
		else
				this.setStatus(TicketStatus.Open);
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

	public String getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(String creationDate) {
		CreationDate = creationDate;
	}


	public String getDeliveryDate() {
		return DeliveryDate;
	}


	public void setDeliveryDate(String deliveryDate) {
		DeliveryDate = deliveryDate;
	}
	
	/**
	 * Get an Item from the Ticket by Ugitag
	 * @param tag
	 * @return null if there is no Item tagged with the given tag. Otherwise the corresponding Item
	 */
	public Item getItem(UgiTag tag)
	{
		Item res =null;
		for (Item i : Items)
		{
			if (tag.getEpc().toString().equals(i.getRFID()))
				res=i;
		}
		
		return res;
	}
	
}
