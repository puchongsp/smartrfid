package sushil.luc.ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sushil.luc.item.Item;
import sushil.luc.item.ItemStatus;
import sushil.luc.msc.Customer;
import sushil.luc.utils.DateUtil;

public class TicketService {

    private static final String TICKET = "ticket";
    private static final String CLIENT = "client";
    private static final String ITEM = "item";


	public TicketService()
	{

	}

    /**
     * This function will fetch json data from exposed REST webservice
     * and return after converting it to Ticket model
     * @return
     */
	public List<Ticket> fetchAllTickets()
	{
//        RemoteDBService dbService = new RemoteDBService();
//        String sql = "SELECT * FROM tickets WHERE status = 0 and date = '26-06-2013'";
//
//
//        //List<Ticket> tickets = (List<Ticket>)(List<?>)dbService.select(sql);
//        List<Ticket> tickets = new ArrayList<Ticket>();
//        List<HashMap<String,String>> rawTickets = dbService.select(sql);
//        for(HashMap<String, String> rawTicket : rawTickets) {
//            tickets.add(convertToTicket(rawTicket));
//        }



        //fetch HTTP with asynctask here

        List<Ticket> tickets = new ArrayList<Ticket>();
        /*
        try {
            tickets = convertToTicket("");
        } catch (JSONException e) {
            tickets = null;
            e.printStackTrace();
        }
        */

       tickets = getMockupData();

        return tickets;
	}
	
	public void saveToRemote(int TicketID)
	{
		
	}

    /**
     * This method converts Json data to Ticket,
     * we will implement proper strings
     * once we get proper API and documentation
     */
    private List<Ticket> convertToTicket(String jsonTickets) throws JSONException {
        List<Ticket> tickets = new ArrayList<Ticket>();

        /*
         * From json object to Ticket object
         * manipulate data if necessary
         */
        JSONObject jObject = new JSONObject(jsonTickets);
        JSONArray jTickets = jObject.getJSONArray(TICKET);

        for(int i = 0; i<jTickets.length(); i++){
            JSONObject jTicket = jTickets.getJSONObject(i);
            Ticket ticket = new Ticket();
            ticket.setTicketID(jTicket.getString(Ticket._ID));
            ticket.setStatus(TicketStatus.valueOf(jTicket.getString(Ticket._STATUS)));
            ticket.setCreationDate(DateUtil.stringToDate(jTicket.getString(Ticket._CREATED_DATE)));

            JSONObject jCustomer = jTicket.getJSONObject(CLIENT);
            Customer customer = new Customer();
            customer.setCustomerID(jCustomer.getString(Customer._ID));
            customer.setName(jCustomer.getString(Customer._NAME));
            customer.setAddress(jCustomer.getString(Customer._ADDRESS));
            //customer.setGPSLocation("");
            customer.setPhone(jCustomer.getString(Customer._PHONE));
            ticket.setTicketCustomer(customer);


            JSONArray jItems = jTicket.getJSONArray(ITEM);
            List<Item> items = new ArrayList<Item>();
            for(int j = 0; j<jItems.length(); j++){
                JSONObject jItem = jItems.getJSONObject(i);
                Item item = new Item();
                //item.setDate(jItem.getString(""));
                item.setStatus(ItemStatus.valueOf(jItem.getString(Item._STATUS)));
                item.setItemName(jItem.getString(Item._NAME));
                item.setItemID(jItem.getString(Item._ID));
                item.setWarehouseLocation(jItem.getString(Item._LOCATION));
            }
            ticket.setItems(items);
        }

        return tickets;
    }


    /**
     * Provides mockup data
     * To be removed
     * @return List of tickets
     */
    private List<Ticket> getMockupData(){
        List<Ticket> tickets = new ArrayList<Ticket>();
        /**
         * MockupData
         */
        
        int i=0;
        
        for (;i<3;i++)
        {
        Ticket ticket = new Ticket();
        ticket.setTicketID(String.valueOf("00"+i));
        ticket.setStatus(TicketStatus.Open);
        ticket.setCreationDate(DateUtil.stringToDate("01-07-2013"));
        ticket.setDeliveryDate(DateUtil.stringToDate((10-i)+"-07-2013"));

        Customer client = new Customer();
        client.setName("James Bond");
        client.setCustomerID("007");
        client.setAddress("Texas");
        ticket.setTicketCustomer(client);

        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setItemName("Item_ABC");
        item.setStatus(ItemStatus.Available);
        item.setItemID("ITM001");
        items.add(item);
        
        item = new Item();
        item.setItemName("Item_XYZ");
        item.setStatus(ItemStatus.Available);
        item.setItemID("ITM002");
        items.add(item);

        ticket.setItems(items);
        tickets.add(ticket);
        
        }
        
        for (;i<6;i++)
        {
        Ticket ticket = new Ticket();
        ticket.setTicketID(String.valueOf("00"+i));
        ticket.setStatus(TicketStatus.Closed);
        ticket.setCreationDate(DateUtil.stringToDate("01-07-2013"));
        ticket.setDeliveryDate(DateUtil.stringToDate((30-i)+"-07-2013"));

        Customer client = new Customer();
        client.setName("James Bond");
        client.setCustomerID("007");
        client.setAddress("Texas");
        ticket.setTicketCustomer(client);

        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setItemName("Item_ABC");
        item.setStatus(ItemStatus.Collected);
        item.setItemID("ITM001");
        items.add(item);
        
        item = new Item();
        item.setItemName("Item_XYZ");
        item.setStatus(ItemStatus.Collected);
        item.setItemID("ITM002");
        items.add(item);

        ticket.setItems(items);
        tickets.add(ticket);
        
        }
        
        for (;i<9;i++)
        {
        Ticket ticket = new Ticket();
        ticket.setTicketID(String.valueOf("00"+i));
        ticket.setStatus(TicketStatus.InProgress);
        ticket.setCreationDate(DateUtil.stringToDate("01-07-2013"));
        ticket.setDeliveryDate(DateUtil.stringToDate((20-i)+"-07-2013"));

        Customer client = new Customer();
        client.setName("James Bond");
        client.setCustomerID("007");
        client.setAddress("Texas");
        ticket.setTicketCustomer(client);

        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setItemName("Item_ABC");
        item.setStatus(ItemStatus.Collected);
        item.setItemID("ITM001");
        items.add(item);
        
        item = new Item();
        item.setItemName("Item_XYZ");
        item.setStatus(ItemStatus.Available);
        item.setItemID("ITM002");
        items.add(item);

        ticket.setItems(items);
        tickets.add(ticket);
        
        }
        
        return tickets;
    }
}
