package com.cephmonitor.cephmonitor.layout.component.calculator;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.resourcelibrary.model.logic.ByteUnit;

import java.math.BigInteger;

/**
 * Created by chriske on 2015/10/10.
 */
public class UsagePercentageCalculator extends OriginCalculator {
    public TextView totalValue;
    public TextView partValue;
    public long total;
    public long part;
    public float maxPercentage;
    public float minPercentage;
    public float partPercentage;

    public UsagePercentageCalculator(Context context) {
        super(context);
        maxPercentage = 0.5F;
        minPercentage = 0.2F;
        partPercentage = 0.1F;
        total = 100;
        part = (long) (total * partPercentage);

        totalValue = addValueField();
        addConditionFillView();
        addBoldSubTitle("/");
        addConditionFillView();
        partValue = addValueField();

        totalValue.setText(String.valueOf(total));
        updatePartValue();
    }

    @Override
    protected OnClickListener clickNumberEvent() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = (int) view.getTag();
                String oldValue = fieldValue.getText().toString();
                String newValue = oldValue + number;
                BigInteger unCheckValue = new BigInteger(newValue);
                if (unCheckValue.compareTo(BigInteger.valueOf(100L)) == 1) {
                    return;
                } else if (unCheckValue.compareTo(BigInteger.valueOf(0L)) == -1) {
                    return;
                }
                long value = Long.parseLong(unCheckValue.toString());
                int max = (int) (maxPercentage * 100);
                int min = (int) (minPercentage * 100);
                if (value > max || value < min) {
                    validStateChangeEvent.onInvalidEvent();
                    setValueInvaild();
                } else {
                    validStateChangeEvent.onValidEvent();
                    setValueVaild();
                }
                partPercentage = (float) value / 100F;
                fieldValue.setText(String.valueOf(value));
                part = (long) (total * partPercentage);
                updatePartValue();
            }
        };
    }


    @Override
    protected void clear() {
        fieldValue.setText("");
        partPercentage = 0;
        validStateChangeEvent.onInvalidEvent();
        updatePartValue();
    }

    protected float getResultValue() {
        return partPercentage;
    }

    public void setMaxPercentage(float maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public void setMinPercentage(float minPercentage) {
        this.minPercentage = minPercentage;
    }

    public void setTotal(int total) {
        this.total = total;
        totalValue.setText(String.valueOf(total));
        updatePartValue();
    }

    public void setPartPercentage(float partPercentage) {
        this.partPercentage = partPercentage;
        updatePartValue();
    }

    private void updatePartValue() {
        part = (long) (total * maxPercentage);
        partValue.setText(ByteUnit.change(part));
    }
}
