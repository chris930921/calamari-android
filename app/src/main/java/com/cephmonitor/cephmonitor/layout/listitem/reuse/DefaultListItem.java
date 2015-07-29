package com.cephmonitor.cephmonitor.layout.listitem.reuse;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartLine;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartTable;
import com.cephmonitor.cephmonitor.layout.component.container.HorizonFloatContainer;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.util.ArrayList;
import java.util.Calendar;

public class DefaultListItem extends RelativeLayout {
    private Context context;
    private WH ruler;
    private DesignSpec designSpec;
    private TextViewStyle subhead;
    private TextViewStyle bodyTwo;
    private float topBottomMarginOne;
    private float topBottomPaddingOne;
    private float leftRightPaddingOne;

    public TextView title;
    public HorizonFloatContainer floatContainer;
    public ChartTable table;

    public DefaultListItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.designSpec = ThemeManager.getStyle(context);
        subhead = new TextViewStyle(designSpec.getStyle().getSubhead());
        bodyTwo = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        topBottomMarginOne = designSpec.getMargin().getTopBottomOne();
        topBottomPaddingOne = designSpec.getPadding().getTopBottomOne();
        leftRightPaddingOne = designSpec.getPadding().getLeftRightOne();

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setPadding(0, ruler.getW(topBottomMarginOne), 0, ruler.getW(topBottomPaddingOne));

        title = title();
        floatContainer = floatContainer(title);
        table = table(floatContainer);

        addView(title);
        addView(floatContainer);
        addView(table);
    }

    private TextView title() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_TOP);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        subhead.style(v);

        return v;
    }

    public HorizonFloatContainer floatContainer(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_LEFT, topView.getId());
        params.addRule(BELOW, topView.getId());
        params.setMargins(0, ruler.getW(topBottomPaddingOne), 0, 0);

        HorizonFloatContainer v = new HorizonFloatContainer(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setViewLeftMargin(ruler.getW(leftRightPaddingOne));

        return v;
    }

    private TextView addText(int textId, int color) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(textId);
        bodyTwo.style(v);
        v.setTextColor(color);

        return v;
    }

    private ChartTable table(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(45));
        params.addRule(ALIGN_LEFT, topView.getId());
        params.addRule(BELOW, topView.getId());
        params.setMargins(0, ruler.getW(topBottomPaddingOne), 0, 0);

        ChartTable v = new ChartTable(context);
        v.setBackgroundColor(Color.TRANSPARENT);
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

    public void setName(int textId) {
        title.setText(textId);
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
