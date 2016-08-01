package models;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SlotScheduleTest {
    @Test
    public void validateModelSetting(){
        SlotSchedule slotSchedule = new SlotSchedule(2, 4, 2, new ArrayList<Integer>());
        assertThat(slotSchedule.getStart(), is(2));
        assertThat(slotSchedule.getEnd(), is(4));
        assertThat(slotSchedule.getDuration(), is(2));
        assertThat(slotSchedule.getSlots().size(), is(1));
    }

    @Test
    public void shouldGiveZeroAvailableSlotsIfScheduleStartTimeLessThanEndTime(){
        SlotSchedule slotSchedule = new SlotSchedule(4, 2, 2, new ArrayList<Integer>());
        assertThat(slotSchedule.getSlots().size(), is(0));
    }

}
