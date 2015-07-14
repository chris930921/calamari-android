package com.cephmonitor.cephmonitor.layout.component.container;

import android.app.Activity;
import android.content.Context;
import android.widget.ScrollView;

import com.resourcelibrary.model.log.ShowLog;

/**
 * Created by User on 2015/7/13.
 */
public class FractionAbleScrollView extends ScrollView {

    public FractionAbleScrollView(Context context) {
        super(context);
    }

    public float getXFraction() {
        ShowLog.d("getXFraction Ya");
        int width = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
        return (width == 0) ? 0 : getX() / (float) width;
    }

    public void setXFraction(float xFraction) {
        ShowLog.d("setXFraction Ya");
        int width = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
        setX((width > 0) ? (xFraction * width) : 0);
    }
}
