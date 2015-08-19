package com.cephmonitor.cephmonitor.layout.listitem.reuse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.TextView;

/**
 * Created by User on 2015/8/17.
 */
public class TopBorderTextViewItem extends TextView {
    private int lineColor = Color.BLACK;
    private int lineWidth = 5;
    private boolean topBorderEnable = false;
    private Paint linePaint;

    public TopBorderTextViewItem(Context context) {
        super(context);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    public void setTopBorderColor(int color) {
        lineColor = color;
        linePaint.setColor(lineColor);
        invalidate();
    }

    public void setTopBorderWidth(int width) {
        lineWidth = width;
        linePaint.setStrokeWidth(lineWidth);
        invalidate();
    }

    public void setTopBorderEnable(boolean enable) {
        topBorderEnable = enable;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (topBorderEnable) {
            int width = canvas.getWidth();
            canvas.drawLine(0, lineWidth / 2, width, lineWidth / 2, linePaint);
        }

        super.onDraw(canvas);
    }
}
