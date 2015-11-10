package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.other.ClockSetMoudle;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.SettingDialog;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by chriske on 2015/9/20.
 */
public class TimePeriodPickerDialog extends SettingDialog {
    public ClockSetMoudle clockSetModule;
    public WH ruler;

    private DesignSpec designSpec;

    public TimePeriodPickerDialog(Context context) {
        super(context);
        this.ruler = new WH(getContext());
        this.designSpec = ThemeManager.getStyle(getContext());

        clockSetModule = clockSetModule();

        addContentView(clockSetModule.view);
        addButton(R.string.settings_dialog_cancel, ColorTable._666666, new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    private ClockSetMoudle clockSetModule() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ruler.getW(80));

        ClockSetMoudle v = new ClockSetMoudle(getContext());
        v.view.setId(RandomId.get());
        v.view.setLayoutParams(params);

        return v;
    }

    public void setTitle(int resource) {
        setTitle(getContext().getString(resource));
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

    public void setTime(int hour, int minute, int second) {
        clockSetModule.leftNumToStr = ((hour >= 10) ? "" : "0") + String.valueOf(hour);
        clockSetModule.centerNumToStr = ((minute >= 10) ? "" : "0") + String.valueOf(minute);
        clockSetModule.rightNumToStr = ((second >= 10) ? "" : "0") + String.valueOf(second);
    }

    public long getSecondPeriod() {
        long result = 0;
        result += Integer.parseInt(clockSetModule.leftNumToStr) * 60 * 60;
        result += Integer.parseInt(clockSetModule.centerNumToStr) * 60;
        result += Integer.parseInt(clockSetModule.rightNumToStr);
        return result;
    }
}
