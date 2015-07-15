package com.cephmonitor.cephmonitor.model.app.theme.custom.attribute;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.SpecAttribute;

/**
 * Created by User on 2015/7/14.
 */
public abstract class IconButton extends SpecAttribute {

    public IconButton(DesignSpec designSpec, Context context) {
        super(designSpec, context);
    }

    public abstract float getNormalWidth();

    public abstract float getActiveWidth();

    public abstract float getNormalHeight();

    public abstract float getActiveHeight();
}
