package sushil.luc.dtos;

/**
 * Data Transfer Object
 * to store OrderInfo Object received from web api
 */
public class OrderInfoDTO {
    private String id;
    private OrderAddressDTO address;
    private String creationDate;
    private String deliveryDate;
    private String dueDate;
    private String jobNumber;
    private String office;

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

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
}
