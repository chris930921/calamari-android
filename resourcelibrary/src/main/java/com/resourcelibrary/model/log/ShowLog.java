package com.resourcelibrary.model.log;

import android.util.Log;

/**
 * Created by User on 2/2/2015.
 */
public class ShowLog {
    public static final String TAG = "Ceph App 除錯訊息";
    public static final boolean isShowLog = true;
    public static final boolean isShowError = true;

    public static void e(String content) {
        if (isShowLog) {
            Log.e(TAG, content);
        }
    }

    public static void e(String content, Exception e) {
        if (isShowError) {
            Log.e(TAG, content, e);
        } else {
            e(content);
        }
    }

    public static void d(String content) {
        if (isShowLog) {
            Log.d(TAG, content);
        }
    }

    public static void d(String content, Exception e) {
        if (isShowError) {
            Log.d(TAG, content, e);
        } else {
            d(content);
        }
    }
}
