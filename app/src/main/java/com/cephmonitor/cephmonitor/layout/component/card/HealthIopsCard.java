package com.cephmonitor.cephmonitor.layout.component.card;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartLine;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartTable;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;

import java.util.Calendar;

/**
 * Created by User on 4/17/2015.
 */
public class HealthIopsCard extends HealthBaseCard {
    public ChartTable histogram;
    public TextView readWriteText;
    private DesignSpec designSpec;
    private TextViewStyle bodyTwo;
    private float topBottomPaddingOne;
    private float leftRightPaddingOne;

    public HealthIopsCard(Context context) {
        super(context);
        this.designSpec = ThemeManager.getStyle(context);
        bodyTwo = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        topBottomPaddingOne = designSpec.getPadding().getTopBottomOne();
        leftRightPaddingOne = designSpec.getPadding().getLeftRightOne();

        readWriteText = readWriteText();
        histogram = histogram(readWriteText);

        centerValueContainer.addView(histogram);
        centerValueContainer.addView(readWriteText);
        centerCenterContainer.setVisibility(GONE);
        bottomContainer.setVisibility(GONE);
    }

    public TextView readWriteText() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_TOP);
        params.addRule(ALIGN_PARENT_LEFT);
        params.leftMargin = ruler.getW(leftRightPaddingOne);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.health_card_read_write);
        bodyTwo.style(v);
        v.setTextColor(ColorTable._8DC41F);

        return v;
    }

    private ChartTable histogram(View topView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ruler.getW(45)
        );
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());
        params.leftMargin = ruler.getW(leftRightPaddingOne);
        params.rightMargin = ruler.getW(leftRightPaddingOne);
        params.topMargin = ruler.getW(topBottomPaddingOne);

        ChartTable v = new ChartTable(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setMaxTime(Calendar.getInstance());
        v.setBackgroundColor(Color.TRANSPARENT);

        return v;
    }


    public void setChartData(ChartLine adapter) {
        if (histogram == null) return;
        histogram.addAdapter(adapter);
    }

    @Override
    public void setValue(int leftValue, int rightValue) {
    }
}