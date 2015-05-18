package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OsdHealthBoxes;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;
import com.resourcelibrary.model.view.button.RoundStrokeFillButton;
import com.resourcelibrary.model.view.button.RoundStrokeFillImage;

public class OSDHealthLayout extends RelativeLayout {
    private Context context;
    private WH ruler;

    public ScrollView scrollContainer;
    public RelativeLayout containerInnerScroll;
    public RelativeLayout workFineContainer;
    public RelativeLayout workFineCenterContainer;
    public View workFineImage;
    public TextView workFineLineOne;
    public TextView workFineLineTwo;

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

        workFineContainer.addView(workFineCenterContainer = workFineCenterContainer());
        workFineCenterContainer.addView(workFineImage = workFineImage());
        workFineCenterContainer.addView(workFineLineOne = workFineLineOne(workFineImage));
        workFineCenterContainer.addView(workFineLineTwo = workFineLineTwo(workFineLineOne));

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
        v.setFillColor(Color.parseColor("#f3f3f3"));
        v.setStrokeColor(Color.parseColor("#b7b7b7"));
        v.setClickColor(Color.parseColor("#d9d9d9"));

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
        v.setTextColor(Color.parseColor("#666666"));
        v.setFillColor(Color.parseColor("#f3f3f3"));
        v.setStrokeColor(Color.parseColor("#b7b7b7"));
        v.setClickColor(Color.parseColor("#d9d9d9"));

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
        v.setFillColor(Color.parseColor("#f3f3f3"));
        v.setStrokeColor(Color.parseColor("#b7b7b7"));
        v.setClickColor(Color.parseColor("#d9d9d9"));

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
        v.setVisibility(INVISIBLE);

        return v;
    }

    private RelativeLayout workFineCenterContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setLayoutParams(params);

        return v;
    }

    public View workFineImage() {
        LayoutParams params = new LayoutParams(ruler.getW(30), ruler.getW(30));
        params.addRule(CENTER_HORIZONTAL);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundResource(R.drawable.icon026);

        return v;
    }

    public TextView workFineLineOne(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());
        params.setMargins(0, ruler.getH(5), 0, 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setText(R.string.osd_health_great);
        v.setTextColor(Color.parseColor("#666666"));
        v.setTextSize(14);

        return v;
    }

    public TextView workFineLineTwo(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setText(R.string.osd_health_work_fine);
        v.setTextColor(Color.parseColor("#666666"));
        v.setTextSize(14);

        return v;
    }

    private OsdHealthBoxes boxesContainer() {
        ScrollView.LayoutParams params = new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        OsdHealthBoxes v = new OsdHealthBoxes(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public void showWorkFind() {
        workFineContainer.setVisibility(VISIBLE);
        boxesContainer.setVisibility(INVISIBLE);
    }

    public void hideWorkFind() {
        boxesContainer.setVisibility(VISIBLE);
        workFineContainer.setVisibility(INVISIBLE);
    }

    public void recoverButtons() {
        int color = Color.parseColor("#f3f3f3");
        leftButton.setFillColor(color);
        rightButton.setFillColor(color);
        centerButton.setFillColor(color);
    }
}
