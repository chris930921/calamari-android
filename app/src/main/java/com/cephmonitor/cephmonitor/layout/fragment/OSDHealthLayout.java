package com.cephmonitor.cephmonitor.layout.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.button.ClickAbleTabButton;
import com.cephmonitor.cephmonitor.layout.component.button.ClickAbleTabImage;
import com.cephmonitor.cephmonitor.layout.component.container.FractionAbleRelativeLayout;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OsdHealthBoxes;
import com.cephmonitor.cephmonitor.layout.component.other.WorkFindView;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class OSDHealthLayout extends FractionAbleRelativeLayout {
    private Context context;
    private WH ruler;

    public ScrollView scrollContainer;
    public RelativeLayout containerInnerScroll;
    public RelativeLayout workFineContainer;
    public WorkFindView workFine;

    public OsdHealthBoxes boxesContainer;
    public LinearLayout buttonContainer;
    public ClickAbleTabButton leftButton;
    public ClickAbleTabImage centerButton;
    public ClickAbleTabImage rightButton;

    public DesignSpec designSpec;

    public OSDHealthLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.designSpec = ThemeManager.getStyle(getContext());

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);

        addView(buttonContainer = buttonContainer());
        addView(scrollContainer = scrollContainer(buttonContainer));
        addView(workFineContainer = workFineContainer(scrollContainer));
        buttonContainer.addView(leftButton = leftButton());
        buttonContainer.addView(centerButton = centerButton());
        buttonContainer.addView(rightButton = rightButton());
        workFineContainer.addView(workFine = workFine());

        scrollContainer.addView(containerInnerScroll = containerInnerScroll());
        containerInnerScroll.addView(boxesContainer = boxesContainer());
    }

    private LinearLayout buttonContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);

        LinearLayout v = new LinearLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setWeightSum(3);

        return v;
    }


    private ClickAbleTabButton leftButton() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ruler.getH(7.5));
        params.weight = 1;

        ClickAbleTabButton v = new ClickAbleTabButton(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setText(R.string.osd_health_all);
        v.setFillColor(designSpec.getPrimaryColors().getBackgroundTwo());
        v.setStrokeColor(designSpec.getPrimaryColors().getHorizontalTwo());
        v.setStrokeWidth((int) designSpec.getHorizontal().getHorizontalTwoHeight());
        v.setClickColor(designSpec.getPrimaryColors().getPrimary());
        v.setOriginTextColor(ColorTable._999999);
        v.recover();

        return v;
    }

    private ClickAbleTabImage centerButton() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ruler.getH(7.5));
        params.weight = 1;

        ClickAbleTabImage v = new ClickAbleTabImage(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(ruler.getW(10), ruler.getH(1.5), ruler.getW(10), ruler.getH(1.5));
        v.setFillColor(designSpec.getPrimaryColors().getBackgroundTwo());
        v.setStrokeColor(designSpec.getPrimaryColors().getHorizontalTwo());
        v.setStrokeWidth((int) designSpec.getHorizontal().getHorizontalTwoHeight());
        v.setClickColor(designSpec.getPrimaryColors().getPrimary(), R.drawable.icon043);
        v.setOriginImageResource(R.drawable.icon024);
        v.recover();

        return v;
    }

    private ClickAbleTabImage rightButton() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ruler.getH(7.5));
        params.weight = 1;

        ClickAbleTabImage v = new ClickAbleTabImage(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(ruler.getW(10), ruler.getH(1.5), ruler.getW(10), ruler.getH(1.5));
        v.setFillColor(designSpec.getPrimaryColors().getBackgroundTwo());
        v.setStrokeColor(designSpec.getPrimaryColors().getHorizontalTwo());
        v.setStrokeWidth((int) designSpec.getHorizontal().getHorizontalTwoHeight());
        v.setClickColor(designSpec.getPrimaryColors().getPrimary(), R.drawable.icon042);
        v.setDividerLine(false);
        v.setOriginImageResource(R.drawable.icon025);
        v.recover();

        return v;
    }

    private ScrollView scrollContainer(View bottomView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(ABOVE, bottomView.getId());
        params.addRule(ALIGN_PARENT_TOP);
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

    private RelativeLayout workFineContainer(View backgroundView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(ALIGN_TOP, backgroundView.getId());
        params.addRule(ALIGN_BOTTOM, backgroundView.getId());

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
        leftButton.recover();
        rightButton.recover();
        centerButton.recover();
    }
}
