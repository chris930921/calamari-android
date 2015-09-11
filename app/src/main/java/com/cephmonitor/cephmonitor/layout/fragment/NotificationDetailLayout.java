package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleScrollView;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationDetailLayout extends FractionAbleScrollView {
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Context context;
    private WH ruler;
    private int dividerHeight;

    public RelativeLayout container;

    public TextView message;
    public RelativeLayout bottomContainer;
    public View statusIcon;
    public TextView status;
    public TextView descriptionTitle;
    public TextView description;
    public TextView triggeredTitle;
    public TextView triggered;
    public TextView resolvedTitle;
    public TextView resolved;

    private CephNotificationConstant.StatusConstant statusConstant;
    private DesignSpec designSpec;
    private TextViewStyle titleTextStyle;
    private TextViewStyle timeTextStyle;
    private TextViewStyle statusTextStyle;
    private int backgroundColor;
    private int topBottomPaddingOne;
    private int leftRightPaddingOne;
    private int downloadIconSize;

    public NotificationDetailLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.dividerHeight = ruler.getW(5);
        statusConstant = new CephNotificationConstant.StatusConstant(getContext());

        this.designSpec = ThemeManager.getStyle(context);
        titleTextStyle = new TextViewStyle(designSpec.getStyle().getSubhead());
        timeTextStyle = new TextViewStyle(designSpec.getStyle().getNote());
        statusTextStyle = new TextViewStyle(designSpec.getStyle().getBodyOne());
        backgroundColor = designSpec.getPrimaryColors().getBackgroundThree();
        topBottomPaddingOne = ruler.getW(designSpec.getPadding().getTopBottomOne());
        leftRightPaddingOne = ruler.getW(designSpec.getPadding().getLeftRightOne());
        downloadIconSize = ruler.getW(designSpec.getIconSize().getSubhead());

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setId(RandomId.get());
        setLayoutParams(params);

        container = container();
        message = message();
        bottomContainer = bottomContainer(message);
        statusIcon = statusIcon();
        status = status(statusIcon);
        descriptionTitle = descriptionTitle(bottomContainer);
        description = description(descriptionTitle);
        triggeredTitle = triggeredTitle(description);
        triggered = triggered(triggeredTitle);
        resolvedTitle = resolvedTitle(triggered);
        resolved = resolved(resolvedTitle);

        addView(container);
        container.addView(message);
        container.addView(bottomContainer);
        container.addView(descriptionTitle);
        container.addView(description);
        container.addView(triggeredTitle);
        container.addView(triggered);
        container.addView(resolvedTitle);
        container.addView(resolved);
        bottomContainer.addView(statusIcon);
        bottomContainer.addView(status);
    }

    private RelativeLayout container() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(dividerHeight, dividerHeight, dividerHeight, dividerHeight);

        return v;
    }

    protected TextView message() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        titleTextStyle.style(v);

        return v;
    }

    protected RelativeLayout bottomContainer(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }


    protected View statusIcon() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(downloadIconSize, downloadIconSize);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_VERTICAL);

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(Color.YELLOW);

        return v;
    }

    protected TextView status(View leftView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.RIGHT_OF, leftView.getId());
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.leftMargin = leftRightPaddingOne;

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        statusTextStyle.style(v);

        return v;
    }

    protected TextView triggeredTitle(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());
        params.topMargin = dividerHeight;

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        titleTextStyle.style(v);
        v.setText(R.string.notification_detail_triggered_title);

        return v;
    }

    protected TextView triggered(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        timeTextStyle.style(v);

        return v;
    }

    protected TextView descriptionTitle(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());
        params.topMargin = dividerHeight;

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        titleTextStyle.style(v);
        v.setText(R.string.notification_detail_triggered_title);

        return v;
    }

    protected TextView description(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        timeTextStyle.style(v);

        return v;
    }

    protected TextView resolvedTitle(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());
        params.topMargin = dividerHeight;

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        titleTextStyle.style(v);
        v.setText(R.string.notification_detail_resolved_title);

        return v;
    }

    protected TextView resolved(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        timeTextStyle.style(v);

        return v;
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public void setStatus(String status, Calendar resolvedTime) {
        this.status.setText(status);
        this.statusIcon.setBackgroundResource(statusConstant.getStatusIconGroup().get(status));
        this.status.setTextColor(statusConstant.getStatusTextColorGroup().get(status));
        if (status.equals(CephNotificationConstant.STATUS_PENDING)) {
            resolvedTitle.setVisibility(GONE);
            resolved.setVisibility(GONE);
        } else {
            resolvedTitle.setVisibility(VISIBLE);
            resolved.setVisibility(VISIBLE);
            resolved.setText(datetimeFormat.format(resolvedTime.getTime()));
        }
    }

    public void setDescription(String title, String content) {
        descriptionTitle.setText(title);
        description.setText(content);
    }

    public void setTriggered(Calendar calendar) {
        triggered.setText(datetimeFormat.format(calendar.getTime()));
    }
}
