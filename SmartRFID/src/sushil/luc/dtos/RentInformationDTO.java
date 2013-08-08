package sushil.luc.dtos;

/**
 * Created by sushil
 */
public class RentInformationDTO {
    private String id;
    private String startRent;
    private String stopRent;
    private String returnDate;
    private String originalStartRent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartRent() {
        return startRent;
    }

    public void setStartRent(String startRent) {
        this.startRent = startRent;
    }

    public String getStopRent() {
        return stopRent;
    }

    public void setStopRent(String stopRent) {
        this.stopRent = stopRent;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getOriginalStartRent() {
        return originalStartRent;
    }

    public void setOriginalStartRent(String originalStartRent) {
        this.originalStartRent = originalStartRent;
    }
}
