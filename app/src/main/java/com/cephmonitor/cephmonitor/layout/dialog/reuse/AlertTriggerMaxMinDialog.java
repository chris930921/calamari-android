package com.cephmonitor.cephmonitor.layout.dialog.reuse;

import android.content.Context;

import com.cephmonitor.cephmonitor.layout.component.calculator.MaxMinCalculator;
import com.cephmonitor.cephmonitor.layout.component.calculator.OriginCalculator;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerMaxMinDialog extends AlertTriggerOriginCalculatorDialog {

    public AlertTriggerMaxMinDialog(Context context) {
        super(context);
    }

    @Override
    protected OriginCalculator getOriginCalculator() {
        return new MaxMinCalculator(getContext());
    }

    public MaxMinCalculator getCalculator() {
        return (MaxMinCalculator) calculator;
    }
}
