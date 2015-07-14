package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class HostDetailSummaryLayout extends FractionAbleRelativeLayout {
    private WH ruler;

    private int[] summaryTextGroup = {R.string.host_detail_summary_system, R.string.host_detail_summary_user, R.string.host_detail_summary_Idle};
    private int[] summaryTextColorGroup = {ColorTable._8DC41F, ColorTable._39C0ED, ColorTable._F7B500};

    private int[] averageTextGroup = {R.string.host_detail_summary_one_min, R.string.host_detail_summary_five_min, R.string.host_detail_summary_fifteen_min};
    private int[] averageTextColorGroup = {ColorTable._8DC41F, ColorTable._39C0ED, ColorTable._F7B500};

    private int[] memoryTextGroup = {R.string.host_detail_summary_active, R.string.host_detail_summary_buffers, R.string.host_detail_summary_free, R.string.host_detail_summary_cached};
    private int[] memoryTextColorGroup = {ColorTable._8DC41F, ColorTable._8D81C2, ColorTable._39C0ED, ColorTable._F7B500};

    public int[] titleGroup = {R.string.host_detail_title_cpu_summary, R.string.host_detail_title_load_average, R.string.host_detail_title_memory};
    public int[][] lineTextGroup = {summaryTextGroup, averageTextGroup, memoryTextGroup};
    public int[][] colorGroup = {summaryTextColorGroup, averageTextColorGroup, memoryTextColorGroup};

    public ListView list;

    public HostDetailSummaryLayout(Context context) {
        super(context);
        this.ruler = new WH(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        list = list();
        addView(list);
    }

    public ListView list() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.setMargins(ruler.getW(5), 0, ruler.getW(5), 0);

        ListView v = new ListView(getContext());
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

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }
}
