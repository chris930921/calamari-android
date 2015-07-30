package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.app.Notification;
import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.logic.CompareString;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.notification.style.CephDefaultNotification;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class OsdCountErrorNotification extends ConditionNotification<ClusterV1HealthCounterData> {

    public OsdCountErrorNotification(Context context) {
        super(context);
    }

    @Override
    protected boolean decide(ClusterV1HealthCounterData data) {
        Boolean check = true;
        String previousStatus = getClassSelfStatus().loadStatus();
        try {
            int compareValue = data.getOsdErrorCount();
            check &= CompareString.notEqualInt(previousStatus, compareValue);
            check &= compareValue > 0;
        } catch (JSONException e) {
            e.printStackTrace();
            check = false;
        }
        return check;
    }

    @Override
    protected Notification onTrue(ClusterV1HealthCounterData data) {
        try {
            String title = getContext().getResources().getString(R.string.check_service_osd_count_error_title);
            String content = String.format(
                    getContext().getResources().getString(R.string.check_service_osd_count_error_content),
                    data.getOsdErrorCount()
            );
            String status = ClusterV1HealthData.HEALTH_ERR;
            CephDefaultNotification.save(getContext(), title, content, status);

            getClassSelfStatus().saveStatus(data.getOsdErrorCount() + "");
            return CephDefaultNotification.get(getContext(), title, content);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
