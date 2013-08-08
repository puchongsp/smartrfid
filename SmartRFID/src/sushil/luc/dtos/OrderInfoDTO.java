package sushil.luc.dtos;

/**
 * Created by sushil
 */
public class OrderInfoDTO {
    private String id;
    private OrderAddressDTO address;
    private String creationDate;
    private String dueDate;
    private String jobNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderAddressDTO getAddress() {
        return address;
    }

    public void setAddress(OrderAddressDTO address) {
        this.address = address;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
}
