package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.compare.RecordedPatternTwo;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class PgCountErrorNotification extends ConditionNotification<ClusterV1HealthCounterData> {
    private int monitorType = 3;
    private int level = 2;
    private int monitorNumber = 1;
    private RecordedPatternTwo comparePattern;

    public PgCountErrorNotification(Context context) {
        super(context);
    }

    @Override
    protected void decide(ClusterV1HealthCounterData data) {
        float percent = 0;
        float errorCount;
        float totalCount;
        try {
            errorCount = data.getPlacmentGroupsErrorCount();
            totalCount = data.getPlacmentGroupsTotalCount();
            percent = errorCount / totalCount;
        } catch (JSONException e) {
            e.printStackTrace();
            getCheckResult().isSendNotification = false;
            getCheckResult().isCheckError = false;
            return;
        }

        comparePattern = new RecordedPatternTwo();
        comparePattern.setParams(
                getContext(),
                getCheckResult(),
                percent,
                (int) errorCount,
                (int) totalCount,
                monitorType,
                level,
                monitorNumber,
                R.string.check_service_032001_abnormal_content_new,
                R.string.check_service_032001_abnormal_content_more,
                R.string.check_service_032001_abnormal_content_relapse,
                R.string.check_service_032001_normal_content_finish,
                R.string.check_service_032001_abnormal_title,
                R.string.check_service_032001_normal_title,
                CephNotificationConstant.WARNING_TYPE_ERROR
        );
        comparePattern.compare();
    }
}
