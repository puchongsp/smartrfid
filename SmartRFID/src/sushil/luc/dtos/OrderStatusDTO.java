package sushil.luc.dtos;

/**
 * Created by sushil
 */
public class OrderStatusDTO {
    private String id;
    private int isStaged;
    private int isCheckedOut;
    private int isReturned;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int isStaged() {
        return isStaged;
    }

    public void setStaged(int staged) {
        isStaged = staged;
    }

    public int isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(int checkedOut) {
        isCheckedOut = checkedOut;
    }

	public int isReturned() {
		return isReturned;
	}

	public void setReturned(int isReturned) {
		this.isReturned = isReturned;
	}
}
