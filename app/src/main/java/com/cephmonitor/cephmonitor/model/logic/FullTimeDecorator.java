package com.cephmonitor.cephmonitor.model.logic;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;

/**
 * Created by User on 5/26/2015.
 */
public class FullTimeDecorator {
    public static final long SECOND_OF_YEAR = 31536000;
    public static final long SECOND_OF_MONTH = 2592000;
    public static final long SECOND_OF_DAY = 86400;
    public static final long SECOND_OF_HOUR = 3600;
    public static final long SECOND_OF_MIN = 60;
    public static final long SECOND = 1;

    public static final int UNIT_OF_YEAR = R.string.other_time_unit_year;
    public static final int UNIT_OF_MONTH = R.string.other_time_unit_month;
    public static final int UNIT_OF_DAY = R.string.other_time_unit_day;
    public static final int UNIT_OF_HOUR = R.string.other_time_unit_hour;
    public static final int UNIT_OF_MIN = R.string.other_time_unit_min;
    public static final int UNIT_OF_SECOND = R.string.other_time_unit_second;
    public static final int MULTIPLE_SYMBOL = R.string.other_time_unit_multiple;

    private long currentSeconds;
    private String result;
    private Context context;

    public static String change(Context context, long seconds) {
        return new FullTimeDecorator(context).changeValue(seconds);
    }

    private FullTimeDecorator(Context context) {
        this.context = context;
    }

    private String changeValue(long seconds) {
        if (seconds <= 0) {
            seconds = 0;
            return seconds + " " + UNIT_OF_SECOND;
        }

        result = "";
        currentSeconds = seconds;
        connectSuitableTime(SECOND_OF_YEAR, UNIT_OF_YEAR);
        connectSuitableTime(SECOND_OF_MONTH, UNIT_OF_MONTH);
        connectSuitableTime(SECOND_OF_DAY, UNIT_OF_DAY);
        connectSuitableTime(SECOND_OF_HOUR, UNIT_OF_HOUR);
        connectSuitableTime(SECOND_OF_MIN, UNIT_OF_MIN);
        connectSuitableTime(SECOND, UNIT_OF_SECOND);
        return result;
    }

    private void connectSuitableTime(long unit, int unitText) {
        if (currentSeconds >= unit) {
            int count = (int) (currentSeconds / unit);
            String plurality = checkMultiple(count);
            result = result + count + " " + context.getString(unitText) + plurality + " ";
            currentSeconds = currentSeconds - (unit * count);
        }
    }

    private String checkMultiple(int count) {
        return (count > 1) ? context.getString(MULTIPLE_SYMBOL) : "";
    }
}
