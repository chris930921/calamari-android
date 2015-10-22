package com.cephmonitor.cephmonitor.layout.component.other;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.container.CircleBorderContainer;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.TopBorderTextViewItem;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 2015/8/3.
 */
public class NavigationMenu extends RelativeLayout {
    private WH ruler;
    private TextView selectItem;

    public ListView optionGroup;
    public BaseAdapter optionAdapter;
    public RelativeLayout title;
    public CircleBorderContainer circleBorderContainer;
    public ImageView titleIcon;
    public RelativeLayout verticalCenterContainer;
    public TextView titleLineOne;
    public TextView titleLineTwo;

    private DesignSpec designSpec;
    private TextViewStyle itemStyle;
    private int textViewHorizonPadding;
    private int listViewVerticalPadding;
    private int titleIconSize;
    private TextViewStyle titleTextStyle;
    private int menuDividerColor;
    private int menuDividerWidth;

    private int itemTextColor;
    private int itemHeight;
    private int titleHeight;

    private ArrayList<TextView> itemViewGroup;
    private HashMap<Integer, TextView> itemViewMap;

    public static final int[] optionNameResourceGroup = {
            R.string.main_activity_fragment_health, R.string.main_activity_fragment_health_detail, R.string.main_activity_fragment_osd_health,
            R.string.main_activity_fragment_mon_health, R.string.main_activity_fragment_pool_list, R.string.main_activity_fragment_host_health,
            R.string.main_activity_fragment_pg_status, R.string.main_activity_fragment_usage_status, R.string.main_activity_fragment_pool_iops,
            R.string.main_activity_fragment_notification, //FIXME R.string.main_activity_option_logs,
            R.string.main_activity_option_setting, R.string.main_activity_option_logout,
    };

    public NavigationMenu(Context context) {
        super(context);
        this.ruler = new WH(context);
        this.itemViewGroup = new ArrayList<>();
        this.itemViewMap = new HashMap<>();
        designSpec = ThemeManager.getStyle(context);
        itemStyle = new TextViewStyle(designSpec.getStyle().getBodyOne());
        titleTextStyle = new TextViewStyle(designSpec.getStyle().getBodyOne());
        textViewHorizonPadding = ruler.getW(designSpec.getMargin().getLeftRightOne());
        listViewVerticalPadding = ruler.getW(designSpec.getMargin().getTopBottomOne());
        titleIconSize = ruler.getW(designSpec.getIconSize().getDisplayOne());
        menuDividerColor = (int) designSpec.getPrimaryColors().getHorizontalOne();
        menuDividerWidth = (int) designSpec.getHorizontal().getHorizontalOneHeight();

        itemTextColor = Color.parseColor("#555555");
        itemHeight = ruler.getW(13);
        titleHeight = ruler.getW(26);

        title = title();
        optionGroup = optionGroup(title);
        optionAdapter = optionAdapter();
        circleBorderContainer = circleBorderContainer();
        titleIcon = titleIcon();
        verticalCenterContainer = verticalCenterContainer(circleBorderContainer);
        titleLineOne = titleLineOne();
        titleLineTwo = titleLineTwo(titleLineOne);

        addView(title);
        addView(optionGroup);
        title.addView(circleBorderContainer);
        title.addView(verticalCenterContainer);
        circleBorderContainer.addView(titleIcon);
        verticalCenterContainer.addView(titleLineOne);
        verticalCenterContainer.addView(titleLineTwo);

        optionGroup.setAdapter(optionAdapter);
    }

    protected CircleBorderContainer circleBorderContainer() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                titleIconSize,
                titleIconSize);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.leftMargin = textViewHorizonPadding;

        CircleBorderContainer v = new CircleBorderContainer(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    protected ImageView titleIcon() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);

        ImageView v = new ImageView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon033);

        return v;
    }

    protected RelativeLayout title() {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                titleHeight);

        RelativeLayout v = new RelativeLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(designSpec.getPrimaryColors().getPrimary());

        return v;
    }

    protected ListView optionGroup(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());


        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.parseColor("#d5d5d5")));
        states.addState(new int[]{android.R.attr.state_focused}, new ColorDrawable(Color.parseColor("#d5d5d5")));
        states.addState(new int[]{}, new ColorDrawable(Color.TRANSPARENT));

        ListView v = new ListView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        v.setSelector(states);
        v.setDivider(new ColorDrawable(Color.TRANSPARENT));
        v.setPadding(
                0, listViewVerticalPadding,
                0, listViewVerticalPadding);
        return v;
    }

    protected RelativeLayout verticalCenterContainer(View rightView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RIGHT_OF, rightView.getId());
        params.leftMargin = textViewHorizonPadding;
        params.rightMargin = textViewHorizonPadding;

        RelativeLayout v = new RelativeLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    protected TextView titleLineOne() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        titleTextStyle.style(v);
        v.setTextColor(Color.WHITE);

        return v;
    }

    protected TextView titleLineTwo(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        titleTextStyle.style(v);
        v.setTextColor(Color.WHITE);

        return v;
    }


    private BaseAdapter optionAdapter() {
        itemViewGroup = new ArrayList<>();
        for (int i = 0; i < optionNameResourceGroup.length; i++) {
            int resource = optionNameResourceGroup[i];
            TextView v = getOptionItem(resource == R.string.main_activity_option_setting);
            v.setText(resource);
            v.setTag(resource);
            itemViewGroup.add(v);
            itemViewMap.put(resource, v);
        }
        selectItem = itemViewGroup.get(0);
        selectItem.setBackgroundColor(Color.parseColor("#d5d5d5"));

        BaseAdapter v = new BaseAdapter() {
            @Override
            public int getCount() {
                return itemViewGroup.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return itemViewGroup.get(i);
            }
        };
        return v;
    }

    public void setOnItemClickListener(final AdapterView.OnItemClickListener event) {
        optionGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectItem != null) {
                    selectItem.setBackgroundColor(Color.TRANSPARENT);
                }
                selectItem = (TextView) view;
                selectItem.setBackgroundColor(Color.parseColor("#d5d5d5"));
                event.onItemClick(adapterView, view, i, l);
            }
        });
    }

    protected TopBorderTextViewItem getOptionItem(boolean enableTopBorder) {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                itemHeight);

        TopBorderTextViewItem v = new TopBorderTextViewItem(getContext());
        v.setLayoutParams(params);
        itemStyle.style(v);
        v.setTextColor(itemTextColor);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setPadding(textViewHorizonPadding, 0, textViewHorizonPadding, 0);
        v.setTopBorderColor(menuDividerColor);
        v.setTopBorderWidth(menuDividerWidth);
        v.setTopBorderEnable(enableTopBorder);

        return v;
    }

    public void setSelected(int resourceId) {
        if (selectItem != null) {
            selectItem.setBackgroundColor(Color.TRANSPARENT);
        }
        selectItem = itemViewMap.get(resourceId);
        selectItem.setBackgroundColor(Color.parseColor("#d5d5d5"));
    }
}
