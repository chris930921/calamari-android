package com.cephmonitor.cephmonitor.layout.listitem.reuse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.view.WH;

import java.util.HashMap;


public class RoundLeftBarItem extends RelativeLayout {
    private HashMap<String, Integer> choiceColor;

    private Context context;
    private WH ruler;
    private RectF bounds;
    private int borderWidth = 1;
    private int radius = 10;
    private Paint borderPaint;
    private Paint backgroundPaint;
    private Paint leftBarPaint;

    public RelativeLayout contentContainer;

    private int statusBarWidth;
    private int barColor;
    private int borderColor;
    private int backgroundColor;
    private int leftPadding;
    private int topPadding;
    private int rightPadding;
    private int bottomPadding;

    public RoundLeftBarItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.bounds = new RectF();
        choiceColor = new HashMap<>();
        barColor = Color.BLACK;
        borderColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        leftPadding = 10;
        topPadding = 10;
        rightPadding = 10;
        bottomPadding = 10;
        statusBarWidth = ruler.getW(3);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);

        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        leftBarPaint = new Paint();
        leftBarPaint.setAntiAlias(true);
        leftBarPaint.setColor(barColor);

        contentContainer = contentContainer();
        super.addView(contentContainer);
    }

    @Override
    public void addView(View child) {
        contentContainer.addView(child);
    }

    private RelativeLayout contentContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        RelativeLayout v = new RelativeLayout(context) {
            @Override
            protected void dispatchDraw(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();

                int left = borderWidth;
                int top = borderWidth;
                int right = width - left;
                int bottom = height - top;
                bounds.set(left, top, right, bottom);

                canvas.drawRoundRect(bounds, radius, radius, backgroundPaint);
                canvas.drawRoundRect(bounds, radius, radius, borderPaint);

                bounds.set(0, 0, statusBarWidth, height);
                canvas.drawRoundRect(bounds, radius, radius, leftBarPaint);

                bounds.set(statusBarWidth / 2, 0, statusBarWidth, height);
                canvas.drawRect(bounds, leftBarPaint);

                super.dispatchDraw(canvas);
            }
        };
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setPadding(statusBarWidth + leftPadding, 0, rightPadding, 0);

        return v;
    }

    public void setViewBackgroundColor(int color) {
        backgroundColor = color;
        backgroundPaint.setColor(backgroundColor);
        invalidate();
    }

    public void setViewPadding(int left, int top, int right, int bottom) {
        leftPadding = left;
        topPadding = top;
        rightPadding = right;
        bottomPadding = bottom;

        contentContainer.setPadding(statusBarWidth + leftPadding, topPadding, rightPadding, bottomPadding);
    }

    public void setStatusBarColor(int color) {
        barColor = color;
        leftBarPaint.setColor(barColor);
        invalidate();
    }

    public void setBorderColor(int color) {
        borderColor = color;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public void setStatusBarWidth(int statusBarWidth) {
        this.statusBarWidth = statusBarWidth;
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
