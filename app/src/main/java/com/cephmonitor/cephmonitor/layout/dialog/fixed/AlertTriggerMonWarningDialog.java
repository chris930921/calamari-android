package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.android.volley.Response;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.AlertTriggerMaxMinDialog;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.logic.AlertTriggerDialogRequest;
import com.cephmonitor.cephmonitor.model.network.remotesetting.RemoteSettingApiUrl;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerMonWarningDialog extends AlertTriggerMaxMinDialog {
    private SettingStorage storage;

    public AlertTriggerMonWarningDialog(Context context) {
        super(context);
        storage = new SettingStorage(getContext());

        long maxHalf = ((long) Math.floor(storage.getAlertTriggerMonTotal())) / 2;
        long max = maxHalf + ((maxHalf == 0) ? 1 : 0);
        setTitle(getContext().getString(R.string.settings_alert_triggers_monitor_warning_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_mon));
        getCalculator().setMax(max);
        getCalculator().setMin(1);
        getCalculator().setResultValue(storage.getAlertTriggerMonWarning());
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final long value = getCalculator().getResultValue();

                LoginParams params = new LoginParams(getContext());
                String url = RemoteSettingApiUrl.apiV1UserMeMonWarning(params);
                String resourceName = "mon_warning";
                AlertTriggerDialogRequest.start(getContext(), params, url, resourceName, value, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        storage.setAlertTriggerMonWarning(value);
                        callTask();
                    }
                });
            }
        });
    }
}
