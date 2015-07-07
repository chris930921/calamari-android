package com.cephmonitor.cephmonitor.layout.listitem;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartLine;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartTable;
import com.cephmonitor.cephmonitor.layout.component.other.HorizonFloatContainer;
import com.cephmonitor.cephmonitor.layout.fragment.HostDetailIopsLayout;
import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.util.ArrayList;
import java.util.Calendar;

public class HostDetailIopsItem extends RelativeLayout {
    private Context context;
    private WH ruler;


    public TextView title;
    public HorizonFloatContainer floatContainer;
    public ChartTable table;

    public HostDetailIopsItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);
        setPadding(0, ruler.getW(5), 0, 0);

        title = title();
        floatContainer = floatContainer(title);
        table = table(floatContainer);

        addView(title);
        addView(floatContainer);
        addView(table);

        setLineText(HostDetailIopsLayout.textGroup, HostDetailIopsLayout.textColorGroup);
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

    public HorizonFloatContainer floatContainer(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_LEFT, topView.getId());
        params.addRule(BELOW, topView.getId());
        params.setMargins(0, ruler.getW(3), 0, 0);

        HorizonFloatContainer v = new HorizonFloatContainer(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    private TextView addText(int textId, int color) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setTextColor(color);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(textId);

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

    public void setLineText(int[] textIdGroup, int[] colorGroup) {
        floatContainer.removeAllViews();
        for (int i = 0; i < textIdGroup.length; i++) {
            floatContainer.addView(addText(textIdGroup[i], colorGroup[i]));
        }
    }

    public void setName(String text) {
        title.setText(text);
    }

    public void setData(ArrayList<ChartLine> dataGroup) {
        table.cleanData();
        table.setMaxTime(Calendar.getInstance());
        ArrayList<ChartLine> newDataGroup = new ArrayList<>(dataGroup);
        for (ChartLine adapter : newDataGroup) {
            table.addAdapter(adapter);
        }
        invalidate();
    }
}
