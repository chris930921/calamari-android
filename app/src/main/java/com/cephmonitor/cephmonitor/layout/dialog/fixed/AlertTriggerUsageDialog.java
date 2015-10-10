package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;

/**
 * Created by chriske on 2015/10/10.
 */
public class AlertTriggerUsageDialog extends AlertTriggerMaxMinDialog {
    public AlertTriggerUsageDialog(Context context) {
        super(context);
        calculator.setUnit(getContext().getString(R.string.other_calculater_unit_mon));
    }
}
