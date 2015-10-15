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

/**
 * Created by chriske on 2015/10/10.
 */
public abstract class OriginCalculator extends CalculatorLayout {
    private WH ruler;
    private View conditionLastRightView;
    private DesignSpec designSpec;
    private TextViewStyle styleBodyTwo;
    private TextViewStyle styleNote;


    public OriginCalculator(Context context) {
        super(context);
        this.ruler = new WH(getContext());
        this.designSpec = ThemeManager.getStyle(getContext());
        styleBodyTwo = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        styleNote = new TextViewStyle(designSpec.getStyle().getNote());

        fieldValue.setText("");

        OnClickListener numberClickEvent = clickNumberEvent();
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

    protected abstract OnClickListener clickNumberEvent();

    private OnClickListener clearClickEvent = new OnClickListener() {
        @Override
        public void onClick(View view) {
            clear();
        }
    };

    protected abstract void clear();

    public void setUnit(String unit) {
        unitHeightFillView.setText(unit);
        fieldUnit.setText(unit);
    }

    protected TextView addBoldSubTitle(int resourceText) {
        return addBoldSubTitle(getContext().getString(resourceText));
    }

    protected TextView addBoldSubTitle(String text) {
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
        v.setText(text);

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
