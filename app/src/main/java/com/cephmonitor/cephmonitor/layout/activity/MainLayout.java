package com.cephmonitor.cephmonitor.layout.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class MainLayout extends RelativeLayout {
    public static final int CONTAINER_ID = MainLayout.class.hashCode();
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

    public MainLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

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
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(12.04));
        params.addRule(ALIGN_PARENT_TOP);


        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        v.setBackgroundColor(ColorTable._E63427);

        return v;
    }

    private ImageView back() {
        LayoutParams params = new LayoutParams(ruler.getH(12.04), LayoutParams.MATCH_PARENT);
        params.addRule(ALIGN_PARENT_TOP);
        params.addRule(ALIGN_PARENT_LEFT);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(ruler.getW(5), ruler.getW(5), ruler.getW(5), ruler.getW(5));
        v.setImageResource(R.drawable.icon021);
        v.setVisibility(INVISIBLE);

        return v;
    }

    private TextView title(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RIGHT_OF, leftView.getId());
        params.setMargins(0, 0, ruler.getH(12.04), 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(30));
        v.setGravity(Gravity.CENTER);
        v.setTypeface(null, Typeface.BOLD);
        v.setTextColor(Color.WHITE);

        return v;
    }

    private SimpleTabView tabGroup(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        SimpleTabView v = new SimpleTabView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(18);
        v.setBackgroundColor(ColorTable._CD2626);

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
    }

    public void hideBack() {
        back.setVisibility(INVISIBLE);
        back.setOnClickListener(null);
    }

    public void hideAllComponent() {
        back.setVisibility(GONE);
        back.setOnClickListener(null);
        bottomBar.setVisibility(GONE);
        tabGroup.setVisibility(GONE);
        tabGroup.clear();
    }

    public void showTab() {
        tabGroup.setVisibility(VISIBLE);
    }

    public void addTab(String name, Object tag, OnTabChangeListener listener) {
        tabGroup.add(name, tag, listener);
    }
}
