package com.cephmonitor.cephmonitor.model.app.theme.custom.attribute;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.SpecAttribute;

/**
 * Created by User on 2015/7/14.
 */
public abstract class TextButton extends SpecAttribute {

    public TextButton(DesignSpec designSpec, Context context) {
        super(designSpec, context);
    }

    public abstract float getDefaultHeight();

    public abstract float getLargeHeight();

    public abstract int getDefaultFontSize();

    public abstract int getLargeFontSize();

    public abstract int getNormalBackground();

    public abstract int getNormalBorderSize();

    public abstract int getNormalBorderColor();

    public abstract int getNormalTextColor();

    public abstract int getActiveBackground();

    public abstract int getActiveBorderSize();

    public abstract int getActiveBorderColor();

    public abstract int getActiveTextColor();

}
