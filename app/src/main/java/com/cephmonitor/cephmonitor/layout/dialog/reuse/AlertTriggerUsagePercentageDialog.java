package com.cephmonitor.cephmonitor.layout.dialog.reuse;

import android.content.Context;

import com.cephmonitor.cephmonitor.layout.component.calculator.OriginCalculator;
import com.cephmonitor.cephmonitor.layout.component.calculator.UsagePercentageCalculator;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerUsagePercentageDialog extends AlertTriggerOriginCalculatorDialog {

    public AlertTriggerUsagePercentageDialog(Context context) {
        super(context);
    }

    @Override
    protected OriginCalculator getOriginCalculator() {
        return new UsagePercentageCalculator(getContext());
    }

    public UsagePercentageCalculator getCalculator() {
        return (UsagePercentageCalculator) calculator;
    }
}
