package com.resourcelibrary.model.view;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by User on 1/19/2015.
 */
public class TextTypeface {
    private Context context;
    private static Typeface HelveticLt25;
    private static Typeface HelveticLt35;
    private static Typeface HelveticLt45;
    private static Typeface HelveticLt55;
    private static Typeface HelveticLt65;

    public TextTypeface(Context context) {
        this.context = context;
    }

    public Typeface getHelveticLt25() {
        return HelveticLt25 = isInstance(HelveticLt25, "fonts/Helvetica_LT_25_Ultra_Light.ttf");
    }

    public Typeface getHelveticLt35() {
        return HelveticLt35 = isInstance(HelveticLt35, "fonts/Helvetica_LT_35_Thin.ttf");
    }

    public Typeface getHelveticLt45() {
        return HelveticLt45 = isInstance(HelveticLt45, "fonts/Helvetica_LT_45_Light.ttf");
    }

    public Typeface getHelveticLt55() {
        return HelveticLt55 = isInstance(HelveticLt55, "fonts/Helvetica_LT_55_Roman.ttf");
    }

    public Typeface getHelveticLt65() {
        return HelveticLt65 = isInstance(HelveticLt65, "fonts/Helvetica_LT_65_Medium.ttf");
    }

    public Typeface isInstance(Typeface Instance, String Path) {
        if (Instance == null) {
            return Typeface.createFromAsset(context.getAssets(), Path);
        } else {
            return Instance;
        }
    }
}
