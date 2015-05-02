package com.resourcelibrary.model.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.widget.CompoundButton;

import com.resourcelibrary.model.log.ShowLog;


/**
 * Created by User on 1/22/2015.
 */
public class CircleProgress extends CompoundButton {
    private float width, height;
    private float percent;
    private float targetPercent;
    private Paint pen;
    private float centerX, centerY, circleDiameter, progressDiameter, progressRadius, centerBlockDiameter, centerBlockRadius;
    private LinearGradient progressGradient, outCircleGradient, inCircleGradient;
    private RectF arcRectF;

    public CircleProgress(Context context) {
        super(context);
        percent = 0;
    }

    public void setPercent(float percent) {
        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }
        this.percent = percent;
        invalidate();
    }

    public void setPercentAnim(float percent) {
        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }
        this.targetPercent = percent;
        this.percent = 0;
        postDelayed(anim, 100);
    }

    private Runnable anim = new Runnable() {
        @Override
        public void run() {
            if (percent >= targetPercent) {
                percent = targetPercent;
                invalidate();
                return;
            }
            invalidate();
            percent++;
            postDelayed(anim, 100);
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        ShowLog.d("onSizeChanged長寬:" + w + ":" + h);
        centerX = width / 2;
        centerY = height / 2;
        circleDiameter = (width < height) ? height : width;
        progressDiameter = circleDiameter * 0.9f;
        progressRadius = progressDiameter / 2;
        centerBlockDiameter = circleDiameter * 0.6f;
        centerBlockRadius = centerBlockDiameter / 2;

        progressGradient = new LinearGradient(
                width, 0, 0, height,
                new int[]{Color.parseColor("#59D9F2"), Color.parseColor("#0E4BC6")},
                null, Shader.TileMode.REPEAT);
        outCircleGradient = new LinearGradient(
                centerX, height / 2, centerX, height,
                new int[]{Color.parseColor("#D1D3D6"), Color.WHITE},
                null, Shader.TileMode.CLAMP);
        inCircleGradient = new LinearGradient(
                centerX + centerBlockRadius, 0, 0, centerY + centerBlockRadius,
                new int[]{Color.parseColor("#FFFFFF"), Color.parseColor("#D1D3D6")},
                null, Shader.TileMode.REPEAT);

        arcRectF = new RectF(
                centerX - progressRadius,
                centerY - progressRadius,
                centerX + progressRadius,
                centerY + progressRadius);
        pen = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ShowLog.d("onDraw長寬:" + canvas.getWidth() + ":" + canvas.getHeight());
        pen.reset();
        pen.setShader(null);
        pen.setAntiAlias(true);

        pen.setStyle(Paint.Style.FILL);
        pen.setColor(Color.parseColor("#AAAAAA"));
        canvas.drawCircle(width / 2, height / 2, progressRadius, pen);

        pen.setShader(progressGradient);
        canvas.drawArc(arcRectF, 90, 360 * (percent / 100), true, pen);

        pen.setStrokeWidth(circleDiameter * 0.05f);
        pen.setStyle(Paint.Style.STROKE);
        pen.setShader(outCircleGradient);
        canvas.drawCircle(width / 2, height / 2, (circleDiameter / 2f) * 0.9f, pen);

        pen.setStyle(Paint.Style.FILL);
        pen.setShader(inCircleGradient);
        canvas.drawCircle(width / 2, height / 2, centerBlockRadius, pen);
    }
}