package com.resourcelibrary.model.logic;

/**
 * Created by User on 5/26/2015.
 */
public class TimeUnit {
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
    public static final String UNIT_OF_MIN = "min";
    public static final String UNIT_OF_SECOND = "second";
    public static final String NOW = "JUST NOW";

    public static String change(long millisTimeStampPeriod) {
        String result;
        if (millisTimeStampPeriod < 0)
            millisTimeStampPeriod = -millisTimeStampPeriod;

        if (millisTimeStampPeriod == 0) {
            result = NOW;

        } else if (millisTimeStampPeriod >= SECOND_OF_YEAR) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_YEAR);
            String plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_YEAR, plurality);

        } else if (millisTimeStampPeriod >= SECOND_OF_MONTH) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_MONTH);
            String plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_MONTH, plurality);

        } else if (millisTimeStampPeriod >= SECOND_OF_DAY) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_DAY);
            String plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_DAY, plurality);

        } else if (millisTimeStampPeriod >= SECOND_OF_HOUR) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_HOUR);
            String plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_HOUR, plurality);

        } else if (millisTimeStampPeriod >= SECOND_OF_MIN) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_MIN);
            String plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_MIN, plurality);

        } else {
            int count = (int) millisTimeStampPeriod;
            String plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_SECOND, plurality);

        }
        return result;
    }

    private static String checkPlurality(int count) {
        return (count > 1) ? "s" : "";
    }

    private static String combineResult(int count, String unit, String plurality) {
        return count + " " + unit + plurality + " ago";
    }
}
