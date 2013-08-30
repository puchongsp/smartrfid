package sushil.luc.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sushil.luc.item.Item;

public class ItemHistory {

	private static ItemHistory currentHist;
	private HashMap <String, LinkedList<ItemHistoryObject>> history; // maps from identifier to a list of item states
	private LinkedList<Touple> lastchanges;
	/**
	 * Singleton pattern. Returns always the same instance
	 * Changes can only be rolled back during one run of the app. After closing the app no rollback can be done
	 * @return
	 */
	public static ItemHistory getInstance()
	{
		if (currentHist==null)
			currentHist = new ItemHistory();

		return currentHist;
	}
	
	/**
	 * private constructor. Use always get Instance to receive a new instance
	 */
	private ItemHistory()
	{
		history = new HashMap<String, LinkedList<ItemHistoryObject>>();
		lastchanges = new LinkedList<Touple>();
	}
	
	/**
	 * As soon as a Item changes his status or RFID the Item should be added to the history
	 * @param i
	 */
	public void saveToHistory(Item i)
	{
		Item newItem =null;
		String change=null;
		try {
			newItem = (Item)i.clone();
			// check what changed
			List<ItemHistoryObject> Itemhist = currentHist.history.get(newItem.getItemID());
			
			Item lastItem=null;
			
			if (Itemhist!=null)
			{
				lastItem =Itemhist.get(Itemhist.size()).getItem();
			}
			
			change = checkChange(lastItem, newItem);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		if (newItem!=null)
		{
			saveToHashMap(newItem,change);
			saveLastChanges(newItem);
			
		}
	}
	
	/**
	 * Adds the Item and the description of the change into the hashmap
	 * @param i
	 * @param change
	 */
	private void saveToHashMap(Item i,String change)
	{
		String id = i.getItemID();
		
		if (currentHist.history.containsKey(id))
		{
			LinkedList<ItemHistoryObject> b = currentHist.history.get(id);
			ItemHistoryObject tmp = new ItemHistoryObject(i, change);
			b.add(tmp);
			currentHist.history.put(id, b);
		}
		else
		{
			LinkedList<ItemHistoryObject> b = new LinkedList<ItemHistoryObject>();
			ItemHistoryObject tmp = new ItemHistoryObject(i, change);
			b.add(tmp);
			currentHist.history.put(id, b);
		}
	}
	
	/**
	 * Returns the last 5 versions of an item (if there are 5).
	 * @param item
	 * @return returns a Map which maps version number to a appropriate 'version' string
	 */
	public Map<Integer,String> getItemHistotyStates(Item item)
	{
		Map<Integer,String> res = new HashMap<Integer, String>();
		
		String id = item.getItemID();
		
		List<ItemHistoryObject> itemstates = currentHist.history.get(id);
		
		if (itemstates.size()>4)
		{
			int j=4;
			for (int i=0; i<=j;i++)
			{
				String tmp = itemstates.get(itemstates.size()-i).getChange();
				res.put((itemstates.size()-i), tmp);
			}
		}
		else
		{
			for (int i=0; i<=itemstates.size();i++)
			{
				String tmp = itemstates.get(i).getChange();
				res.put(i, tmp);
			}
		}
		
		return res;
	}
	
	/**
	 * Returns the requested version of an item. Deletes the history after this point
	 * @param i
	 * @param version
	 * @return
	 */
	public Item rollback (Item item, int version)
	{
		String id = item.getItemID();
		List<ItemHistoryObject> states = currentHist.history.get(id);
		
		Item res = null;
		
		if (version<states.size())
		{
			res = states.get(version).getItem();
			int j =states.size();
			
			// remove newer history
			for (int i=j;i>version;i--)
			{
				states.remove(i);
			}
		}
		
		return res;
	}
	
	/**
	 * Checks where the item did change
	 * @param oldItem
	 * @param newItem
	 * @return
	 */
	private String checkChange(Item oldItem, Item newItem)
	{
		String res="";
		
		if (oldItem==null)
			res ="Intial state";
		else
			if (oldItem.getStatus()!=newItem.getStatus())
				res = "ItemStatus changed from "+oldItem.getStatus()+" to "+newItem.getStatus();
			else
				if (oldItem.getRFID()!=newItem.getRFID())
					res = "Assigned a new RFID";
				else
					res ="Intial state";
		
		return res;
	}
	
	/**
	 * Save to lastchanges. Contains only the last x changes.
	 * @param i
	 */
	private void saveLastChanges(Item i)
	{
		if (currentHist.lastchanges.size()>5)
		{
			currentHist.lastchanges.pop();
		}
		int version = currentHist.history.get(i.getItemID()).size();
		currentHist.lastchanges.add(new Touple (i.getItemID(),version));
		
		/*List<Integer> containsVersions = new LinkedList<Integer>();
		for  (Touple t :currentHist.lastchanges)
		{
			if (t.getItemId().equals(i.getItemID()))
				containsVersions.add(t.getVersion());
		}
		
		if (containsVersions.isEmpty())
		{
			Touple t = new Touple(i.getItemID(), currentHist.history.get(i.getItemID()).size());
			currentHist.lastchanges.add(t);
		}
		else
		{
			int version =-1;
			for()
		}*/
	}
	
	public Map<Touple,String> getLastChanges()
	{
		
		Map<Touple,String> res = new HashMap<Touple, String>();
		
		
		
		return res;
	}
	
	/**
	 * private class to map the item to the version, which changed last
	 */
	private class Touple
	{
		private String itemId;
		private Integer version;
		
		public Touple (String itemId, Integer version)
		{
			this.itemId = itemId;
			this.version = version;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}
	}
}
