package com.cephmonitor.cephmonitor.model.database;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 2015/9/1.
 */
public class DatabaseParser {

    public static Calendar stringToCalendar(String time) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date triggeredDate = DatabaseConstant.datetimeFormat.parse(time);
            calendar.setTime(triggeredDate);
        } catch (Exception e) {
            e.printStackTrace();
            calendar = null;
        }
        return calendar;
    }

    public static Object timeToDatabaseValue(Calendar calendar) {
        if (calendar != null) {
            return DatabaseConstant.datetimeFormat.format(calendar.getTime());
        } else {
            return DatabaseConstant.NULL_VALUE;
        }
    }

    public static String replaceSymbolToParam(String text, Object[] paramGroup) {
        String[] subStringGroup = new String[paramGroup.length + 1];
        String symbol = " ? ";
        int offset = 0;
        // TODO 可以整理成字串分割取代的模組
        for (int i = 0; i < paramGroup.length; i++) {
            int end = text.indexOf(symbol, offset) + symbol.length();
            subStringGroup[i] = text.substring(offset, end);
            offset = end;
        }
        subStringGroup[subStringGroup.length - 1] = text.substring(offset, text.length());

        for (int i = 0; i < paramGroup.length; i++) {
            // 把[ ? ]取代成參數。
            Object param = paramGroup[i];
            String result;
            if (param == null) {
                result = "NULL";
            } else if (param instanceof DatabaseConstant.NullValue) {
                result = "NULL";
            } else if (param instanceof DataResourceName) {
                result = "`" + param + "`";
            } else if (param instanceof String) {
                result = "'" + param + "'";
            } else {
                result = String.valueOf(param);
            }
            result = " " + result + " ";
            subStringGroup[i] = subStringGroup[i].replace(symbol, result);
        }
        // TODO 可以整理成字串陣列合併成字串的模組
        StringBuilder subStringCombine = new StringBuilder();
        for (int i = 0; i < subStringGroup.length; i++) {
            subStringCombine.append(subStringGroup[i]);
        }
        text = subStringCombine.toString();
        Log.d("ShowDebug", text);
        return text;
    }
}