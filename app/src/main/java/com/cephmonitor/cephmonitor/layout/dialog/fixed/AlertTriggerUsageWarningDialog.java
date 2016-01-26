package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.android.volley.Response;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.AlertTriggerUsagePercentageDialog;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.logic.AlertTriggerDialogRequest;
import com.cephmonitor.cephmonitor.model.network.remotesetting.RemoteSettingApiUrl;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;

/**
 * Created by chriske on 2015/10/10.
 */
public class AlertTriggerUsageWarningDialog extends AlertTriggerUsagePercentageDialog {
    private SettingStorage storage;

    public AlertTriggerUsageWarningDialog(Context context) {
        super(context);
        storage = new SettingStorage(getContext());

        setTitle(getContext().getString(R.string.settings_alert_triggers_usage_warning_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_usage));
        getCalculator().setTotal(storage.getAlertTriggerUsageTotal());
        getCalculator().setMaxPercentage(storage.getAlertTriggerUsageError());
        getCalculator().setMinPercentage(0.05F);
        getCalculator().setPartPercentage(storage.getAlertTriggerUsageWarning());
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final float realValue = getCalculator().getResultValue();
                int value = (int) (realValue * 100);

                LoginParams params = new LoginParams(getContext());
                String url = RemoteSettingApiUrl.apiV1UserMeUsageWarning(params);
                String resourceName = "usage_warning";
                AlertTriggerDialogRequest.start(getContext(), params, url, resourceName, value, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        storage.setAlertTriggerUsageWarning(realValue);
                        callTask();
                    }
                });
            }
        });
    }
}
