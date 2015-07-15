package com.cephmonitor.cephmonitor.model.app.theme.custom.attribute;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.object.FontStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.SpecAttribute;

/**
 * Created by User on 2015/7/14.
 */
public abstract class Style extends SpecAttribute {

    public Style(DesignSpec designSpec, Context context) {
        super(designSpec, context);
    }

    public abstract FontStyle getDisplayOne();

    public abstract FontStyle getHeadline();

    public abstract FontStyle getTitle();

    public abstract FontStyle getSubhead();

    public abstract FontStyle getBodyTwo();

    public abstract FontStyle getBodyOne();

    public abstract FontStyle getNote();

    public abstract FontStyle getDefaultButton();

    public abstract FontStyle getLargeButton();
}
