package com.resourcelibrary.model.logic.notification;

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
            boolean isAbove = decide(data);
            Notification msg;

            if (isAbove) {
                msg = onTrue(data);
            } else {
                msg = onFalse(data);
            }

            if (msg != null) {
                int id = (int) System.currentTimeMillis();
                msg.defaults = Notification.DEFAULT_ALL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(id, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
