package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;


public class HealthDetailItem extends RelativeLayout {
    private Context context;
    private WH ruler;

    public ImageView leftImage;
    public TextView rightText;

    private DesignSpec designSpec;
    private TextViewStyle bodyOne;
    private float bodyIconSize;
    private float topBottomMarginOne;
    private float leftRightPaddingOne;

    public HealthDetailItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.designSpec = ThemeManager.getStyle(context);
        bodyOne = new TextViewStyle(designSpec.getStyle().getBodyOne());
        bodyIconSize = designSpec.getIconSize().getBody();
        topBottomMarginOne = designSpec.getMargin().getTopBottomOne();
        leftRightPaddingOne = designSpec.getPadding().getLeftRightOne();

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        addView(leftImage = leftImage());
        addView(rightText = rightText(leftImage));
    }

    public ImageView leftImage() {
        LayoutParams params = new LayoutParams(ruler.getW(bodyIconSize), ruler.getW(bodyIconSize));
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_TOP);
        params.topMargin = ruler.getW(topBottomMarginOne);
        params.bottomMargin = ruler.getW(topBottomMarginOne);
        params.rightMargin = ruler.getW(leftRightPaddingOne);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public TextView rightText(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_TOP);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(RIGHT_OF, leftView.getId());

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.TOP);
        v.setPadding(0, ruler.getW(topBottomMarginOne), 0, ruler.getW(topBottomMarginOne));
        bodyOne.style(v);

        return v;
    }

    public void setItemValue(int imageResource, String content) {
        leftImage.setImageResource(imageResource);
        rightText.setText(content);
    }


}
