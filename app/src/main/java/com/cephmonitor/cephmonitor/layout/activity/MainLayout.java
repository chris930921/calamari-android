package com.cephmonitor.cephmonitor.layout.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.container.DrawerRelativeLayout;
import com.cephmonitor.cephmonitor.layout.component.other.NavigationMenu;
import com.cephmonitor.cephmonitor.layout.component.tab.OnTabChangeListener;
import com.cephmonitor.cephmonitor.layout.component.tab.SimpleTabView;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class MainLayout extends DrawerRelativeLayout {
    public static final int CONTAINER_ID = MainLayout.class.hashCode();

    private OnClickListener backClickEvent;

    public NavigationMenu navigationMenu;
    public FrameLayout drawerDragBar;
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
    public ImageView navigationButton;
    public View realBackButton;

    public TextView title;
    public FrameLayout fragment;

    private Context context;
    private WH ruler;
    private DesignSpec designSpec;
    private int backgroundOne;

    public MainLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        designSpec = ThemeManager.getStyle(context);
        backgroundOne = designSpec.getPrimaryColors().getBackgroundOne();

        setId(RandomId.get());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);
        setScrimColor(Color.parseColor("#80000000"));

        navigationMenu = navigationMenu();
        topBar = topBar();
        back = back();
        navigationButton = navigationButton();
        realBackButton = realBackButton();
        title = title(back);
        bottomBar = bottomBar();
        bottomBarLine = bottomBarLine();
        bottomContainer = bottomContainer(bottomBarLine);
        health = health();
        usage = usage();
        performance = performance();
        performance = performance();
        more = more();
        tabGroup = tabGroup(topBar);
        fragment = fragment(tabGroup, bottomBar);
        drawerDragBar = drawerDragBar();

        setLeftSideView(navigationMenu);
        addView(topBar);
        topBar.addView(back);
        topBar.addView(navigationButton);
        topBar.addView(realBackButton);
        topBar.addView(title);
        addView(bottomBar = bottomBar());
        bottomBar.addView(bottomBarLine);
        bottomBar.addView(bottomContainer);
        bottomContainer.addView(health);
        bottomContainer.addView(divider());
        bottomContainer.addView(usage);
        bottomContainer.addView(divider());
        bottomContainer.addView(performance);
        bottomContainer.addView(divider());
        bottomContainer.addView(more);
        addView(tabGroup);
        addView(fragment);
        addView(drawerDragBar);
    }

    private FrameLayout drawerDragBar() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                40,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        FrameLayout v = new FrameLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(Color.TRANSPARENT);

        return v;
    }

    private NavigationMenu navigationMenu() {
        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(
                ruler.getW(80),
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;

        NavigationMenu v = new NavigationMenu(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setVisibility(INVISIBLE);
        v.setBackgroundColor(backgroundOne);

        return v;
    }

    private RelativeLayout topBar() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(13));
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        v.setBackgroundColor(designSpec.getPrimaryColors().getPrimary());

        return v;
    }

    private ImageView back() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ruler.getW(designSpec.getIconSize().getTitle()),
                ruler.getW(designSpec.getIconSize().getTitle())
        );
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.leftMargin = ruler.getW(3);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon021);
        v.setVisibility(INVISIBLE);

        return v;
    }

    private ImageView navigationButton() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ruler.getW(designSpec.getIconSize().getTitle()),
                ruler.getW(designSpec.getIconSize().getTitle())
        );
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.leftMargin = ruler.getW(3);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon032);
        v.setVisibility(INVISIBLE);

        return v;
    }

    private View realBackButton() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ruler.getW(20),
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private TextView title(View leftView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.RIGHT_OF, leftView.getId());
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
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                ruler.getW(13));
        params.addRule(RelativeLayout.BELOW, topView.getId());

        SimpleTabView v = new SimpleTabView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(designSpec.getStyle().getBodyTwo().getSize());
        v.setBackgroundColor(designSpec.getPrimaryColors().getSecondary());
        v.setColor(Color.WHITE, Color.parseColor("#d9d9d9"));

        return v;
    }

    private RelativeLayout bottomBar() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(12.04));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        v.setBackgroundColor(Color.WHITE);

        return v;
    }

    private LinearLayout bottomContainer(View relativeView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, relativeView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

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
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(0.36));
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._D9D9D9);

        return v;
    }

    private FrameLayout fragment(View topView, View bottomView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());
        params.addRule(RelativeLayout.ABOVE, bottomView.getId());

        FrameLayout v = new FrameLayout(context);
        v.setId(CONTAINER_ID);
        v.setLayoutParams(params);
        v.setBackgroundColor(backgroundOne);

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
        realBackButton.setOnClickListener(event);
        backClickEvent = event;
    }

    public void showNavigation() {
        navigationButton.setVisibility(VISIBLE);
        realBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNavigationStatus();
            }
        });
    }

    public void hideAllComponent() {
        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        back.setVisibility(INVISIBLE);
        navigationButton.setVisibility(INVISIBLE);
        realBackButton.setOnClickListener(null);
        backClickEvent = null;
        bottomBar.setVisibility(GONE);
        tabGroup.setVisibility(GONE);
        tabGroup.clear();
    }

    public boolean isBackListener() {
        return backClickEvent != null;
    }

    public void executeBackListener() {
        backClickEvent.onClick(realBackButton);
    }

    public void showTab() {
        tabGroup.setVisibility(VISIBLE);
    }

    public void addTab(String name, Object tag, OnTabChangeListener listener) {
        tabGroup.add(name, tag, listener);
    }

    public void setNavigationTitleText(String lineOne, String lineTwo) {
        navigationMenu.titleLineOne.setText(lineOne);
        navigationMenu.titleLineTwo.setText(lineTwo);
    }

    public void setSelected(int resourceId) {
        navigationMenu.setSelected(resourceId);
    }

}
