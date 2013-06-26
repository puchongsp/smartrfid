package sushil.luc.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sushil.luc.database.RemoteDBService;
import sushil.luc.utils.DateUtil;

public class ItemService {
	
	public ItemService() {

	}
	
	public List<Item> fetchRelevantItems (List<Integer> ticketIDs)
	{
        RemoteDBService dbService = new RemoteDBService();
        String sql = "SELECT * FROM items WHERE ticket_id = 1 and status = 1";

        // Convert Hashmap to item
        //List <Item> items = (List<Item>)(List<?>)dbService.select(sql);

        List<Item> items = new ArrayList<Item>();
        List<HashMap<String,String>> rawItems = dbService.select(sql);
        for(HashMap<String, String> rawItem : rawItems) {
            items.add(convertToItem(rawItem));
        }
		return items;
	}
	
	public List<Item> fetchNewItems()
	{
        RemoteDBService dbService = new RemoteDBService();
        String sql = "SELECT * FROM items WHERE status = 0";
        List <Item> items = (List<Item>)(List<?>)dbService.select(sql);
        return items;
	}

    private Item convertToItem(HashMap<String, String> map) {
        Item item = new Item();
        item.setItemID(Long.valueOf(map.get("id")));
        item.setItemName(map.get("name"));
        item.setRFID(map.get("rfid"));
        item.setStatus(ItemStatus.valueOf(map.get("status")));
        item.setDate(DateUtil.stringToDate(map.get("date")));
        return item;
    }
}
