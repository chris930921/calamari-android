package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OsdHealthBoxes;
import com.cephmonitor.cephmonitor.layout.component.other.WorkFindView;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;
import com.resourcelibrary.model.view.button.RoundStrokeFillButton;
import com.resourcelibrary.model.view.button.RoundStrokeFillImage;

public class OSDHealthLayout extends FractionAbleRelativeLayout {
    private Context context;
    private WH ruler;

    public ScrollView scrollContainer;
    public RelativeLayout containerInnerScroll;
    public RelativeLayout workFineContainer;
    public WorkFindView workFine;

    public OsdHealthBoxes boxesContainer;
    public RoundStrokeFillButton leftButton;
    public RoundStrokeFillImage centerButton;
    public RoundStrokeFillImage rightButton;

    public OSDHealthLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        addView(centerButton = centerButton());
        addView(leftButton = leftButton(centerButton));
        addView(rightButton = rightButton(centerButton));
        addView(scrollContainer = scrollContainer(centerButton));
        addView(workFineContainer = workFineContainer());

        workFineContainer.addView(workFine = workFine());

        scrollContainer.addView(containerInnerScroll = containerInnerScroll());
        containerInnerScroll.addView(boxesContainer = boxesContainer());
    }

    private RoundStrokeFillImage centerButton() {
        LayoutParams params = new LayoutParams(ruler.getW(30), ruler.getH(7.5));
        params.addRule(CENTER_HORIZONTAL);
        params.setMargins(0, ruler.getW(5), 0, 0);

        RoundStrokeFillImage v = new RoundStrokeFillImage(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon024);
        v.setPadding(ruler.getW(10), ruler.getH(1.5), ruler.getW(10), ruler.getH(1.5));
        v.setFillColor(ColorTable._F3F3F3);
        v.setStrokeColor(ColorTable._B7B7B7);
        v.setClickColor(ColorTable._D9D9D9);

        return v;
    }

    private RoundStrokeFillButton leftButton(View rightView) {
        LayoutParams params = new LayoutParams(ruler.getW(30), ruler.getH(7.5));
        params.addRule(LEFT_OF, rightView.getId());
        params.addRule(ALIGN_TOP, rightView.getId());
        params.setMargins(ruler.getW(5), 0, -3, 0);

        RoundStrokeFillButton v = new RoundStrokeFillButton(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setText(R.string.osd_health_all);
        v.setTextColor(ColorTable._666666);
        v.setFillColor(ColorTable._F3F3F3);
        v.setStrokeColor(ColorTable._B7B7B7);
        v.setClickColor(ColorTable._D9D9D9);

        return v;
    }

    private RoundStrokeFillImage rightButton(View leftView) {
        LayoutParams params = new LayoutParams(ruler.getW(30), ruler.getH(7.5));
        params.addRule(RIGHT_OF, leftView.getId());
        params.addRule(ALIGN_TOP, leftView.getId());
        params.setMargins(-3, 0, ruler.getW(5), 0);

        RoundStrokeFillImage v = new RoundStrokeFillImage(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon025);
        v.setPadding(ruler.getW(10), ruler.getH(1.5), ruler.getW(10), ruler.getH(1.5));
        v.setFillColor(ColorTable._F3F3F3);
        v.setStrokeColor(ColorTable._B7B7B7);
        v.setClickColor(ColorTable._D9D9D9);

        return v;
    }

    private ScrollView scrollContainer(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(BELOW, topView.getId());
        params.setMargins(ruler.getW(5), ruler.getH(5), ruler.getW(5), ruler.getH(5));

        ScrollView v = new ScrollView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private RelativeLayout containerInnerScroll() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setLayoutParams(params);

        return v;
    }

    private RelativeLayout workFineContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setLayoutParams(params);

        return v;
    }

    private OsdHealthBoxes boxesContainer() {
        ScrollView.LayoutParams params = new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        OsdHealthBoxes v = new OsdHealthBoxes(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private WorkFindView workFine() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        WorkFindView v = new WorkFindView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setVisibility(GONE);
        v.setText(
                getResources().getString(R.string.osd_health_great),
                getResources().getString(R.string.osd_health_work_fine)
        );

        return v;
    }

    public void showWorkFind() {
        workFine.showWorkFind(boxesContainer);
    }

    public void hideWorkFind() {
        workFine.hideWorkFind(boxesContainer);
    }

    public void recoverButtons() {
        int color = ColorTable._F3F3F3;
        leftButton.setFillColor(color);
        rightButton.setFillColor(color);
        centerButton.setFillColor(color);
    }
}
