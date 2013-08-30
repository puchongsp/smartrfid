package sushil.luc.utils;

import sushil.luc.item.Item;

public class ItemHistoryObject {
	private Item item;
	private String change;
	
	public ItemHistoryObject(Item i, String change)
	{
		this.item = i;
		this.change = change;
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
}
