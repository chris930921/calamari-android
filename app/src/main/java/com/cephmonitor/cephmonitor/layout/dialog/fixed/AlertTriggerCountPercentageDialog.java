package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;

import com.cephmonitor.cephmonitor.layout.component.calculator.CountPercentageCalculator;
import com.cephmonitor.cephmonitor.layout.component.calculator.OriginCalculator;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerCountPercentageDialog extends AlertTriggerOriginCalculatorDialog {

    public AlertTriggerCountPercentageDialog(Context context) {
        super(context);
    }

    @Override
    protected OriginCalculator getOriginCalculator() {
        return new CountPercentageCalculator(getContext());
    }

    public CountPercentageCalculator getCalculator() {
        return (CountPercentageCalculator) calculator;
    }
}
