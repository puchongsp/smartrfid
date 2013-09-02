package sushil.luc.msc;

import android.location.Location;

import sushil.luc.dtos.CustomerDTO;
import sushil.luc.dtos.OrderInfoDTO;

public class Customer {

	private String CustomerID;
	private String Name;
	private String Address;
	
	/**
	 * Create a new Customer object with the given DTO fie
	 * @param customerDTO
	 * @param orderInfoDTO
	 */
    public Customer(CustomerDTO customerDTO, OrderInfoDTO orderInfoDTO) {
        this.CustomerID = customerDTO.getId();
        this.Name = customerDTO.getCustomerName();
        if (orderInfoDTO!=null)
        {
        	this.Address = orderInfoDTO.getAddress().getAddress1()+" "
        					+ orderInfoDTO.getAddress().getCity()+" "
        					+orderInfoDTO.getAddress().getZip();
        }
    }

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String clientID) {
		CustomerID = clientID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}
}
