package com.cephmonitor.cephmonitor.model.logic;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;

import com.cephmonitor.cephmonitor.ActivityLauncher;
import com.cephmonitor.cephmonitor.BuildConfig;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.database.StoreNotifications;
import com.cephmonitor.cephmonitor.model.database.data.RecordedData;
import com.cephmonitor.cephmonitor.model.database.data.TriggeredRecordedData;
import com.cephmonitor.cephmonitor.model.database.operator.RecordedOperator;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;

import java.util.ArrayList;

/**
 * Created by User on 5/13/2015.
 */
public abstract class ConditionNotification<T> {
    public static final int NOTIFICATION_ID = (BuildConfig.IS_LOCALHOST) ? 9218 : 4937; // 隨機定義的通知編號，沒有特別意義。
    private SettingStorage settingStorage;
    private CheckResult checkResult;
    private Context context;

    public ConditionNotification(Context context) {
        this.context = context;
    }

    public boolean check(T data) {
        settingStorage = new SettingStorage(getContext());
        checkResult = new CheckResult();
        decide(data);

        if (checkResult.isSendNotification && settingStorage.getNotifications()) {
            Notification msg = getNotification();
            msg.defaults = Notification.DEFAULT_ALL;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, msg);
        }
        return checkResult.isCheckError;
    }

    protected CheckResult getCheckResult() {
        return checkResult;
    }

    protected Context getContext() {
        return context;
    }

    protected abstract void decide(T data);

    protected Notification getNotification() {
        StoreNotifications store = new StoreNotifications(context);
        SQLiteDatabase database = store.getReadableDatabase();
        TriggeredRecordedData triggeredTimeGroup = new TriggeredRecordedData();
        triggeredTimeGroup.load(database);

        ArrayList<RecordedData> recordGroup = triggeredTimeGroup.recordGroup;
        RecordedOperator recordedOperator = new RecordedOperator(context);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        for (int i = 0; i < recordGroup.size() & i < 8; i++) {
            recordedOperator.setValue(recordGroup.get(i));
            String message = recordedOperator.getMessageWithParam();
            style.addLine(message);
        }

        recordedOperator.setValue(recordGroup.get(0));
        String title = recordedOperator.getTitleWithParam();
        String content = recordedOperator.getMessageWithParam();
        Notification notification = new NotificationCompat.Builder(context)
                .setContentIntent(ActivityLauncher.goMainActivityNotificationPending(context))
                .setTicker(title)
                .setSmallIcon(R.drawable.icon01)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(style)
                .build();
        notification.priority = Notification.PRIORITY_MAX;
        return notification;
    }
}
