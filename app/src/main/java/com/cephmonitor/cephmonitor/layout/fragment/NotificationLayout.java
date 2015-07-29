package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.cephmonitor.cephmonitor.layout.component.other.WorkFindView;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class NotificationLayout extends FractionAbleRelativeLayout {
    private Context context;
    private WH ruler;

    public ListView list;
    public WorkFindView workFine;

    private DesignSpec designSpec;
    private float leftRightMarginOne;
    private float topBottomMarginOne;

    public NotificationLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.designSpec = ThemeManager.getStyle(context);
        leftRightMarginOne = designSpec.getMargin().getLeftRightOne();
        topBottomMarginOne = designSpec.getMargin().getTopBottomOne();

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);

        addView(list = list());
        addView(workFine = workFine());
    }

    public ListView list() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.setMargins(ruler.getW(leftRightMarginOne), 0, ruler.getW(leftRightMarginOne), 0);

        ListView v = new ListView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setDivider(new ColorDrawable(ColorTable._EFEFEF));
        v.setDividerHeight(ruler.getH(0.36));
        v.setPadding(0, 0, 0, ruler.getW(topBottomMarginOne));

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
