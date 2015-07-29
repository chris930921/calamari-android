package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.cephmonitor.cephmonitor.layout.component.other.WorkFindView;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class HealthDetailLayout extends FractionAbleRelativeLayout {
    private Context context;
    private WH ruler;

    public ListView list;
    public WorkFindView workFine;

    private DesignSpec designSpec;
    private float leftRightMarginOne;
    private float topBottomMarginOne;
    private int horizontalSize;
    private int horizontalColor;

    public HealthDetailLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.designSpec = ThemeManager.getStyle(context);
        leftRightMarginOne = designSpec.getMargin().getLeftRightOne();
        topBottomMarginOne = designSpec.getMargin().getTopBottomOne();
        horizontalSize = (int) designSpec.getHorizontal().getHorizontalOneHeight();
        horizontalColor = designSpec.getPrimaryColors().getHorizontalOne();

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);

        list = list();
        workFine = workFine();

        addView(list);
        addView(workFine);
    }

    public ListView list() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.setMargins(ruler.getW(leftRightMarginOne), 0, ruler.getW(leftRightMarginOne), 0);

        ListView v = new ListView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setDivider(new ColorDrawable(horizontalColor));
        v.setDividerHeight(horizontalSize);
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
                getResources().getString(R.string.health_detail_great),
                getResources().getString(R.string.health_detail_work_fine)
        );

        return v;
    }

    public void showWorkFind() {
        workFine.showWorkFind(list);
    }
}
