package models;


import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SlotTest {
    @Test
    public void validateModelSetting(){
        Slot slot = new Slot(10, 2);
        assertThat(slot.getStartTime(), is(10));
        assertThat(slot.getDuration(), is(2));
    }

    @Test
    public void shouldValidateSlotAsInvalid(){
        int currentHour = DateTime.now().getHourOfDay();
        Slot slot = new Slot(currentHour - 2, 2);
        assertThat(slot.slotValid(), is(false));
    }

    @Test
    public void shouldValidateSlotAsvalid(){
        int currentHour = DateTime.now().getHourOfDay();
        Slot slot = new Slot(currentHour, 2);
        assertThat(slot.slotValid(), is(true));
    }
}
