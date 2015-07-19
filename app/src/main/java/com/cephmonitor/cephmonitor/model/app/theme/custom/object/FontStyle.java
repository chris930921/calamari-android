package com.cephmonitor.cephmonitor.model.app.theme.custom.object;

/**
 * Created by User on 2015/7/14.
 */
public abstract class FontStyle {
    public static final Integer NO_DEFAULT_COLOR = null;

    public abstract int getSize();

    public abstract Integer getColor();

    public abstract int getTypeface();
}
