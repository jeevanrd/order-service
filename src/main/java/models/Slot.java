package models;

import org.joda.time.DateTime;

public class Slot {
    private int duration;

    private int startTime;

    public Slot() {}

    public Slot(int startTime, int duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }


    public Boolean slotValid() {
        int currentHour = DateTime.now().getHourOfDay();
        return (this.startTime + this.duration) > currentHour;
    }

}
