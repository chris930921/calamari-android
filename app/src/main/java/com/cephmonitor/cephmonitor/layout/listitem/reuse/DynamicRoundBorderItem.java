package com.cephmonitor.cephmonitor.layout.listitem.reuse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;


public class DynamicRoundBorderItem extends RelativeLayout {
    public static final int ITEM_STYLE_HEADER = 0;
    public static final int ITEM_STYLE_CENTER = 1;
    public static final int ITEM_STYLE_FOOTER = 2;
    public static final int ITEM_STYLE_ONLY_ONE = 3;
    public static final int ITEM_STYLE_ONLY_HEADER = 4;

    private WH ruler;
    private RectF topBounds;
    private RectF bottomBounds;
    private float borderWidth = 1;
    private float radius = 10;
    private Paint borderPaint;
    private Paint backgroundPaint;
    private int borderStyle;
    private Path borderPath;

    public RelativeLayout contentContainer;
    public View topFillView;
    public View bottomFillView;

    private int borderColor;
    private int backgroundColor;

    public DynamicRoundBorderItem(Context context) {
        super(context);
        this.ruler = new WH(context);
        borderColor = Color.BLACK;
        backgroundColor = Color.YELLOW;
        borderStyle = ITEM_STYLE_ONLY_ONE;
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

        topFillView = topFillView();
        contentContainer = contentContainer(topFillView);
        bottomFillView = bottomFillView(contentContainer);

        super.addView(topFillView);
        super.addView(contentContainer);
        super.addView(bottomFillView);
    }

    @Override
    public void addView(View child) {
        contentContainer.addView(child);
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

        if (borderStyle == ITEM_STYLE_HEADER) {
            borderPath.moveTo(left, topQuad);
            borderPath.quadTo(left, top, leftQuad, top);
            borderPath.moveTo(rightQuad, top);
            borderPath.quadTo(right, top, right, topQuad);

            borderPath.moveTo(right, bottomQuad);
            borderPath.lineTo(right, bottom);
            borderPath.moveTo(left, bottom);
            borderPath.lineTo(left, bottomQuad);

            canvas.drawRoundRect(topBounds, radius, radius, backgroundPaint);
            canvas.drawRect(bottomBounds, backgroundPaint);

        } else if (borderStyle == ITEM_STYLE_CENTER) {
            borderPath.moveTo(left, topQuad);
            borderPath.lineTo(left, top);
            borderPath.lineTo(leftQuad, top);
            borderPath.moveTo(rightQuad, top);
            borderPath.lineTo(right, top);
            borderPath.lineTo(right, topQuad);

            borderPath.moveTo(right, bottomQuad);
            borderPath.lineTo(right, bottom);
            borderPath.moveTo(left, bottom);
            borderPath.lineTo(left, bottomQuad);

            canvas.drawRect(topBounds, backgroundPaint);
            canvas.drawRect(bottomBounds, backgroundPaint);

        } else if (borderStyle == ITEM_STYLE_FOOTER) {
            borderPath.moveTo(left, topQuad);
            borderPath.lineTo(left, top);
            borderPath.lineTo(leftQuad, top);
            borderPath.moveTo(rightQuad, top);
            borderPath.lineTo(right, top);
            borderPath.lineTo(right, topQuad);

            borderPath.moveTo(right, bottomQuad);
            borderPath.quadTo(right, bottom, rightQuad, bottom);
            borderPath.moveTo(leftQuad, bottom);
            borderPath.quadTo(left, bottom, left, bottomQuad);

            canvas.drawRect(topBounds, backgroundPaint);
            canvas.drawRoundRect(bottomBounds, radius, radius, backgroundPaint);

            borderPath.moveTo(rightQuad, bottom);
            borderPath.lineTo(leftQuad, bottom);
        }else if (borderStyle == ITEM_STYLE_ONLY_HEADER) {
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
        } else {
            borderPath.moveTo(left, topQuad);
            borderPath.quadTo(left, top, leftQuad, top);
            borderPath.moveTo(rightQuad, top);
            borderPath.quadTo(right, top, right, topQuad);

            borderPath.moveTo(right, bottomQuad);
            borderPath.quadTo(right, bottom, rightQuad, bottom);
            borderPath.moveTo(leftQuad, bottom);
            borderPath.quadTo(left, bottom, left, bottomQuad);

            canvas.drawRoundRect(topBounds, radius, radius, backgroundPaint);
            canvas.drawRoundRect(bottomBounds, radius, radius, backgroundPaint);

            borderPath.moveTo(rightQuad, bottom);
            borderPath.lineTo(leftQuad, bottom);
        }
        canvas.drawPath(borderPath, borderPaint);

        super.dispatchDraw(canvas);
    }

    private RelativeLayout contentContainer(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(getContext());
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View topFillView() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_TOP);

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View bottomFillView(View topView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(BELOW, topView.getId());

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public void setViewBackgroundColor(int color) {
        backgroundColor = color;
        backgroundPaint.setColor(backgroundColor);
        invalidate();
    }

    public void setViewPadding(int left, int top, int right, int bottom) {
        contentContainer.setPadding(left, 0, right, 0);

        ViewGroup.LayoutParams topParams = topFillView.getLayoutParams();
        ViewGroup.LayoutParams bottomParams = bottomFillView.getLayoutParams();
        topParams.height = top;
        bottomParams.height = bottom;
        topFillView.setLayoutParams(topParams);
        bottomFillView.setLayoutParams(bottomParams);
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

    public void setBorderStyle(int style) {
        borderStyle = style;
    }
}
