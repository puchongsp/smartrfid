package sushil.luc.item;

import java.util.LinkedList;
import java.util.List;
import java.util.Date;

public class Item{
	private long ItemID;
	private String ItemName;
	private String ContainerId;
	private String RFID;
	private String WarehouseLocation;
	private ItemStatus Status;
	private Date Date;
	private List<String> RepairLogs;
	
	public Item()
	{
		this.RepairLogs = new LinkedList<String>();
	}
	
	public long getItemID() {
		return ItemID;
	}
	public void setItemID(long itemID) {
		ItemID = itemID;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public String getContainerId() {
		return ContainerId;
	}
	public void setContainerId(String containerId) {
		ContainerId = containerId;
	}
	public String getRFID() {
		return RFID;
	}
	public void setRFID(String rFID) {
		RFID = rFID;
	}
	public String getWarehouseLocation() {
		return WarehouseLocation;
	}
	public void setWarehouseLocation(String warehouseLocation) {
		WarehouseLocation = warehouseLocation;
	}
	public ItemStatus getStatus() {
		return Status;
	}
	public void setStatus(ItemStatus status) {
		Status = status;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public List<String> getRepairLogs() {
		return RepairLogs;
	}
	public void setRepairLogs(List<String> repairLogs) {
		RepairLogs = repairLogs;
	}
	
}
