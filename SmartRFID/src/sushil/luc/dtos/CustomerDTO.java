package sushil.luc.dtos;

/**
 * Data Transfer Object
 * to store Customer Object from web api
 */
public class CustomerDTO {
    private String id;
    private String customerName;
    private String accountNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
