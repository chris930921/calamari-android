package com.cephmonitor.cephmonitor.layout.component.edittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.EditText;

/**
 * Created by User on 2015/8/3.
 */
public class BorderEditText extends EditText {
    private int strokeWidth = 2;
    private RectF bounds;
    private Paint paint;

    private int normalColor;
    private int waringColor;

    public BorderEditText(Context context) {
        super(context);
        normalColor = Color.parseColor("#f3f3f3");
        waringColor = Color.parseColor("#e63427");
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(normalColor);
        setBackgroundColor(Color.WHITE);
    }

    public void recoverColor() {
        paint.setColor(normalColor);
        invalidate();
    }

    public void warningColor() {
        paint.setColor(waringColor);
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

        canvas.drawRect(bounds, paint);

        super.onDraw(canvas);
    }
}
