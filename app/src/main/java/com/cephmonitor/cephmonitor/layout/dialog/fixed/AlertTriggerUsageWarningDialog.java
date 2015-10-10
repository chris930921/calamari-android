package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;

/**
 * Created by chriske on 2015/10/10.
 */
public class AlertTriggerUsageWarningDialog extends AlertTriggerUsagePercentageDialog {
    private SettingStorage storage;

    public AlertTriggerUsageWarningDialog(Context context, int total) {
        super(context);
        storage = new SettingStorage(getContext());

        setTitle(getContext().getString(R.string.settings_alert_triggers_usage_warning_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_usage));
        getCalculator().setTotal(total);
        getCalculator().setPartPercentage(storage.getAlertTriggerUsageWarning());
        getCalculator().setMaxPercentage(0.85F);
        getCalculator().setMinPercentage(0.05F);
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.setAlertTriggerUsageWarning(getCalculator().getResultValue());
            }
        });
        getCalculator().clear();
    }
}
