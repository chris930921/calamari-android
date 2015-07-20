package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;

import com.cephmonitor.cephmonitor.layout.fragment.HostDetailIopsLayout;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.DefaultListItem;

public class HostDetailIopsItem extends DefaultListItem {
    public HostDetailIopsItem(Context context) {
        super(context);

        setLineText(HostDetailIopsLayout.textGroup, HostDetailIopsLayout.textColorGroup);
    }

    public void setName(String text) {
        title.setText(text);
    }
}
