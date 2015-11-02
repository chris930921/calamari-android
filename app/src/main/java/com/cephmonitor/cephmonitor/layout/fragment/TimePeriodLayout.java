package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.SettingContentList;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.DynamicRoundBorderItem;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.SettingDescriptionItem;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.util.ArrayList;

public class TimePeriodLayout extends FractionAbleRelativeLayout {
    private WH ruler;
    private ArrayList<View> contentViewGroup;

    public ListView contentList;
    public SettingDescriptionItem normalPeriod;
    public SettingDescriptionItem abnormalPeriod;
    public SettingDescriptionItem serverAbnormalPeriod;

    private DesignSpec designSpec;

    public TimePeriodLayout(Context context) {
        super(context);
        this.ruler = new WH(context);
        this.contentViewGroup = new ArrayList<>();
        this.designSpec = ThemeManager.getStyle(getContext());

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        setId(RandomId.get());
        setLayoutParams(params);
        setPadding(
                ruler.getW(designSpec.getMargin().getTopBottomOne()), 0,
                ruler.getW(designSpec.getMargin().getTopBottomOne()), 0);

        contentList = contentList();
        normalPeriod = normalPeriod();
        abnormalPeriod = abnormalPeriod();
        serverAbnormalPeriod = serverAbnormalPeriod();

        contentViewGroup.add(normalPeriod);
        contentViewGroup.add(abnormalPeriod);
        contentViewGroup.add(serverAbnormalPeriod);
        addView(contentList);
    }

    protected SettingContentList contentList() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        SettingContentList v = new SettingContentList(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setDivider(new ColorDrawable(Color.TRANSPARENT));
        v.setDividerHeight(0);
        v.setHeaderHeight(ruler.getW(designSpec.getMargin().getTopBottomOne()));
        v.setFooterHeight(ruler.getW(designSpec.getMargin().getTopBottomOne()));
        v.setSelector(new ColorDrawable(Color.TRANSPARENT));
        v.setDataOfAdapter(contentViewGroup);

        return v;
    }

    protected SettingDescriptionItem normalPeriod() {
        final SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_time_period_normal_title,
                DynamicRoundBorderItem.ITEM_STYLE_HEADER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return v;
    }

    protected SettingDescriptionItem abnormalPeriod() {
        final SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_time_period_abnormal_title,
                DynamicRoundBorderItem.ITEM_STYLE_CENTER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return v;
    }

    protected SettingDescriptionItem serverAbnormalPeriod() {
        final SettingDescriptionItem v = getDescriptionItem(
                R.string.settings_time_period_server_abnormal_title,
                DynamicRoundBorderItem.ITEM_STYLE_FOOTER);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return v;
    }

    protected SettingDescriptionItem getDescriptionItem(int title, int borderStyle) {
        SettingDescriptionItem v = new SettingDescriptionItem(getContext());
        v.setBorderStyle(borderStyle);
        v.setTitle(getContext().getString(title));

        return v;
    }

}