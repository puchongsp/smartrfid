package sushil.luc.item;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import sushil.luc.dtos.ItemDTO;
import sushil.luc.utils.DateUtil;

public class Item{

    /*
     * to map models vars with fields in json
     */
    public static final String _ID = "itemIdentifier";
    public static final String _NAME = "description";
    public static final String _RFID = "rfid";
    public static final String _LOCATION = "location";
    public static final String _STATUS = "status";
    //public static final String _DATE = "date";

	private String ItemID;
	private String ItemName;
    private String RFID;
	private String WarehouseLocation;
	private ItemStatus Status;
	private Date Date;
	private List<String> RepairLogs;
	
	public Item()
	{
		this.RepairLogs = new LinkedList<String>();
	}

    public Item(ItemDTO itemDto){
        this.ItemID = itemDto.getId();
        this.ItemName = itemDto.getTicketItemInfo().getDescription();

        try {
            this.RFID = itemDto.getTicketItemInfo().getRfidInfo().getRfidNumber();
        } catch (NullPointerException e) {
            this.RFID = "";
        }

        this.Status = ItemStatus.Available;

        if(itemDto.getItemStatus().isChecked())
            this.Status = ItemStatus.RentToCustomer;
        else if(itemDto.getItemStatus().isReturned())
            this.Status = ItemStatus.Returned;


        this.Date = DateUtil.stringToDate(itemDto.getRentInformation().getReturnDate());
    }
	
	public String getItemID() {
		return ItemID;
	}
	public void setItemID(String itemID) {
		ItemID = itemID;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
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
