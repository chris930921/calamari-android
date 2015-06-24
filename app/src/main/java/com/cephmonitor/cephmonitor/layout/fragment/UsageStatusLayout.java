package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class UsageStatusLayout extends RelativeLayout {
    private Context context;
    private WH ruler;

    public TextView diskUsageStatisticsTitle;
    public TextView diskUsageStatisticsAvailable;
    public TextView diskUsageStatisticsUsed;

    public UsageStatusLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        diskUsageStatisticsTitle = diskUsageStatisticsTitle();
        diskUsageStatisticsAvailable = diskUsageStatisticsAvailable(diskUsageStatisticsTitle);
        diskUsageStatisticsUsed = diskUsageStatisticsUsed(diskUsageStatisticsAvailable);

        addView(diskUsageStatisticsTitle);
        addView(diskUsageStatisticsAvailable);
        addView(diskUsageStatisticsUsed);
    }

    private TextView diskUsageStatisticsTitle() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_TOP);
        params.setMargins(ruler.getW(5), ruler.getW(5), ruler.getW(5), 0);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setTextColor(ColorTable._666666);
        v.setTypeface(null, Typeface.BOLD);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.usage_status_disk_usage_statistics);

        return v;
    }

    private TextView diskUsageStatisticsAvailable(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_LEFT, topView.getId());
        params.addRule(BELOW, topView.getId());
        params.setMargins(0, ruler.getW(3), 0, 0);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setTextColor(ColorTable._F7B500);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.usage_status_disk_usage_available);

        return v;
    }

    private TextView diskUsageStatisticsUsed(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, leftView.getId());
        params.addRule(RIGHT_OF, leftView.getId());

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setTextColor(ColorTable._8DC41F);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.usage_status_disk_usage_used);

        return v;
    }
}
