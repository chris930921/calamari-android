package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.list.DefaultListLayout;

public class HostDetailAllCpusLayout extends DefaultListLayout {

    public static int[] textGroup = {
            R.string.host_detail_all_cpus_system, R.string.host_detail_all_cpus_user, R.string.host_detail_all_cpus_nice,
            R.string.host_detail_all_cpus_idle, R.string.host_detail_all_cpus_io_wait, R.string.host_detail_all_cpus_irq,
            R.string.host_detail_all_cpus_soft_irq, R.string.host_detail_all_cpus_steal,
    };

    public static int[] textColorGroup = {
            ColorTable._8DC41F, ColorTable._8D81C2, ColorTable._39C0ED, ColorTable._F7B500,
            ColorTable._E63427, ColorTable._45818E, ColorTable._D5A6BD, ColorTable._B45F06,
    };

    public HostDetailAllCpusLayout(Context context) {
        super(context);
    }
}
