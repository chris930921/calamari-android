package com.cephmonitor.cephmonitor.model.logic;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;

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

    public static final int UNIT_OF_YEAR = R.string.other_time_unit_year;
    public static final int UNIT_OF_MONTH = R.string.other_time_unit_month;
    public static final int UNIT_OF_DAY = R.string.other_time_unit_day;
    public static final int UNIT_OF_HOUR = R.string.other_time_unit_hour;
    public static final int UNIT_OF_MIN = R.string.other_time_unit_min;
    public static final int UNIT_OF_SECOND = R.string.other_time_unit_second;
    public static final int NOW = R.string.other_time_unit_just_now;

    public static String change(long millisTimeStampPeriod, Context context) {
        String result;
        if (millisTimeStampPeriod < 0)
            millisTimeStampPeriod = -millisTimeStampPeriod;

        if (millisTimeStampPeriod == 0) {
            result = context.getString(NOW);

        } else if (millisTimeStampPeriod >= SECOND_OF_YEAR) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_YEAR);
            int plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_YEAR, plurality, context);

        } else if (millisTimeStampPeriod >= SECOND_OF_MONTH) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_MONTH);
            int plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_MONTH, plurality, context);

        } else if (millisTimeStampPeriod >= SECOND_OF_DAY) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_DAY);
            int plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_DAY, plurality, context);

        } else if (millisTimeStampPeriod >= SECOND_OF_HOUR) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_HOUR);
            int plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_HOUR, plurality, context);

        } else if (millisTimeStampPeriod >= SECOND_OF_MIN) {
            int count = (int) (millisTimeStampPeriod / SECOND_OF_MIN);
            int plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_MIN, plurality, context);

        } else {
            int count = (int) millisTimeStampPeriod;
            int plurality = checkPlurality(count);
            result = combineResult(count, UNIT_OF_SECOND, plurality, context);

        }
        return result;
    }

    private static int checkPlurality(int count) {
        return (count > 1) ? R.string.other_time_unit_multiple : R.string.other_empty;
    }

    private static String combineResult(int count, int unit, int plurality, Context context) {
        return count + " " +
                context.getString(unit) +
                context.getString(plurality) +
                context.getString(R.string.health_card_ago);
    }
}
