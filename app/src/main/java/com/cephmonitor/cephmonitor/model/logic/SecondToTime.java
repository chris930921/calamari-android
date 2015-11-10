package com.cephmonitor.cephmonitor.model.logic;

/**
 * Created by chriske on 2015/11/2.
 */
public class SecondToTime {
    private long totlsSeconds;
    private int hours;
    private int minute;
    private int seconds;

    public SecondToTime(long totalsSeconds) {
        this.totlsSeconds = totalsSeconds;
        int count = 0;

        count = (int) (totalsSeconds / (60 * 60));
        totalsSeconds -= count * 60 * 60;
        hours = count;

        count = (int) (totalsSeconds / 60);
        totalsSeconds -= count * 60;
        minute = count;

        seconds = (int) totalsSeconds;
    }

    public int getHour() {
        return hours;
    }

    public int getMintune() {
        return minute;
    }

    public int getSecond() {
        return seconds;
    }
}
