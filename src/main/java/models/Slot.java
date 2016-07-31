package models;

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
}
