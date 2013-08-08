package sushil.luc.dtos;

import java.util.List;

/**
 * Created by sushil
 */
public class TicketDTO {
    private String id;
    private double identifier;
    private List<ItemDTO> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getIdentifier() {
        return identifier;
    }

    public void setIdentifier(double identifier) {
        this.identifier = identifier;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
