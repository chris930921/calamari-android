package com.cephmonitor.cephmonitor.model.logic;

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

    public static final String UNIT_OF_YEAR = "year";
    public static final String UNIT_OF_MONTH = "month";
    public static final String UNIT_OF_DAY = "day";
    public static final String UNIT_OF_HOUR = "hour";
    public static final String UNIT_OF_MIN = "minute";
    public static final String UNIT_OF_SECOND = "second";

    public static final String MULTIPLE_SYMBOL = "s";

    private long currentSeconds;
    private String result;

    public static String change(long seconds) {
        return new FullTimeDecorator().changeValue(seconds);
    }

    private FullTimeDecorator() {
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

    private void connectSuitableTime(long unit, String unitText) {
        if (currentSeconds >= unit) {
            int count = (int) (currentSeconds / unit);
            String plurality = checkMultiple(count);
            result = result + count + " " + unitText + plurality + " ";
            currentSeconds = currentSeconds - (unit * count);
        }
    }

    private String checkMultiple(int count) {
        return (count > 1) ? MULTIPLE_SYMBOL : "";
    }
}
