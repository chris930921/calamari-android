package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerOsdErrorDialog extends AlertTriggerMaxMinDialog {
    private SettingStorage storage;

    public AlertTriggerOsdErrorDialog(Context context, int total) {
        super(context);
        storage = new SettingStorage(getContext());


        setTitle(getContext().getString(R.string.settings_alert_triggers_osd_error_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_osd));
        getCalculator().setMax(total / 2);
        getCalculator().setMin(1);
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                long value = getCalculator().getResultValue();
                storage.setAlertTriggerOsdError(value);
            }
        });
        getCalculator().clear();
    }
}
