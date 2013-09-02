package sushil.luc.dtos;

/**
 * Data Transfer Object
 * to store Order Object received from web api
 */
public class OrderDTO {
    private String id;
    private String identifier;
    private CustomerDTO customer;
    private OrderStatusDTO status;
    private OrderInfoDTO info;

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

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public OrderStatusDTO getStatus() {
        return status;
    }

    public void setStatus(OrderStatusDTO status) {
        this.status = status;
    }

    public OrderInfoDTO getInfo() {
        return info;
    }

    public void setInfo(OrderInfoDTO info) {
        this.info = info;
    }
}
