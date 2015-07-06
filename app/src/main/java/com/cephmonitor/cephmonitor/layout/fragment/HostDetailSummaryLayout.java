package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;

import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class HostDetailSummaryLayout extends RelativeLayout {
    private WH ruler;

    public HostDetailSummaryLayout(Context context) {
        super(context);
        this.ruler = new WH(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

    }
}
