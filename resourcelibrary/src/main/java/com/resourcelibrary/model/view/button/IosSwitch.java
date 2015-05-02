package com.resourcelibrary.model.view.button;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.CompoundButton;

/**
 * Created by User on 1/22/2015.
 */
public class IosSwitch extends CompoundButton {
    public final float CIRCEL_DIAMETER = 50;
    private float leftSideCenterY, leftSideCenterX, rightSideCenterY, rightSideCenterX;
    private float width, height;
    private float thumbCenterX = -1, thumbCenterY = -1;
    private float thumbRadius;
    private float startX;
    private boolean state;
    private OnCheckedChangeListener event;
    private boolean isClick;

    public IosSwitch(Context context) {
        super(context);
        state = false;
        isClick = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();

        float circleDiameter = height;
        float radius = circleDiameter / 2;
        leftSideCenterY = height / 2;
        leftSideCenterX = height / 2;

        rightSideCenterY = leftSideCenterY;
        rightSideCenterX = width - leftSideCenterX;

        thumbRadius = radius * 0.9f;
        if (thumbCenterX < 0 && thumbCenterY < 0 && !state) {
            thumbCenterX = leftSideCenterX;
            thumbCenterY = height / 2;
        } else if (thumbCenterX < 0 && thumbCenterY < 0 && state) {
            thumbCenterX = rightSideCenterX;
            thumbCenterY = height / 2;
        }

        Paint pen = new Paint();
        pen.setColor(((state) ? Color.parseColor("#35ED69") : Color.parseColor("#C9CBCA")));
        pen.setStyle(Paint.Style.FILL);
        pen.setStrokeWidth(5);
        pen.setAntiAlias(true);

        // draw a circle
        canvas.drawCircle(leftSideCenterX, leftSideCenterY, radius, pen);
        canvas.drawCircle(rightSideCenterX, rightSideCenterY, radius, pen);
        canvas.drawRect(leftSideCenterX, 0, rightSideCenterX, height, pen);

        pen.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawCircle(thumbCenterX, thumbCenterY, thumbRadius, pen);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (thumbCenterX < 0 && thumbCenterY < 0) return false;
            if (Math.sqrt(Math.pow(motionEvent.getX() - thumbCenterX, 2) + Math.pow(motionEvent.getY() - thumbCenterY, 2)) > thumbRadius) {
                isClick = true;
                return true;
            }
            startX = motionEvent.getX();
            return true;
        } else if (isClick && motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        } else if (isClick && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (state) {
                stateOff();
            } else {
                stateOn();
            }
            if (event != null) event.onCheckedChanged(IosSwitch.this, state);
            isClick = false;
            return false;
        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            float x = ((state) ? rightSideCenterX : leftSideCenterX) + motionEvent.getX() - startX;
            if (x < leftSideCenterX) {
                thumbCenterX = leftSideCenterX;
            } else if (x > rightSideCenterX) {
                thumbCenterX = rightSideCenterX;
            } else {
                thumbCenterX = x;
            }
            invalidate();
            return true;
        } else {
            if (thumbCenterX > width / 2) {
                stateOn();
            } else {
                stateOff();
            }
            if (event != null) event.onCheckedChanged(IosSwitch.this, state);
            return false;
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        event = l;
    }

    public void stateOn() {
        thumbCenterX = rightSideCenterX;
        state = true;
        invalidate();
    }

    public void stateOff() {
        thumbCenterX = leftSideCenterX;
        state = false;
        invalidate();
    }

    public void setOff() {
        state = false;
    }

    public void setOn() {
        state = true;
    }

    @Override
    public void setChecked(boolean b) {
        state = b;
    }

    @Override
    public boolean isChecked() {
        return state;
    }

    @Override
    public void toggle() {
        state = !state;
    }
}