package sushil.luc.dtos;



import java.util.Date;

import sushil.luc.utils.DateUtil;

/**
 * Created by sushil
 */
public class ItemInfoDTO {
    private int $id;
    private String identifier;
    private String statusNote;
    private String category;
    private String subCategory;
    private String creationDate;
    private String description;
    private String binLocation;
    private RfidInfoDTO rfidInfo;


    public int getId() {
        return $id;
    }

    public void setId(int id) {
        this.$id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBinLocation() {
        return binLocation;
    }

    public void setBinLocation(String binLocation) {
        this.binLocation = binLocation;
    }

    public RfidInfoDTO getRfidInfo() {
        return rfidInfo;
    }

    public void setRfidInfo(RfidInfoDTO rfidInfo) {
        this.rfidInfo = rfidInfo;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getStatusNote() {
		return statusNote;
	}

	public void setStatusNote(String statusNote) {
		this.statusNote = statusNote;
	}
}
