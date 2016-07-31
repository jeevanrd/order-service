package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class SlotVehicle {

    private int cartonCount;

    private int vehicleCount;

    private Dimensions cartonDimensions;

    public SlotVehicle(int vehicleCount, int cartonCount, Dimensions cartonDimensions) {
        this.vehicleCount = vehicleCount;
        this.cartonCount = cartonCount;
        this.cartonDimensions = cartonDimensions;
    }

    public int getCartonCount() {
        return cartonCount;
    }

    public void setCartonCount(int cartonCount) {
        this.cartonCount = cartonCount;
    }

    public Dimensions getCartonDimensions() {
        return cartonDimensions;
    }

    public void setCartonDimensions(Dimensions cartonDimensions) {
        this.cartonDimensions = cartonDimensions;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public int getMaxCartonCount() {
        return this.vehicleCount * this.cartonCount;
    }
}
