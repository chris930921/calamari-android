package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;

import com.cephmonitor.cephmonitor.layout.fragment.PoolIopsLayout;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.DefaultListItem;

public class PoolIopsItem extends DefaultListItem {
    public PoolIopsItem(Context context) {
        super(context);

        setLineText(PoolIopsLayout.textGroup, PoolIopsLayout.textColorGroup);
    }

    public void setName(String titleText) {
        title.setText(titleText);
    }
}
