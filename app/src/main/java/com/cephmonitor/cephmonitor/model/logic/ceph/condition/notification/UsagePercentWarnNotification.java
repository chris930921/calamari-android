package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.app.Notification;
import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.notification.style.CephDefaultNotification;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1Space;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class UsagePercentWarnNotification extends ConditionNotification<ClusterV1Space> {
    private static final int WARN_PERCENT_MIN = 70;
    private double percent;

    public UsagePercentWarnNotification(Context context) {
        super(context);
    }

    @Override
    protected boolean decide(ClusterV1Space data) {
        try {
            percent = (double) data.getUsedBytes() / (double) data.getCapacityBytes();
            int comparePercent = (int) (percent * 100);
            boolean result = true;
            result &= comparePercent >= WARN_PERCENT_MIN;
            result &= comparePercent < UsagePercentErrorNotification.WARN_PERCENT_MAX;
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
                percent * 100
        );
        String status = ClusterV1HealthData.HEALTH_WARN;
        CephDefaultNotification.save(getContext(), title, content, status);

        return CephDefaultNotification.get(getContext(), title, content);
    }
}
