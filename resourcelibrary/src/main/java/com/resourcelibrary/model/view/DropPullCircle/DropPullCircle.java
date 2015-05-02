package com.resourcelibrary.model.view.DropPullCircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.CompoundButton;

/**
 * Created by User on 1/22/2015.
 */
public class DropPullCircle extends CompoundButton {
    private float width, height;
    private float percent;
    private Paint pen;
    private float frameSize, circleCenterX, circleCenterY, drawRadius;
    private RectF circleModel;
    private boolean isLoading;
    private float loadHeadPercent;
    private float loadFootPercent;
    private int loadState;
    private boolean isLoadOver;
    private Context context;

    public DropPullCircle(Context context) {
        super(context);
        this.context = context;
        percent = 50;
        pen = new Paint();
        circleModel = new RectF();
        isLoading = false;
        isLoadOver = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();

        frameSize = (width > height) ? height : width;
        drawRadius = frameSize / 2.5f;

        circleCenterX = (width / 2);
        circleCenterY = (height / 2);

//        canvas.drawRect((width / 2) - (frameSize / 2), (height / 2) - (frameSize / 2), (width / 2) + (frameSize / 2), (height / 2) + (frameSize / 2), borderPen());
        canvas.drawCircle(circleCenterX, circleCenterY, drawRadius, circlePen());
        circleModel.set(
                circleCenterX - drawRadius,
                circleCenterY - drawRadius,
                circleCenterX + drawRadius,
                circleCenterY + drawRadius);
        if (isLoading) {
            canvas.drawArc(circleModel, 90 + 360 * (loadFootPercent / 100), 360 * (loadHeadPercent / 100), false, progressPen());
        } else {
            canvas.drawArc(circleModel, 90, 360 * (percent / 100), false, progressPen());
        }
    }

    //開始執行讀取與動畫
    public void startLoading() {
        isLoading = true;
        loadFootPercent = 0;
        loadHeadPercent = percent;
        loadState = 0;
        isLoadOver = false;
        postDelayed(loading, 100);
    }

    public void stopLoading() {
        isLoadOver = true;
    }

    //讀取動畫的執行緒工作
    private Runnable loading = new Runnable() {
        @Override
        public void run() {
            switch (loadState) {
                case 0:
                    if (loadHeadPercent >= 101) {
                        loadHeadPercent = 101;
                        loadState = 1;
                        postDelayed(loading, 10);
                    } else {
                        loadHeadPercent += 5;
                        postDelayed(loading, 10);
                    }
                    break;
                case 1:
                    if (loadFootPercent >= 100) {
                        loadFootPercent = 100;
                        loadState = 2;
                        postDelayed(loading, 10);
                    } else {
                        if (loadFootPercent >= 90) {
                            loadFootPercent += 2;
                            loadHeadPercent -= 2;
                        } else {
                            loadFootPercent += 5;
                            loadHeadPercent -= 5;
                        }
                        postDelayed(loading, 10);
                    }
                    break;
                case 2:
                    if (loadHeadPercent >= 80) {
                        loadHeadPercent = 80;
                        if (isLoadOver) {
                            loadState = 4;
                            postDelayed(loading, 10);
                        } else {
                            loadState = 3;
                            postDelayed(loading, 10);
                        }
                    } else {
                        if (loadHeadPercent >= 75) {
                            loadFootPercent += 0.2;
                            loadHeadPercent += 0.5;
                            postDelayed(loading, 10);
                        } else {
                            loadFootPercent += 2;
                            loadHeadPercent += 3;
                            postDelayed(loading, 10);
                        }
                    }
                    break;
                case 3:
                    if (loadHeadPercent <= 20) {
                        loadHeadPercent = 20;
                        if (isLoadOver) {
                            loadState = 4;
                            postDelayed(loading, 10);
                        } else {
                            loadState = 2;
                            postDelayed(loading, 10);
                        }
                    } else {
                        if (loadHeadPercent <= 25) {
                            loadFootPercent += 0.5;
                            loadHeadPercent -= 0.2;
                            postDelayed(loading, 10);
                        } else {
                            loadFootPercent += 3;
                            loadHeadPercent -= 2;
                            postDelayed(loading, 10);
                        }
                    }
                    break;
                case 4:
                    if (loadHeadPercent <= 0) {
                        loadFootPercent = 0;
                        loadHeadPercent = 0;
                        loadState = 5;
                        postDelayed(loading, 500);
                    } else {
                        loadFootPercent += 3;
                        loadHeadPercent -= 3;
                        postDelayed(loading, 10);
                    }
                    break;
                case 5:
                    isLoading = false;
                    break;
            }
            invalidate();
        }
    };

    //是否正在讀取中
    public boolean isLoading() {
        return isLoading;
    }

    //設定進度
    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }

    //畫圓圈正方形外框用的畫筆
    private Paint borderPen() {
        pen.setStyle(Paint.Style.STROKE);
        pen.setStrokeWidth(frameSize * 0.01f);
        pen.setColor(Color.BLACK);
        return pen;
    }

    //畫背景圓的的畫筆
    private Paint circlePen() {
        pen.setStyle(Paint.Style.STROKE);
        pen.setStrokeWidth(frameSize * 0.1f);
        pen.setAntiAlias(true);

        pen.setColor(Color.parseColor("#E7E8E9"));
        return pen;
    }

    //畫進度條用的畫筆
    private Paint progressPen() {
        pen.setStyle(Paint.Style.STROKE);
        pen.setStrokeWidth(frameSize * 0.1f);
        pen.setAntiAlias(true);
        pen.setStrokeCap(Paint.Cap.ROUND);
        pen.setColor(Color.parseColor("#0E86C9"));
        return pen;
    }
}