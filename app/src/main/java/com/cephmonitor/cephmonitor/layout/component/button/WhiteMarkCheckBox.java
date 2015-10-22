package com.cephmonitor.cephmonitor.layout.component.button;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.cephmonitor.cephmonitor.layout.ColorTable;

/**
 * Created by chriske on 2015/10/20.
 */
public class WhiteMarkCheckBox extends View {
    private boolean state;
    private int backgroundColor;
    private Paint backgroundPen = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint checkPen = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPen = new Paint(Paint.ANTI_ALIAS_FLAG);

    public WhiteMarkCheckBox(Context context) {
        super(context);
        state = false;
        backgroundColor = Color.GREEN;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        backgroundPen.setColor((isChecked()) ? backgroundColor : Color.TRANSPARENT);
        backgroundPen.setStyle(Paint.Style.FILL);

        float strokeWidth = canvas.getWidth() / 10F;

        checkPen.setColor((isChecked()) ? Color.WHITE : Color.TRANSPARENT);
        checkPen.setStyle(Paint.Style.STROKE);
        checkPen.setStrokeWidth(strokeWidth);

        borderPen.setColor(ColorTable._999999);
        borderPen.setStyle(Paint.Style.STROKE);
        borderPen.setStrokeWidth(strokeWidth);

        canvas.drawRect(strokeWidth, strokeWidth, canvas.getWidth() - strokeWidth, canvas.getHeight() - strokeWidth, borderPen);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPen);
        canvas.drawLine(
                canvas.getWidth() * 0.1F, canvas.getHeight() * 0.5F,
                canvas.getWidth() * 0.3F, canvas.getHeight() * 0.7F, checkPen);
        canvas.drawLine(
                canvas.getWidth() * 0.3F, canvas.getHeight() * 0.7F,
                canvas.getWidth() * 0.9F, canvas.getHeight() * 0.2F, checkPen);
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    public boolean isChecked() {
        return state;
    }

    public void setChecked(boolean state) {
        this.state = state;
        invalidate();
    }
}
