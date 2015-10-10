package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.AlertTriggerCountPercentageDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.AlertTriggerMonErrorDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.AlertTriggerMonWarningDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.AlertTriggerOriginCalculatorDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.AlertTriggerOsdErrorDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.AlertTriggerOsdWarningDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.AlertTriggerPgErrorDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.AlertTriggerPgWarningDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.OnSaveFinishedEvent;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.SettingTitleItem;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.DynamicRoundBorderItem;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.SettingDescriptionItem;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.util.ArrayList;

public class AlertTriggersLayout extends FractionAbleRelativeLayout {
    private WH ruler;
    private ArrayList<View> contentViewGroup;

    public ListView contentList;
    public SettingTitleItem osdTitle;
    public SettingDescriptionItem osdWarning;
    public SettingDescriptionItem osdError;

    public SettingTitleItem monitorTitle;
    public SettingDescriptionItem monitorWarning;
    public SettingDescriptionItem monitorError;

    public SettingTitleItem pgTitle;
    public SettingDescriptionItem pgWarning;
    public SettingDescriptionItem pgError;

    public SettingTitleItem usageTitle;
    public SettingDescriptionItem usageWarning;
    public SettingDescriptionItem usageError;

    private DesignSpec designSpec;
    private int contentListVerticalPadding;
    private int contentListHorizontalPadding;
    private TextViewStyle titleStyle;

    public AlertTriggersLayout(Context context) {
        super(context);
        this.ruler = new WH(context);
        this.contentViewGroup = new ArrayList<>();
        this.designSpec = ThemeManager.getStyle(getContext());
        contentListVerticalPadding = ruler.getW(designSpec.getMargin().getTopBottomOne());
        contentListHorizontalPadding = ruler.getW(designSpec.getMargin().getTopBottomOne());
        titleStyle = new TextViewStyle(designSpec.getStyle().getSubhead());

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        setId(RandomId.get());
        setLayoutParams(params);
        setPadding(contentListHorizontalPadding, 0, contentListHorizontalPadding, 0);

        osdTitle = osdTitle();
        osdWarning = osdWarning();
        osdError = osdError();
        monitorTitle = monitorTitle();
        monitorWarning = monitorWarning();
        monitorError = monitorError();
        pgTitle = pgTitle();
        pgWarning = pgWarning();
        pgError = pgError();
        usageTitle = usageTitle();
        usageWarning = usageWarning();
        usageError = usageError();

        contentViewGroup.add(osdTitle);
        contentViewGroup.add(osdWarning);
        contentViewGroup.add(osdError);
        contentViewGroup.add(monitorTitle);
        contentViewGroup.add(monitorWarning);
        contentViewGroup.add(monitorError);
        contentViewGroup.add(pgTitle);
        contentViewGroup.add(pgWarning);
        contentViewGroup.add(pgError);
        contentViewGroup.add(usageTitle);
        contentViewGroup.add(usageWarning);
        contentViewGroup.add(usageError);

        contentList = contentList();

        addView(contentList);
    }

    protected ListView contentList() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        ListView v = new ListView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setDivider(new ColorDrawable(Color.TRANSPARENT));
        v.setDividerHeight(0);
        v.addFooterView(fillView());
        v.setSelector(new ColorDrawable(Color.TRANSPARENT));
        v.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return contentViewGroup.size();
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return contentViewGroup.get(i);
            }
        });

        return v;
    }

    protected View fillView() {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                contentListVerticalPadding
        );

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    protected SettingTitleItem osdTitle() {
        SettingTitleItem v = getTitle(R.string.settings_alert_triggers_osd);
        return v;
    }

    protected SettingDescriptionItem osdWarning() {
        final SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_alert_triggers_osd_warning,
                R.string.settings_alert_triggers_osd_warning_description,
                DynamicRoundBorderItem.ITEM_STYLE_HEADER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertTriggerOsdWarningDialog dialog = new AlertTriggerOsdWarningDialog(getContext(), 200);
                dialog.setSaveFinishedEvent(dialogSaveFinishedEvent);
                dialog.setTag(v);
                dialog.show();
            }
        });

        return v;
    }

    protected SettingDescriptionItem osdError() {
        final SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_alert_triggers_osd_error,
                R.string.settings_alert_triggers_osd_error_description,
                DynamicRoundBorderItem.ITEM_STYLE_FOOTER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertTriggerOsdErrorDialog dialog = new AlertTriggerOsdErrorDialog(getContext(), 200);
                dialog.setSaveFinishedEvent(dialogSaveFinishedEvent);
                dialog.setTag(v);
                dialog.show();
            }
        });
        return v;
    }

    protected SettingTitleItem monitorTitle() {
        SettingTitleItem v = getTitle(R.string.settings_alert_triggers_monitor);

        return v;
    }

    protected SettingDescriptionItem monitorWarning() {
        final SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_alert_triggers_monitor_warning,
                R.string.settings_alert_triggers_monitor_warning_description,
                DynamicRoundBorderItem.ITEM_STYLE_HEADER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertTriggerMonWarningDialog dialog = new AlertTriggerMonWarningDialog(getContext(), 200);
                dialog.setSaveFinishedEvent(dialogSaveFinishedEvent);
                dialog.setTag(v);
                dialog.show();
            }
        });
        return v;
    }

    protected SettingDescriptionItem monitorError() {
        final SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_alert_triggers_monitor_error,
                R.string.settings_alert_triggers_monitor_error_description,
                DynamicRoundBorderItem.ITEM_STYLE_FOOTER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertTriggerMonErrorDialog dialog = new AlertTriggerMonErrorDialog(getContext(), 200);
                dialog.setSaveFinishedEvent(dialogSaveFinishedEvent);
                dialog.setTag(v);
                dialog.show();
            }
        });
        return v;
    }

    protected SettingTitleItem pgTitle() {
        SettingTitleItem v = getTitle(R.string.settings_alert_triggers_pg);
        return v;
    }

    protected SettingDescriptionItem pgWarning() {
        final SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_alert_triggers_pg_warning,
                R.string.settings_alert_triggers_pg_warning_description,
                DynamicRoundBorderItem.ITEM_STYLE_HEADER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertTriggerPgWarningDialog dialog = new AlertTriggerPgWarningDialog(getContext(), 200);
                dialog.setSaveFinishedEvent(dialogSaveFinishedEvent);
                dialog.setTag(v);
                dialog.show();
            }
        });
        return v;
    }

    protected SettingDescriptionItem pgError() {
        final SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_alert_triggers_pg_error,
                R.string.settings_alert_triggers_pg_error_description,
                DynamicRoundBorderItem.ITEM_STYLE_FOOTER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertTriggerPgErrorDialog dialog = new AlertTriggerPgErrorDialog(getContext(), 200);
                dialog.setSaveFinishedEvent(dialogSaveFinishedEvent);
                dialog.setTag(v);
                dialog.show();
            }
        });
        return v;
    }

    protected SettingTitleItem usageTitle() {
        SettingTitleItem v = getTitle(R.string.settings_alert_triggers_usage);
        return v;
    }

    protected SettingDescriptionItem usageWarning() {
        SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_alert_triggers_usage_warning,
                R.string.settings_alert_triggers_usage_warning_description,
                DynamicRoundBorderItem.ITEM_STYLE_HEADER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertTriggerCountPercentageDialog dialog = new AlertTriggerCountPercentageDialog(getContext());
                dialog.setTitle(getContext().getString(R.string.settings_alert_triggers_usage_warning_dialog_title));
                dialog.setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_usage));
                dialog.show();
            }
        });
        return v;
    }

    protected SettingDescriptionItem usageError() {
        SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_alert_triggers_usage_error,
                R.string.settings_alert_triggers_usage_error_description,
                DynamicRoundBorderItem.ITEM_STYLE_FOOTER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertTriggerCountPercentageDialog dialog = new AlertTriggerCountPercentageDialog(getContext());
                dialog.setTitle(getContext().getString(R.string.settings_alert_triggers_usage_error_dialog_title));
                dialog.setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_usage));
                dialog.show();
            }
        });
        return v;
    }

    protected SettingTitleItem getTitle(int resource) {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        SettingTitleItem v = new SettingTitleItem(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setText(resource);
        v.setTextStyle(titleStyle);
        v.setVerticalPadding(contentListVerticalPadding, contentListVerticalPadding);

        return v;
    }

    protected SettingDescriptionItem getDescriptionItem(int title, int subTitle, int borderStyle) {
        SettingDescriptionItem v = new SettingDescriptionItem(getContext());
        v.setBorderStyle(borderStyle);
        v.setTitle(getContext().getString(title));
        v.setSubTitle(getContext().getString(subTitle));

        return v;
    }

    public void setDialogSaveFinishedEvent(OnSaveFinishedEvent dialogSaveFinishedEvent) {
        this.dialogSaveFinishedEvent = dialogSaveFinishedEvent;

    }

    private OnSaveFinishedEvent dialogSaveFinishedEvent = new OnSaveFinishedEvent() {
        @Override
        public void onFinish(AlertTriggerOriginCalculatorDialog dialog) {

        }
    };
}