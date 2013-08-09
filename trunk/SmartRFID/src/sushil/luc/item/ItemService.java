package sushil.luc.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sushil.luc.dtos.ItemDTO;
import sushil.luc.dtos.RfidInfoDTO;
import sushil.luc.network.Callback;
import sushil.luc.network.NetworkHandler;
import sushil.luc.utils.DateUtil;

public class ItemService {

    private static List<Item> items;
    private static Set<Item> returnedItems;

    //private RemoteDBService dbService;
	
	public ItemService() {
        if(returnedItems == null) {
            returnedItems = new HashSet<Item>();
        }
		//dbService = new RemoteDBService();
	}
	
//	public List<Item> fetchRelevantItems (List<Integer> ticketIDs)
//	{
//
//        String sql = "SELECT * FROM items WHERE ticket_id = 1 and status = 1";
//
//        // Convert Hashmap to item
//        //List <Item> items = (List<Item>)(List<?>)dbService.select(sql);
//
//        List<Item> items = new ArrayList<Item>();
//        List<HashMap<String,String>> rawItems = dbService.select(sql);
//        for(HashMap<String, String> rawItem : rawItems) {
//            items.add(convertToItem(rawItem));
//        }
//		return items;
//	}
	
	public List<Item> getNewItems()
	{
//        String sql = "SELECT * FROM items WHERE status = 0";
//        List <Item> items = (List<Item>)(List<?>)dbService.select(sql);
//        return items;
        List <Item> items = getMockupData();
        List <Item> newItems = new ArrayList<Item>();
        for(Item item : items) {
            if(item.getRFID() == null || item.getRFID().equals("")){
                newItems.add(item);
            }
        }
        return newItems;
	}

    private Item convertToItem(HashMap<String, String> map) {
        Item item = new Item();
        item.setItemID(String.valueOf(map.get("id")));
        item.setItemName(map.get("name"));
        item.setRFID(map.get("rfid"));
        item.setStatus(ItemStatus.valueOf(map.get("status")));
        item.setDate(DateUtil.stringToDate(map.get("date")));
        return item;
    }
    
    /**
     * Connects the RFID Tag and the Item. Does all the necessary checks and updates the database
     * @param i
     * @param RFIDTag
     * @return true if everything is fine. False error and no change in the database.
     */
//    public boolean TagNewItem(Item i, String RFIDTag)
//    {
//    	boolean res = false;
//
//    	if (i.getRFID()!=null || RFIDTag.equals(""))
//    		return res;
//
//    	// check if there is an item already tagged with the given RFIDTag
//    	String query ="Select * from items where RFIDTag = "+RFIDTag+" ;";
//        List <Item> tmpitems = (List<Item>)(List<?>)dbService.select(query);
//
//        if(tmpitems.size()>0)
//         return res;
//        else {
//         i.setRFID(RFIDTag);
//
//         res = updateItem(i);
//
//         return res;
//        }
//    }
    
    /**
     * Updates all the attribute of the given Item object in the database
     * @param i
     * @return true if everything is fine. False error and no change in the database.
     */
//    private boolean updateItem (Item i)
//    {
//    	String sql = "UPDATE table_name SET column1=value1,column2=value2,... WHERE itemid = "+i.getItemID()+" ;";
//    	Boolean res = dbService.update(sql);
//    	return res;
//    }
    
    /**
     * 
     * @param RFID
     * @return Item: if null no entry found
     */
    public Item getItemInfo (String RFID)
    {
    	List<Item> values = getMockupData();
    	
    	for (Item a :values)
    	{
    		String b = a.getRFID();
    		
    		if (b!=null && b.equals(RFID))
    			return a;
    	}
    	
    	return null;
    }
    
    /**
     * Provides mockup data
     * To be removed
     * @return List of Items
     */
    private List<Item> getMockupData(){
        if(items == null) {
            items = new ArrayList<Item>();

            Item item = new Item();
            item.setItemName("Valve");
            item.setStatus(ItemStatus.Available);
            item.setItemID("VL_001_L");
            item.setRFID("000000000000000000000026");
            item.setWarehouseLocation("Column 10 Row 12");
            items.add(item);

            item = new Item();
            item.setItemName("Drilling Machine");
            item.setStatus(ItemStatus.Available);
            item.setItemID("DL_002");
            item.setRFID("000000000000000000000001");
            item.setWarehouseLocation("Column 11 Row 15");
            items.add(item);

            item = new Item();
            item.setItemName("Wooden Log");
            item.setStatus(ItemStatus.Available);
            item.setItemID("DL_003");
            item.setRFID("000000000000000000000002");
            item.setWarehouseLocation("Column 09 Row 15");
            items.add(item);

            item = new Item();
            item.setItemName("Lathe Machine");
            item.setStatus(ItemStatus.Collected);
            item.setItemID("DL_004");
         //   item.setRFID("000000000000000000000003");
            item.setWarehouseLocation("Column 10 Row 3");
            items.add(item);

            item = new Item();
            item.setItemName("Steel Disc");
            item.setStatus(ItemStatus.Available);
            item.setItemID("DC_005_S");
           // item.setRFID("000000000000000000000004");
            item.setWarehouseLocation("Column 1 Row 1");
            items.add(item);

            item = new Item();
            item.setItemName("Iron Board");
            item.setStatus(ItemStatus.Available);
            item.setItemID("BD_007_P");
          //  item.setRFID("000000000000000000000005");
            item.setWarehouseLocation("Column 1 Row 5");
            items.add(item);
        }

        return items;
    }

    /**
     * Fetches Rfid by item identifier from remote server
     * @param identifier
     * @return
     */
    public String fetchRfidFromItemIdentifier(String identifier) {
        String rfid = "";
        try {
            final RfidInfoDTO rfidInfoDTO = new RfidInfoDTO();
            final NetworkHandler networkHandler = NetworkHandler.getInstance();
            String URL = "";
            networkHandler.read(URL,RfidInfoDTO.class, new Callback<RfidInfoDTO>() {
                @Override
                public void callback(final RfidInfoDTO myRfidInfoDTO) {
                    rfidInfoDTO.setId(myRfidInfoDTO.getId());
                    rfidInfoDTO.setRfidNumber(myRfidInfoDTO.getRfidNumber());
                }
            });

            rfid = rfidInfoDTO.getRfidNumber();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rfid;
    }

    /**
     * Fetches Rfid by item identifier from remote server
     * @param rfid
     * @return
     */
    public Item fetchItemFromRfid(String rfid) {
        final List<ItemDTO> itemDtos = new ArrayList<ItemDTO>(1);
        try {
            final NetworkHandler networkHandler = NetworkHandler.getInstance();
            String URL = "";
            networkHandler.read(URL, ItemDTO.class, new Callback<ItemDTO>() {
                @Override
                public void callback(final ItemDTO myItemDto) {
                    itemDtos.add(myItemDto);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Item(itemDtos.get(0));
    }

    public List<Item> getAllItems() {
        return getMockupData();
    }

    public List<Item> getReturnedItems() {
        List<Item> items =  new ArrayList<Item>();
        items.addAll(returnedItems);
        return items;
    }

    public void returnItem(Item item) {
        item.setStatus(ItemStatus.Returned);
        returnedItems.add(item);
    }

    public void sendToRepair(Item item) {
        if(item.getStatus().equals(ItemStatus.Returned)){
//            if(returnedItems.contains(item)) {
//                returnedItems.remove(item);
//            }
            item.setStatus(ItemStatus.Repair);
        }
    }

    public void sendToWarehouse(Item item) {
        if(item.getStatus().equals(ItemStatus.Returned) || item.getStatus().equals(ItemStatus.Repair) ){
            if(returnedItems.contains(item)) {
                returnedItems.remove(item);
            }
            item.setStatus(ItemStatus.Available);
        }
    }
}
