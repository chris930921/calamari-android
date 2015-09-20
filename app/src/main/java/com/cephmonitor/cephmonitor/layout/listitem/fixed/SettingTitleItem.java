package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;

public class SettingTitleItem extends RelativeLayout {
    public TextView title;
    public View topView;
    public View bottomView;

    public SettingTitleItem(Context context) {
        super(context);
        topView = topFillView();
        title = title(topView);
        bottomView = bottomFillView(title);

        addView(topView);
        addView(title);
        addView(bottomView);
    }

    private View topFillView() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public TextView title(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(getContext());
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View bottomFillView(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public void setVerticalPadding(int top, int bottom) {
        LayoutParams param;

        param = (LayoutParams) topView.getLayoutParams();
        param.height = top;
        topView.setLayoutParams(param);

        param = (LayoutParams) bottomView.getLayoutParams();
        param.height = bottom;
        bottomView.setLayoutParams(param);
    }

    public void setTextStyle(TextViewStyle style) {
        style.style(title);
    }

    public void setText(int resource) {
        title.setText(resource);
    }


}
