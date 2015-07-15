package com.cephmonitor.cephmonitor.model.app.theme.custom.attribute;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.SpecAttribute;

/**
 * Created by User on 2015/7/14.
 */
public abstract class PrimaryColors extends SpecAttribute {

    public PrimaryColors(DesignSpec designSpec, Context context) {
        super(designSpec, context);
    }

    public abstract int getBackgroundOne();

    public abstract int getBackgroundTwo();

    public abstract int getBackgroundThree();

    public abstract int getPrimary();

    public abstract int getSecondary();

    public abstract int getHorizontalOne();

    public abstract int getHorizontalTwo();
}
