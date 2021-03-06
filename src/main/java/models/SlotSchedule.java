package models;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class SlotSchedule {
    private int start;

    private int end;

    private int duration;

    private List<Integer> nonAvailabilityTimings;

    public SlotSchedule(){}

    public SlotSchedule(int start, int end, int duration, List<Integer> nonAvailabilityTimings) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.nonAvailabilityTimings = nonAvailabilityTimings;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Integer> getNonAvailabilityTimings() {
        return nonAvailabilityTimings;
    }

    public void setNonAvailabilityTimings(List<Integer> nonAvailabilityTimings) {
        this.nonAvailabilityTimings = nonAvailabilityTimings;
    }

    public List<Slot> getAvailableSlots() {
        List<Slot> slots = getSlots();
        List<Slot> availableSlots = new ArrayList<>();
        for(Slot slot: slots) {
            if (slot.slotValid())
                availableSlots.add(slot);
        }
        return availableSlots;
    }

    public List<Slot> getSlots() {
        int currentHour = DateTime.now().getHourOfDay();
        List<Slot> slots = new ArrayList<>();
        if(this.end < this.start)
            return slots;

        for(int i = this.start; i< this.end; i+= this.duration) {

            if(this.nonAvailabilityTimings.contains(i)) {
                i++;
            }

            Slot slot = new Slot(i, this.duration);
            slots.add(slot);
        }
        return slots;
    }
}
