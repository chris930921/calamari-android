package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.AlertTriggerUsagePercentageDialog;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;

/**
 * Created by chriske on 2015/10/10.
 */
public class AlertTriggerUsageErrorDialog extends AlertTriggerUsagePercentageDialog {
    private SettingStorage storage;

    public AlertTriggerUsageErrorDialog(Context context) {
        super(context);
        storage = new SettingStorage(getContext());


        setTitle(getContext().getString(R.string.settings_alert_triggers_usage_error_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_usage));
        getCalculator().setTotal(storage.getAlertTriggerUsageTotal());
        getCalculator().setPartPercentage(storage.getAlertTriggerUsageError());
        getCalculator().setMaxPercentage(0.85F);
        getCalculator().setMinPercentage(storage.getAlertTriggerUsageWarning());
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.setAlertTriggerUsageError(getCalculator().getResultValue());
            }
        });
        getCalculator().clear();
    }
}
