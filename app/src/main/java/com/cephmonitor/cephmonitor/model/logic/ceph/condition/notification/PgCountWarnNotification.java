package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.app.Notification;
import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.logic.CompareString;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.notification.style.CephDefaultNotification;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class PgCountWarnNotification extends ConditionNotification<ClusterV1HealthCounterData> {
    private float percent;

    public PgCountWarnNotification(Context context) {
        super(context);
    }

    @Override
    protected boolean decide(ClusterV1HealthCounterData data) {
        Boolean check = true;
        String previousStatus = getClassSelfStatus().loadStatus();
        try {
            float warnCount = data.getPlacmentGroupsWarningCount();
            float totalCount = data.getPlacmentGroupsTotalCount();
            percent = warnCount / totalCount;

            int compareValue = data.getPlacmentGroupsWarningCount();
            check &= CompareString.notEqualFloat(previousStatus, compareValue);
            check &= percent > 0.2;
            ShowLog.d(getClass().getName() + " 判斷比較:compareValue " + compareValue + " previousStatus " + previousStatus + " percent " + percent);
        } catch (JSONException e) {
            e.printStackTrace();
            check = false;
        }
        return check;
    }

    @Override
    protected Notification onTrue(ClusterV1HealthCounterData data) {
        try {
            String title = getContext().getResources().getString(R.string.check_service_pg_count_warn_title);
            String content = String.format(
                    getContext().getResources().getString(R.string.check_service_pg_count_warn_content),
                    data.getPlacmentGroupsWarningCount()
            );
            String status = ClusterV1HealthData.HEALTH_WARN;
            CephDefaultNotification.save(getContext(), title, content, status);

            getClassSelfStatus().saveStatus(data.getPlacmentGroupsWarningCount() + "");
            return CephDefaultNotification.get(getContext(), title, content);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
