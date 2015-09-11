package com.cephmonitor.cephmonitor.model.database;

import java.text.SimpleDateFormat;

/**
 * Created by User on 2015/9/1.
 */
public class DatabaseConstant {
    public static final NullValue NULL_VALUE = new NullValue();
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class NullValue {
    }
}
