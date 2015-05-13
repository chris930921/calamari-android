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
public class PgCountErrorNotification extends ConditionNotification<ClusterV1HealthCounterData> {

    public PgCountErrorNotification(Context context) {
        super(context);
    }

    @Override
    protected boolean decide(ClusterV1HealthCounterData data) {
        try {
            return data.getPlacmentGroupsErrorCount() > 0;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected Notification onTrue(ClusterV1HealthCounterData data) {
        try {
            String title = getContext().getResources().getString(R.string.check_service_pg_count_error_title);
            String content = String.format(
                    getContext().getResources().getString(R.string.check_service_pg_count_error_content),
                    data.getPlacmentGroupsErrorCount()
            );

            Notification msg = new NotificationCompat.Builder(getContext())
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
