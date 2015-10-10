package com.cephmonitor.cephmonitor.layout.component.calculator;

import android.content.Context;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;

import java.math.BigInteger;

/**
 * Created by chriske on 2015/10/10.
 */
public class OsdCalculator extends OriginCalculator {
    public TextView minValue;
    public TextView maxValue;
    public long min;
    public long max;

    public OsdCalculator(Context context) {
        super(context);
        min = 10;
        max = 100;

        setUnit(getContext().getString(R.string.other_calculater_unit_osd));
        maxValue = addValueField();
        addBoldSubTitle(R.string.other_calculater_condition_max);
        addConditionFillView();
        minValue = addValueField();
        addBoldSubTitle(R.string.other_calculater_condition_min);

        maxValue.setText(String.valueOf(max));
        minValue.setText(String.valueOf(min));
    }

    public void setMax(int max) {
        this.max = max;
        maxValue.setText(String.valueOf(max));
    }

    public void setMin(int min) {
        this.min = min;
        minValue.setText(String.valueOf(min));
    }

    @Override
    protected BigInteger valueTooLong() {
        validStateChangeEvent.onInvalidEvent();
        return super.valueTooLong();
    }

    @Override
    protected long calculate() {
        long value = super.calculate();
        if (value > max || value < min) {
            validStateChangeEvent.onInvalidEvent();
            setValueInvaild();
        } else {
            validStateChangeEvent.onValidEvent();
            setValueVaild();
        }
        return value;
    }

    @Override
    protected long clear() {
        validStateChangeEvent.onInvalidEvent();
        return super.clear();
    }
}
