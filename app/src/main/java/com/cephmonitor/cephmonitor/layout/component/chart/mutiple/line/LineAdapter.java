package com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

/**
 * Created by User on 2015/6/30.
 */
public class LineAdapter implements ChartLine {
    private Paint valuePaint;
    private int color;
    private ArrayList<Double> values = new ArrayList<>();
    private ArrayList<Long> times = new ArrayList<>();
    private Path path = new Path();

    public void setData(ArrayList<Double> values, ArrayList<Long> times) {
        this.values = values;
        this.times = times;
    }

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

        path.reset();
        int i = 0;
        for (; i < values.size(); i++) {
            float x = caleX(table, i);
            if (table.isOverX(x)) continue;

            float y = caleY(table, i);
            path.moveTo(x, y);
            break;
        }

        for (; i < values.size(); i++) {
            float x = caleX(table, i);
            if (table.isOverX(x)) continue;

            float y = caleY(table, i);
            path.lineTo(x, y);
        }
        canvas.drawPath(path, valuePaint);
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
