package com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.resourcelibrary.model.view.WH;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 2015/6/30.
 */
public class ChartTable extends View {
    private WH ruler;
    private DateFormat dateFormat = new SimpleDateFormat("ddMMM", Locale.US);
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private Calendar time;

    private Double maxValue;
    private Double tableMaxValue;
    private String leftHalfValue;
    private String leftMaxValue;

    private float width;
    private float height;
    private float leftTextSpace;
    private float leftMaxTextSpace;
    private float leftHalfTextSpace;
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

    private Rect textBounds;

    private ArrayList<Float> list;

    private int timeUnit = 1;
    private float xGridCount = 6;

    private ArrayList<ChartLine> lineAdapters;

    public ChartTable(Context context) {
        super(context);
        ruler = new WH(context);
        list = new ArrayList<>();
        textBounds = new Rect();
        time = Calendar.getInstance();
        lineAdapters = new ArrayList<>();
        tableMaxValue = 1.0;

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setColor(ColorTable._666666);
        axisPaint.setStrokeWidth(ruler.getW(0.5));

        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(ColorTable._CBCDCC);
        gridPaint.setStrokeWidth(ruler.getW(0.3));
        gridPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(ColorTable._666666);

        tableTotalTimeStamp = 60f * 60f * timeUnit * xGridCount * 1000;
        tableMinTimeStamp = time.getTimeInMillis() - tableTotalTimeStamp;
        xUnitOffset = 0;
        maxValue = 1.0;
        updateMax();
    }

    public void addAdapter(ChartLine adapter) {
        lineAdapters.add(adapter);
        adapter.init();
        double currentMax = adapter.max();
        if (currentMax > maxValue) {
            maxValue = currentMax;
            updateMax();
        }
        invalidate();
    }

    public void setMaxTime(Calendar time) {
        tableMinTimeStamp = time.getTimeInMillis() - tableTotalTimeStamp;
        invalidate();
    }

    @Override
    public void setBackgroundColor(int color) {
        backgroundPaint.setColor(color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        updateMax();
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
        float percent = (float) (deltaTime / (60d * 60d * timeUnit * 1000d));
        return xUnitWidth * percent;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawData(canvas);
        countXGridPosition();
        drawGrid(canvas, list);
        drawAxis(canvas);
        drawLeftText(canvas);
        drawBottomText(canvas, list);
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

        drawTextByRightAndVerticalCenter(canvas, leftTextSpace, topPadding, leftMaxValue);
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
        for (int i = 0; i < xGridPosition.size(); i++) {
            float drawX = xGridPosition.get(i);
            canvas.drawLine(
                    drawX, 0,
                    drawX, topPadding + tableHeight,
                    gridPaint
            );
        }

        canvas.drawLine(
                leftTextSpace, topPadding,
                leftTextSpace + tableWidth, topPadding,
                gridPaint
        );
        canvas.drawLine(
                leftTextSpace, topPadding + yUnitHeight,
                leftTextSpace + tableWidth, topPadding + yUnitHeight,
                gridPaint
        );

        canvas.drawRect(0, 0, width, height, backgroundPaint);
    }

    private void drawAxis(Canvas canvas) {
        canvas.drawLine(
                leftTextSpace, 0,
                leftTextSpace, topPadding + tableHeight,
                axisPaint
        );
        canvas.drawLine(
                leftTextSpace, topPadding + tableHeight,
                leftTextSpace + tableWidth, topPadding + tableHeight,
                axisPaint
        );
    }

    public float getY(double value) {
        return topPadding + (float) ((1 - (value / tableMaxValue)) * tableHeight);
    }

    public float getX(long timestamp) {
        return leftTextSpace + (((timestamp - tableMinTimeStamp) / tableTotalTimeStamp) * tableWidth);
    }

    public float getTableTop() {
        return topPadding;
    }

    public float getTableBottom() {
        return topPadding + tableHeight;
    }


    public boolean isOverY(float y) {
        return y < topPadding;
    }

    public boolean isOverX(float x) {
        return x < leftTextSpace || x > (width - rightPadding);
    }

    public void updateMax() {
        tableMaxValue = Math.ceil(maxValue / 20) * 20;
        leftMaxValue = NumberUnit.change(tableMaxValue);
        leftHalfValue = NumberUnit.change(tableMaxValue / 2);
        reCaleConst();
    }


    public void reCaleConst() {
        textPaint.setTextSize((width * 0.073f) / 2);
        leftMaxTextSpace = (int) textPaint.measureText(leftMaxValue);
        leftHalfTextSpace = (int) textPaint.measureText(leftHalfValue);
        leftTextSpace = (leftMaxTextSpace > leftHalfTextSpace) ? leftMaxTextSpace : leftHalfTextSpace;
        bottomTextSpace = height * 0.160f;
        rightPadding = width * 0.024f;
        topPadding = height * 0.080f;
        tableWidth = width - leftTextSpace - rightPadding;
        tableHeight = height - bottomTextSpace - topPadding;
        xUnitWidth = tableWidth * (1f / xGridCount);
        yUnitHeight = tableHeight * 0.5f;
        xUnitOffset = getTimeOffset(getPreviousTime());
    }

    public void drawData(Canvas canvas) {
        long startTime = SystemClock.elapsedRealtime();
        for (ChartLine adapter : lineAdapters) {
            adapter.draw(this, canvas);
        }
        Log.d("TestRunTime", "繪製資料總共耗時:" + (SystemClock.elapsedRealtime() - startTime));
    }

    public void cleanData() {
        lineAdapters.clear();
        maxValue = 1.0;
        updateMax();
        invalidate();
    }
}
