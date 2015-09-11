package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.compare.RecordedPatternThree;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1Space;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class UsagePercentErrorNotification extends ConditionNotification<ClusterV1Space> {
    private int monitorType = 4;
    private int level = 2;
    private int monitorNumber = 1;
    private RecordedPatternThree comparePattern;

    public UsagePercentErrorNotification(Context context) {
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
        ShowLog.d("監控訊息: 錯誤數值是 " + comparePercent);
        comparePattern = new RecordedPatternThree();
        comparePattern.setParams(
                getContext(),
                getCheckResult(),
                comparePercent,
                monitorType,
                level,
                monitorNumber,
                R.string.check_service_042001_abnormal_content_new,
                R.string.check_service_042001_normal_content_finish,
                R.string.check_service_042001_abnormal_title,
                R.string.check_service_042001_normal_title,
                CephNotificationConstant.WARNING_TYPE_ERROR,
                8500
        );
        comparePattern.compare();
    }
}
