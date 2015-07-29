package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;

import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class ManageHostsLayout extends FractionAbleRelativeLayout {
    private Context context;
    private WH ruler;

    public ManageHostsLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
    }
}
