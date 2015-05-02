package com.resourcelibrary.model.view.button;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by User on 4/17/2015.
 */
public class RoundFillColorButton extends Button {
    private int radius = 10;
    private RectF bounds;
    private Paint paint;
    private int fillColor;
    private int pressColor;

    public RoundFillColorButton(Context context) {
        super(context);
        bounds = new RectF();

        fillColor = Color.BLACK;
        pressColor = Color.GRAY;

        paint = new Paint();
        paint.setColor(fillColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        setBackgroundColor(Color.parseColor("#00000000"));
    }

    public void setRaduis(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void setFillAndPressColor(int fillColor, int pressColor) {
        this.fillColor = fillColor;
        this.pressColor = pressColor;
        paint.setColor(fillColor);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            paint.setColor(pressColor);
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            paint.setColor(pressColor);
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            paint.setColor(fillColor);
            invalidate();
            return true;
        }
        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int left = 0;
        int top = 0;
        int right = width - left;
        int bottom = height - top;
        bounds.set(left, top, right, bottom);

        canvas.drawRoundRect(bounds, radius, radius, paint);

        super.onDraw(canvas);
    }
}
