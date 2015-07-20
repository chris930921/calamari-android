package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.list.DefaultListLayout;

public class HostDetailIopsLayout extends DefaultListLayout {

    public static int[] textGroup = {
            R.string.host_detail_iops,
    };
    public static int[] textColorGroup = {
            ColorTable._8DC41F,
    };

    public HostDetailIopsLayout(Context context) {
        super(context);
    }
}
