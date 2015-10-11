package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.AlertTriggerCountPercentageDialog;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerPgWarningDialog extends AlertTriggerCountPercentageDialog {
    public SettingStorage storage;

    public AlertTriggerPgWarningDialog(Context context) {
        super(context);
        storage = new SettingStorage(getContext());


        setTitle(getContext().getString(R.string.settings_alert_triggers_pg_warning_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_pg));
        getCalculator().setTotal(storage.getAlertTriggerPgTotal());
        getCalculator().setPartPercentage(storage.getAlertTriggerPgWarning());
        getCalculator().setMaxPercentage(0.8F);
        getCalculator().setMinPercentage(0.2F);
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.setAlertTriggerPgWarning(getCalculator().getResultValue());
            }
        });
        getCalculator().clear();
    }
}
