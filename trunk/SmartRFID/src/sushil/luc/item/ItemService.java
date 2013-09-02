package sushil.luc.item;

import android.util.Log;

import com.ugrokit.api.UgiTag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import sushil.luc.dtos.ItemDTO;
import sushil.luc.dtos.RepairHistoryDTO;
import sushil.luc.dtos.RfidInfoDTO;
import sushil.luc.gui.MainActivity;
import sushil.luc.gui.NewItemFragment;
import sushil.luc.gui.RepairItemFragment;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.network.Callback;
import sushil.luc.network.NetworkHandler;
import sushil.luc.network.SimpleGetTask;
import sushil.luc.ugrokit.RFIDManager;

public class ItemService {
    private static List<Item> repairItems;
    public static List<Item> newItems;
    private static List<Item> returnedItems;
	
    /**
     * provides the functionality to query the database
     */
	public ItemService() {
        if(repairItems == null) {
            repairItems = new LinkedList<Item>();
        }
        if(newItems == null) {
        	newItems = new LinkedList<Item>();
        }
        if(returnedItems==null)
        {
        	returnedItems = new LinkedList<Item>();
        }
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

       //     String URL ="http://rfidproject.azurewebsites.net/api/items/query?identifiers="+identifier;

            String URL = MainActivity.HOST_URL + "/api/items/query.php?identifiers="+identifier;

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
     * Fetch the item by the given RFID
     * @param tag
     * @param activity
     * @param rfidManager
     * @return
     */
    public Item fetchItemFromRfid(final UgiTag tag, final UgroKitActivity activity, final RFIDManager rfidManager) {
        final List<ItemDTO> itemDtos = new ArrayList<ItemDTO>(1);
        try {
            final NetworkHandler networkHandler = NetworkHandler.getInstance();

          //  String URL = "http://rfidproject.azurewebsites.net/api/items/query?rfids="+rfid;

            String URL = MainActivity.HOST_URL + "/api/items/query.php?rfids="+tag.getEpc().toString();
            Log.i("Item Service :", URL);

            networkHandler.read(URL, ItemDTO.class, new Callback<ItemDTO>() {
                @Override
                public void callback(final ItemDTO myItemDto) {

                    Log.i("ItemServiceCallback","id="+myItemDto.getId());
                    Log.i("ItemServiceCallback", "rfid=" + myItemDto.getItemInfo().getRfidInfo().getRfidNumber());
                    Log.i("ItemServiceCallback", "idf=" + myItemDto.getIdentifier());

                    itemDtos.add(myItemDto);
                    // update the calling activites as soon as the result is available
                    if (activity!=null)
                    	activity.updateIteminfo(new Item(myItemDto));
                    if(rfidManager!=null)
                    	rfidManager.handle2(new Item(myItemDto), tag);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        Item item =null;

        if(itemDtos.size() > 0) {
            item = new Item(itemDtos.get(0));
        }

        return item;
    }
    
    /**
     * Get all the items from the database, which do not have an rfid number assigned yet
     * @param caller
     * @return
     */
    public List<Item> getNewItems(final String caller)
    {

        int limit = 20;
    	if (newItems.size()<limit)
    	{
            try {
	              final NetworkHandler networkHandler = NetworkHandler.getInstance();

	         //     String URL = "http://rfidproject.azurewebsites.net/api/items/query?limit="+limit+"&skip=0&orderBy=0&filters=2";

	              String URL = MainActivity.HOST_URL + "/api/items/queryNewItems.php?limit="+limit;
                  Log.i("IS","URL = "+URL);
	              networkHandler.readList(URL, ItemDTO[].class, new Callback<List<ItemDTO>>() {
	
					@Override
					public void callback(List<ItemDTO> t) {
						for (ItemDTO o :t)
						{
							Item item = new Item(o);
							if (!checkItem(item, newItems))
                                newItems.add(item);
						}
						// Inform the caller about the newitems
						if (caller.equals("NewItemFragment"))
						{
							Log.d("ItemService", "Call done "+newItems.size());
							NewItemFragment.updateView(newItems);
						}
					}
	              });
	
	          } catch (Exception e) {
	              e.printStackTrace();
	          }
    	}
          Log.d("ItemService", "newItems size :"+newItems.size()); 
    	  return newItems;
    }
    
    /**
     * Checks if an Item item is contained in the given itemlist
     * @param item
     * @param itemlist
     * @return true yes, false no
     */
	private boolean checkItem (Item item, List<Item> itemlist)
	{
		boolean check =false;
		for (Item i: itemlist)  
		{
			if (item.getItemID().equals(i.getItemID()))
				check=true;
		}
		return check;
	}
    
	/**
	 * Gets you the ItemHistory for a given item from the database
	 * @param item
	 * @param caller
	 * @return
	 */
	public List<ItemHistory> getItemHistory (Item item, final String caller)
	{
		final List<ItemHistory> hist =new LinkedList<ItemHistory>();
		
		try {
            final NetworkHandler networkHandler = NetworkHandler.getInstance();

       //     String URL = "http://rfidproject.azurewebsites.net/api/items/query?limit="+limit+"&skip=0&orderBy=0&filters=2";
            
            String URL = MainActivity.HOST_URL + "/api/repairHistory/query.php?itemId="+item.getId();

            networkHandler.readList(URL, RepairHistoryDTO[].class, new Callback<List<RepairHistoryDTO>>() {

				@Override
				public void callback(List<RepairHistoryDTO> ih) {
					for (RepairHistoryDTO o :ih)
					{
						ItemHistory itemhis = new ItemHistory(o);
						hist.add(itemhis);
					}
					// inform the caller about the result
					if (caller.equals("RepairItemFragment"))
					{						
						RepairItemFragment.updateHistory(hist);
					}
				}
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return hist;
	}
	
	/**
	 * Check if a given Item i is contained in the returnItems list
	 * @param i
	 * @return
	 */
	private boolean containsReturnedItem (Item i)
    {
        boolean res = false;
        for(Item item : returnedItems)
        {
            if (item.getItemID().equals(i.getItemID()))
                return true;

        }
        return res;
    }

	/**
	 * Return an item, but changes only local
	 * @param item
	 */
    public void returnItem(Item item) {
        if (!containsReturnedItem(item)  )
            returnedItems.add(item);
    }
    
    /**
     * get all the localy returned items
     * @return
     */
    public List<Item> getReturnedItems()
    {
    	return returnedItems;
    }
    
    /** Tell the database that the given Item item has changed his rfid
     * @param item
     */
    public void tagNewItem(Item item) {
        SimpleGetTask getTask = new SimpleGetTask( SimpleGetTask.TagNewItem, item);
        getTask.execute();
    }
/**
 * Tell the database that the given Item item has been send to repair
 * @param item
 */
    public void sendToRepair(Item item) {
    	
    	// Update Database
    	SimpleGetTask getTask = new SimpleGetTask( SimpleGetTask.ItemReturned, item);
    	getTask.execute();
    	// Update local
    	item.setStatus(ItemStatus.Returned);
    	if (!checkItem(item, repairItems))
    		repairItems.add(item);
    }

    /**
     * Tell the database that the given Item item has been send to back to the warehouse
     * @param item
     */
    public void sendToWarehouse(Item item) {
    	
    	// Update Database
    	SimpleGetTask getTask = new SimpleGetTask( SimpleGetTask.ItemAvailable, item);
    	getTask.execute();
    	// Update local
    	item.setStatus(ItemStatus.Available);
    }
    
    /**
     * updates the itemhistory of the given item with the given text
     * @param item
     * @param text
     */
    public void updateItemHistory (Item item, String text)
    {
    	// Update Database
    	SimpleGetTask getTask = new SimpleGetTask(SimpleGetTask.UpdateItemHistory, item,text);
    	getTask.execute();
    }
}
