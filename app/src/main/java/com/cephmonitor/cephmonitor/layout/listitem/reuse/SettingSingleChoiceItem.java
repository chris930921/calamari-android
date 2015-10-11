package com.cephmonitor.cephmonitor.layout.listitem.reuse;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.button.CircleRadioButton;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class SettingSingleChoiceItem extends RelativeLayout {
    private WH ruler;
    public TextView filedName;
    public CircleRadioButton filedValue;

    private DesignSpec designSpec;
    private TextViewStyle nameStyle;

    public SettingSingleChoiceItem(Context context) {
        super(context);
        ruler = new WH(getContext());
        designSpec = ThemeManager.getStyle(getContext());
        nameStyle = new TextViewStyle(designSpec.getStyle().getBodyTwo());

        setPadding(
                ruler.getW(designSpec.getPadding().getLeftRightOne()),
                ruler.getW(designSpec.getPadding().getTopBottomOne()),
                ruler.getW(designSpec.getPadding().getLeftRightOne()),
                ruler.getW(designSpec.getPadding().getTopBottomOne()));

        filedValue = filedValue();
        filedName = filedName(filedValue);

        addView(filedName);
        addView(filedValue);
    }

    protected TextView filedName(View rightView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(CENTER_VERTICAL);
        params.addRule(RelativeLayout.LEFT_OF, rightView.getId());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        nameStyle.style(v);

        return v;
    }

    protected CircleRadioButton filedValue() {
        LayoutParams params = new LayoutParams(
                ruler.getW(designSpec.getIconSize().getBody()),
                ruler.getW(designSpec.getIconSize().getBody())
        );
        params.addRule(CENTER_VERTICAL);
        params.addRule(ALIGN_PARENT_RIGHT);

        CircleRadioButton v = new CircleRadioButton(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setCircleColor(ColorTable._D9D9D9, designSpec.getPrimaryColors().getPrimary());
        v.setCircleStyle(3, 0.7F);

        return v;
    }

    public void setName(String content) {
        filedName.setText(content);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
