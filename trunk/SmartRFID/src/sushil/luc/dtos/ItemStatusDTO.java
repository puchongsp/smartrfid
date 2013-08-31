package sushil.luc.dtos;

/**
 * Created by sushil
 */
public class ItemStatusDTO {
    private String id;
    private int isStaged;
    private int isChecked;
    private int isReturned;
    // if all are false - what does it mean
    // if isReturned is true and all other false = what does it mean

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

    public int isChecked() {
        return isChecked;
    }

    public void setChecked(int checked) {
        isChecked = checked;
    }

    public int isReturned() {
        return isReturned;
    }

    public void setReturned(int returned) {
        isReturned = returned;
    }
}
