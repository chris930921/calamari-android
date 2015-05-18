package com.cephmonitor.cephmonitor.layout.component.osdhealthboxes;

import android.graphics.Color;

/**
 * Created by User on 5/18/2015.
 */
public class OsdBox {
    public static final int NORMAL_STATUS = 0;
    public static final int WARN_STATUS = 1;
    public static final int ERROR_STATUS = 2;

    public int value;
    public int status;
    private int color;

    public int getColor() {
        return color;
    }

    public void setStatus(int status) {
        this.status = status;
        if (status == NORMAL_STATUS) {
            color = Color.parseColor("#8dc41f");
        } else if (status == WARN_STATUS) {
            color = Color.parseColor("#f7b500");
        } else if (status == ERROR_STATUS) {
            color = Color.parseColor("#e63427");
        }
    }
}
