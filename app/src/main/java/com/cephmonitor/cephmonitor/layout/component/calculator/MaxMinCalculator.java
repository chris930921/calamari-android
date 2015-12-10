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
                dealWithStringValue(newValue);
            }
        };
    }

    @Override
    public void clear() {
        fieldValue.setText("");
        resultValue = 0;
        validStateChangeEvent.onInvalidEvent();
        // 要繪製其他元件來刷新 fieldValue 元件，不然Clear無法動作，系統Bug之一。
        fieldUnit.setText(fieldUnit.getText());
    }

    public void setResultValue(long value) {
        dealWithStringValue(String.valueOf(value));
    }

    public long getResultValue() {
        return resultValue;
    }

    public void setMax(long max) {
        this.max = max;
        maxValue.setText(String.valueOf(max));
    }

    public void setMin(long min) {
        this.min = min;
        minValue.setText(String.valueOf(min));
    }

    private void dealWithStringValue(String newValue) {
        BigInteger value = new BigInteger(newValue);
        if (value.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) == 1) {
            validStateChangeEvent.onInvalidEvent();
        } else {
            fieldValue.setText(value.toString());
            calculate();
        }
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
