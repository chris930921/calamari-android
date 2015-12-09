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
public class AlertTriggerOsdWarningDialog extends AlertTriggerMaxMinDialog {
    private SettingStorage storage;

    public AlertTriggerOsdWarningDialog(Context context) {
        super(context);
        storage = new SettingStorage(getContext());

        long maxHalf = storage.getAlertTriggerOsdTotal() / 2;
        long max = maxHalf + ((maxHalf == 0) ? 1 : 0);
        setTitle(getContext().getString(R.string.settings_alert_triggers_osd_warning_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_osd));
        getCalculator().setMax(max);
        getCalculator().setMin(1);
        getCalculator().setResultValue(storage.getAlertTriggerOsdWarning());
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final long value = getCalculator().getResultValue();
                start("osd_warning", String.valueOf(value), "http://" + new LoginParams(getContext()).getHost() + "/api/v1/user/me/osd/warning", new Runnable() {
                    @Override
                    public void run() {
                        storage.setAlertTriggerOsdWarning(value);
                    }
                });
            }
        });
    }
}
