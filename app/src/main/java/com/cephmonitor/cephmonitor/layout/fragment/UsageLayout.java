package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.widget.RelativeLayout;

import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class UsageLayout extends RelativeLayout {
    private Context context;
    private WH ruler;

    public UsageLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
    }
}
