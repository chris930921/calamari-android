package com.resourcelibrary.model.view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class WH {
    public int Width;
    public int Height;
    public int densityDpi;
    private Context context;

    public WH(Context context) {
        this.context = context;
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        Width = dm.widthPixels;
        Height = dm.heightPixels;
        densityDpi = dm.densityDpi;
    }

    public int getW(double Per) {
        if (Per == -1)
            return -1;
        else if (Per == -2)
            return -2;
        return (int) ((Per > 100.0) ? Width : ((Width * Per) / 100));
    }

    public int getH(double Per) {
        if (Per == -1)
            return -1;
        else if (Per == -2)
            return -2;
        return (int) ((Per > 100.0) ? Height : ((Height * Per) / 100));
    }

    public int getCW(int W, double Per) {
        return (int) ((Per > 100.0) ? W : ((W * Per) / 100));
    }

    public int getCH(int H, double Per) {
        return (int) ((Per > 100.0) ? H : ((H * Per) / 100));
    }

    public int getTextSize(int size) {
        switch (densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return size;
            case DisplayMetrics.DENSITY_MEDIUM:
                return size;
            case DisplayMetrics.DENSITY_TV:
                return size;
            case DisplayMetrics.DENSITY_HIGH:
                return (int) (size * 0.68);
            case DisplayMetrics.DENSITY_XHIGH:
                return size;
            case DisplayMetrics.DENSITY_XXHIGH:
//                return (int) (size * 1.5);
                return (int) (size * 1);
            case DisplayMetrics.DENSITY_XXXHIGH:
                return (int) (size * 2.5);
            default:
                return (int) (size * 10);
        }
    }

}
