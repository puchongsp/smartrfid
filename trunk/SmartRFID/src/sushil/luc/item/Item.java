package sushil.luc.item;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

import sushil.luc.dtos.ItemDTO;
import sushil.luc.dtos.ItemInfoDTO;
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

	private int ItemID;
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
       // Log.d("Item",  "ID "+this.ItemID);
        if (itemDto.getItemInfo()==null)
        	Log.d("ItemInfo", "null");
        
        try {
        	this.ItemName = itemDto.getItemInfo().getDescription();
        } catch (NullPointerException e) {
            this.ItemName = "";
        }
        //Log.d("Item",  "ItemName "+this.ItemName);
        
        try {
            this.RFID = itemDto.getItemInfo().getRfidInfo().getRfidNumber();
        } catch (NullPointerException e) {
            this.RFID = "";
        }
        //Log.d("Item",  "RFID "+this.RFID);

        this.Status = ItemStatus.Available;
        
        if (itemDto.getItemStatus()!=null)
        {
        	if(itemDto.getItemStatus().isChecked())
        		this.Status = ItemStatus.RentToCustomer;
        	else if(itemDto.getItemStatus().isReturned())
        		this.Status = ItemStatus.Returned;
        }
      //  Log.d("Item",  "Status "+this.Status);
        
    /*    else
        	this.Status=null;*/
        if (itemDto.getRentInformation()!=null)
        {
        	this.Date = DateUtil.stringToDate(itemDto.getRentInformation().getReturnDate());
        }
        else
        	this.Date=new Date();
       // Log.d("Item",  "Date "+this.Date);
        Log.d("Item", "ItemId "+this.ItemID+" ItemName "+this.ItemName+
				" WarehouseLoc "+this.WarehouseLocation+" RFID "+this.RFID
				+" Status "+this.Status+" RentDate "+this.Date );
    }
	
	public int getItemID() {
		return ItemID;
	}
	public void setItemID(int itemID) {
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
