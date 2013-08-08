package sushil.luc.dtos;

/**
 * Created by sushil
 */
public class ItemInfoDTO {
    private String id;
    private String identifier;
    private String description;
    private String binLocation;
    private RfidInfoDTO rfidInfo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBinLocation() {
        return binLocation;
    }

    public void setBinLocation(String binLocation) {
        this.binLocation = binLocation;
    }

    public RfidInfoDTO getRfidInfo() {
        return rfidInfo;
    }

    public void setRfidInfo(RfidInfoDTO rfidInfo) {
        this.rfidInfo = rfidInfo;
    }
}
