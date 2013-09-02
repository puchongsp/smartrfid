package sushil.luc.dtos;

/**
 * Data Transfer Object
 * to store rfidInfo Object received from web api
 */
public class RfidInfoDTO {
    private int id;
    private String rfidNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRfidNumber() {
        return rfidNumber;
    }

    public void setRfidNumber(String rfidNumber) {
        this.rfidNumber = rfidNumber;
    }
}
