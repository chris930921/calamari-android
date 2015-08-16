package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartTable;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class UsageStatusLayout extends FractionAbleRelativeLayout {
    private Context context;
    private WH ruler;
    private DesignSpec designSpec;
    private TextViewStyle subhead;
    private TextViewStyle bodyTwo;
    private float topBottomMarginOne;
    private float topBottomPaddingOne;
    private float leftRightPaddingOne;
    private float leftRightMarginOne;

    public TextView diskUsageStatisticsTitle;
    public TextView diskUsageStatisticsAvailable;
    public TextView diskUsageStatisticsUsed;
    public ChartTable table;

    public UsageStatusLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.designSpec = ThemeManager.getStyle(context);
        subhead = new TextViewStyle(designSpec.getStyle().getSubhead());
        bodyTwo = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        leftRightMarginOne = designSpec.getMargin().getLeftRightOne();
        topBottomMarginOne = designSpec.getMargin().getTopBottomOne();
        topBottomPaddingOne = designSpec.getPadding().getTopBottomOne();
        leftRightPaddingOne = designSpec.getPadding().getLeftRightOne();

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);

        diskUsageStatisticsTitle = diskUsageStatisticsTitle();
        diskUsageStatisticsAvailable = diskUsageStatisticsAvailable(diskUsageStatisticsTitle);
        diskUsageStatisticsUsed = diskUsageStatisticsUsed(diskUsageStatisticsAvailable);
        table = table(diskUsageStatisticsAvailable);

        addView(diskUsageStatisticsTitle);
        addView(diskUsageStatisticsAvailable);
        addView(diskUsageStatisticsUsed);
        addView(table);
    }

    private TextView diskUsageStatisticsTitle() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_TOP);
        params.setMargins(
                ruler.getW(leftRightMarginOne),
                ruler.getW(topBottomMarginOne),
                ruler.getW(leftRightMarginOne), 0);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.usage_status_disk_usage_statistics);
        subhead.style(v);

        return v;
    }

    private TextView diskUsageStatisticsAvailable(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_LEFT, topView.getId());
        params.addRule(BELOW, topView.getId());
        params.setMargins(0, ruler.getW(topBottomPaddingOne), 0, 0);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.usage_status_disk_usage_available);
        bodyTwo.style(v);
        v.setTextColor(ColorTable._8DC41F);

        return v;
    }

    private TextView diskUsageStatisticsUsed(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, leftView.getId());
        params.addRule(RIGHT_OF, leftView.getId());
        params.setMargins(ruler.getW(leftRightPaddingOne), 0, 0, 0);


        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.usage_status_disk_usage_used);
        bodyTwo.style(v);
        v.setTextColor(ColorTable._F7B500);

        return v;
    }

    private ChartTable table(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(50));
        params.addRule(BELOW, topView.getId());
        params.setMargins(
                ruler.getW(leftRightMarginOne),
                ruler.getW(topBottomMarginOne),
                ruler.getW(leftRightMarginOne),
                ruler.getW(topBottomMarginOne));

        ChartTable v = new ChartTable(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(Color.TRANSPARENT);

        return v;
    }

}
