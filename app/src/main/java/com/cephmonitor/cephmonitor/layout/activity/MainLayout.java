package com.cephmonitor.cephmonitor.layout.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class MainLayout extends RelativeLayout {
    public static final int CONTAINER_ID = RandomId.get();
    public RelativeLayout bottomBar;
    public LinearLayout bottomContainer;
    public Button health;
    public Button usage;
    public Button performance;
    public Button more;
    public View bottomBarLine;

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

        addView(title = title());

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

        addView(fragment = fragment(title, bottomBar));
    }

    private TextView title() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(12.04));
        params.addRule(ALIGN_PARENT_TOP);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(30));
        v.setGravity(Gravity.CENTER);
        v.setTypeface(null, Typeface.BOLD);
        v.setBackgroundColor(Color.parseColor("#e63427"));
        v.setTextColor(Color.parseColor("#ffffff"));

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
        v.setBackgroundColor(Color.parseColor("#D9D9D9"));

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
}
