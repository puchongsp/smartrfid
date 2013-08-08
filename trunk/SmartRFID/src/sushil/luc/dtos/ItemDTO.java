package sushil.luc.dtos;

/**
 * Created by sushil
 */
public class ItemDTO {
    private String id;
    private double ticketIdentifier;
    private double ticketItemNumber;
    private ItemInfoDTO ticketItemInfo;
    private RentInformationDTO rentInformation;
    private ItemStatusDTO itemStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
