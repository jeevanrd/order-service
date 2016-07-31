package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

import java.util.List;

@Entity(noClassnameStored = true)
public class Order {
    @org.mongodb.morphia.annotations.Id
    private ObjectId Id;

    private List<InputItem> items;

    private models.Slot slot;

    private int cartonCount;

    public Order() {}

    public Order(Slot slot, int cartonCount, List<InputItem> items) {
        this.slot = slot;
        this.cartonCount = cartonCount;
        this.items = items;
    }

    public ObjectId getId() {
        return Id;
    }

    public void setId(ObjectId id) {
        Id = id;
    }

    public List<InputItem> getItems() {
        return items;
    }

    public void setItems(List<InputItem> items) {
        this.items = items;
    }

    public int getCartonCount() {
        return cartonCount;
    }

    public void setCartonCount(int cartonCount) {
        this.cartonCount = cartonCount;
    }

    public models.Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

}