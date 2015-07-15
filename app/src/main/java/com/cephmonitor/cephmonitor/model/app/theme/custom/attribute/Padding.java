package com.cephmonitor.cephmonitor.model.app.theme.custom.attribute;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.SpecAttribute;

/**
 * Created by User on 2015/7/14.
 */
public abstract class Padding extends SpecAttribute {

    public Padding(DesignSpec designSpec, Context context) {
        super(designSpec, context);
    }

    public abstract float getLeftRightOne();

    public abstract float getLeftRightTwo();

    public abstract float getTopBottomOne();

    public abstract float getTopBottomTwo();
}
