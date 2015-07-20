package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.fragment.HostDetailAllCpusLayout;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.DefaultListItem;

public class HostDetailAllCpusItem extends DefaultListItem {
    public HostDetailAllCpusItem(Context context) {
        super(context);
        setLineText(HostDetailAllCpusLayout.textGroup, HostDetailAllCpusLayout.textColorGroup);
    }

    public void setName(String text) {
        title.setText(text + getContext().getString(R.string.host_detail_all_cpus_title_cpu_detail));
    }
}
