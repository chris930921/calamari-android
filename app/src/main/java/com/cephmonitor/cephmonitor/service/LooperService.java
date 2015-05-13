package com.cephmonitor.cephmonitor.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class LooperService extends Service {
    private Boolean isFirstOpen = true;

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isFirstOpen) {
            startForeground(1, new NotificationCompat.Builder(this).setContentTitle("").build());
            init();
            isFirstOpen = false;
        }
        return START_REDELIVER_INTENT;
    }

    private void init() {
        Calendar cal = Calendar.getInstance();
        PendingIntent intent = ServiceLauncher.pendingRequestAndCheckService(this);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30000, intent);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
