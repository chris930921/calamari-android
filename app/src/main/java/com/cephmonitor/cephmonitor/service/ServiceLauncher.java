package com.cephmonitor.cephmonitor.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 5/12/2015.
 */
public class ServiceLauncher {

    public static void startLooperService(Context context) {
        Intent intent = new Intent(context, LooperService.class);
        context.startService(intent);
    }

    public static void startRequestAndCheckService(Context context) {
        Intent intent = new Intent(context, RequestAndCheckService.class);
        context.startService(intent);
    }

    public static PendingIntent pendingRequestAndCheckService(Context context) {
        Intent intent = new Intent(context, RequestAndCheckService.class);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
