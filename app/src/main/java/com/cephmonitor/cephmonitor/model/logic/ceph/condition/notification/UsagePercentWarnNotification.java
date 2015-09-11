package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.compare.RecordedPatternFour;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1Space;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class UsagePercentWarnNotification extends ConditionNotification<ClusterV1Space> {
    private int monitorType = 4;
    private int level = 3;
    private int monitorNumber = 1;
    private RecordedPatternFour comparePattern;

    public UsagePercentWarnNotification(Context context) {
        super(context);
    }

    @Override
    protected void decide(ClusterV1Space data) {
        int comparePercent;
        try {
            double percent = (double) data.getUsedBytes() / (double) data.getCapacityBytes();
            comparePercent = (int) (percent * 10000);
        } catch (JSONException e) {
            e.printStackTrace();
            getCheckResult().isSendNotification = false;
            getCheckResult().isCheckError = false;
            return;
        }
        ShowLog.d("監控訊息: 警告數值是 " + comparePercent);
        comparePattern = new RecordedPatternFour();
        comparePattern.setParams(
                getContext(),
                getCheckResult(),
                comparePercent,
                monitorType,
                level,
                monitorNumber,
                R.string.check_service_043001_abnormal_content_new,
                R.string.check_service_043001_normal_content_finish,
                R.string.check_service_043001_abnormal_title,
                R.string.check_service_043001_normal_title,
                CephNotificationConstant.WARNING_TYPE_WARNING,
                7000,
                8500
        );
        comparePattern.compare();
    }
}
