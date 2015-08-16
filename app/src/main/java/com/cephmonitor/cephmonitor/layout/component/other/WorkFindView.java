package com.cephmonitor.cephmonitor.layout.component.other;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by User on 5/26/2015.
 */
public class WorkFindView extends RelativeLayout {
    private Context context;
    private WH ruler;

    private int messageColor;

    public ImageView workFineImage;
    public TextView workFineLineOne;
    public TextView workFineLineTwo;

    private DesignSpec designSpec;
    private float messageIconSize;
    private float leftRightPaddingOne;
    private float topBottomPaddingOne;
    private TextViewStyle message;

    public WorkFindView(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.designSpec = ThemeManager.getStyle(context);
        message = new TextViewStyle(designSpec.getStyle().getSubhead());
        messageIconSize = designSpec.getIconSize().getMessage();
        leftRightPaddingOne = designSpec.getPadding().getLeftRightOne();
        topBottomPaddingOne = designSpec.getPadding().getTopBottomOne();

        messageColor = Color.parseColor("#999999");

        workFineImage = workFineImage();
        workFineLineOne = workFineLineOne(workFineImage);
        workFineLineTwo = workFineLineTwo(workFineLineOne);

        addView(workFineImage);
        addView(workFineLineOne);
        addView(workFineLineTwo);
    }

    private ImageView workFineImage() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ruler.getW(messageIconSize), ruler.getW(messageIconSize));
        params.addRule(CENTER_HORIZONTAL);
        params.leftMargin = ruler.getW(leftRightPaddingOne);
        params.rightMargin = ruler.getW(leftRightPaddingOne);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundResource(R.drawable.icon026);

        return v;
    }

    private TextView workFineLineOne(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());
        params.leftMargin = ruler.getW(leftRightPaddingOne);
        params.rightMargin = ruler.getW(leftRightPaddingOne);
        params.topMargin = ruler.getW(topBottomPaddingOne);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        message.style(v);
        v.setTextColor(messageColor);

        return v;
    }

    private TextView workFineLineTwo(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());
        params.leftMargin = ruler.getW(leftRightPaddingOne);
        params.rightMargin = ruler.getW(leftRightPaddingOne);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        message.style(v);
        v.setTextColor(messageColor);

        return v;
    }

    public void setText(String lineOne, String lineTwo) {
        workFineLineOne.setText(lineOne);
        workFineLineTwo.setText(lineTwo);
    }

    public void showWorkFind(View hideView) {
        hideView.setVisibility(GONE);
        setVisibility(VISIBLE);
    }

    public void hideWorkFind(View hideView) {
        hideView.setVisibility(VISIBLE);
        setVisibility(GONE);
    }
}
