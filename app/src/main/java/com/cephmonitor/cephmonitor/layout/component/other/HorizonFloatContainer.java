package com.cephmonitor.cephmonitor.layout.component.other;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by User on 2015/6/30.
 */
public class HorizonFloatContainer extends ViewGroup {

    public HorizonFloatContainer(Context context) {
        super(context);
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
            if (widthSum + childWidth >= width) {
                heightSum += height;
                widthSum = childWidth;
            } else {
                widthSum += childWidth;
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
            if (widthSum + childWidth >= getMeasuredWidth()) {
                heightSum += height;
                child.layout(0, heightSum, childWidth, heightSum + childHeight);
                widthSum = childWidth;
            } else {
                child.layout(widthSum, heightSum, widthSum + childWidth, heightSum + childHeight);
                widthSum += childWidth;
            }
        }
    }
}
