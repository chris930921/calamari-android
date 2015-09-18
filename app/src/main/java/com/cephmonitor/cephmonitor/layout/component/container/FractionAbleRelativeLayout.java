package com.cephmonitor.cephmonitor.layout.component.container;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by User on 2015/7/13.
 */
public class FractionAbleRelativeLayout extends RelativeLayout {
    public FractionAbleRelativeLayout(Context context) {
        super(context);
    }

    public float getXFraction() {
        int width = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
        return (width == 0) ? 0 : getX() / (float) width;
    }

    public void setXFraction(float xFraction) {
        int width = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
        setX((width > 0) ? (xFraction * width) : 0);
    }
}
