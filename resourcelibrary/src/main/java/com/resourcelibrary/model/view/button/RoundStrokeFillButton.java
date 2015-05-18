package com.resourcelibrary.model.view.button;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 4/17/2015.
 */
public class  RoundStrokeFillButton extends Button {
    private int radius = 2;
    private RectF bounds;
    private Paint fillPaint;
    private Paint strokePaint;
    private int fillColor;
    private int strokeColor;
    private int clickColor;
    private int strokeWidth = 5;

    public RoundStrokeFillButton(Context context) {
        super(context);
        bounds = new RectF();

        fillColor = Color.BLACK;
        strokeColor = Color.BLACK;

        fillPaint = new Paint();
        fillPaint.setColor(fillColor);
        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.FILL);

        strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeWidth);
    }

    public void setRaduis(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        strokePaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public void setClickColor(int clickColor) {
        this.clickColor = clickColor;
        invalidate();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        strokePaint.setColor(this.strokeColor);
        invalidate();
    }


    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
        fillPaint.setColor(fillColor);
        invalidate();
    }

    public void changeClickColor() {
        fillPaint.setColor(clickColor);
        invalidate();
    }

    @Override
    public void setOnClickListener(final OnClickListener l) {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                l.onClick(view);
                changeClickColor();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int left = strokeWidth / 2;
        int top = strokeWidth / 2;
        int right = width - left;
        int bottom = height - top;
        bounds.set(left, top, right, bottom);

        canvas.drawRoundRect(bounds, radius, radius, fillPaint);
        canvas.drawRoundRect(bounds, radius, radius, strokePaint);

        super.onDraw(canvas);
    }
}
