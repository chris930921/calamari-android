package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.listitem.reuse.SettingItem;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;

public class SettingProfileItem extends SettingItem {
    public TextView filedName;
    public TextView filedValue;

    private DesignSpec designSpec;
    private TextViewStyle nameStyle;
    private TextViewStyle valueStyle;

    public SettingProfileItem(Context context) {
        super(context);
        designSpec = ThemeManager.getStyle(getContext());
        nameStyle = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        valueStyle = new TextViewStyle(designSpec.getStyle().getBodyOne());

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

    protected TextView filedValue() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(CENTER_VERTICAL);
        params.addRule(ALIGN_PARENT_RIGHT);

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        valueStyle.style(v);

        return v;
    }

    public void setName(String content){
        filedName.setText(content);
    }

    public void setValue(String content){
        filedValue.setText(content);
    }
}
