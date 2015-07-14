package com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartLine;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartTable;

import java.util.ArrayList;

/**
 * Created by User on 2015/6/30.
 */
public class HistogramAdapter implements ChartLine {
    private Paint valuePaint;
    private int color;
    private ArrayList<Double> values = new ArrayList<>();
    private ArrayList<Long> times = new ArrayList<>();

    public HistogramAdapter() {
        color = Color.BLACK;
    }

    @Override
    public void setData(ArrayList<Double> values, ArrayList<Long> times) {
        this.values = values;
        this.times = times;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void init() {
        valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valuePaint.setStrokeWidth(3);
        valuePaint.setStyle(Paint.Style.STROKE);
        valuePaint.setColor(color);
    }

    @Override
    public double max() {
        double max = 0;
        for (double value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void draw(ChartTable table, Canvas canvas) {
        if (values.size() == 0) return;

        int cout = (values.size() > times.size()) ? times.size() : values.size();
        for (int i = 0; i < cout; i++) {
            float x = caleX(table, i);
            if (table.isOverX(x)) continue;

            float top = caleY(table, i);
            canvas.drawLine(x, table.getTableBottom(), x, top, valuePaint);
        }
    }

    public float caleX(ChartTable table, int index) {
        long timeStamp = times.get(index);
        return table.getX(timeStamp);
    }

    public float caleY(ChartTable table, int index) {
        double pointValue = values.get(index);
        float y = table.getY(pointValue);
        return (table.isOverY(y)) ? table.getTableTop() : y;
    }

}
