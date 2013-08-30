package sushil.luc.item;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;
import android.widget.TextView;

import sushil.luc.dtos.ItemDTO;
import sushil.luc.dtos.ItemInfoDTO;
import sushil.luc.utils.DateUtil;

public class Item implements Cloneable{

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
	private String StartRentDate;
	private String StopRentDate;
	private String DeliveryDate;
	private String ReturnDateDate;
	private String OriginalStartRentDate;
	private String Category;
	private String SubCategory;
	private String CreationDate;
	private String Type;
	private String InventoryTotal;
	private String InventoryOnHand;
	private String InventoryOut;
	private String Quantity;
	
	
	private List<String> RepairLogs;
	
	public Item()
	{
		this.RepairLogs = new LinkedList<String>();
	}
	
    public Item(ItemDTO itemDto){
    	if (itemDto!=null)
    	{
    		this.ItemID = itemDto.getIdentifier();
    		if (itemDto.getItemInfo()!=null)
    		{
    			this.ItemName = itemDto.getItemInfo().getDescription();
    			this.WarehouseLocation= itemDto.getItemInfo().getBinLocation();
    			
    			if (itemDto.getItemInfo().getRfidInfo()!=null)
    			{
    				this.RFID = itemDto.getItemInfo().getRfidInfo().getRfidNumber();
    			}
    			
    		//	this.Status = mapStatus (itemDto.getItemInfo().getStatusNote());
    			this.Category = itemDto.getItemInfo().getCategory();
    			this.CreationDate = itemDto.getItemInfo().getCreationDate();
    			this.SubCategory = itemDto.getItemInfo().getSubCategory();
    			
    		}
    		
    		if (itemDto.getInventoryInfo()!=null)
    		{
    			this.Type = String.valueOf(itemDto.getInventoryInfo().getType());
    			this.InventoryOnHand = String.valueOf(itemDto.getInventoryInfo().getInventoryOnHand());
    			this.InventoryOut= String.valueOf(itemDto.getInventoryInfo().getInventoryOut());
    			this.InventoryTotal = String.valueOf(itemDto.getInventoryInfo().getInventoryTotal());
    		}
    		
    		if (itemDto.getTicketItemInfo()!=null)
    		{
    			this.ItemID = itemDto.getTicketItemInfo().getIdentifier();
    			this.ItemName = itemDto.getTicketItemInfo().getDescription();
    			this.WarehouseLocation= itemDto.getTicketItemInfo().getBinLocation();
    			
    			if (itemDto.getTicketItemInfo().getRfidInfo()!=null)
    			{
        			this.RFID = itemDto.getTicketItemInfo().getRfidInfo().getRfidNumber();
    			}
    			this.Quantity = String.valueOf(itemDto.getTicketItemInfo().getQuantity());
    		}
    		
    		// TODO
    		if (itemDto.getItemStatus()!=null)
    		{
    			if(itemDto.getItemStatus().isChecked())
            		this.Status = ItemStatus.Checked;
            	else 
            		if(itemDto.getItemStatus().isReturned())
            			this.Status = ItemStatus.Returned;
            		else
            			this.Status = ItemStatus.Available;
    		}
    		
    		if (itemDto.getRentInformation()!=null)
    		{
    			/*this.StartRentDate = DateUtil.stringToDate(itemDto.getRentInformation().getStartRent());
    			this.StopRentDate = DateUtil.stringToDate(itemDto.getRentInformation().getStopRent());
    			this.ReturnDateDate = DateUtil.stringToDate(itemDto.getRentInformation().getReturnDate());
    			this.OriginalStartRentDate= DateUtil.stringToDate(itemDto.getRentInformation().getOriginalStartRent());*/
    			this.StartRentDate = itemDto.getRentInformation().getStartRent();
    			this.StopRentDate = itemDto.getRentInformation().getStopRent();
    			this.DeliveryDate = itemDto.getRentInformation().getDeliveryDate();
    			this.ReturnDateDate = itemDto.getRentInformation().getReturnDate();
    			this.OriginalStartRentDate= itemDto.getRentInformation().getOriginalStartRent();
    		}
    		
    		if (itemDto.getRfidInfo()!=null)
    		{
    			this.RFID = itemDto.getRfidInfo().getRfidNumber();
    		}
    	}
    	/*
        
       // Log.d("Item",  "ID "+this.ItemID);
        if (itemDto.getItemInfo()==null)
        	Log.d("ItemInfo", "null");
        
        try {
        	
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
    /*    if (itemDto.getRentInformation()!=null)
        {
        	this.Date = DateUtil.stringToDate(itemDto.getRentInformation().getReturnDate());
        }
        else
        	this.Date=new Date();
       // Log.d("Item",  "Date "+this.Date);*/
        Log.d("Item", "ItemId "+this.ItemID+" ItemName "+this.ItemName+
				" WarehouseLoc "+this.WarehouseLocation+" RFID "+this.RFID
				+" Status "+this.Status+" StartRentDate "+this.StartRentDate );
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
	public List<String> getRepairLogs() {
		return RepairLogs;
	}
	public void setRepairLogs(List<String> repairLogs) {
		RepairLogs = repairLogs;
	}



	public String getStopRentDate() {
		return StopRentDate;
	}



	public void setStopRentDate(String stopRentDate) {
		StopRentDate = stopRentDate;
	}



	public String getReturnDateDate() {
		return ReturnDateDate;
	}



	public void setReturnDateDate(String returnDateDate) {
		ReturnDateDate = returnDateDate;
	}



	public String getOriginalStartRentDate() {
		return OriginalStartRentDate;
	}



	public void setOriginalStartRentDate(String originalStartRentDate) {
		OriginalStartRentDate = originalStartRentDate;
	}
	
	/*private ItemStatus mapStatus (String status)
	{
		// Have to check if mapping is correct
		
		if (status !=null)
		{
			if (status.equals("IN_TESTING"))
				return ItemStatus.Repair;
			if (status.contains("OUT ON CERD"))
				return ItemStatus.Transport;
		}
		return ItemStatus.Available;
	}*/

	public String getStartRentDate() {
		return StartRentDate;
	}

	public void setStartRentDate(String startRentDate) {
		StartRentDate = startRentDate;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getSubCategory() {
		return SubCategory;
	}

	public void setSubCategory(String subCategory) {
		SubCategory = subCategory;
	}

	public String getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(String creationDate) {
		CreationDate = creationDate;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getInventoryTotal() {
		return InventoryTotal;
	}

	public void setInventoryTotal(String inventoryTotal) {
		InventoryTotal = inventoryTotal;
	}

	public String getInventoryOnHand() {
		return InventoryOnHand;
	}

	public void setInventoryOnHand(String inventoryOnHand) {
		InventoryOnHand = inventoryOnHand;
	}

	public String getInventoryOut() {
		return InventoryOut;
	}

	public void setInventoryOut(String inventoryOut) {
		InventoryOut = inventoryOut;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getDeliveryDate() {
		return DeliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		DeliveryDate = deliveryDate;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
