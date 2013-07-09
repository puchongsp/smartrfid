package sushil.luc.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import sushil.luc.utils.DateUtil;

public class ItemService {
	
	//private RemoteDBService dbService;
	
	public ItemService() {
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
    	List<String> res = new LinkedList<String>();
    	
    	Item i = getItemInfoDatabase(RFID);
    	
    	// Ask about a certain RFID 
    	return i;
    }
    
    private Item getItemInfoDatabase (String RFID)
    {
    	List<Item> values = getMockupData();
    	
    	for (Item a :values)
    	{
    		if (a.getRFID().equals(RFID))
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
        List<Item> items = new ArrayList<Item>();

        Item item = new Item();
        item.setItemName("Item_ABC");
        item.setStatus(ItemStatus.Available);
        item.setItemID("ITM001");
        item.setRFID("072E24F3");
        item.setWarehouseLocation("Colum 10 Row 12");
        items.add(item);
        
        item = new Item();
        item.setItemName("Item_XYZ");
        item.setStatus(ItemStatus.Available);
        item.setItemID("ITM002");
        item.setRFID("515BD227");
        item.setWarehouseLocation("Colum 10 Row 15");
        items.add(item);
        
        item = new Item();
        item.setItemName("Item_XXX");
        item.setStatus(ItemStatus.Available);
        item.setItemID("ITM003");
       // item.setRFID("515BD227");
        item.setWarehouseLocation("Colum 1 Row 1");
        items.add(item);
        
        item = new Item();
        item.setItemName("Item_YYY");
        item.setStatus(ItemStatus.Available);
        item.setItemID("ITM004");
       // item.setRFID("515BD227");
        item.setWarehouseLocation("Colum 1 Row 5");
        items.add(item);

        return items;
    }
}
