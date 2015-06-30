package com.cephmonitor.cephmonitor.layout.listitem;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.LineAdapter;
import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.util.ArrayList;
import java.util.Calendar;

public class PoolIopsItem extends RelativeLayout {
    private Context context;
    private WH ruler;

    public TextView title;
    public TextView read;
    public TextView write;
    public ChartTable table;

    public PoolIopsItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);
        setPadding(0, ruler.getW(5), 0, 0);

        title = title();
        read = read(title);
        write = write(read);
        table = table(read);

        addView(title);
        addView(read);
        addView(write);
        addView(table);
    }

    private TextView title() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_TOP);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setTextColor(ColorTable._666666);
        v.setTypeface(null, Typeface.BOLD);
        v.setGravity(Gravity.CENTER_VERTICAL);

        return v;
    }

    private TextView read(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_LEFT, topView.getId());
        params.addRule(BELOW, topView.getId());
        params.setMargins(0, ruler.getW(3), 0, 0);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setTextColor(ColorTable._8DC41F);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.pool_iops_read);

        return v;
    }

    private TextView write(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, leftView.getId());
        params.addRule(RIGHT_OF, leftView.getId());

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setTextColor(ColorTable._F7B500);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.pool_iops_write);

        return v;
    }

    private ChartTable table(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(50));
        params.addRule(ALIGN_LEFT, topView.getId());
        params.addRule(BELOW, topView.getId());

        ChartTable v = new ChartTable(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    public void setData(String titleText, ArrayList<LineAdapter> dataGroup) {
        title.setText(titleText);
        table.setMaxTime(Calendar.getInstance());
        for (LineAdapter adapter : dataGroup) {
            table.addAdapter(adapter);
        }
        invalidate();
    }
}
