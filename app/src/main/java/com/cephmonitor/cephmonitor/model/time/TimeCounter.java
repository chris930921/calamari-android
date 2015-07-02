package com.cephmonitor.cephmonitor.model.time;

import android.os.SystemClock;

import com.resourcelibrary.model.log.ShowLog;

/**
 * Created by User on 2015/7/1.
 */
public class TimeCounter {
    private static long start = SystemClock.elapsedRealtime();

    public static void start() {
        start = SystemClock.elapsedRealtime();
    }

    public static void end(String content) {
        ShowLog.d(content + " : " + (SystemClock.elapsedRealtime() - start) + " 毫秒");
        start = SystemClock.elapsedRealtime();
    }
}
