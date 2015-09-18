package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.compare.IncrementStrategy;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class OsdCountWarnNotification extends ConditionNotification<ClusterV1HealthCounterData> {
    private int monitorType = 1;
    private int level = 3;
    private int monitorNumber = 1;
    private IncrementStrategy comparePattern;

    public OsdCountWarnNotification(Context context) {
        super(context);
    }

    @Override
    protected void decide(ClusterV1HealthCounterData data) {
        int compareValue;
        try {
            compareValue = data.getOsdWarningCount();
        } catch (JSONException e) {
            e.printStackTrace();
            getCheckResult().isSendNotification = false;
            getCheckResult().isCheckError = false;
            return;
        }
        comparePattern = new IncrementStrategy();
        comparePattern.setParams(
                getContext(),
                getCheckResult(),
                compareValue,
                monitorType,
                level,
                monitorNumber,
                R.string.check_service_013001_abnormal_content_new,
                R.string.check_service_013001_abnormal_content_more,
                R.string.check_service_013001_abnormal_content_relapse,
                R.string.check_service_013001_normal_content_finish,
                R.string.check_service_013001_abnormal_title,
                R.string.check_service_013001_normal_title,
                CephNotificationConstant.WARNING_TYPE_WARNING
        );
        comparePattern.compare();
    }
}
