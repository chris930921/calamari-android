package com.cephmonitor.cephmonitor.layout.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.tab.OnTabChangeListener;
import com.cephmonitor.cephmonitor.layout.component.tab.SimpleTabView;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class MainLayout extends RelativeLayout {
    public static final int CONTAINER_ID = MainLayout.class.hashCode();

    private OnClickListener backClickEvent;

    public RelativeLayout bottomBar;
    public RelativeLayout topBar;
    public SimpleTabView tabGroup;
    public LinearLayout bottomContainer;
    public Button health;
    public Button usage;
    public Button performance;
    public Button more;
    public View bottomBarLine;
    public ImageView back;

    public TextView title;
    public FrameLayout fragment;

    private Context context;
    private WH ruler;
    private DesignSpec designSpec;

    public MainLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        designSpec = ThemeManager.getStyle(context);

        setId(RandomId.get());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        addView(topBar = topBar());
        topBar.addView(back = back());
        topBar.addView(title = title(back));

        addView(bottomBar = bottomBar());
        bottomBar.addView(bottomBarLine = bottomBarLine());
        bottomBar.addView(bottomContainer = bottomContainer(bottomBarLine));
        bottomContainer.addView(health = health());
        bottomContainer.addView(divider());
        bottomContainer.addView(usage = usage());
        bottomContainer.addView(divider());
        bottomContainer.addView(performance = performance());
        bottomContainer.addView(divider());
        bottomContainer.addView(more = more());

        addView(tabGroup = tabGroup(topBar));
        addView(fragment = fragment(tabGroup, bottomBar));
    }

    private RelativeLayout topBar() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(13));
        params.addRule(ALIGN_PARENT_TOP);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        v.setBackgroundColor(designSpec.getPrimaryColors().getPrimary());

        return v;
    }

    private ImageView back() {
        LayoutParams params = new LayoutParams(
                ruler.getW(designSpec.getIconSize().getTitle()),
                ruler.getW(designSpec.getIconSize().getTitle())
        );
        params.addRule(CENTER_VERTICAL);
        params.addRule(ALIGN_PARENT_LEFT);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon021);
        v.setVisibility(INVISIBLE);

        return v;
    }

    private TextView title(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RIGHT_OF, leftView.getId());
        params.setMargins(0, 0, ruler.getW(designSpec.getIconSize().getTitle()), 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(designSpec.getStyle().getTitle().getSize());
        v.setTypeface(null, designSpec.getStyle().getTitle().getTypeface());
        v.setTextColor(designSpec.getStyle().getTitle().getColor());

        v.setGravity(Gravity.CENTER);

        return v;
    }

    private SimpleTabView tabGroup(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                ruler.getW(13));
        params.addRule(BELOW, topView.getId());

        SimpleTabView v = new SimpleTabView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(designSpec.getStyle().getBodyTwo().getSize());
        v.setBackgroundColor(designSpec.getPrimaryColors().getSecondary());
        v.setColor(Color.WHITE, Color.parseColor("#d9d9d9"));

        return v;
    }

    private RelativeLayout bottomBar() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(12.04));
        params.addRule(ALIGN_PARENT_BOTTOM);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        v.setBackgroundColor(Color.WHITE);

        return v;
    }

    private LinearLayout bottomContainer(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(BELOW, relativeView.getId());
        params.addRule(CENTER_HORIZONTAL);

        LinearLayout v = new LinearLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setGravity(Gravity.CENTER);
        return v;
    }

    private View divider() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ruler.getW(9.81), LayoutParams.MATCH_PARENT);
        params.weight = 1;

        View v = new LinearLayout(context);
        v.setLayoutParams(params);
        return v;
    }

    private Button health() {
        Button v = addFunctionButton();
        v.setBackgroundResource(R.drawable.icon05);

        return v;
    }

    private Button usage() {
        Button v = addFunctionButton();
        v.setBackgroundResource(R.drawable.icon07);

        return v;
    }

    private Button performance() {
        Button v = addFunctionButton();
        v.setBackgroundResource(R.drawable.icon09);

        return v;
    }

    private Button more() {
        Button v = addFunctionButton();
        v.setBackgroundResource(R.drawable.health_more_click);

        return v;
    }

    private Button addFunctionButton() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ruler.getW(16.32), ruler.getW(16.32));
        params.weight = 1;

        Button v = new Button(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        return v;
    }

    private View bottomBarLine() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(0.36));
        params.addRule(ALIGN_PARENT_TOP);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._D9D9D9);

        return v;
    }

    private FrameLayout fragment(View topView, View bottomView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(BELOW, topView.getId());
        params.addRule(ABOVE, bottomView.getId());

        FrameLayout v = new FrameLayout(context);
        v.setId(CONTAINER_ID);
        v.setLayoutParams(params);

        return v;
    }

    public void recoverAllButton() {
        health.setBackgroundResource(R.drawable.icon05);
        usage.setBackgroundResource(R.drawable.icon07);
        performance.setBackgroundResource(R.drawable.icon09);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void showBack(OnClickListener event) {
        back.setVisibility(VISIBLE);
        back.setOnClickListener(event);
        backClickEvent = event;
    }

    public void hideAllComponent() {
        back.setVisibility(INVISIBLE);
        back.setOnClickListener(null);
        backClickEvent = null;
        bottomBar.setVisibility(GONE);
        tabGroup.setVisibility(GONE);
        tabGroup.clear();
    }

    public boolean isBackListener() {
        return backClickEvent != null;
    }

    public void executeBackListener() {
        backClickEvent.onClick(back);
    }

    public void showTab() {
        tabGroup.setVisibility(VISIBLE);
    }

    public void addTab(String name, Object tag, OnTabChangeListener listener) {
        tabGroup.add(name, tag, listener);
    }
}
