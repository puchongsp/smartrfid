package sushil.luc.item;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.ugrokit.api.UgiTag;

import sushil.luc.dtos.ItemDTO;
import sushil.luc.dtos.RfidInfoDTO;
import sushil.luc.gui.MainActivity;
import sushil.luc.gui.NewItemFragment;
import sushil.luc.msc.UgroKitActivity;
import sushil.luc.network.Callback;
import sushil.luc.network.NetworkHandler;
import sushil.luc.ugrokit.RFIDManager;

public class ItemService {

   // private static List<Item> items;
    private static Set<Item> returnedItems;
    private static List<Item> newItems;
	
	public ItemService() {
        if(returnedItems == null) {
            returnedItems = new HashSet<Item>();
        }
        if(newItems == null) {
        	newItems = new LinkedList<Item>();
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
     * Fetches Rfid by item identifier from remote server
     * @param rfid
     * @param activity
     * @return
     */
    public Item fetchItemFromRfid(final UgiTag tag, final UgroKitActivity activity, final RFIDManager rfidManager) {
        final List<ItemDTO> itemDtos = new ArrayList<ItemDTO>(1);
        try {
            final NetworkHandler networkHandler = NetworkHandler.getInstance();
          //  rfid = "000000000000000000000001";

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
    
    public List<Item> getNewItems(final String caller)
    {
        int limit = 20;
    	if (newItems.size()<limit)
    	{
	    	try {
	              final NetworkHandler networkHandler = NetworkHandler.getInstance();

	         //     String URL = "http://rfidproject.azurewebsites.net/api/items/query?limit="+limit+"&skip=0&orderBy=0&filters=2";

	              String URL = MainActivity.HOST_URL + "/api/items/query.php?limit="+limit+"&skip=0&orderBy=0&filters=2";

	              networkHandler.readList(URL, ItemDTO[].class, new Callback<List<ItemDTO>>() {
	
					@Override
					public void callback(List<ItemDTO> t) {
						for (ItemDTO o :t)
						{
							Item item = new Item(o);
							if (!checkItem(item))
                                newItems.add(item);
						}
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
    
	private boolean checkItem (Item newItem)
	{
		boolean check =false;
		for (Item i: newItems)  
		{
			if (newItem.getItemID().equals(i.getItemID()))
				check=true;
		}
		return check;
	}
    

    public List<Item> getReturnedItems() {
    	//TODO connect to database
        List<Item> items =  new ArrayList<Item>();
        items.addAll(returnedItems);
        return items;
    }

    public void returnItem(Item item) {
    	//TODO connect to database
        item.setStatus(ItemStatus.Returned);
        returnedItems.add(item);
    }

    public void sendToRepair(Item item) {
    	//TODO connect to database
        if(item.getStatus().equals(ItemStatus.Returned)){
//            if(returnedItems.contains(item)) {
//                returnedItems.remove(item);
//            }
            item.setStatus(ItemStatus.Repair);
        }
    }

    public void sendToWarehouse(Item item) {
    	//TODO connect to database
        if(item.getStatus().equals(ItemStatus.Returned) || item.getStatus().equals(ItemStatus.Repair) ){
            if(returnedItems.contains(item)) {
                returnedItems.remove(item);
            }
            item.setStatus(ItemStatus.Available);
        }
    }
    
    public void onNotify()
    {
    	
    }
}
