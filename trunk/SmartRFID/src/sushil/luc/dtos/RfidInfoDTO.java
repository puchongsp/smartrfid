package sushil.luc.dtos;

/**
 * Created by sushil
 */
public class RfidInfoDTO {
    private int $id;
    private String rfidNumber;

    public int getId() {
        return $id;
    }

    public void set$Id(int id) {
        this.$id = id;
    }

    public String getRfidNumber() {
        return rfidNumber;
    }

    public void setRfidNumber(String rfidNumber) {
        this.rfidNumber = rfidNumber;
    }
}
