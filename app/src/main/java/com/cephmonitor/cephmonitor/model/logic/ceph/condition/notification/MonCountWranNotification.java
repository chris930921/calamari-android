package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class MonCountWranNotification extends ConditionNotification<ClusterV1HealthCounterData> {

    public MonCountWranNotification(Context context) {
        super(context);
    }

    @Override
    public boolean decide(Context context,ClusterV1HealthCounterData data) {
        try {
            return data.getMonWarningCount() > 0;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Notification onTrue(Context context,ClusterV1HealthCounterData data) {
        try {
            String title = context.getResources().getString(R.string.check_service_mon_count_warn_title);
            String content = String.format(
                    context.getResources().getString(R.string.check_service_mon_count_warn_content),
                    data.getMonWarningCount()
            );

            Notification msg = new NotificationCompat.Builder(context)
                    .setContentIntent(null)
                    .setTicker(content)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(content)
                    .build();
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
