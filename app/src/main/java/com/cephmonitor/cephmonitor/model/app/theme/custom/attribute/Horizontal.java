package com.cephmonitor.cephmonitor.model.app.theme.custom.attribute;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.SpecAttribute;

/**
 * Created by User on 2015/7/14.
 */
public abstract class Horizontal extends SpecAttribute {
    public Horizontal(DesignSpec designSpec, Context context) {
        super(designSpec, context);
    }

    public abstract float getHorizontalOneHeight();

    public abstract float getHorizontalTwoHeight();

}
