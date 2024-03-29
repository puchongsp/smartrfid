package sushil.luc.dtos;

/**
 * Data Transfer Object
 * to store repairHistory Object received from web api
 */
public class RepairHistoryDTO {
    private int id;
    private String timestamp;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
