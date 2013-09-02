package sushil.luc.dtos;

/**
 * Data Transfer Object
 * to store Inventory Info Object from web api
 */
public class InventoryInfoDTO {
	private int id;
	private int type;
	private double inventoryTotal;
	private double inventoryOnHand;
	private double inventoryOut;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getInventoryTotal() {
		return inventoryTotal;
	}
	public void setInventoryTotal(double inventoryTotal) {
		this.inventoryTotal = inventoryTotal;
	}
	public double getInventoryOnHand() {
		return inventoryOnHand;
	}
	public void setInventoryOnHand(double inventoryOnHand) {
		this.inventoryOnHand = inventoryOnHand;
	}
	public double getInventoryOut() {
		return inventoryOut;
	}
	public void setInventoryOut(double inventoryOut) {
		this.inventoryOut = inventoryOut;
	}
	

}
