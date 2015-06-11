package com.cephmonitor.cephmonitor.model.notification.style;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.database.NotificationRow;
import com.cephmonitor.cephmonitor.model.database.StoreNotifications;

import java.util.ArrayList;

/**
 * Created by User on 2015/6/2.
 */
public class CephDefaultNotification {
    public static Notification get(Context context, String title, String content) {
        StoreNotifications database = new StoreNotifications(context);
        ArrayList<NotificationRow> messages = database.findTopEight();

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        for (int i = 0; i < messages.size(); i++) {
            String message = messages.get(i).content;
            style.addLine(message);
        }

        Notification notification = new NotificationCompat.Builder(context)
                .setContentIntent(null)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(style)
                .build();
        notification.priority = Notification.PRIORITY_MAX;
        return notification;
    }

    public static void save(Context context, String title, String content, String status) {
        StoreNotifications database = new StoreNotifications(context);
        database.save(title, content, status);
    }
}
