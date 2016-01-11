package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.SettingDialog;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.SettingSingleChoiceItem;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.ceph.constant.SettingConstant;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by chriske on 2015/9/20.
 */
public class SettingDateFormatsDialog extends SettingDialog {
    public WH ruler;
    public LinearLayout dateContainer;
    private DesignSpec designSpec;
    private SettingSingleChoiceItem dateYearMonthDay;
    private SettingSingleChoiceItem dateMonthDayYear;
    private SettingSingleChoiceItem dateDayMonthYear;
    private int selectedId;
    private SettingSingleChoiceItem current;

    public SettingDateFormatsDialog(Context context) {
        super(context);
        this.ruler = new WH(getContext());
        this.designSpec = ThemeManager.getStyle(getContext());

        dateContainer = dateContainer();
        dateYearMonthDay = dateYearMonthDay();
        dateMonthDayYear = dateMonthDayYear();
        dateDayMonthYear = dateDayMonthYear();

        dateContainer.addView(dateYearMonthDay);
        dateContainer.addView(fillView());
        dateContainer.addView(dateMonthDayYear);
        dateContainer.addView(fillView());
        dateContainer.addView(dateDayMonthYear);

        setTitle(getContext().getString(R.string.settings_profile_formats));
        addContentView(dateContainer);
        addButton(R.string.settings_dialog_cancel, ColorTable._666666, new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    private LinearLayout dateContainer() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout v = new LinearLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.VERTICAL);
        v.setGravity(Gravity.CENTER);

        return v;
    }

    private View fillView() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, 3);

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(designSpec.getPrimaryColors().getHorizontalTwo());

        return v;
    }

    private SettingSingleChoiceItem dateYearMonthDay() {
        SettingSingleChoiceItem v = getChoiceItem(SettingConstant.DATE_FORMATS_YEAR_MONTH_DAY, R.string.settings_date_format_dialog_year_month_day);
        return v;
    }

    private SettingSingleChoiceItem dateMonthDayYear() {
        return getChoiceItem(SettingConstant.DATE_FORMATS_MONTH_DAY_YEAR, R.string.settings_date_format_dialog_month_day_year);
    }

    private SettingSingleChoiceItem dateDayMonthYear() {
        return getChoiceItem(SettingConstant.DATE_FORMATS_DAY_MONTH_YEAR, R.string.settings_date_format_dialog_day_month_year);
    }

    private SettingSingleChoiceItem getChoiceItem(final int id, int resource) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        SettingSingleChoiceItem v = new SettingSingleChoiceItem(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setName(getContext().getString(resource));
        v.setTag(id);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dateYearMonthDay.filedValue.setState(false);
                dateMonthDayYear.filedValue.setState(false);
                dateDayMonthYear.filedValue.setState(false);
                current = (SettingSingleChoiceItem) view;
                current.filedValue.setState(true);
                selectedId = (int) current.getTag();
            }
        });

        return v;
    }

    @Override
    public void show() {
        SettingStorage settingStorage = new SettingStorage(getContext());
        selectedId = settingStorage.getDateFormats();
        dateYearMonthDay.filedValue.setState(((int) dateYearMonthDay.getTag()) == selectedId);
        dateMonthDayYear.filedValue.setState(((int) dateMonthDayYear.getTag()) == selectedId);
        dateDayMonthYear.filedValue.setState(((int) dateDayMonthYear.getTag()) == selectedId);
        super.show();
    }

    public int getSelectedId() {
        return selectedId;
    }

    public void setSaveClick(final OnClickListener event) {
        addButton(R.string.settings_dialog_save, designSpec.getPrimaryColors().getPrimary(), new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                if (current == null)
                    return;

                if (event != null) {
                    event.onClick(view);
                }
            }
        });
    }
}
