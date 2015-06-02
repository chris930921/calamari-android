package com.cephmonitor.cephmonitor.model.notification.style;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.file.io.MessageList;

import java.util.ArrayList;

/**
 * Created by User on 2015/6/2.
 */
public class CephDefaultNotification {
    public static final Notification get(Context context, String title, String content) {
        MessageList messagesManager = new MessageList(context);
        messagesManager.addMessage(content);
        ArrayList<String> messages = messagesManager.getArray();

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        for (int i = messages.size() - 1; i >= 0; i--) {
            String message = messages.get(i);
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
}
