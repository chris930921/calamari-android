package com.cephmonitor.cephmonitor.layout.component.button;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chriske on 2015/9/22.
 */
public class CircleRadioButton extends View {
    private Paint outerCirclePen;
    private Paint passiveInsideCirclePen;
    private Paint activeInsideCirclePen;
    private float insideCirclePercentage;
    private boolean radioState;
    private float outerWidth;

    public CircleRadioButton(Context context) {
        super(context);
        insideCirclePercentage = 0.7F;
        radioState = false;
        outerWidth = 10;

        outerCirclePen = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerCirclePen.setStyle(Paint.Style.STROKE);
        outerCirclePen.setStrokeWidth(outerWidth);
        outerCirclePen.setColor(Color.YELLOW);

        passiveInsideCirclePen = new Paint(Paint.ANTI_ALIAS_FLAG);
        passiveInsideCirclePen.setStyle(Paint.Style.FILL);
        passiveInsideCirclePen.setColor(Color.BLUE);

        activeInsideCirclePen = new Paint(Paint.ANTI_ALIAS_FLAG);
        activeInsideCirclePen.setStyle(Paint.Style.FILL);
        activeInsideCirclePen.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        float diameter = (width < height) ? width : height;
        float radius = (diameter - outerWidth) / 2;
        float centerX = width / 2;
        float centerY = height / 2;

        Paint insidePen = (radioState) ? activeInsideCirclePen : passiveInsideCirclePen;
        canvas.drawCircle(centerX, centerY, radius, outerCirclePen);
        canvas.drawCircle(centerX, centerY, radius * insideCirclePercentage, insidePen);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            radioState = !radioState;
            invalidate();
            return false;
        } else {
            return true;
        }
    }

    public void setCircleColor(int passiveInsideColor, int activeInsideColor) {
        outerCirclePen.setColor(passiveInsideColor);
        passiveInsideCirclePen.setColor(passiveInsideColor);
        activeInsideCirclePen.setColor(activeInsideColor);
        invalidate();
    }

    public void setCircleStyle(int outerWidth, float insidePercentage) {
        outerCirclePen.setStrokeWidth(outerWidth);
        insideCirclePercentage = insidePercentage;
        invalidate();
    }

    public boolean getState() {
        return radioState;
    }

    public void setState(boolean state) {
        radioState = state;
        invalidate();
        ;
    }
}
