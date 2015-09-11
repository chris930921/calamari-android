package com.cephmonitor.cephmonitor.layout.listitem.reuse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.util.HashMap;


public class RoundLeftBarItem extends RelativeLayout {
    private HashMap<String, Integer> choiceColor;

    private Context context;
    private WH ruler;
    private RectF bounds;
    private int borderWidth = 1;
    private int radius = 10;
    private Paint borderPaint;
    private Paint backgroundPaint;
    private Paint leftBarPaint;

    public RelativeLayout contentContainer;
    public View topFillView;
    public View bottomFillView;

    private int statusBarWidth;
    private int barColor;
    private int borderColor;
    private int backgroundColor;
    private int leftPadding;
    private int topPadding;
    private int rightPadding;
    private int bottomPadding;
    private int topMargin;
    private int bottomMargin;

    public RoundLeftBarItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.bounds = new RectF();
        choiceColor = new HashMap<>();
        barColor = Color.BLACK;
        borderColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        leftPadding = 10;
        topPadding = 10;
        rightPadding = 10;
        bottomPadding = 10;
        statusBarWidth = ruler.getW(3);
        topMargin = ruler.getW(3);
        bottomMargin = ruler.getW(3);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);

        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        leftBarPaint = new Paint();
        leftBarPaint.setAntiAlias(true);
        leftBarPaint.setColor(barColor);

        topFillView = topFillView();
        contentContainer = contentContainer(topFillView);
        bottomFillView = bottomFillView(contentContainer);

        super.addView(topFillView);
        super.addView(contentContainer);
        super.addView(bottomFillView);
    }

    @Override
    public void addView(View child) {
        contentContainer.addView(child);
    }

    private RelativeLayout contentContainer(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(context) {
            @Override
            protected void dispatchDraw(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();

                int left = borderWidth;
                int top = borderWidth;
                int right = width - left;
                int bottom = height - top;
                bounds.set(left, top, right, bottom);

                canvas.drawRoundRect(bounds, radius, radius, backgroundPaint);
                canvas.drawRoundRect(bounds, radius, radius, borderPaint);

                bounds.set(0, 0, statusBarWidth, height);
                canvas.drawRoundRect(bounds, radius, radius, leftBarPaint);

                bounds.set(statusBarWidth / 2, 0, statusBarWidth, height);
                canvas.drawRect(bounds, leftBarPaint);

                super.dispatchDraw(canvas);
            }
        };
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setPadding(statusBarWidth + leftPadding, 0, rightPadding, 0);

        return v;
    }

    private View topFillView() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, topMargin);
        params.addRule(ALIGN_PARENT_TOP);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View bottomFillView(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, bottomMargin);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(BELOW, topView.getId());

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public void setViewBackgroundColor(int color) {
        backgroundColor = color;
        backgroundPaint.setColor(backgroundColor);
        invalidate();
    }

    public void setViewPadding(int left, int top, int right, int bottom) {
        leftPadding = left;
        topPadding = top;
        rightPadding = right;
        bottomPadding = bottom;

        contentContainer.setPadding(statusBarWidth + leftPadding, topPadding, rightPadding, bottomPadding);
    }

    public void setStatusBarColor(int color) {
        barColor = color;
        leftBarPaint.setColor(barColor);
        invalidate();
    }

    public void setBorderColor(int color) {
        borderColor = color;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public void setStatusBarWidth(int statusBarWidth) {
        this.statusBarWidth = statusBarWidth;
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        invalidate();
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void setTopBottomMargin(int topMargin, int bottomMargin) {
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
        ViewGroup.LayoutParams topParams = topFillView.getLayoutParams();
        ViewGroup.LayoutParams bottomParams = bottomFillView.getLayoutParams();
        topParams.height = topMargin;
        bottomParams.height = bottomMargin;
        topFillView.setLayoutParams(topParams);
        bottomFillView.setLayoutParams(bottomParams);
    }
}
