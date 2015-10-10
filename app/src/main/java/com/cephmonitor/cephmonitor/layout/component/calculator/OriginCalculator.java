package com.cephmonitor.cephmonitor.layout.component.calculator;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.math.BigInteger;

/**
 * Created by chriske on 2015/10/10.
 */
public class OriginCalculator extends CalculatorLayout {
    private WH ruler;
    private View conditionLastRightView;
    private DesignSpec designSpec;
    private TextViewStyle styleBodyTwo;
    private TextViewStyle styleNote;
    private long resultValue;

    public OriginCalculator(Context context) {
        super(context);
        this.ruler = new WH(getContext());
        this.designSpec = ThemeManager.getStyle(getContext());
        styleBodyTwo = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        styleNote = new TextViewStyle(designSpec.getStyle().getNote());

        fieldValue.setText("");

        numberEight.setOnClickListener(numberClickEvent);
        numberSeven.setOnClickListener(numberClickEvent);
        numberNine.setOnClickListener(numberClickEvent);
        numberFive.setOnClickListener(numberClickEvent);
        numberFour.setOnClickListener(numberClickEvent);
        numberSix.setOnClickListener(numberClickEvent);
        numberTwo.setOnClickListener(numberClickEvent);
        numberOne.setOnClickListener(numberClickEvent);
        numberThree.setOnClickListener(numberClickEvent);
        numberZero.setOnClickListener(numberClickEvent);
        buttonClear.setOnClickListener(clearClickEvent);
    }

    private OnClickListener numberClickEvent = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int number = (int) view.getTag();
            String oldValue = fieldValue.getText().toString();
            String newValue = oldValue + number;
            BigInteger value = new BigInteger(newValue);
            if (value.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) == 1) {
                valueTooLong();
            } else {
                fieldValue.setText(value.toString());
                calculate();
            }
        }
    };

    private OnClickListener clearClickEvent = new OnClickListener() {
        @Override
        public void onClick(View view) {
            fieldValue.setText("");
            clear();
        }
    };

    protected BigInteger valueTooLong() {
        return new BigInteger(fieldValue.getText().toString());
    }

    protected long calculate() {
        resultValue = Long.parseLong(fieldValue.getText().toString());
        return resultValue;
    }

    protected long clear() {
        resultValue = 0;
        return resultValue;
    }

    public void setUnit(String unit) {
        unitHeightFillView.setText(unit);
        fieldUnit.setText(unit);
    }

    protected TextView addBoldSubTitle(int resourceText) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        conditionLayoutParams(params);

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        styleNote.style(v);
        v.setTypeface(Typeface.DEFAULT_BOLD);
        v.setTextColor(ColorTable._CCCCCC);
        v.setText(resourceText);

        fieldCondition.addView(v);
        conditionLastRightView = v;
        return v;
    }

    protected TextView addSubTitle(int resourceText) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        conditionLayoutParams(params);

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        styleNote.style(v);
        v.setTextColor(ColorTable._CCCCCC);
        v.setText(resourceText);

        fieldCondition.addView(v);
        conditionLastRightView = v;
        return v;
    }

    protected TextView addValueField() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        conditionLayoutParams(params);

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        styleNote.style(v);
        v.setTextColor(ColorTable._CCCCCC);
        v.setText("0");

        fieldCondition.addView(v);
        conditionLastRightView = v;
        return v;
    }

    protected View addConditionFillView() {
        LayoutParams params = new LayoutParams(
                ruler.getW(2),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        conditionLayoutParams(params);
        params.addRule(ALIGN_BOTTOM, conditionLastRightView.getId());

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        fieldCondition.addView(v);
        conditionLastRightView = v;
        return v;
    }

    protected void conditionLayoutParams(LayoutParams params) {
        if (conditionLastRightView == null) {
            params.addRule(ALIGN_PARENT_RIGHT);
        } else {
            params.addRule(LEFT_OF, conditionLastRightView.getId());
        }
    }

    protected void setValueInvaild() {
        fieldValue.setTextColor(designSpec.getAccentColors().getError());
    }

    protected void setValueVaild() {
        styleBodyTwo.style(fieldValue);
    }

    public long getValue() {
        return resultValue;
    }

    public void setOnInvalidEvent(OnValidStateChangeEvent invalidEvent) {
        this.validStateChangeEvent = invalidEvent;
    }

    protected OnValidStateChangeEvent validStateChangeEvent = new OnValidStateChangeEvent() {
        @Override
        public void onInvalidEvent() {
        }

        @Override
        public void onValidEvent() {

        }
    };

}
