package sushil.luc.dtos;

/**
 * Created by sushil
 */
public class ItemStatusDTO {
    private String id;
    private boolean isStaged;
    private boolean isChecked;
    private boolean isReturned;
    // if all are false - what does it mean
    // if isReturned is true and all other false = what does it mean

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStaged() {
        return isStaged;
    }

    public void setStaged(boolean staged) {
        isStaged = staged;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
