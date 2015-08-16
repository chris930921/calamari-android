package com.cephmonitor.cephmonitor.model.app.theme.custom.attribute;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.SpecAttribute;

/**
 * Created by User on 2015/7/14.
 */
public abstract class IconSize extends SpecAttribute {
    public IconSize(DesignSpec designSpec, Context context) {
        super(designSpec, context);
    }

    public abstract float getTitle();

    public abstract float getSubhead();

    public abstract float getBody();

    public abstract float getNote();

    public abstract float getDefaultButton();

    public abstract float getLargeButton();

    public abstract float getMessage();

    public abstract float getDisplayOne();

    public abstract float getHeadline();
}
