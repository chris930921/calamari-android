package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.app.Notification;
import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.notification.style.CephDefaultNotification;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1Space;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class UsagePercentErrorNotification extends ConditionNotification<ClusterV1Space> {
    public static final int WARN_PERCENT_MAX = 80;

    public UsagePercentErrorNotification(Context context) {
        super(context);
    }

    @Override
    protected boolean decide(ClusterV1Space data) {
        try {
            double percent = (double) data.getUsedBytes() / (double) data.getCapacityBytes();
            int comparePercent = (int) (percent * 100);
            return comparePercent >= WARN_PERCENT_MAX;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected Notification onTrue(ClusterV1Space data) {
        String title = getContext().getResources().getString(R.string.check_service_usage_percent_error_title);
        String content = getContext().getResources().getString(R.string.check_service_usage_percent_error_content);

        return CephDefaultNotification.get(getContext(), title, content);
    }
}
