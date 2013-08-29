package sushil.luc.dtos;

/**
 * Created by sushil
 */
public class OrderStatusDTO {
    private String $id;
    private boolean isStaged;
    private boolean isCheckedOut;
    private boolean isReturned;

    public String getId() {
        return $id;
    }

    public void setId(String id) {
        this.$id = id;
    }

    public boolean isStaged() {
        return isStaged;
    }

    public void setStaged(boolean staged) {
        isStaged = staged;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

	public boolean isReturned() {
		return isReturned;
	}

	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}
}
