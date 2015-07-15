package com.cephmonitor.cephmonitor.model.app.theme.custom.attribute;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.SpecAttribute;

/**
 * Created by User on 2015/7/14.
 */
public abstract class AccentColors extends SpecAttribute {

    public AccentColors(DesignSpec designSpec, Context context) {
        super(designSpec, context);
    }

    public abstract int getActive();

    public abstract int getNormal();

    public abstract int getError();

    public abstract int getWarning();
}
