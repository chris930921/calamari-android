package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class HealthDetailLayout extends RelativeLayout {
    private Context context;
    private WH ruler;

    public ListView list;

    public RelativeLayout workFineContainer;
    public View workFineImage;
    public TextView workFineLineOne;
    public TextView workFineLineTwo;

    public HealthDetailLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        list = list();
        workFineContainer = workFineContainer();
        workFineImage = workFineImage();
        workFineLineOne = workFineLineOne(workFineImage);
        workFineLineTwo = workFineLineTwo(workFineLineOne);

        addView(list);
        addView(workFineContainer);
        workFineContainer.addView(workFineImage);
        workFineContainer.addView(workFineLineOne);
        workFineContainer.addView(workFineLineTwo);
    }

    public ListView list() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.setMargins(ruler.getW(5), 0, ruler.getW(5), ruler.getW(5));

        ListView v = new ListView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setDivider(new ColorDrawable(Color.parseColor("#EFEFEF")));
        v.setDividerHeight(ruler.getH(0.36));

        return v;
    }

    private RelativeLayout workFineContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setVisibility(GONE);

        return v;
    }

    public View workFineImage() {
        LayoutParams params = new LayoutParams(ruler.getW(30), ruler.getW(30));
        params.addRule(CENTER_HORIZONTAL);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundResource(R.drawable.icon026);

        return v;
    }

    public TextView workFineLineOne(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());
        params.setMargins(0, ruler.getH(5), 0, 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setText(R.string.health_detail_great);
        v.setTextColor(ColorTable._666666);
        v.setTextSize(14);

        return v;
    }

    public TextView workFineLineTwo(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setText(R.string.health_detail_work_fine);
        v.setTextColor(ColorTable._666666);
        v.setTextSize(14);

        return v;
    }

    public void showWorkFind() {
        list.setVisibility(GONE);
        workFineContainer.setVisibility(VISIBLE);
    }
}
