package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.list.DefaultListLayout;

public class HostDetailSummaryLayout extends DefaultListLayout {
    private int[] summaryTextGroup = {R.string.host_detail_summary_system, R.string.host_detail_summary_user, R.string.host_detail_summary_Idle};
    private int[] summaryTextColorGroup = {ColorTable._8DC41F, ColorTable._39C0ED, ColorTable._F7B500};

    private int[] averageTextGroup = {R.string.host_detail_summary_one_min, R.string.host_detail_summary_five_min, R.string.host_detail_summary_fifteen_min};
    private int[] averageTextColorGroup = {ColorTable._8DC41F, ColorTable._39C0ED, ColorTable._F7B500};

    private int[] memoryTextGroup = {R.string.host_detail_summary_active, R.string.host_detail_summary_buffers, R.string.host_detail_summary_free, R.string.host_detail_summary_cached};
    private int[] memoryTextColorGroup = {ColorTable._8DC41F, ColorTable._8D81C2, ColorTable._39C0ED, ColorTable._F7B500};

    public int[] titleGroup = {R.string.host_detail_title_cpu_summary, R.string.host_detail_title_load_average, R.string.host_detail_title_memory};
    public int[][] lineTextGroup = {summaryTextGroup, averageTextGroup, memoryTextGroup};
    public int[][] colorGroup = {summaryTextColorGroup, averageTextColorGroup, memoryTextColorGroup};

    public HostDetailSummaryLayout(Context context) {
        super(context);
    }
}
