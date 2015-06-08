package com.cephmonitor.cephmonitor.layout.component.card;

import android.content.Context;
import android.view.View;

/**
 * Created by User on 4/17/2015.
 */
public class HealthUsageCard extends HealthBaseCard {

    public HealthUsageCard(Context context) {
        super(context);
    }

    public void setCenterView(View v) {
        centerValueContainer.addView(v);
        centerCenterContainer.setVisibility(GONE);
    }

    public void setRightTextColor(int color) {
        rightValueText.setTextColor(color);
    }
}