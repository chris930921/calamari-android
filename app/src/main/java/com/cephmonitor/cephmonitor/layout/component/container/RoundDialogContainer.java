package com.cephmonitor.cephmonitor.layout.component.container;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.cephmonitor.cephmonitor.model.tool.ClearRoundAreaPath;
import com.resourcelibrary.model.view.WH;


public class RoundDialogContainer extends RelativeLayout {
    private WH ruler;
    private RectF totalBounds;
    private float borderWidth = 1;
    private float radius = 10;
    private Paint borderPaint;
    private Paint backgroundPaint;
    private Path roundPath;

    private int borderColor;
    private int backgroundColor;
    private Paint roundPaint;

    public RoundDialogContainer(Context context) {
        super(context);
        this.ruler = new WH(context);
        borderColor = Color.BLACK;
        backgroundColor = Color.YELLOW;
        roundPath = new Path();
        totalBounds = new RectF();

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

        roundPaint = new Paint();
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
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

        // draw background
        totalBounds.set(left, top, right, bottom);
        canvas.drawRect(totalBounds, backgroundPaint);

        // draw content
        super.dispatchDraw(canvas);

        // clear round area
        roundPath.reset();
        ClearRoundAreaPath.get(roundPath, width, height, radius);
        canvas.drawPath(roundPath, roundPaint);

        // draw border
        canvas.drawRoundRect(totalBounds, radius, radius, borderPaint);
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
