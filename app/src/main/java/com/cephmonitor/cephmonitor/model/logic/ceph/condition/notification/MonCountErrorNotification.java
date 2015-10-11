package com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.compare.IncrementStrategy;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;

import org.json.JSONException;

/**
 * Created by User on 5/13/2015.
 */
public class MonCountErrorNotification extends ConditionNotification<ClusterV1HealthCounterData> {
    private SettingStorage settingStorage;
    private int monitorType = 2;
    private int level = 2;
    private int monitorNumber = 1;
    private IncrementStrategy comparePattern;

    public MonCountErrorNotification(Context context) {
        super(context);
        settingStorage = new SettingStorage(getContext());
    }

    @Override
    protected void decide(ClusterV1HealthCounterData data) {
        long compareValue = 0;
        try {
            compareValue = data.getMonErrorCount();
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
                compareValue - settingStorage.getAlertTriggerMonError() + 1,
                monitorType,
                level,
                monitorNumber,
                R.string.check_service_022001_abnormal_content_new,
                R.string.check_service_022001_abnormal_content_more,
                R.string.check_service_022001_abnormal_content_relapse,
                R.string.check_service_022001_normal_content_finish,
                R.string.check_service_022001_abnormal_title,
                R.string.check_service_022001_normal_title,
                CephNotificationConstant.WARNING_TYPE_ERROR
        );
        comparePattern.compare();
    }
}
