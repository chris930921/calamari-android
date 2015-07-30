package com.cephmonitor.cephmonitor.layout.component.card;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.progress.UsageCardProgress;
import com.resourcelibrary.model.logic.ByteUnit;
import com.resourcelibrary.model.logic.RandomId;

/**
 * Created by User on 4/17/2015.
 */
public class HealthUsageCard extends HealthBaseCard {
    public UsageCardProgress usageCardProgress;

    public HealthUsageCard(Context context) {
        super(context);
        usageCardProgress = usageCardProgress();
        setCenterView(usageCardProgress);
    }

    public void setCenterView(View v) {
        centerValueContainer.addView(v);
        centerCenterContainer.setVisibility(GONE);
    }

    private UsageCardProgress usageCardProgress() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ruler.getW(50),
                ruler.getW(50)
        );
        params.addRule(CENTER_IN_PARENT);

        UsageCardProgress v = new UsageCardProgress(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setText(getResources().getString(R.string.health_card_used));

        return v;
    }

    public void setLongValue(double leftValue, double rightValue) {
        boolean isLeftEqualsZero = leftValue == 0;
        boolean isLeftJustByte = leftValue < ByteUnit.KB && !isLeftEqualsZero;
        int leftColor = (isLeftEqualsZero) ? ColorTable._999999 : ColorTable._F7B500;
        leftValue = (isLeftJustByte) ? ByteUnit.KB : leftValue;
        leftValueText.setTextColor(leftColor);
        leftValueText.setText(ByteUnit.change(leftValue));

        boolean isRightEqualsZero = rightValue == 0;
        boolean isRightJustByte = rightValue < ByteUnit.KB && !isRightEqualsZero;
        int rightColor = (isRightEqualsZero) ? ColorTable._999999 : ColorTable._8DC41F;
        rightValue = (isRightJustByte) ? ByteUnit.KB : rightValue;
        rightValueText.setTextColor(rightColor);
        rightValueText.setText(ByteUnit.change(rightValue));

        double percent = (rightValue != 0) ? (leftValue * 100) / rightValue : 0;
        percent = (percent < 1.0) ? Math.ceil(percent) : percent;
        usageCardProgress.setPercent((float) percent);
    }

    @Override
    public void setValue(int leftValue, int rightValue) {
    }

    public void setRightTextColor(int color) {
        rightValueText.setTextColor(color);
    }
}