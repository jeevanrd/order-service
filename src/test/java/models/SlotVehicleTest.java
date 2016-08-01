package models;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SlotVehicleTest {
    @Test
    public void validateModelSetting(){
        Dimensions cartonDimensions = new Dimensions(5, 5, 5);
        SlotVehicle vehicle = new SlotVehicle(1, 5, cartonDimensions);

        assertThat(vehicle.getVehicleCount(), is(1));
        assertThat(vehicle.getCartonCount(), is(5));
        assertThat(vehicle.getCartonDimensions().getBreadth(), is(5));
        assertThat(vehicle.getCartonDimensions().getHeight(), is(5));
        assertThat(vehicle.getCartonDimensions().getWidth(), is(5));
    }

    @Test
    public void validateMaxCartonCount(){
        Dimensions cartonDimensions = new Dimensions(5, 5, 5);
        assertThat(new SlotVehicle(1, 5, cartonDimensions).getMaxCartonCount(), is(5));
        assertThat(new SlotVehicle(2, 5, cartonDimensions).getMaxCartonCount(), is(10));
    }

}
