package com.cephmonitor.cephmonitor.layout.component.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class ClockSetMoudle implements View.OnTouchListener {
    public int showsixty = 0;
    public int showtwentyfour = 0;
    public View view;
    public String centerNumToStr = "00";
    public String leftNumToStr = "00";
    public String rightNumToStr = "00";

    private int once = 0;//初始化
    private int point = 2;//哪個按鈕被按下
    private int setbefore = 0;
    private float x, y;
    private float circlex;//=500;//移動圓 X座標
    private float circley;//=500;//移動圓 Y座標
    private double finalpoitxsub;
    private double finalpoitysub;
    private double innerfinalpoitxsub;
    private double innerfinalpoitysub;
    private Context context;
    private boolean flag = false;//   判斷touch
    private boolean act = false;
    private static float angle = (float) Math.PI / 6;


    public ClockSetMoudle(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        view = new View(context) {
            Paint backgroundPaint = new Paint();
            Paint backgroundLine = new Paint();
            Paint backgroundText = new Paint();
            Paint buttonSlide = new Paint();
            Paint buttonCenter = new Paint();
            Paint centerText = new Paint();
            Paint centerTextleft = new Paint();
            Paint centerTextcenter = new Paint();
            Paint centerTextright = new Paint();
            Paint trColor = new Paint();
            Path tr = new Path();
            Path trLeft = new Path();
            Path trRight = new Path();

            protected void onDraw(Canvas canvas) {
                x = view.getWidth();
                y = view.getHeight();

                //內圓(灰)
                backgroundPaint.setAntiAlias(true);
                backgroundPaint.setStyle(Paint.Style.FILL);
                backgroundPaint.setColor(Color.argb(255, 239, 239, 239));
                canvas.drawCircle(x / 2, y / 2, x * 22, backgroundPaint);

                //飾圈1(最內 白)
                backgroundPaint.setColor(Color.argb(255, 255, 255, 255));
                backgroundPaint.setStyle(Paint.Style.STROKE);
                backgroundPaint.setStrokeWidth(x / 100 * 1);
                canvas.drawCircle(x / 2, y / 2, x / 200 * 45, backgroundPaint);

                //內圓環(灰)
                backgroundPaint.setStyle(Paint.Style.STROKE);
                backgroundPaint.setStrokeWidth(x / 100 * 7);
                backgroundPaint.setColor(Color.argb(255, 183, 183, 183));
                canvas.drawCircle(x / 2, y / 2, x / 200 * 53, backgroundPaint);

                //飾圈2(中間,有點白)
                backgroundPaint.setColor(Color.argb(255, 153, 153, 153));
                backgroundPaint.setStyle(Paint.Style.STROKE);
                backgroundPaint.setStrokeWidth(x / 200 * 1);
                canvas.drawCircle(x / 2, y / 2, x / 100 * 30, backgroundPaint);

                //外圓環
                backgroundPaint.setStyle(Paint.Style.STROKE);
                backgroundPaint.setStrokeWidth(x / 100 * 2);
                backgroundPaint.setColor(Color.rgb(243, 243, 243));
                canvas.drawCircle(x / 2, y / 2, x / 100 * 31, backgroundPaint);

                //飾圈3(刻度外圍)
                backgroundPaint.setColor(Color.argb(255, 239, 239, 239));
                backgroundPaint.setStyle(Paint.Style.STROKE);
                backgroundPaint.setStrokeWidth(x / 200);
                backgroundPaint.setAntiAlias(true);
                canvas.drawCircle(x / 2, y / 2, x / 100 * 32, backgroundPaint);

                //刻度線
                backgroundLine.setColor(Color.argb(255, 204, 204, 204));
                backgroundLine.setStrokeWidth(3);
                for (int i = 1; i < 13; i++) {
                    canvas.drawLine(x / 2 + x / 100 * 30 * (float) Math.cos(angle * i), y / 2 - x / 100 * 30 * (float) Math.sin(angle * i), x / 2 + x / 100 * 32 * (float) Math.cos(angle * i), y / 2 - x / 100 * 32 * (float) Math.sin(angle * i), backgroundLine);
                }
                //外圍文字
                backgroundText.setTextSize(x / 100 * 6);
                backgroundText.setColor(Color.argb(255, 153, 153, 153));
                if (point != 1) {
                    canvas.drawText("00", x / 200 * 93 + x / 400, y / 2 - x / 100 * 33, backgroundText);
                    canvas.drawText("10", x / 2 + x / 100 * 33 * (float) Math.cos(angle), y / 2 - x / 100 * 33 * (float) Math.sin(angle), backgroundText);
                    canvas.drawText("20", x / 2 + x / 100 * 33 * (float) Math.cos(angle), y / 2 + x / 100 * 38 * (float) Math.sin(angle), backgroundText);
                    canvas.drawText("30", x / 200 * 93, y / 2 + x / 100 * 38, backgroundText);
                    canvas.drawText("40", x / 2 - x / 100 * 41 * (float) Math.cos(angle), y / 2 + x / 100 * 38 * (float) Math.sin(angle), backgroundText);
                    canvas.drawText("50", x / 2 - x / 100 * 41 * (float) Math.cos(angle), y / 2 - x / 100 * 33 * (float) Math.sin(angle), backgroundText);
                } else {
                    canvas.drawText("00", x / 200 * 93 + x / 400, y / 2 - x / 100 * 33, backgroundText);
                    canvas.drawText("04", x / 2 + x / 100 * 33 * (float) Math.cos(angle), y / 2 - x / 100 * 33 * (float) Math.sin(angle), backgroundText);
                    canvas.drawText("08", x / 2 + x / 100 * 33 * (float) Math.cos(angle), y / 2 + x / 100 * 38 * (float) Math.sin(angle), backgroundText);
                    canvas.drawText("12", x / 200 * 93, y / 2 + x / 100 * 38, backgroundText);
                    canvas.drawText("16", x / 2 - x / 100 * 41 * (float) Math.cos(angle), y / 2 + x / 100 * 38 * (float) Math.sin(angle), backgroundText);
                    canvas.drawText("20", x / 2 - x / 100 * 41 * (float) Math.cos(angle), y / 2 - x / 100 * 33 * (float) Math.sin(angle), backgroundText);
                }
                //----------------------------------上面為背景，下面為滑、按鈕---------------------------------
                //滑鈕
                buttonSlide.setColor(Color.rgb(239, 239, 239));
                buttonSlide.setStyle(Paint.Style.FILL);
                buttonSlide.setAntiAlias(true);
                if (!flag && once == 0) {
                    if (point == 1) {
                        circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                        circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                    } else if (point == 2) {
                        circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                        circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                    } else if (point == 3) {
                        circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                        circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                    }
                    once = 1;
                    canvas.drawCircle(circlex, circley, x / 200 * 6, buttonSlide);
                }
                if (flag && once != 0) {// 普通動作
                    if (point == 1 && setbefore == 1) {
                        circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                        circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                    } else if (point == 2 && setbefore == 1) {
                        circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                        circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                    } else if (point == 3 && setbefore == 1) {
                        circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                        circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                    }

                    canvas.drawCircle(circlex, circley, x / 200 * 6, buttonSlide);
                } else if (once == 0) {//初始化設定
                    canvas.drawCircle(x / 2, y / 2 - x / 200 * 53, x / 200 * 6, buttonSlide);
                }
                //(滑鈕飾圈)
                buttonSlide.setColor(Color.rgb(255, 255, 255));
                buttonSlide.setStyle(Paint.Style.STROKE);
                buttonSlide.setStrokeWidth(x / 200 * 1);
                if (flag && once != 0) {
                    canvas.drawCircle(circlex, circley, x / 200 * 6, buttonSlide);
                } else if (once == 0) {
                    canvas.drawCircle(x / 2, y / 2 - x / 200 * 53, x / 200 * 6, buttonSlide);
                }

                //選取圈
                buttonCenter.setColor(Color.argb(255, 217, 217, 217));
                buttonCenter.setAntiAlias(true);
                buttonCenter.setStyle(Paint.Style.FILL);
                if (point == 1) {
                    canvas.drawCircle(x / 2 - x / 100 * 15, y / 2, x / 100 * 5, buttonCenter);//左
                } else if (point == 2) {
                    canvas.drawCircle(x / 2, y / 2, x / 100 * 5, buttonCenter);//中
                } else if (point == 3) {
                    canvas.drawCircle(x / 2 + x / 100 * 15, y / 2, x / 100 * 5, buttonCenter);//右
                }

                //選取飾圈
                buttonCenter.setColor(Color.rgb(204, 204, 204));
                buttonCenter.setStyle(Paint.Style.STROKE);
                buttonCenter.setStrokeWidth(x / 200 * 1);
                if (point == 1) {
                    canvas.drawCircle(x / 2 - x / 100 * 15, y / 2, x / 100 * 5, buttonCenter);//左
                } else if (point == 2) {
                    canvas.drawCircle(x / 2, y / 2, x / 100 * 5, buttonCenter);//中
                } else if (point == 3) {
                    canvas.drawCircle(x / 2 + x / 100 * 15, y / 2, x / 100 * 5, buttonCenter);//右
                }

                //選取三角形
                trColor.setColor(Color.rgb(41, 128, 185));
                if (point == 1) {
                    trLeft.moveTo(x / 2 - x / 100 * 15, y / 2 + x / 100 * 6);
                    trLeft.lineTo(x / 2 - x / 100 * 15 + x / 200 * 3, y / 2 + x / 200 * 15);
                    trLeft.lineTo(x / 2 - x / 100 * 15 - x / 200 * 3, y / 2 + x / 200 * 15);
                    trLeft.close();
                    canvas.drawPath(trLeft, trColor);
                }
                if (point == 2) {
                    tr.moveTo(x / 2, y / 2 + x / 100 * 6);
                    tr.lineTo(x / 2 + x / 200 * 3, y / 2 + x / 200 * 15);
                    tr.lineTo(x / 2 - x / 200 * 3, y / 2 + x / 200 * 15);
                    tr.close();
                    canvas.drawPath(tr, trColor);
                }
                if (point == 3) {
                    trRight.moveTo(x / 2 + x / 100 * 15, y / 2 + x / 100 * 6);
                    trRight.lineTo(x / 2 + x / 100 * 15 + x / 200 * 3, y / 2 + x / 200 * 15);
                    trRight.lineTo(x / 2 + x / 100 * 15 - x / 200 * 3, y / 2 + x / 200 * 15);
                    trRight.close();
                    canvas.drawPath(trRight, trColor);
                }
                //中央文字
                centerText.setTextSize(x / 100 * 7);
                centerTextleft.setTextSize(x / 100 * 7);
                centerTextcenter.setTextSize(x / 100 * 7);
                centerTextright.setTextSize(x / 100 * 7);
                centerTextleft.setColor(Color.BLACK);
                centerTextcenter.setColor(Color.BLACK);
                centerTextright.setColor(Color.BLACK);
                if (point == 1) {
                    centerTextleft.setColor(Color.rgb(86, 134, 189));
                } else if (point == 2) {
                    centerTextcenter.setColor(Color.rgb(86, 134, 189));
                } else if (point == 3) {
                    centerTextright.setColor(Color.rgb(86, 134, 189));
                }
                canvas.drawText(leftNumToStr, x / 100 * 31, y / 2 + x / 200 * 5, centerTextleft);
                canvas.drawText(":", x / 200 * 83, y / 2 + x / 200 * 5, centerText);
                canvas.drawText(centerNumToStr, x / 200 * 92, y / 2 + x / 200 * 5, centerTextcenter);
                canvas.drawText(":", x / 200 * 113, y / 2 + x / 200 * 5, centerText);
                canvas.drawText(rightNumToStr, x / 200 * 122, y / 2 + x / 200 * 5, centerTextright);
            }
        };
        view.setOnTouchListener(ClockSetMoudle.this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        float bgcirclex = x / 2;
        float bgcircley = y / 2;
        float bgcircleR = x / 200 * 53;
        float finalgety = motionEvent.getY();
        int judge;
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            flag = true;
            if (circlejudge(x / 2, y / 2, motionEvent.getX(), finalgety, x / 200 * 40) == 2 &&
                    circlejudge(x / 2, y / 2, motionEvent.getX(), finalgety, x / 200 * 70) == 3) {
                act = true;
                once = 1;
            }
            if (judgeslect(x / 2 - x / 100 * 15, y / 2, motionEvent.getX(), finalgety, x / 100 * 5)) {
                point = 1;
                setbefore = 1;
            } else if (judgeslect(x / 2, y / 2, motionEvent.getX(), finalgety, x / 100 * 5)) {
                point = 2;
                setbefore = 1;
            } else if (judgeslect(x / 2 + x / 100 * 15, y / 2, motionEvent.getX(), finalgety, x / 100 * 5)) {
                point = 3;
                setbefore = 1;
            }
            Graduation(motionEvent);
//            setSixty(x / 2, y / 2 - x / 200 * 53, circlex, circley, bgcircleR);
//            settwantyfour(x / 2, y / 2 - x / 200 * 53, circlex, circley, bgcircleR);
//            addzoero();
            view.invalidate();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE && act) {
            judge = circlejudge(bgcirclex, bgcircley, motionEvent.getX(), finalgety, bgcircleR);
            if (judge == 1) {//圓上
                circlex = motionEvent.getX();
                circley = finalgety;
            } else if (judge == 2) {//圓外
                calculate(bgcirclex, bgcircley, motionEvent.getX(), finalgety, bgcircleR);
                //-----------------------判斷象限-----------------
                if (motionEvent.getX() >= bgcirclex && finalgety <= bgcircley) {//1
                    circlex = motionEvent.getX() - (float) finalpoitxsub;
                    circley = finalgety + (float) finalpoitysub;
                } else if (motionEvent.getX() <= bgcirclex && finalgety <= bgcircley) {//2
                    circlex = motionEvent.getX() + (float) finalpoitxsub;
                    circley = finalgety + (float) finalpoitysub;
                } else if (motionEvent.getX() <= bgcirclex && finalgety >= bgcircley) {//3
                    circlex = motionEvent.getX() + (float) finalpoitxsub;
                    circley = finalgety - (float) finalpoitysub;
                } else {//4
                    circlex = motionEvent.getX() - (float) finalpoitxsub;
                    circley = finalgety - (float) finalpoitysub;
                }
            } else {//圓內
                inner(bgcirclex, bgcircley, motionEvent.getX(), finalgety, bgcircleR);
                //-----------------------判斷象限-----------------
                if (motionEvent.getX() >= bgcirclex && finalgety <= bgcircley) {//1
                    circlex = motionEvent.getX() + (float) innerfinalpoitxsub;
                    circley = finalgety - (float) innerfinalpoitysub;
                } else if (motionEvent.getX() <= bgcirclex && finalgety <= bgcircley) {//2
                    circlex = motionEvent.getX() - (float) innerfinalpoitxsub;
                    circley = finalgety - (float) innerfinalpoitysub;
                } else if (motionEvent.getX() <= bgcirclex && finalgety >= bgcircley) {//3
                    circlex = motionEvent.getX() - (float) innerfinalpoitxsub;
                    circley = finalgety + (float) innerfinalpoitysub;
                } else {//4
                    circlex = motionEvent.getX() + (float) innerfinalpoitxsub;
                    circley = finalgety + (float) innerfinalpoitysub;
                }
            }
            setSixty(x / 2, y / 2 - x / 200 * 53, circlex, circley, bgcircleR);
            settwantyfour(x / 2, y / 2 - x / 200 * 53, circlex, circley, bgcircleR);
            //                 初始座標
            addzoero();
            view.invalidate();//刷新
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            act = false;
            setbefore = 0;
        }
        return true;
    }

    //-----------------------------------------------------------
    public boolean judgeslect(float x, float y, float eventx, float eventy, float r) {
        return (circlejudge(x, y, eventx, eventy, r) != 2);
    }

    //-----------------------------------------------------------
    public int circlejudge(float circlex, float circley, float pointx, float pointy, float r) {
        float calculate;
        calculate = ((circlex - pointx) * (circlex - pointx)) + ((circley - pointy) * (circley - pointy));
        if (calculate == r * r) {//on
            return 1;
        } else if (calculate > r * r) {//out
            return 2;
        } else {//inner
            return 3;
        }
    }

    //-----------------------------------------------------------
    public void calculate(float circlex, float circley, float pointx, float pointy, float r) {
        float pointxsub;
        float pointysub;
        double radian;
        double number;
        double L;
        number = Math.sqrt((circlex - pointx) * (circlex - pointx) + (circley - pointy) * (circley - pointy));
        L = number - r;
        pointxsub = Math.abs(circlex - pointx);
        pointysub = Math.abs(circley - pointy);
        radian = Math.atan(pointxsub / pointysub);

        finalpoitxsub = Math.sin(radian) * L;
        finalpoitysub = Math.cos(radian) * L;
    }

    public void inner(float circlex, float circley, float pointx, float pointy, float r) {
        float innerpointxsub;
        float innerpointysub;
        double innerradian;
        double numberinner;
        double innerL;
        numberinner = Math.sqrt((circlex - pointx) * (circlex - pointx) + (circley - pointy) * (circley - pointy));
        innerL = r - numberinner;
        innerpointxsub = Math.abs(circlex - pointx);
        innerpointysub = Math.abs(circley - pointy);
        innerradian = Math.atan(innerpointxsub / innerpointysub);

        innerfinalpoitxsub = Math.sin(innerradian) * innerL;
        innerfinalpoitysub = Math.cos(innerradian) * innerL;
    }

    //------------------------------------------------------------------
    public void setSixty(float beforex, float beforey, float afterx, float aftery, float r) {
        double distance;
        double angle;
        distance = Math.sqrt(((beforex - afterx) * (beforex - afterx)) + ((beforey - aftery) * (beforey - aftery)));
        angle = (Math.asin((distance / 2) / r)) * 2;
        int index = 0;
        if (afterx >= beforex) {
            for (int i = 0; i < 30; i++) {
                if ((6 * Math.PI / 180) * i <= angle && (6 * Math.PI / 180) * (i + 1) > angle) {
                    if (angle > ((6 * Math.PI / 180) * (i + 1) / 2)) {
                        showsixty = index + i + 1;
                    } else {
                        showsixty = index + i;
                    }
                }
            }
        } else {
            for (int i = 0; i < 30; i++) {
                if ((6 * Math.PI / 180) * i <= angle && (6 * Math.PI / 180) * (i + 1) > angle) {
                    if (angle > ((6 * Math.PI / 180) * (i + 1) / 2)) {
                        showsixty = 60 - (index + i + 1);
                    } else {
                        if (60 - (index + i) == 60) {
                            showsixty = 0;
                        } else
                            showsixty = 60 - (index + i);
                    }
                }
            }
        }
    }

    public void settwantyfour(float beforex, float beforey, float afterx, float aftery, float r) {
        double distance;
        double angle;
        distance = Math.sqrt(((beforex - afterx) * (beforex - afterx)) + ((beforey - aftery) * (beforey - aftery)));
        angle = (Math.asin((distance / 2) / r)) * 2;
        int index = 0;
        if (afterx >= beforex) {
            for (int i = 0; i <= 12; i++) {
                if ((15 * Math.PI / 180) * i <= angle && (15 * Math.PI / 180) * (i + 1) > angle) {
                    showtwentyfour = index + i + 1;
                }
            }
        } else {
            for (int i = 0; i <= 12; i++) {
                if ((15 * Math.PI / 180) * i <= angle && (15 * Math.PI / 180) * (i + 1) > angle) {
                    if (24 - (index + i) == 24) {
                        showtwentyfour = 0;
                    } else
                        showtwentyfour = 24 - (index + i);
                }
            }
        }
    }

    public void addzoero() {
        if (point == 1) {
            if (showtwentyfour < 10)
                leftNumToStr = "0" + Integer.toString(showtwentyfour);
            else
                leftNumToStr = Integer.toString(showtwentyfour);
        } else if (point == 2) {
            if (showsixty < 10)
                centerNumToStr = "0" + Integer.toString(showsixty);
            else
                centerNumToStr = Integer.toString(showsixty);
        } else if (point == 3) {
            if (showsixty < 10)
                rightNumToStr = "0" + Integer.toString(showsixty);
            else
                rightNumToStr = Integer.toString(showsixty);
        }
    }

    public void Graduation(MotionEvent motionEvent) {
            /*30*/
        if (circlejudge(x / 200 * 93, y / 2 + x / 100 * 38, motionEvent.getX(), motionEvent.getY(), x / 100 * 7) == 3) {
            if (point == 1) {
                leftNumToStr = "12";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
            } else if (point == 2) {
                centerNumToStr = "30";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));

            } else {
                rightNumToStr = "30";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
            }
        }
        /*20*/
        else if (circlejudge(x / 2 + x / 100 * 33 * (float) Math.cos(angle), y / 2 + x / 100 * 38 * (float) Math.sin(angle), motionEvent.getX(), motionEvent.getY(), x / 100 * 7) == 3) {
            if (point == 1) {
                leftNumToStr = "08";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
            } else if (point == 2) {
                centerNumToStr = "20";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));

            } else {
                rightNumToStr = "20";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
            }
        }
        /*10*/
        else if (circlejudge(x / 2 + x / 100 * 33 * (float) Math.cos(angle), y / 2 - x / 100 * 33 * (float) Math.sin(angle), motionEvent.getX(), motionEvent.getY(), x / 100 * 7) == 3) {
            if (point == 1) {
                leftNumToStr = "04";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
            } else if (point == 2) {
                centerNumToStr = "10";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));

            } else {
                rightNumToStr = "10";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
            }
        }
        /*40*/
        else if (circlejudge(x / 2 - x / 100 * 33 * (float) Math.cos(angle), y / 2 + x / 100 * 38 * (float) Math.sin(angle), motionEvent.getX(), motionEvent.getY(), x / 100 * 7) == 3) {
            if (point == 1) {
                leftNumToStr = "16";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
            } else if (point == 2) {
                centerNumToStr = "40";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));

            } else {
                rightNumToStr = "40";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
            }
        }
        /*50*/
        else if (circlejudge(x / 2 - x / 100 * 33 * (float) Math.cos(angle), y / 2 - x / 100 * 33 * (float) Math.sin(angle), motionEvent.getX(), motionEvent.getY(), x / 100 * 7) == 3) {
            if (point == 1) {
                leftNumToStr = "20";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
            } else if (point == 2) {
                centerNumToStr = "50";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));

            } else {
                rightNumToStr = "50";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
            }

        }
        /*00*/
        else if (circlejudge(x / 200 * 93 + x / 400, y / 2 - x / 100 * 33, motionEvent.getX(), motionEvent.getY(), x / 100 * 7) == 3) {
            if (point == 1) {
                leftNumToStr = "00";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (15 * Math.PI / 180) * Integer.valueOf(leftNumToStr));
            } else if (point == 2) {
                centerNumToStr = "00";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(centerNumToStr));

            } else {
                rightNumToStr = "00";
                circlex = x / 2 + x / 200 * 53 * (float) Math.cos((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
                circley = y / 2 - x / 200 * 53 * (float) Math.sin((Math.PI / 2) - (6 * Math.PI / 180) * Integer.valueOf(rightNumToStr));
            }
        }
    }
}