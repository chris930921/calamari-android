package com.resourcelibrary.model.view.edittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.EditText;

/**
 * Created by User on 4/17/2015.
 */
public class RoundCornerEditText extends EditText {
    private int strokeWidth = 10;
    private int radius = 10;
    private RectF bounds;
    private Paint paint;

    public RoundCornerEditText(Context context) {
        super(context);
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        setBackgroundColor(Color.parseColor("#00000000"));
    }

    public void setRaduis(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void setBorderWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    public void setBorderColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int left = strokeWidth;
        int top = strokeWidth;
        int right = width - left;
        int bottom = height - top;
        bounds.set(left, top, right, bottom);

        canvas.drawRoundRect(bounds, radius, radius, paint);

        super.onDraw(canvas);
    }
}
