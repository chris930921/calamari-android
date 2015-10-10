package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerPgErrorDialog extends AlertTriggerCountPercentageDialog {
    public SettingStorage storage;

    public AlertTriggerPgErrorDialog(Context context, int total) {
        super(context);
        storage = new SettingStorage(getContext());

        setTitle(getContext().getString(R.string.settings_alert_triggers_pg_error_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_pg));
        getCalculator().setTotal(total);
        getCalculator().setPartPercentage(storage.getAlertTriggerPgError());
        getCalculator().setMaxPercentage(0.8F);
        getCalculator().setMinPercentage(0.2F);
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.setAlertTriggerPgError(getCalculator().getResultValue());
            }
        });
    }
}
