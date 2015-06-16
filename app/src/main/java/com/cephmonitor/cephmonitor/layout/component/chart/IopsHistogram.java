package com.cephmonitor.cephmonitor.layout.component.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.view.WH;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 6/15/2015.
 */
public class IopsHistogram extends View {
    private WH ruler;
    private DateFormat dateFormat = new SimpleDateFormat("ddMMM", Locale.US);
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private Calendar time;

    private Double maxValue;
    private Double tableMaxValue;
    private String leftHalfValue;

    private float width;
    private float height;
    private float leftTextSpace;
    private float bottomTextSpace;
    private float rightPadding;
    private float topPadding;
    private float tableWidth;
    private float tableHeight;
    private float xUnitWidth;
    private float yUnitHeight;

    private float xUnitOffset;
    private float tableTotalTimeStamp;
    private float tableMinTimeStamp;

    private Paint backgroundPaint;
    private Paint axisPaint;
    private Paint gridPaint;
    private Paint textPaint;
    private Paint valuePaint;

    private Rect textBounds;

    private ArrayList<Float> list;

    private int timeUnit = 6;
    private float xGridCount = 4;

    private ArrayList<Double> values;
    private ArrayList<Long> times;

    public IopsHistogram(Context context) {
        super(context);
        ruler = new WH(context);
        list = new ArrayList<>();
        textBounds = new Rect();
        leftHalfValue = "1.0";
        maxValue = 2.0;
        tableMaxValue = 2.0;
        time = Calendar.getInstance();

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.WHITE);

        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setColor(ColorTable._666666);
        axisPaint.setStrokeWidth(ruler.getW(0.5));

        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.parseColor("#CBCDCC"));
        gridPaint.setStrokeWidth(ruler.getW(0.3));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(ColorTable._666666);

        valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valuePaint.setColor(ColorTable._8DC41F);
        valuePaint.setStrokeWidth(ruler.getW(0.5));

        xUnitOffset = 0;
    }

    public void setData(Calendar time, ArrayList<Double> values, ArrayList<Long> times) {
        this.values = values;
        this.times = times;
        this.time = time;

        tableTotalTimeStamp = 60f * 60f * 6f * xGridCount * 1000;
        tableMinTimeStamp = time.getTimeInMillis() - tableTotalTimeStamp;

        updateMaxValue();
        updateTableValue();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        updateTableValue();
    }

    private void updateTableValue() {
        textPaint.setTextSize((width * 0.073f) / 2);
        leftTextSpace = (int) textPaint.measureText(leftHalfValue, 0, leftHalfValue.length());
        bottomTextSpace = height * 0.160f;
        rightPadding = width * 0.024f;
        topPadding = height * 0.080f;
        tableWidth = width - leftTextSpace - rightPadding;
        tableHeight = height - bottomTextSpace - topPadding;
        xUnitWidth = tableWidth * (1f / xGridCount);
        yUnitHeight = tableHeight * 0.5f;
        xUnitOffset = getTimeOffset(getPreviousTime());
    }

    private void updateMaxValue() {
        for (int i = 0; i < values.size(); i++) {
            Double pointValue = values.get(i);
            long timestamp = times.get(i);
            if (pointValue > maxValue && timestamp >= tableMinTimeStamp) {
                maxValue = pointValue;
            }
        }
        Integer integerMaxValue = maxValue.intValue();
        double tenUnit = (int) Math.pow(10, integerMaxValue.toString().length() - 1);
        tableMaxValue = (((int) (integerMaxValue / tenUnit)) + 1) * tenUnit;
        leftHalfValue = String.format("%.1f", tableMaxValue / 2f) + "";
    }

    private long getPreviousTime() {
        Calendar drawTime = Calendar.getInstance();
        drawTime.setTime(time.getTime());
        int hour = drawTime.get(Calendar.HOUR_OF_DAY);
        int delta = 0;
        for (; ((hour - delta) % timeUnit) != 0; delta++) ;
        drawTime.add(Calendar.HOUR_OF_DAY, -delta);
        drawTime.set(Calendar.MINUTE, 0);
        return drawTime.getTimeInMillis();
    }

    private float getTimeOffset(long previousTimeStamp) {
        double deltaTime = time.getTimeInMillis() - previousTimeStamp;
        float percent = (float) (deltaTime / (60d * 60d * 6d * 1000d));
        return xUnitWidth * percent;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        countXGridPosition();
        drawGrid(canvas, list);
        if (values != null && times != null) {
            drawData(canvas);
        }
        drawAxis(canvas);
        drawLeftText(canvas);
        drawBottomText(canvas, list);
    }

    private void drawData(Canvas canvas) {
        int cout = (values.size() > times.size()) ? times.size() : values.size();
        for (int i = 0; i < cout; i++) {
            double pointValue = values.get(i);
            long timeStamp = times.get(i);
            float top = topPadding + (float) ((1 - (pointValue / tableMaxValue)) * tableHeight);
            float x = leftTextSpace + (((timeStamp - tableMinTimeStamp) / tableTotalTimeStamp) * tableWidth);
            if (x <= leftTextSpace) continue;
            canvas.drawLine(x, topPadding + tableHeight, x, top, valuePaint);
        }
    }

    private ArrayList<Float> countXGridPosition() {
        list.clear();
        float xUnitCount = (float) Math.ceil(tableWidth / xUnitWidth);
        for (int i = 0; i < xUnitCount; i++) {
            float drawX = leftTextSpace + (tableWidth - (xUnitWidth * i) - xUnitOffset);
            if (drawX < leftTextSpace) break;
            list.add(drawX);
        }
        return list;
    }

    private void drawLeftText(Canvas canvas) {
        String zeroValue = 0 + "";

        drawTextByRightAndVerticalCenter(canvas, leftTextSpace, topPadding + yUnitHeight, leftHalfValue);
        drawTextByRightAndVerticalCenter(canvas, leftTextSpace, topPadding + tableHeight, zeroValue);
    }

    private void drawTextByRightAndVerticalCenter(Canvas canvas, float right, float verticalCenter, String text) {
        String maxTextHeightExample = "Ij";

        textPaint.getTextBounds(text + maxTextHeightExample, 0, text.length() + maxTextHeightExample.length(), textBounds);
        float textWidth = (int) textPaint.measureText(text, 0, text.length());
        float textHeight = textBounds.height();

        float textLeft = right - textWidth;
        float textBottom = verticalCenter + (textHeight / 2f);

        canvas.drawText(text, textLeft, textBottom, textPaint);
    }

    private void drawBottomText(Canvas canvas, ArrayList<Float> xGridPosition) {
        Calendar drawTime = Calendar.getInstance();
        drawTime.setTimeInMillis(getPreviousTime());
        drawTime.set(Calendar.MINUTE, 0);
        String text;
        for (int i = 0; i < xGridPosition.size(); i++) {
            ShowLog.d("小時:" + drawTime.get(Calendar.HOUR_OF_DAY));
            if (drawTime.get(Calendar.HOUR_OF_DAY) == 0) {
                text = dateFormat.format(drawTime.getTime());
            } else {
                text = timeFormat.format(drawTime.getTime());
            }
            float drawX = xGridPosition.get(i);
            drawTextByTopAndHorizonCenter(canvas, topPadding + tableHeight, drawX, text);
            drawTime.add(Calendar.HOUR_OF_DAY, -timeUnit);
        }

    }

    private void drawTextByTopAndHorizonCenter(Canvas canvas, float top, float horizonCenter, String text) {
        String maxTextHeightExample = "Ij";

        textPaint.getTextBounds(text + maxTextHeightExample, 0, text.length() + maxTextHeightExample.length(), textBounds);
        float textWidth = (int) textPaint.measureText(text, 0, text.length());
        float textHeight = textBounds.height();

        float textLeft = horizonCenter - (textWidth / 2);
        float textBottom = top + textHeight;

        if (textLeft + textWidth > leftTextSpace + tableWidth) {
            canvas.drawText(text, leftTextSpace + tableWidth - textWidth, textBottom, textPaint);
        } else if (textLeft < leftTextSpace) {
            canvas.drawText(text, leftTextSpace, textBottom, textPaint);
        } else {
            canvas.drawText(text, textLeft, textBottom, textPaint);
        }
    }

    private void drawGrid(Canvas canvas, ArrayList<Float> xGridPosition) {
        canvas.drawRect(0, 0, width, height, backgroundPaint);

        canvas.drawLine(
                leftTextSpace, topPadding + yUnitHeight,
                leftTextSpace + tableWidth, topPadding + yUnitHeight,
                gridPaint
        );

        for (int i = 0; i < xGridPosition.size(); i++) {
            float drawX = xGridPosition.get(i);
            canvas.drawLine(
                    drawX, topPadding,
                    drawX, topPadding + tableHeight,
                    gridPaint
            );
        }
    }

    private void drawAxis(Canvas canvas) {
        canvas.drawLine(
                leftTextSpace, topPadding,
                leftTextSpace, topPadding + tableHeight,
                axisPaint
        );
        canvas.drawLine(
                leftTextSpace, topPadding + tableHeight,
                leftTextSpace + tableWidth, topPadding + tableHeight,
                axisPaint
        );
    }
}
