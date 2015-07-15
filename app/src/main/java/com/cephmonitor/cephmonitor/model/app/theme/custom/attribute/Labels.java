package com.cephmonitor.cephmonitor.model.app.theme.custom.attribute;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.SpecAttribute;

/**
 * Created by User on 2015/7/14.
 */
public abstract class Labels extends SpecAttribute {

    public Labels(DesignSpec designSpec, Context context) {
        super(designSpec, context);
    }

    public abstract int getSuccessColor();

    public abstract int getInfoColor();

    public abstract int getWarningColor();

    public abstract int getDangerColor();

    public abstract int getBorderRadius();

    public abstract int getFontSize();

    public abstract float getTopPadding();

    public abstract float getBottomPadding();

    public abstract float getLeftPadding();

    public abstract float getRightPadding();

    public abstract float getHorizonMargin();

    public abstract float getVerticalMargin();

}
