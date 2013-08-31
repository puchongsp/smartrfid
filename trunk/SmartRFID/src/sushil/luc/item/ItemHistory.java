package sushil.luc.item;

import sushil.luc.dtos.RepairHistoryDTO;

public class ItemHistory {
	
	private String desc;
	private String timestamp;
	private int id;
	
	public ItemHistory (RepairHistoryDTO dto)
	{
		this.desc = dto.getDescription();
		this.timestamp = dto.getTimestamp();
		this.id = dto.getId();
		
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
