package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.calculator.LimitCalculator;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.SettingDialog;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerDialog extends SettingDialog {
    public LimitCalculator calculator;

    private DesignSpec designSpec;

    public AlertTriggerDialog(Context context) {
        super(context);
        this.designSpec = ThemeManager.getStyle(getContext());

        calculator = calculator();
        setTitle(getContext().getString(R.string.settings_profile_language));
        addContentView(calculator);
        addButton(R.string.settings_dialog_cancel, ColorTable._666666, new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    private LimitCalculator calculator() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LimitCalculator v = new LimitCalculator(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }


    public void setOkClick(final OnClickListener event) {
        addButton(R.string.settings_dialog_ok, designSpec.getPrimaryColors().getPrimary(), new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                if (event != null) {
                    event.onClick(view);
                }
            }
        });
    }
}
