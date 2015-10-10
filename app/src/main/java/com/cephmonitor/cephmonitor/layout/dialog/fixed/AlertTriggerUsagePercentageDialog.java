package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.calculator.CountPercentageCalculator;
import com.cephmonitor.cephmonitor.layout.component.calculator.OnValidStateChangeEvent;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.SettingDialog;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerUsagePercentageDialog extends SettingDialog {
    private DesignSpec designSpec;
    protected CountPercentageCalculator calculator;
    private TextView save;

    public AlertTriggerUsagePercentageDialog(Context context) {
        super(context);
        designSpec = ThemeManager.getStyle(getContext());
        calculator = calculator();

        addContentView(calculator);
        addButton(R.string.settings_dialog_cancel, ColorTable._666666, cancelClickEvent);
        save = addButton(R.string.settings_dialog_save, designSpec.getPrimaryColors().getPrimary(), null);
        calculator.setOnInvalidEvent(new OnValidStateChangeEvent() {
            @Override
            public void onInvalidEvent() {
                save.setTextColor(ColorTable._D5D5D5);
                save.setOnClickListener(null);
            }

            @Override
            public void onValidEvent() {
                save.setTextColor(designSpec.getPrimaryColors().getPrimary());
                save.setOnClickListener(cancelClickEvent);
            }
        });
    }

    private CountPercentageCalculator calculator() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        CountPercentageCalculator v = new CountPercentageCalculator(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private OnClickListener cancelClickEvent = new OnClickListener() {
        @Override
        public void onClick(View view) {
            cancel();
        }
    };

    public void setSaveClick(final OnClickListener event) {
        save.setOnClickListener(event);
    }

    public void setCalculatorUnit(String text) {
        calculator.setUnit(text);
    }
}
