package com.cephmonitor.cephmonitor.layout.component.container;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by User on 2015/8/3.
 */
public class CircleBorderContainer extends FrameLayout {
    public Paint centerCirclePaint;
    public Paint circleBorder;
    public Path path;

    private int borderWidth;

    public CircleBorderContainer(Context context) {
        super(context);
        path = new Path();
        borderWidth = 2;

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        centerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerCirclePaint.setStyle(Paint.Style.FILL);
        centerCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        circleBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleBorder.setStyle(Paint.Style.STROKE);
        circleBorder.setStrokeWidth(borderWidth);
        circleBorder.setColor(Color.WHITE);
    }


    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int radius = (width < height) ? width / 2 : height / 2;

        path.addCircle(width / 2, height / 2, radius, Path.Direction.CCW);
        path.moveTo(radius, 0);
        path.lineTo(width, 0);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, 0);
        path.close();

        canvas.drawPath(path, centerCirclePaint);
        canvas.drawCircle(width / 2, height / 2, radius - (borderWidth / 2), circleBorder);
    }
}
