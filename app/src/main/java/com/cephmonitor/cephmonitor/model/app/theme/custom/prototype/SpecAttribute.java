package com.cephmonitor.cephmonitor.model.app.theme.custom.prototype;

import android.content.Context;

/**
 * Created by User on 2015/7/14.
 */
public class SpecAttribute {
    private DesignSpec designSpec;
    private Context context;

    public SpecAttribute(DesignSpec designSpec, Context context) {
        this.designSpec = designSpec;
        this.context = context;
    }

    public DesignSpec getDesignSpec() {
        return designSpec;
    }

    public Context getContext() {
        return context;
    }
}
