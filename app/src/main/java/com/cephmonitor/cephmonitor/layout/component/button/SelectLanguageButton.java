package com.cephmonitor.cephmonitor.layout.component.button;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.container.BorderContainer;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.ceph.constant.SettingConstant;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class SelectLanguageButton extends BorderContainer {
    public WH ruler;

    public ImageView flag;
    public TextView languageName;
    public ImageView dropArrow;

    private DesignSpec designSpec;
    private TextViewStyle textStyle;

    public SelectLanguageButton(Context context) {
        super(context);
        ruler = new WH(getContext());
        designSpec = ThemeManager.getStyle(getContext());
        textStyle = new TextViewStyle(designSpec.getStyle().getBodyOne());

        flag = flag();
        dropArrow = dropArrow();
        languageName = countryName(flag, dropArrow);

        addView(flag);
        addView(dropArrow);
        addView(languageName);
    }

    protected ImageView flag() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ruler.getW(designSpec.getIconSize().getBody())
        );
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_VERTICAL);
        params.leftMargin = ruler.getW(designSpec.getPadding().getLeftRightOne());

        ImageView v = new ImageView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    protected ImageView dropArrow() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ruler.getW(designSpec.getIconSize().getBody())
        );
        params.addRule(ALIGN_PARENT_RIGHT);
        params.addRule(CENTER_VERTICAL);

        ImageView v = new ImageView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon038);

        return v;
    }

    protected TextView countryName(View leftView, View rightView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RIGHT_OF, leftView.getId());
        params.addRule(LEFT_OF, rightView.getId());
        params.addRule(CENTER_VERTICAL);
        params.leftMargin = ruler.getW(designSpec.getPadding().getLeftRightOne());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        textStyle.style(v);

        return v;
    }

    public void setLanguage(int id){
        languageName.setText(SettingConstant.getLanguageResource(id));
        flag.setImageResource(SettingConstant.getLanguageFlag(id));
    }

}
