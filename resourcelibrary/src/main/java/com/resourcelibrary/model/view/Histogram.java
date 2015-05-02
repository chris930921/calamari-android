package com.resourcelibrary.model.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 1/22/2015.
 */
public class Histogram extends CompoundButton {
    private float width, height;
    private float tablePadding;
    private float tableWidth, tableHeight;
    private float chartLeftPadding, chartRightPadding;
    private float chartWidth;
    private float max;
    private ArrayList<Float> values;
    private int columns;
    private HashMap<String, ArrayList<Float>> drawValuesGroups;
    private String currentDrawingData;

    private final float FRAME_LENGTH = 70;
    private final int INTERVAL = 10;
    private float currentFrame = 0;
    private RectF clickBounds;
    private float X;
    private float Y;
    private boolean isHoursShow;
    private int showingTime;
    private boolean isHoursButtonEnable;
    private Shader chartShader;

    public Histogram(Context context) {
        super(context);
        this.currentDrawingData = (Math.random() * 100000) + "";
        this.drawValuesGroups = new HashMap<>();
        this.isHoursButtonEnable = false;
        this.drawValuesGroups.put(currentDrawingData, new ArrayList<Float>());

        postDelayed(closeHoursButton(), 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();
        if (chartShader == null) {
            chartShader = new LinearGradient(
                    width / 2, 0, width / 2, height,
                    new int[]{Color.parseColor("#FFFFFFFF"), Color.parseColor("#00FFFFFF")},
                    null, Shader.TileMode.REPEAT);
        }

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

        Paint chartPen = new Paint();
        chartPen.setStyle(Paint.Style.FILL);
//        chartPen.setColor(Color.parseColor("#80FFFFFF"));
        chartPen.setShader(chartShader);

        ArrayList<Float> drawValues = drawValuesGroups.get(currentDrawingData);
        if (drawValues != null) {
            columns = drawValues.size();
            float lineWidth = tableWidth / (columns + 6);
            for (float i = 0; i < columns; i++) {
                float left = tableWidth * (i / columns);
                float top = tableHeight - (tableHeight * (drawValues.get((int) i) / max));
                float right = left + lineWidth;
                canvas.drawRect(getRelativeX(left), getRelativeY(top), getRelativeX(right), getRelativeY(tableHeight), chartPen);
            }
        }
        if (isHoursShow && isHoursButtonEnable) {
            Paint buttonPen = new Paint();
            buttonPen.setStyle(Paint.Style.FILL);
            buttonPen.setColor(Color.parseColor("#FFFFFF"));

            Paint hoursTextPen = new Paint();
            hoursTextPen.setTextSize(tablePadding * 0.7f);
            hoursTextPen.setStyle(Paint.Style.FILL);
            hoursTextPen.setColor(Color.parseColor("#4F595B"));

            int clickLine = (int) (X / (width / columns));
            clickLine = (clickLine <= 0) ? 1 : clickLine;
            clickLine = (clickLine >= columns - 1) ? columns - 2 : clickLine;
            float hoursBlockWidth = width / 3;
            float hoursBlockHeight = height / 3;

            clickBounds = new RectF();
            float reviseX = (X - (hoursBlockWidth / 2) < 0) ? (hoursBlockWidth / 2) : X;
            reviseX = (reviseX + (hoursBlockWidth / 2) > width) ? (width - (hoursBlockWidth / 2)) : reviseX;
//            float reviseY = (Y - (hoursBlockHeight) < 0) ? hoursBlockHeight : Y;
            float reviseY = hoursBlockHeight + tablePadding;
            clickBounds.left = reviseX - (hoursBlockWidth / 2);
            clickBounds.right = reviseX + (hoursBlockWidth / 2);
            clickBounds.top = reviseY - (hoursBlockHeight);
            clickBounds.bottom = reviseY;
            canvas.drawRoundRect(clickBounds, 10, 10, buttonPen);

            Rect hoursBounds = new Rect();
            float centerY = ((clickBounds.bottom - clickBounds.top) / 2) + clickBounds.top;
            String leftText = (clickLine - 1) + "";
            textPen.getTextBounds(leftText, 0, leftText.length(), hoursBounds);
            canvas.drawText(leftText, clickBounds.left + (hoursBlockWidth * (1f / 6f) - (hoursBounds.width() / 2f)), centerY + (hoursBounds.height() / 2f), hoursTextPen);

            String centerText = (clickLine) + "";
            textPen.getTextBounds(centerText, 0, centerText.length(), hoursBounds);
            canvas.drawText(centerText, clickBounds.left + (hoursBlockWidth * (3f / 6f) - (hoursBounds.width() / 2f)), centerY + (hoursBounds.height() / 2f), hoursTextPen);

            String rightText = (clickLine + 1) + "";
            textPen.getTextBounds(rightText, 0, rightText.length(), hoursBounds);
            canvas.drawText(rightText, clickBounds.left + (hoursBlockWidth * (5f / 6f) - (hoursBounds.width() / 2f)), centerY + (hoursBounds.height() / 2f), hoursTextPen);
        }
    }

    private float getRelativeX(float x) {
        return tablePadding + x;
    }

    private float getRelativeY(float y) {
        return tablePadding + y;
    }

    private float getCharX(float x) {
        return chartLeftPadding + x;
    }

    public void setHoursButtonEnable(boolean isHoursButtonEnable) {
        this.isHoursButtonEnable = isHoursButtonEnable;
    }

    public void setData(ArrayList<Float> values) {
        changeTag();
        this.values = values;
        this.columns = values.size();
        for (float value : values) {
            if (value > max) max = value;
        }
        drawValuesGroups.put(currentDrawingData, new ArrayList<>(values));
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ((event.getAction() == MotionEvent.ACTION_DOWN) || (event.getAction() == MotionEvent.ACTION_MOVE)) {
            showingTime = 2000;
            X = event.getX();
            Y = event.getY();
            Log.d("Test專案偵錯", X + "::" + Y);
            isHoursShow = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            return false;
        }
        return true;
    }

    public Runnable closeHoursButton() {
        return new Runnable() {
            @Override
            public void run() {
                if (isHoursShow & (showingTime <= 0)) {
                    isHoursShow = false;
                } else if (isHoursShow) {
                    showingTime -= 100;
                }
                invalidate();
                postDelayed(closeHoursButton(), 100);
            }
        };
    }

    public void setDataWithAnim(ArrayList<Float> values) {
        changeTag();
        this.currentFrame = 0;
        this.values = values;
        for (float value : values) {
            if (value > max) max = value;
        }
        postDelayed(anim(currentDrawingData), INTERVAL);
    }

    private Runnable anim(final String tag) {
        return new Runnable() {
            @Override
            public void run() {
                if (currentFrame > FRAME_LENGTH) return;

                if (tag.equals(currentDrawingData)) {
                    drawValuesGroups.put(tag, new ArrayList<Float>());
                    for (int i = 0; i < values.size(); i++) {
                        drawValuesGroups.get(tag).add(values.get(i) * (currentFrame / FRAME_LENGTH));
                    }
                    invalidate();
                    currentFrame++;
                    postDelayed(anim(tag), INTERVAL);
                } else {
                    drawValuesGroups.remove(tag);
                }
            }
        };
    }

    private synchronized void changeTag() {
        this.drawValuesGroups.remove(currentDrawingData);
        this.currentDrawingData = (Math.random() * 100000) + "";
        drawValuesGroups.put(currentDrawingData, new ArrayList<Float>());
    }
}