package sushil.luc.msc;

import android.location.Location;

import sushil.luc.dtos.CustomerDTO;

public class Customer {

    /*
     * to map models vars with fields in json
     */
    public static final String _ID = "id";
    public static final String _NAME = "name";
    public static final String _ADDRESS = "address";
    public static final String _PHONE = "phone";
    public static final String _GPS = "gps";


	private String CustomerID;
	private String Name;
	private String Address;
	private String Phone;
	private Location GPSLocation;
	
	public Customer()
	{

	}

    public Customer(CustomerDTO customerDTO) {
        this.CustomerID = customerDTO.getId();
        this.Name = customerDTO.getCustomerName();
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

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public Location getGPSLocation() {
		return GPSLocation;
	}

	public void setGPSLocation(Location gPSLocation) {
		GPSLocation = gPSLocation;
	}
}
