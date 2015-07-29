package com.cephmonitor.cephmonitor.layout.component.list;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class DefaultListLayout extends FractionAbleRelativeLayout {
    private WH ruler;

    public ListView list;

    private DesignSpec designSpec;
    private int horizontalOneColor;
    private float horizontalOneHeight;
    private float leftRightMarginOne;

    public DefaultListLayout(Context context) {
        super(context);
        this.ruler = new WH(context);
        this.designSpec = ThemeManager.getStyle(context);
        horizontalOneColor = designSpec.getPrimaryColors().getHorizontalOne();
        horizontalOneHeight = designSpec.getHorizontal().getHorizontalOneHeight();
        leftRightMarginOne = designSpec.getMargin().getLeftRightOne();

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);

        addView(list = list());
    }

    public ListView list() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.setMargins(ruler.getW(leftRightMarginOne), 0, ruler.getW(leftRightMarginOne), 0);

        ListView v = new ListView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.addFooterView(fillView());
        v.setDivider(new ColorDrawable(horizontalOneColor));
        v.setDividerHeight((int) horizontalOneHeight);
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
