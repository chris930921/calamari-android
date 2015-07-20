package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.list.DefaultListLayout;

public class PoolIopsLayout extends DefaultListLayout {
    public static int[] textGroup = {
            R.string.pool_iops_read, R.string.pool_iops_write,
    };
    public static int[] textColorGroup = {
            ColorTable._8DC41F, ColorTable._F7B500,
    };

    public PoolIopsLayout(Context context) {
        super(context);
    }
}
