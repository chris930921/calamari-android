package com.cephmonitor.cephmonitor.layout.component.container;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.resourcelibrary.model.view.WH;


public class TopRoundDialogContainer extends RelativeLayout {
    private WH ruler;
    private RectF topBounds;
    private RectF bottomBounds;
    private float borderWidth = 1;
    private float radius = 10;
    private Paint borderPaint;
    private Paint backgroundPaint;
    private Path borderPath;

    private int borderColor;
    private int backgroundColor;

    public TopRoundDialogContainer(Context context) {
        super(context);
        this.ruler = new WH(context);
        borderColor = Color.BLACK;
        backgroundColor = Color.YELLOW;
        borderPath = new Path();
        topBounds = new RectF();
        bottomBounds = new RectF();

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        float width = canvas.getWidth();
        float height = canvas.getHeight();

        float halfBorder = borderWidth / 2;
        float left = halfBorder;
        float top = halfBorder;
        float right = width - left;
        float bottom = height - top;
        float leftQuad = left + radius;
        float topQuad = top + radius;
        float rightQuad = right - radius;
        float bottomQuad = bottom - radius;

        borderPath.reset();
        borderPath.moveTo(leftQuad, top);
        borderPath.lineTo(rightQuad, top);
        borderPath.moveTo(right, topQuad);
        borderPath.lineTo(right, bottomQuad);
        borderPath.moveTo(left, bottomQuad);
        borderPath.lineTo(left, topQuad);

        topBounds.set(left, top, right, bottomQuad);
        bottomBounds.set(left, topQuad, right, bottom);

        borderPath.moveTo(left, topQuad);
        borderPath.quadTo(left, top, leftQuad, top);
        borderPath.moveTo(rightQuad, top);
        borderPath.quadTo(right, top, right, topQuad);

        borderPath.moveTo(right, bottomQuad);
        borderPath.lineTo(right, bottom);
        borderPath.moveTo(left, bottom);
        borderPath.lineTo(left, bottomQuad);
        borderPath.moveTo(left, bottom);
        borderPath.lineTo(right, bottom);

        canvas.drawRoundRect(topBounds, radius, radius, backgroundPaint);
        canvas.drawRect(bottomBounds, backgroundPaint);
        canvas.drawPath(borderPath, borderPaint);

        super.dispatchDraw(canvas);
    }

    public void setViewBackgroundColor(int color) {
        backgroundColor = color;
        backgroundPaint.setColor(backgroundColor);
        invalidate();
    }

    public void setBorderColor(int color) {
        borderColor = color;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        invalidate();
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }
}
