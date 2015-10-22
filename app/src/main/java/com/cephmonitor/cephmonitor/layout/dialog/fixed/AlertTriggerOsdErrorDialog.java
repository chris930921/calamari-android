package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.AlertTriggerMaxMinDialog;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerOsdErrorDialog extends AlertTriggerMaxMinDialog {
    private SettingStorage storage;

    public AlertTriggerOsdErrorDialog(Context context) {
        super(context);
        storage = new SettingStorage(getContext());

        long maxHalf = storage.getAlertTriggerOsdTotal() / 2;
        long max = maxHalf + ((maxHalf == 0) ? 1 : 0);
        setTitle(getContext().getString(R.string.settings_alert_triggers_osd_error_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_osd));
        getCalculator().setMax(max);
        getCalculator().setMin(1);
        getCalculator().setResultValue(storage.getAlertTriggerOsdError());
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                long value = getCalculator().getResultValue();
                storage.setAlertTriggerOsdError(value);
            }
        });
    }
}
