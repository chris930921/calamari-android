package com.cephmonitor.cephmonitor.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.cephmonitor.cephmonitor.receiver.ChangePeriodReceiver;
import com.resourcelibrary.model.log.ShowLog;

import java.util.Calendar;

public class LooperService extends Service {
    private Boolean isFirstOpen = true;
    private PendingIntent intent;
    private AlarmManager alarm;
    private ChangePeriodReceiver receiver;
    private int periodStatus;

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isFirstOpen) {
            startForeground(1, new NotificationCompat.Builder(this).setContentTitle("").build());
            init();
            isFirstOpen = false;
        }
        return START_REDELIVER_INTENT;
    }

    private void init() {
        periodStatus = -1;
        intent = ServiceLauncher.pendingRequestAndCheckService(this);
        alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        changeDefaultPeriod();
        receiver = new ChangePeriodReceiver(this);
        receiver.register();
    }

    public void changeDefaultPeriod() {
        if (periodStatus != 0) {
            periodStatus = 0;
            ShowLog.d("切換到預設週期 30 秒");
            changePeriod(30000);
        }
    }

    public void changeCheckPeriod() {
        if (periodStatus != 1) {
            periodStatus = 1;
            ShowLog.d("切換到預設週期 2 分鐘");
            changePeriod(1000 * 60 * 2);
        }
    }

    public void changeServerErrorPeriod() {
        if (periodStatus != 2) {
            periodStatus = 2;
            ShowLog.d("切換到預設週期 1 小時");
            changePeriod(1000 * 60 * 60);
        }
    }

    private void changePeriod(long millis) {
        alarm.cancel(intent);
        Calendar cal = Calendar.getInstance();
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + millis, millis, intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiver.unregister();
        alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(intent);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
