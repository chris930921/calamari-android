package com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by User on 2015/6/30.
 */
public interface ChartLine {

    public void setData(ArrayList<Double> values, ArrayList<Long> times);

    public void setColor(int color);

    public void init();

    public double max();

    public void draw(ChartTable table, Canvas canvas);
}
