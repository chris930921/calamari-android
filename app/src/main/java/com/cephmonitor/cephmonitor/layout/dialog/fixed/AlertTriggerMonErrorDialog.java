package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.AlertTriggerMaxMinDialog;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerMonErrorDialog extends AlertTriggerMaxMinDialog {
    private SettingStorage storage;

    public AlertTriggerMonErrorDialog(Context context) {
        super(context);
        storage = new SettingStorage(getContext());

        long maxHalf = ((long) Math.floor(storage.getAlertTriggerMonTotal())) / 2;
        long max = maxHalf + ((maxHalf == 0) ? 1 : 0);
        setTitle(getContext().getString(R.string.settings_alert_triggers_monitor_error_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_mon));
        getCalculator().setMax(max);
        getCalculator().setMin(1);
        getCalculator().setResultValue(storage.getAlertTriggerMonError());
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginParams params = new LoginParams(getContext());
                final long value = getCalculator().getResultValue();
                start("mon_error", String.valueOf(value), "http://" + params.getHost() + ":" + params.getPort() + "/api/v1/user/me/mon/error", new Runnable() {
                    @Override
                    public void run() {
                        storage.setAlertTriggerMonError(value);
                    }
                });
            }
        });
    }
}
