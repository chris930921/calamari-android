package com.cephmonitor.cephmonitor.layout.component.card;

import android.content.Context;

import com.cephmonitor.cephmonitor.layout.component.chart.IopsHistogram;
import com.resourcelibrary.model.logic.RandomId;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 4/17/2015.
 */
public class HealthIopsCard extends HealthBaseCard {
    public IopsHistogram histogram;

    public HealthIopsCard(Context context) {
        super(context);
        histogram = histogram();
        centerValueContainer.addView(histogram);
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

    public void setChartData(Calendar time, ArrayList<Double> values, ArrayList<Long> times) {
        if (histogram == null) return;
        histogram.setData(time, values, times);
    }

    @Override
    public void setValue(int leftValue, int rightValue) {
    }
}