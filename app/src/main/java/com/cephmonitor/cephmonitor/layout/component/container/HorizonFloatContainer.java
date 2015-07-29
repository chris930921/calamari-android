package com.cephmonitor.cephmonitor.layout.component.container;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by User on 2015/6/30.
 */
public class HorizonFloatContainer extends ViewGroup {
    private int viewLeftMargin;

    public HorizonFloatContainer(Context context) {
        super(context);
        viewLeftMargin = 0;
    }

    public void setViewLeftMargin(int viewLeftMargin) {
        this.viewLeftMargin = viewLeftMargin;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = widthMeasureSpec & 0x000FFFF;

        int widthSum = 0;
        int heightSum = 0;
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childHeight = child.getMeasuredHeight();
            if (childHeight > height) {
                height = childHeight;
            }

            int childWidth = child.getMeasuredWidth();
            int nextWidthSum = widthSum + viewLeftMargin + childWidth;
            if (nextWidthSum >= width) {
                heightSum += height;
                widthSum = childWidth + viewLeftMargin;
            } else {
                widthSum = nextWidthSum;
            }
        }
        heightSum += height;


        setMeasuredDimension(widthMeasureSpec, heightSum);
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        int widthSum = 0;
        int heightSum = 0;
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childHeight = child.getMeasuredHeight();
            if (childHeight > height) {
                height = childHeight;
            }

            int childWidth = child.getMeasuredWidth();
            int nextWidthSum = widthSum + viewLeftMargin + childWidth;
            if (nextWidthSum >= getMeasuredWidth()) {
                heightSum += height;
                child.layout(0, heightSum, childWidth, heightSum + childHeight);
                widthSum = childWidth + viewLeftMargin;
            } else {
                child.layout(widthSum, heightSum, widthSum + childWidth, heightSum + childHeight);
                widthSum = nextWidthSum;
            }
        }
    }
}
