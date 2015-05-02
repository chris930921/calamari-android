package com.resourcelibrary.model.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.Log;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * Created by User on 1/22/2015.
 */
public class LineTable extends CompoundButton {
    private float width, height;
    private float tablePadding;
    private float tableWidth, tableHeight;
    private float chartLeftPadding, chartRightPadding;
    private float chartWidth;
    private double max;
    private int animIndex;
    private boolean start;

    private ArrayList<Integer> position;
    private ArrayList<Float> valueX;
    private ArrayList<Float> valueY;
    private ArrayList<Float> originX;
    private ArrayList<Float> originY;

    public LineTable(Context context) {
        super(context);
        position = new ArrayList<>();
        originX = new ArrayList<>();
        originY = new ArrayList<>();
        valueX = new ArrayList<>();
        valueY = new ArrayList<>();
        animIndex = 0;
        start = false;
    }

    public void setValuesX(ArrayList<Long> values, long start, long end) {
        Log.w("Test Project", values.toString() + ":" + start + ":" + end);
        originX = new ArrayList<>();
        float period = end - start;
        if (values.size() < 1) return;

        for (int i = 0; i < values.size(); i++) {
            originX.add(chartWidth * ((values.get(i) - start) / period));
        }
    }

    public void setValuesY(ArrayList<Double> values, double max) {
        Log.w("Test Project", values.toString() + ":" + max);
        originY = new ArrayList<>();
        this.max = max;
        float delta = (float) max;

        if (values.size() < 1) return;
        for (int i = 0; i < values.size(); i++) {
            originY.add(tableHeight - (tableHeight * (values.get(i).floatValue() / delta)));
        }
    }

    public void start() {
        float distance = 2;

        Log.w("Test Project", originX.toString());
        Log.w("Test Project", originY.toString());

        position = new ArrayList<>();
        valueX = new ArrayList<>();
        valueY = new ArrayList<>();
        animIndex = 0;
        start = true;

        if (originX.size() == 0) return;
        else if (originY.size() == 0) return;

        valueX.add(originX.get(0));
        valueY.add(originY.get(0));
        for (int i = 1; i < originX.size(); i++) {
            float current = originX.get(i - 1) + distance;
            while (current < originX.get(i)) {
                valueX.add(current);
                valueY.add(equationY(current, originX.get(i - 1), originY.get(i - 1), originX.get(i), originY.get(i)));
                current += distance;
            }
            valueX.add(originX.get(i));
            valueY.add(originY.get(i));
            position.add(valueX.size() - 1);
        }
//        for (int i = 1; i < originY.size(); i++) {
//            if (originY.get(i - 1) > originY.get(i)) {
//                float current = originY.get(i - 1) - distance;
//                while (current > originY.get(i)) {
//                    Y.add(current);
//                    X.add(equationX(current, originX.get(i - 1), originY.get(i - 1), originX.get(i), originY.get(i)));
//                    current -= distance;
//                }
//            } else {
//                float current = originY.get(i - 1) + distance;
//                while (current < originY.get(i)) {
//                    Y.add(current);
//                    X.add(equationX(current, originX.get(i - 1), originY.get(i - 1), originX.get(i), originY.get(i)));
//                    current += distance;
//                }
//            }
//            X.add(originX.get(i));
//            Y.add(originY.get(i));
//        }
        animIndex = 0;
        post(anim);
    }

    public Runnable anim = new Runnable() {
        @Override
        public void run() {
            invalidate();
            animIndex++;
            if (animIndex < valueY.size() && start) {
                postDelayed(anim, 50);
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();

        tablePadding = height * 0.1f;
        tableWidth = width - tablePadding * 2;
        tableHeight = height - tablePadding * 2;

        chartLeftPadding = height * 0.18f;
        chartRightPadding = height * 0.1f;
        chartWidth = width - chartLeftPadding - chartRightPadding;

        Paint pen = new Paint();
        pen.setColor(Color.parseColor("#80FFFFFF"));
        pen.setStyle(Paint.Style.STROKE);
        pen.setStrokeWidth(height * 0.005f);
        pen.setAntiAlias(true);

        canvas.drawLine(getRelativeX(0), getRelativeY(0), getRelativeX(tableWidth), getRelativeY(0), pen);
        canvas.drawLine(getRelativeX(0), getRelativeY(tableHeight / 2), getRelativeX(tableWidth), getRelativeY(tableHeight / 2), pen);
        canvas.drawLine(getRelativeX(0), getRelativeY(tableHeight), getRelativeX(tableWidth), getRelativeY(tableHeight), pen);

        Paint textPen = new Paint();
        textPen.setTextSize(tablePadding * 0.7f);
        textPen.setStyle(Paint.Style.FILL);
        textPen.setColor(Color.parseColor("#80FFFFFF"));

        Rect bounds = new Rect();

        String maxStr = String.format("%.2f", max);
        textPen.getTextBounds(maxStr, 0, maxStr.length(), bounds);
        canvas.drawText(maxStr, getRelativeX(0), getRelativeY(0) - (getRelativeY(0) - bounds.height()) / 2, textPen);

        String minStr = "0";
        textPen.getTextBounds(minStr, 0, minStr.length(), bounds);
        canvas.drawText(minStr, getRelativeX(0), height - (getRelativeY(0) - bounds.height()) / 2, textPen);

        pen.setStrokeWidth(height * 0.007f);
        pen.setColor(Color.WHITE);
        pen.setStyle(Paint.Style.STROKE);

        try {
            if (valueX.size() == 0) return;
            if (animIndex >= valueY.size()) animIndex = valueY.size();

            Path linePath = new Path();
            Path backgroundPath = new Path();

            linePath.moveTo(getCharX(valueX.get(0)), getRelativeY(valueY.get(0)));
            backgroundPath.moveTo(getCharX(valueX.get(0)), getRelativeY(tableHeight));
            canvas.drawCircle(getCharX(valueX.get(0)), getRelativeY(valueY.get(0)), height * 0.01f, pen);

            for (int i = 1; i < animIndex; i++) {
                linePath.lineTo(getCharX(valueX.get(i)), getRelativeY(valueY.get(i)));
                backgroundPath.lineTo(getCharX(valueX.get(i)), getRelativeY(valueY.get(i)));
                if (position.contains(i)) {
                    canvas.drawCircle(getCharX(valueX.get(i)), getRelativeY(valueY.get(i)), height * 0.01f, pen);
                }
            }
            canvas.drawPath(linePath, pen);

            backgroundPath.lineTo(getCharX(valueX.get(animIndex - 1)), getRelativeY(tableHeight));
            Paint backgroundPen = new Paint();
            backgroundPen.setStyle(Paint.Style.FILL);
            if (chartShader == null) {
                chartShader = new LinearGradient(
                        width / 2, 0, width / 2, height,
                        new int[]{Color.parseColor("#FFFFFFFF"), Color.parseColor("#00FFFFFF")},
                        null, Shader.TileMode.REPEAT);
            }
            backgroundPen.setShader(chartShader);
            canvas.drawPath(backgroundPath, backgroundPen);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            start = false;
        }
    }

    private Shader chartShader;

    private float getRelativeX(float x) {
        return tablePadding + x;
    }

    private float getRelativeY(float y) {
        return tablePadding + y;
    }

    private float getCharX(float x) {
        return chartLeftPadding + x;
    }

    private float slope(float startX, float startY, float endX, float endY) {
        return (endY - startY) / (endX - startX);
    }

    private float equationY(float x, float startX, float startY, float endX, float endY) {
        return slope(startX, startY, endX, endY) * (x - startX) + startY;
    }

    private float equationX(float y, float startX, float startY, float endX, float endY) {
        return (y - startY) / slope(startX, startY, endX, endY) + startX;
    }
}