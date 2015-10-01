package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.RoundLeftBarItem;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.ceph.constant.SettingConstant;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NotificationItem extends RoundLeftBarItem {
    private WH ruler;
    private int borderColor;
    private int statusBarWidth;
    private int statusBarColor;

    public View download;
    public TextView message;
    public RelativeLayout bottomContainer;
    public View statusIcon;
    public TextView status;
    public TextView triggerTime;

    private CephNotificationConstant.StatusConstant statusConstant;
    private DesignSpec designSpec;
    private TextViewStyle messageTextStyle;
    private TextViewStyle triggeredTextStyle;
    private TextViewStyle statusTextStyle;
    private int backgroundColor;
    private int topBottomPaddingOne;
    private int leftRightPaddingOne;
    private int downloadIconSize;

    public NotificationItem(Context context) {
        super(context);
        ruler = new WH(getContext());
        borderColor = ColorTable._D9D9D9;
        statusBarWidth = ruler.getW(3);
        statusBarColor = Color.BLACK;
        statusConstant = new CephNotificationConstant.StatusConstant(getContext());

        this.designSpec = ThemeManager.getStyle(context);
        messageTextStyle = new TextViewStyle(designSpec.getStyle().getBodyOne());
        triggeredTextStyle = new TextViewStyle(designSpec.getStyle().getNote());
        statusTextStyle = new TextViewStyle(designSpec.getStyle().getNote());
        backgroundColor = designSpec.getPrimaryColors().getBackgroundThree();
        topBottomPaddingOne = ruler.getW(designSpec.getPadding().getTopBottomOne());
        leftRightPaddingOne = ruler.getW(designSpec.getPadding().getLeftRightOne());
        downloadIconSize = ruler.getW(designSpec.getIconSize().getSubhead());

        setViewBackgroundColor(backgroundColor);
        setViewPadding(leftRightPaddingOne, topBottomPaddingOne, leftRightPaddingOne, topBottomPaddingOne);
        setStatusBarWidth(statusBarWidth);
        setStatusBarColor(statusBarColor);
        setBorderColor(borderColor);
        setBorderWidth(3);
        setRadius(10);

        download = download();
        message = message(download);
        bottomContainer = bottomContainer(message);
        statusIcon = statusIcon();
        status = status(statusIcon);
        triggerTime = triggerTime(status);

        addView(download);
        addView(message);
        addView(bottomContainer);
        bottomContainer.addView(statusIcon);
        bottomContainer.addView(status);
        bottomContainer.addView(triggerTime);
    }

    protected View download() {
        LayoutParams params = new LayoutParams(downloadIconSize, downloadIconSize);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.leftMargin = leftRightPaddingOne;

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundResource(R.drawable.icon037);

        return v;
    }

    protected TextView message(View rightView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_TOP);
        params.addRule(LEFT_OF, rightView.getId());
        params.bottomMargin = topBottomPaddingOne;

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        messageTextStyle.style(v);

        return v;
    }

    protected RelativeLayout bottomContainer(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());
        params.addRule(ALIGN_RIGHT, topView.getId());

        RelativeLayout v = new RelativeLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }


    protected View statusIcon() {
        LayoutParams params = new LayoutParams(downloadIconSize, downloadIconSize);
        params.addRule(ALIGN_PARENT_TOP);
        params.addRule(CENTER_VERTICAL);

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(Color.YELLOW);

        return v;
    }

    protected TextView status(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RIGHT_OF, leftView.getId());
        params.addRule(CENTER_VERTICAL);
        params.leftMargin = leftRightPaddingOne;

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        statusTextStyle.style(v);

        return v;
    }

    protected TextView triggerTime(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RIGHT_OF, leftView.getId());
        params.addRule(CENTER_VERTICAL);
        params.addRule(ALIGN_PARENT_RIGHT);

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        triggeredTextStyle.style(v);

        return v;
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public void setTime(Calendar calendar) {
        SettingStorage settingStorage = new SettingStorage(getContext());
        int id = settingStorage.getDateFormats();
        SimpleDateFormat datetimeFormat = SettingConstant.getSimpleFormat(id);
        this.triggerTime.setText(" - " + datetimeFormat.format(calendar.getTime()));
    }

    public void setStatus(String status) {
        this.status.setText(status);
        this.statusIcon.setBackgroundResource(statusConstant.getStatusIconGroup().get(status));
        this.status.setTextColor(statusConstant.getStatusTextColorGroup().get(status));
        if (status.equals(CephNotificationConstant.STATUS_RESOLVED)) {
            download.setVisibility(VISIBLE);
        } else {
            download.setVisibility(GONE);
        }
    }

    public void setLevel(int level) {
        this.statusBarColor = statusConstant.getStatusColorGroup().get(level);
        setStatusBarColor(this.statusBarColor);
    }
}
