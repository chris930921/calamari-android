package com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartLine;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartTable;

import java.util.ArrayList;

/**
 * Created by User on 2015/6/30.
 */
public class AreaLineAdapter implements ChartLine {
    private Paint valuePaint;
    private Paint fillPaint;
    private int color;
    private ArrayList<Double> values = new ArrayList<>();
    private ArrayList<Long> times = new ArrayList<>();
    private Path path = new Path();
    private Path fillPath = new Path();

    public AreaLineAdapter() {
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
        valuePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStrokeWidth(3);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.argb(128, Color.red(color), Color.green(color), Color.blue(color)));
        fillPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
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
        fillPath.reset();
        float previousX = 0;
        float previousY = 0;
        int i = 0;
        for (; i < values.size(); i++) {
            float x = caleX(table, i);
            if (table.isOverX(x)) continue;

            float y = caleY(table, i);
            path.moveTo(x, table.getTableBottom());
            path.lineTo(x, y);
            fillPath.moveTo(x, table.getTableBottom());
            fillPath.lineTo(x, y);
            previousY = y;
            previousX = x;
            break;
        }

        for (; i < values.size(); i++) {
            float x = caleX(table, i);
            if (table.isOverX(x)) continue;

            float y = caleY(table, i);
            path.lineTo(x, previousY);
            path.lineTo(x, y);
            fillPath.lineTo(x, previousY);
            fillPath.lineTo(x, y);
            previousY = y;
            previousX = x;
        }
        path.lineTo(previousX, table.getTableBottom());
        fillPath.lineTo(previousX, table.getTableBottom());

        fillPath.close();

        canvas.drawPath(fillPath, fillPaint);
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
