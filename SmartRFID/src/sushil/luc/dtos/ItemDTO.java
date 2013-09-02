package sushil.luc.dtos;

/**
 * Data Transfer Object
 * to store Item Object from web api
 */
public class ItemDTO {
    private int id;
    private String identifier;
    private double ticketIdentifier;
    private double ticketItemNumber;
    private ItemInfoDTO ticketItemInfo;
    private ItemInfoDTO itemInfo;
    private RentInformationDTO rentInformation;
    private ItemStatusDTO itemStatus;
    private InventoryInfoDTO inventoryInfo;
   
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTicketIdentifier() {
        return ticketIdentifier;
    }

    public void setTicketIdentifier(double ticketIdentifier) {
        this.ticketIdentifier = ticketIdentifier;
    }

    public double getTicketItemNumber() {
        return ticketItemNumber;
    }

    public void setTicketItemNumber(double ticketItemNumber) {
        this.ticketItemNumber = ticketItemNumber;
    }

    public ItemInfoDTO getTicketItemInfo() {
        return ticketItemInfo;
    }

    public void setTicketItemInfo(ItemInfoDTO ticketItemInfo) {
        this.ticketItemInfo = ticketItemInfo;
    }

    public RentInformationDTO getRentInformation() {
        return rentInformation;
    }

    public void setRentInformation(RentInformationDTO rentInformation) {
        this.rentInformation = rentInformation;
    }

    public ItemStatusDTO getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatusDTO itemStatus) {
        this.itemStatus = itemStatus;
    }

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public ItemInfoDTO getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(ItemInfoDTO itemInfo) {
		this.itemInfo = itemInfo;
	}

	public InventoryInfoDTO getInventoryInfo() {
		return inventoryInfo;
	}

	public void setInventoryInfo(InventoryInfoDTO inventoryInfo) {
		this.inventoryInfo = inventoryInfo;
	}

	
}
