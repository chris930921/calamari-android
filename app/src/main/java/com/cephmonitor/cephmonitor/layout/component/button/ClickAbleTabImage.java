package com.cephmonitor.cephmonitor.layout.component.button;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.resourcelibrary.model.logic.RandomId;

/**
 * Created by User on 4/17/2015.
 */
public class ClickAbleTabImage extends RelativeLayout {
    private Paint fillPaint;
    private Paint strokePaint;
    private Paint bottomLinePaint;
    private int fillColor;
    private int strokeColor;
    private int clickColor;
    private int strokeWidth = 5;
    private boolean isActive;
    private int clickImageResource;
    private boolean isDividerShow;
    private int originImageResource;
    private ImageView image;

    public ClickAbleTabImage(Context context) {
        super(context);
        isDividerShow = true;

        image = image();
        addView(image);

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

        bottomLinePaint = new Paint();
        bottomLinePaint.setColor(strokeColor);
        bottomLinePaint.setAntiAlias(true);
        bottomLinePaint.setStyle(Paint.Style.STROKE);
    }

    private ImageView image() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(CENTER_IN_PARENT);

        ImageView v = new ImageView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public void setImageViewSize(int width, int height) {
        LayoutParams params = (LayoutParams) image.getLayoutParams();
        params.width = width;
        params.height = height;
        image.setLayoutParams(params);
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        strokePaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public void setOriginImageResource(int resource) {
        originImageResource = resource;
        invalidate();
    }

    public void setClickColor(int clickColor, int clickImageResource) {
        this.clickColor = clickColor;
        this.clickImageResource = clickImageResource;
        this.bottomLinePaint.setColor(clickColor);
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
        image.setImageResource(clickImageResource);
        isActive = true;
        invalidate();
    }

    public void recover() {
        image.setImageResource(originImageResource);
        isActive = false;
        invalidate();
    }

    public void setDividerLine(boolean isShow) {
        isDividerShow = isShow;
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
    protected void dispatchDraw(Canvas canvas) {
        float halfStrokeWidth = strokeWidth / 2;
        float bottomLineWidth = 8;
        float halfBottomLineWidth = bottomLineWidth / 2;
        bottomLinePaint.setStrokeWidth(bottomLineWidth);

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), fillPaint);
        canvas.drawLine(0, halfStrokeWidth, canvas.getWidth(), halfStrokeWidth, strokePaint);
        if (isDividerShow) {
            canvas.drawLine(
                    canvas.getWidth() - halfStrokeWidth, canvas.getHeight() * 0.2F,
                    canvas.getWidth() - halfStrokeWidth, canvas.getHeight() * 0.8F, strokePaint);
        }
        if (isActive) {
            canvas.drawLine(
                    0, canvas.getHeight() - halfBottomLineWidth,
                    canvas.getWidth(), canvas.getHeight() - halfBottomLineWidth, bottomLinePaint);
        }

        super.dispatchDraw(canvas);
    }
}
