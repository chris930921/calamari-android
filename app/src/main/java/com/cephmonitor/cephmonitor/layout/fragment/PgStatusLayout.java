package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class PgStatusLayout extends RelativeLayout {
    private Context context;
    private WH ruler;

    public ListView list;

    public PgStatusLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        addView(list = list());
    }

    public ListView list() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.setMargins(ruler.getW(5), 0, ruler.getW(5), 0);

        ListView v = new ListView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.addFooterView(fillView());
        v.setDivider(new ColorDrawable(Color.TRANSPARENT));
        v.setFooterDividersEnabled(false);
        v.setClickable(false);

        return v;
    }

    private View fillView() {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(5));

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }
}
