package com.resourcelibrary.model.logic;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by User on 1/23/2015.
 */
public class TimeConverter {

    public static String getDateStartTime(long timestamp) {
        Date date = new Date(timestamp);
        Calendar time = Calendar.getInstance();
        time.setTime(date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowText = formatter.format(time.getTime()) + "T00:00:00";

        return nowText;
    }

    public static String getDateEndTime(long timestamp) {
        Date date = new Date(timestamp);
        Calendar time = Calendar.getInstance();
        time.setTime(date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowText = formatter.format(time.getTime()) + "T23:59:59";

        return nowText;
    }


    public static String getDateStartHour(long timestamp, int hour) {
        Date date = new Date(timestamp);
        Calendar time = Calendar.getInstance();
        time.setTime(date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowText = formatter.format(time.getTime()) + "T" + String.format("%02d", hour) + ":00:00";

        return nowText;
    }

    public static String getDateEndHour(long timestamp, int hour) {
        Date date = new Date(timestamp);
        Calendar time = Calendar.getInstance();
        time.setTime(date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nowText = formatter.format(time.getTime()) + "T" + String.format("%02d", hour) + ":59:59";

        return nowText;
    }
    public static String getDateAndTime(long timestamp){
        Date date = new Date(timestamp);
        Calendar time = Calendar.getInstance();
        time.setTime(date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return  formatter.format(time.getTime());
    }

    public static long getTimestamp(String timeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        long timestamp = 0;
        try {
            Date time = format.parse(timeStr);
            Calendar cale = Calendar.getInstance();
            cale.setTime(time);
            timestamp = cale.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp / 1000;
    }

    public static String getTime(long timestamp) {
        Date date = new Date(timestamp * 1000);
        Calendar time = Calendar.getInstance();
        time.setTime(date);

        Log.e("Monitor App Debug", TimeZone.getDefault().getID());

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String nowText = formatter.format(time.getTime());

        return nowText;
    }

    public static Calendar getCalendar(String timeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date time = format.parse(timeStr);
            Calendar cale = Calendar.getInstance();
            cale.setTime(time);
            return cale;
        } catch (ParseException e) {
            e.printStackTrace();
            return Calendar.getInstance();
        }
    }
}
