package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1Space;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class UsagePercentWarnNotification extends ConditionNotification<ClusterV1Space> {
    private static final float WARN_PERCENT_MIN = 0.7f;
    private double percent;

    public UsagePercentWarnNotification(Context context) {
        super(context);
    }

    @Override
    protected boolean decide(ClusterV1Space data) {
        try {
            percent = (double) data.getUsedBytes() / (double) data.getCapacityBytes();
            boolean result = percent >= WARN_PERCENT_MIN;
            result &= percent < UsagePercentErrorNotification.WARN_PERCENT_MAX;
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected Notification onTrue(ClusterV1Space data) {
        String title = getContext().getResources().getString(R.string.check_service_usage_percent_warn_title);
        String content = String.format(
                getContext().getResources().getString(R.string.check_service_usage_percent_warn_content),
                percent
        );

        Notification msg = new NotificationCompat.Builder(getContext())
                .setContentIntent(null)
                .setTicker(content)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .build();
        return msg;
    }
}
