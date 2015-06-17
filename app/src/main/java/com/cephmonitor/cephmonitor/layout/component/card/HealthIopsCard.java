package com.cephmonitor.cephmonitor.layout.component.card;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.IopsHistogram;
import com.resourcelibrary.model.logic.RandomId;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 4/17/2015.
 */
public class HealthIopsCard extends HealthBaseCard {
    public IopsHistogram histogram;
    public TextView readWriteText;

    public HealthIopsCard(Context context) {
        super(context);
        histogram = histogram();
        readWriteText = readWriteText(histogram);

        centerValueContainer.addView(histogram);
        centerValueContainer.addView(readWriteText);
        centerCenterContainer.setVisibility(GONE);
        bottomContainer.setVisibility(GONE);
    }


    private IopsHistogram histogram() {
        LayoutParams params = new LayoutParams(
                ruler.getW(90),
                ruler.getW(45)
        );
        params.addRule(CENTER_IN_PARENT);

        IopsHistogram v = new IopsHistogram(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public TextView readWriteText(View bottomView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        params.addRule(ABOVE, bottomView.getId());
        params.addRule(ALIGN_LEFT, bottomView.getId());

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTextColor(ColorTable._8DC41F);
        v.setText(R.string.health_card_read_write);

        return v;
    }

    public void setChartData(Calendar time, ArrayList<Double> values, ArrayList<Long> times) {
        if (histogram == null) return;
        histogram.setData(time, values, times);
    }

    @Override
    public void setValue(int leftValue, int rightValue) {
    }
}