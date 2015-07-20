package com.cephmonitor.cephmonitor.layout.component.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.resourcelibrary.model.view.WH;


/**
 * Created by User on 1/22/2015.
 */
public class UsageCardProgress extends CompoundButton {
    private WH ruler;

    private float width, height;
    private float progressWidth;
    private float percent;
    private String percentText;
    private String bottomText;
    private Paint percentageTextPen;
    private Paint usedTextPen;
    private Paint backgroundProgressPen;
    private Paint progressPen;
    private Paint clearPen;
    private RectF progressBounds;
    private Rect textBounds;
    private float lineOneHeight;
    private float lineTwoHeight;

    public UsageCardProgress(Context context) {
        super(context);
        percent = 0;
        ruler = new WH(context);
        progressWidth = ruler.getW(5);
        textBounds = new Rect();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TextView v = new TextView(context);

        v.setTextSize(20);
        percentageTextPen = new Paint();
        percentageTextPen.setAntiAlias(true);
        percentageTextPen.setTextSize(v.getTextSize());
        percentageTextPen.setTypeface(Typeface.DEFAULT_BOLD);

        v.setTextSize(14);
        usedTextPen = new Paint();
        usedTextPen.setAntiAlias(true);
        usedTextPen.setTextSize(v.getTextSize());
        usedTextPen.setColor(ColorTable._999999);

        backgroundProgressPen = new Paint();
        backgroundProgressPen.setAntiAlias(true);
        backgroundProgressPen.setColor(ColorTable._8DC41F);
        backgroundProgressPen.setStrokeCap(Paint.Cap.SQUARE);

        progressPen = new Paint();
        progressPen.setAntiAlias(true);
        progressPen.setStrokeCap(Paint.Cap.SQUARE);

        clearPen = new Paint();
        clearPen.setAntiAlias(true);
        clearPen.setStrokeCap(Paint.Cap.SQUARE);
        clearPen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setPercent(float percent) {
        this.percent = checkPercent(percent);
        this.percentText = ((int) this.percent) + "%";
        choiceStatus();
        invalidate();
    }

    public void setPercentTextStyle(int size, int typeFace) {
        TextView v = new TextView(getContext());
        v.setTextSize(size);
        percentageTextPen.setTextSize(v.getTextSize());
        if (typeFace == Typeface.BOLD) {
            percentageTextPen.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (typeFace == Typeface.NORMAL) {
            percentageTextPen.setTypeface(Typeface.DEFAULT);
        }
        invalidate();
    }

    public void setBottomTextStyle(int size, int typeFace) {
        TextView v = new TextView(getContext());
        v.setTextSize(size);
        usedTextPen.setTextSize(v.getTextSize());
        if (typeFace == Typeface.BOLD) {
            usedTextPen.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (typeFace == Typeface.NORMAL) {
            usedTextPen.setTypeface(Typeface.DEFAULT);
        }
        invalidate();
    }

    public void setText(String text) {
        this.bottomText = text;
        invalidate();
    }

    private float checkPercent(float percent) {
        if (percent < 0) {
            return 0;
        } else if (percent > 100) {
            return 100;
        }
        return percent;
    }

    private void choiceStatus() {
        if (percent >= 85) {
            setErrorStatus();
        } else if (percent >= 75) {
            setWarningStatus();
        } else {
            setNormalStatus();
        }
    }

    private void setNormalStatus() {
        percentageTextPen.setColor(ColorTable._8DC41F);
        progressPen.setColor(ColorTable._F7B500);
    }

    private void setWarningStatus() {
        percentageTextPen.setColor(ColorTable._F7B500);
        progressPen.setColor(ColorTable._F7B500);
    }

    private void setErrorStatus() {
        percentageTextPen.setColor(ColorTable._E63427);
        progressPen.setColor(ColorTable._E63427);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int size = (w < h) ? w : h;

        width = size - (progressWidth);
        height = size - (progressWidth);

        float padding = progressWidth / 2;

        progressBounds = new RectF(
                padding,
                padding,
                size - padding,
                size - padding
        );
        lineOneHeight = getTextHeight(textBounds, percentText, percentageTextPen);
        lineTwoHeight = getTextHeight(textBounds, bottomText, usedTextPen);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(progressBounds, -90, 360, false, backgroundProgressPen);
        canvas.drawArc(progressBounds, -90, ((360 * percent) / 100), true, progressPen);

        canvas.drawCircle(
                (progressBounds.right + progressBounds.left) / 2,
                (progressBounds.bottom + progressBounds.top) / 2,
                (width / 2) - progressWidth,
                clearPen
        );

        drawCenterText(
                canvas,
                progressBounds.left,
                progressBounds.top,
                progressBounds.right,
                progressBounds.bottom - (lineOneHeight),
                textBounds,
                percentText,
                percentageTextPen
        );

        drawCenterText(
                canvas,
                progressBounds.left,
                progressBounds.top + (lineTwoHeight),
                progressBounds.right,
                progressBounds.bottom,
                textBounds,
                bottomText,
                usedTextPen
        );
    }

    private void drawCenterText(Canvas canvas, float left, float top, float right, float bottom, Rect textBounds, String text, Paint pen) {
        String maxTextHeightExample = "Ij";

        pen.getTextBounds(text + maxTextHeightExample, 0, text.length() + maxTextHeightExample.length(), textBounds);
        int textWidth = (int) pen.measureText(text, 0, text.length());
        int textHeight = textBounds.height();

        float textLeft = left + (((right - left) - textWidth) / 2);
        float textBottom = bottom - (((bottom - top) - textHeight) / 2);
        canvas.drawText(text, textLeft, textBottom, pen);
    }

    private float getTextHeight(Rect textBounds, String text, Paint pen) {
        String maxTextHeightExample = "Ij";

        pen.getTextBounds(text + maxTextHeightExample, 0, text.length() + maxTextHeightExample.length(), textBounds);
        return textBounds.height();
    }
}