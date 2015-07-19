package com.cephmonitor.cephmonitor.model.app.theme.custom.manager;

import android.widget.TextView;

import com.cephmonitor.cephmonitor.model.app.theme.custom.object.FontStyle;

/**
 * Created by User on 2015/7/20.
 */
public class TextViewStyle {
    private Integer color;
    private int size;
    private int typeface;

    public TextViewStyle(FontStyle fontStyle) {
        color = fontStyle.getColor();
        size = fontStyle.getSize();
        typeface = fontStyle.getTypeface();
    }

    public void style(TextView v) {
        v.setTextSize(size);
        v.setTypeface(null, typeface);
        if (color != FontStyle.NO_DEFAULT_COLOR) {
            v.setTextColor(color);
        }
    }
}
