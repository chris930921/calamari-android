package com.cephmonitor.cephmonitor.layout.component.card;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;

/**
 * Created by User on 4/17/2015.
 */
public class HealthPoolsCard extends HealthBaseCard {
    private DesignSpec designSpec;

    public RelativeLayout backgroundValueContainer;
    public RelativeLayout backgroundCenterContainer;
    public TextView backgroundValueText;
    public TextView backgroundText;

    public HealthPoolsCard(Context context) {
        super(context);
        designSpec = ThemeManager.getStyle(context);
        centerValueContainer.setVisibility(INVISIBLE);
        bottomContainer.setVisibility(INVISIBLE);
        twoValuesTopLine.setVisibility(INVISIBLE);

        backgroundValueContainer = centerValueContainer;
        backgroundCenterContainer = centerCenterContainer;
        backgroundValueText = centerValueText;
        backgroundText = centerText;

        centerValueContainer = coverValueContainer(centerValueContainer, bottomContainer);
        centerCenterContainer = centerCenterContainer();
        centerValueText = centerValueText();
        centerText = centerText(centerValueText);

        contentContainer.addView(centerValueContainer);
        centerValueContainer.addView(centerCenterContainer);
        centerCenterContainer.addView(centerValueText);
        centerCenterContainer.addView(centerText);
    }

    public RelativeLayout coverValueContainer(View topView, View bottomView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, topView.getId());
        params.addRule(ALIGN_BOTTOM, bottomView.getId());

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(
                0, ruler.getW(designSpec.getPadding().getTopBottomTwo()),
                0, ruler.getW(designSpec.getPadding().getTopBottomTwo()));

        return v;
    }
}