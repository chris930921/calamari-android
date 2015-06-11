package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.other.WorkFindView;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class NotificationLayout extends RelativeLayout {
    private Context context;
    private WH ruler;

    public ListView list;
    public WorkFindView workFine;

    public NotificationLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        addView(list = list());
        addView(workFine = workFine());
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

    private WorkFindView workFine() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        WorkFindView v = new WorkFindView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setVisibility(GONE);
        v.setText(
                getResources().getString(R.string.notification_work_find_line_one),
                getResources().getString(R.string.notification_work_find_line_two)
        );

        return v;
    }

    public void showWorkFind() {
        workFine.showWorkFind(list);
    }

    public void hideWorkFind() {
        workFine.hideWorkFind(list);
    }
}
