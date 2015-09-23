package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cephmonitor.cephmonitor.BuildConfig;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.SettingDateFormatsDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.SettingLanguageDialog;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.SettingProfileItem;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.SettingTitleItem;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.DynamicRoundBorderItem;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.util.ArrayList;

public class SettingsLayout extends FractionAbleRelativeLayout {
    private WH ruler;
    private ArrayList<View> contentViewGroup;

    public ListView contentList;
    public SettingTitleItem profileTitle;
    public SettingProfileItem languageItem;
    public SettingProfileItem dateFormatsItem;
    public SettingTitleItem alertTitle;
    public SettingTitleItem aboutTitle;
    public SettingProfileItem versionItem;
    public SettingLanguageDialog languageDialog;
    public SettingDateFormatsDialog dateFormatsDialog;

    private DesignSpec designSpec;
    private int contentListVerticalPadding;
    private int contentListHorizontalPadding;
    private TextViewStyle titleStyle;

    public SettingsLayout(Context context) {
        super(context);
        this.ruler = new WH(context);
        this.contentViewGroup = new ArrayList<>();
        this.designSpec = ThemeManager.getStyle(getContext());
        this.languageDialog = new SettingLanguageDialog(getContext());
        this.dateFormatsDialog = new SettingDateFormatsDialog(getContext());
        contentListVerticalPadding = ruler.getW(designSpec.getMargin().getTopBottomOne());
        contentListHorizontalPadding = ruler.getW(designSpec.getMargin().getTopBottomOne());
        titleStyle = new TextViewStyle(designSpec.getStyle().getSubhead());

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        setId(RandomId.get());
        setLayoutParams(params);
        setPadding(contentListHorizontalPadding, 0, contentListHorizontalPadding, 0);

        profileTitle = profileTitle();
        languageItem = languageItem();
        dateFormatsItem = dateFormatsItem();
        alertTitle = alertTitle();
        aboutTitle = aboutTitle();
        versionItem = versionItem();

        contentViewGroup.add(profileTitle);
        contentViewGroup.add(languageItem);
        contentViewGroup.add(dateFormatsItem);
        contentViewGroup.add(alertTitle);
        contentViewGroup.add(aboutTitle);
        contentViewGroup.add(versionItem);

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

    protected SettingTitleItem profileTitle() {
        SettingTitleItem v = getTitle(R.string.settings_profile);
        return v;
    }

    protected SettingProfileItem languageItem() {
        SettingProfileItem v = new SettingProfileItem(getContext());
        v.setBorderStyle(DynamicRoundBorderItem.ITEM_STYLE_HEADER);
        v.setName(getContext().getString(R.string.settings_profile_language));
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                languageDialog.show();
            }
        });

        return v;
    }

    protected SettingProfileItem dateFormatsItem() {
        SettingProfileItem v = new SettingProfileItem(getContext());
        v.setBorderStyle(DynamicRoundBorderItem.ITEM_STYLE_FOOTER);
        v.setName(getContext().getString(R.string.settings_profile_formats));
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFormatsDialog.show();
            }
        });

        return v;
    }

    protected SettingTitleItem alertTitle() {
        SettingTitleItem v = getTitle(R.string.settings_alert);
        return v;
    }

    protected SettingTitleItem aboutTitle() {
        SettingTitleItem v = getTitle(R.string.settings_about);
        return v;
    }

    protected SettingProfileItem versionItem() {
        SettingProfileItem v = new SettingProfileItem(getContext());
        v.setBorderStyle(DynamicRoundBorderItem.ITEM_STYLE_ONLY_ONE);
        v.setName(getContext().getString(R.string.settings_about_version));
        v.setValue(BuildConfig.VERSION_NAME);

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

    protected ListView getList() {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        ListView v = new ListView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setDivider(new ColorDrawable(Color.TRANSPARENT));
        v.setDividerHeight(0);

        return v;
    }
}