package com.cephmonitor.cephmonitor.model.logic;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by User on 5/13/2015.
 */
public abstract class ConditionNotification<T> {
    private Context context;

    public ConditionNotification(Context context) {
        this.context = context;
    }

    public void check(T data) {
        try {
            boolean isAbove = decide(context, data);
            Notification msg;

            if (isAbove) {
                msg = onTrue(context, data);
            } else {
                msg = onFalse(context, data);
            }

            if (msg != null) {
                msg.defaults = Notification.DEFAULT_ALL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(hashCode(), msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract boolean decide(Context context, T data);

    protected Notification onTrue(Context context, T data) {
        return null;
    }

    protected Notification onFalse(Context context, T data) {
        return null;
    }
}
