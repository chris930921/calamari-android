package com.cephmonitor.cephmonitor.model.logic;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import com.cephmonitor.cephmonitor.BuildConfig;
import com.cephmonitor.cephmonitor.model.file.io.ClassSelfStatus;

/**
 * Created by User on 5/13/2015.
 */
public abstract class ConditionNotification<T> {
    public static final int NOTIFICATION_ID = (BuildConfig.IS_LOCALHOST) ? 9218 : 4937; // 隨機定義的通知編號，沒有特別意義。
    private Context context;
    private ClassSelfStatus statusManager;

    public ConditionNotification(Context context) {
        this.context = context;
        this.statusManager = new ClassSelfStatus(getClass(), getContext());
    }

    public void check(T data) {
        boolean isAbove = decide(data);
        Notification msg;

        if (isAbove) {
            msg = onTrue(data);
        } else {
            msg = onFalse(data);
        }

        if (msg != null) {
            msg.defaults = Notification.DEFAULT_ALL;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, msg);
        }
    }

    protected ClassSelfStatus getClassSelfStatus() {
        return statusManager;
    }

    protected Context getContext() {
        return context;
    }

    protected abstract boolean decide(T data);

    protected Notification onTrue(T data) {
        return null;
    }

    protected Notification onFalse(T data) {
        return null;
    }
}
