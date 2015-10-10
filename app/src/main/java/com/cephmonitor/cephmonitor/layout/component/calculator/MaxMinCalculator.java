package com.cephmonitor.cephmonitor.layout.component.calculator;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;

import java.math.BigInteger;

/**
 * Created by chriske on 2015/10/10.
 */
public class MaxMinCalculator extends OriginCalculator {
    public TextView minValue;
    public TextView maxValue;
    public long min;
    public long max;
    private long resultValue;

    public MaxMinCalculator(Context context) {
        super(context);
        min = 10;
        max = 100;

        maxValue = addValueField();
        addBoldSubTitle(R.string.other_calculater_condition_max);
        addConditionFillView();
        minValue = addValueField();
        addBoldSubTitle(R.string.other_calculater_condition_min);

        maxValue.setText(String.valueOf(max));
        minValue.setText(String.valueOf(min));
        clear();
    }

    @Override
    protected OnClickListener clickNumberEvent() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = (int) view.getTag();
                String oldValue = fieldValue.getText().toString();
                String newValue = oldValue + number;
                BigInteger value = new BigInteger(newValue);
                if (value.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) == 1) {
                    validStateChangeEvent.onInvalidEvent();
                } else {
                    fieldValue.setText(value.toString());
                    calculate();
                }
            }
        };
    }

    @Override
    protected void clear() {
        fieldValue.setText("");
        resultValue = 0;
        validStateChangeEvent.onInvalidEvent();
    }

    public long getResultValue() {
        return resultValue;
    }

    public void setResultValue(long resultValue) {
        this.resultValue = resultValue;
        fieldValue.setText(String.valueOf(resultValue));
    }

    public void setMax(int max) {
        this.max = max;
        maxValue.setText(String.valueOf(max));
    }

    public void setMin(int min) {
        this.min = min;
        minValue.setText(String.valueOf(min));
    }

    protected void calculate() {
        long value = Long.parseLong(fieldValue.getText().toString());
        if (value > max || value < min) {
            validStateChangeEvent.onInvalidEvent();
            setValueInvaild();
        } else {
            validStateChangeEvent.onValidEvent();
            setValueVaild();
        }
        resultValue = value;
    }

}
