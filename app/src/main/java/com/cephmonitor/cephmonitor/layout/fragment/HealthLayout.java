package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.card.HealthBaseCard;
import com.cephmonitor.cephmonitor.layout.component.card.HealthUsageCard;
import com.cephmonitor.cephmonitor.layout.component.progress.UsageCardProgress;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class HealthLayout extends RelativeLayout {
    public ScrollView cardContainer;
    public LinearLayout cardList;
    public HealthBaseCard healthCard;
    public HealthBaseCard osdCard;
    public HealthBaseCard monCard;
    public HealthBaseCard poolsCard;
    public HealthBaseCard hostsCard;
    public HealthBaseCard pgStatusCard;
    public HealthUsageCard usageCard;
    public UsageCardProgress usageCardProgress;

    private Context context;
    private WH ruler;

    public HealthLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        setId(RandomId.get());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        addView(cardContainer = cardContainer());
        cardContainer.addView(cardList = cardList());
        cardList.addView(healthCard = healthCard());
        cardList.addView(cardDivider());
        cardList.addView(osdCard = osdCard());
        cardList.addView(cardDivider());
        cardList.addView(monCard = monCard());
        cardList.addView(cardDivider());
        cardList.addView(poolsCard = poolsCard());
        cardList.addView(cardDivider());
        cardList.addView(hostsCard = hostsCard());
        cardList.addView(cardDivider());
        cardList.addView(pgStatusCard = pgStatusCard());
        cardList.addView(cardDivider());
        cardList.addView(usageCard = usageCard());

        usageCard.setCenterView(usageCardProgress = usageCardProgress());
    }

    private ScrollView cardContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(ALIGN_PARENT_TOP);

        ScrollView v = new ScrollView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private LinearLayout cardList() {
        ScrollView.LayoutParams params = new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        LinearLayout v = new LinearLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.VERTICAL);
        v.setGravity(Gravity.CENTER_HORIZONTAL);
        v.setPadding(ruler.getW(1.42), ruler.getW(1.42), ruler.getW(1.42), ruler.getW(1.42));

        return v;
    }

    private HealthBaseCard healthCard() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(46.98));
        params.weight = 1;

        HealthBaseCard v = new HealthBaseCard(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setIcon(R.drawable.icon013);
        v.setArrow(R.drawable.icon014);
        v.setTitle(context.getResources().getString(R.string.health_card_health));
        v.setLeftText(context.getResources().getString(R.string.health_card_warnings));
        v.setRightText(context.getResources().getString(R.string.health_card_errors));
        v.setCenterText(context.getResources().getString(R.string.health_card_ago));
        v.setCenterValueText("OK");
        v.setCompareMode(false);
        v.setChangeCenterValueColor(true);
        v.setValue(0, 0);

        return v;
    }

    private HealthBaseCard osdCard() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(46.98));
        params.weight = 1;

        HealthBaseCard v = new HealthBaseCard(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setIcon(R.drawable.icon015);
        v.setArrow(R.drawable.icon014);
        v.setTitle(context.getResources().getString(R.string.health_card_osd));
        v.setLeftText(context.getResources().getString(R.string.health_card_warnings));
        v.setRightText(context.getResources().getString(R.string.health_card_errors));
        v.setCenterText(context.getResources().getString(R.string.health_card_in_and_up));
        v.setCenterValueText("0 / 0");
        v.setValue(0, 0);

        return v;
    }

    private HealthBaseCard monCard() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(46.98));
        params.weight = 1;

        HealthBaseCard v = new HealthBaseCard(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setIcon(R.drawable.icon016);
        v.setArrow(R.drawable.icon014);
        v.setTitle(context.getResources().getString(R.string.health_card_mon));
        v.setLeftText(context.getResources().getString(R.string.health_card_warnings));
        v.setRightText(context.getResources().getString(R.string.health_card_errors));
        v.setCenterText(context.getResources().getString(R.string.health_card_quorom));

        v.setValue(0, 0);
        v.setCenterValueText("0 / 0");

        return v;
    }

    private HealthBaseCard poolsCard() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(46.98));
        params.weight = 1;

        HealthBaseCard v = new HealthBaseCard(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setIcon(R.drawable.icon017);
        v.setArrow(R.drawable.icon014);
        v.setTitle(context.getResources().getString(R.string.health_card_pools));
        v.setLeftText(context.getResources().getString(R.string.health_card_warnings));
        v.setRightText(context.getResources().getString(R.string.health_card_errors));
        v.setCenterText(context.getResources().getString(R.string.health_card_active));
        v.setCenterValueText("0");
        v.setValue(0, 0);

        return v;
    }

    private HealthBaseCard hostsCard() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(46.98));
        params.weight = 1;

        HealthBaseCard v = new HealthBaseCard(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setIcon(R.drawable.icon018);
        v.setArrow(R.drawable.icon014);
        v.setTitle(context.getResources().getString(R.string.health_card_hosts));
        v.setLeftText(context.getResources().getString(R.string.health_card_mon));
        v.setRightText(context.getResources().getString(R.string.health_card_osd));
        v.setCenterText(context.getResources().getString(R.string.health_card_reporting));
        v.setCompareMode(false);
        v.setChangeTwoValueColor(false, false);

        v.setValue(0, 0);
        v.setCenterValueText("0");

        return v;
    }

    private HealthBaseCard pgStatusCard() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(46.98));
        params.weight = 1;

        HealthBaseCard v = new HealthBaseCard(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setIcon(R.drawable.icon020);
        v.setArrow(R.drawable.icon014);
        v.setTitle(context.getResources().getString(R.string.health_card_pg));
        v.setLeftText(context.getResources().getString(R.string.health_card_Working));
        v.setRightText(context.getResources().getString(R.string.health_card_Dirty));
        v.setCenterText(context.getResources().getString(R.string.health_card_clean));

        v.setCenterValueText("0");
        v.setValue(0, 0);

        return v;
    }

    private HealthUsageCard usageCard() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(24.98) + ruler.getW(60));
        params.weight = 1;

        HealthUsageCard v = new HealthUsageCard(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setIcon(R.drawable.icon028);
        v.setTitle(context.getResources().getString(R.string.health_card_usage));
        v.setLeftText(context.getResources().getString(R.string.health_card_used));
        v.setRightText(context.getResources().getString(R.string.health_card_available));
        v.setRightTextColor(ColorTable._8DC41F);
        v.setCompareMode(false);

        v.setValue(0, 0);
        v.setValue(60, 40);

        return v;
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
        v.setPercent(85);

        return v;
    }

    private View cardDivider() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(3));
        params.weight = 1;

        View v = new LinearLayout(context);
        v.setLayoutParams(params);
        return v;
    }
}
