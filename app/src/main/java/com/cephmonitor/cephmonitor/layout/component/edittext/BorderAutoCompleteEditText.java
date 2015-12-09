package com.cephmonitor.cephmonitor.layout.component.edittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.AutoCompleteTextView;

import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;

/**
 * Created by User on 2015/8/3.
 */
public class BorderAutoCompleteEditText extends AutoCompleteTextView {
    private DesignSpec designSpec;
    private int strokeWidth = 2;
    private RectF bounds;
    private Paint paint;

    private int normalColor;
    private int waringColor;
    private int focusColor;
    private int currentColor;

    public BorderAutoCompleteEditText(Context context) {
        super(context);
        designSpec = ThemeManager.getStyle(context);
        normalColor = Color.parseColor("#f3f3f3");
        waringColor = Color.parseColor("#e63427");
        focusColor = designSpec.getAccentColors().getNormal();
        bounds = new RectF();
        currentColor = normalColor;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setColor(normalColor);
        setBackgroundColor(Color.WHITE);
    }

    public void recoverColor() {
        currentColor = normalColor;
        invalidate();
    }

    public void warningColor() {
        currentColor = waringColor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isFocused()) {
            paint.setColor(focusColor);
        } else {
            paint.setColor(currentColor);
        }

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
