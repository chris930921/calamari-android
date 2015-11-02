package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.resourcelibrary.model.logic.RandomId;

import java.util.ArrayList;

/**
 * Created by chriske on 2015/11/1.
 */
public class SettingContentList extends ListView {
    private View headFillView;
    private View footFillView;

    public SettingContentList(Context context) {
        super(context);

        headFillView = fillView();
        footFillView = fillView();
        addHeaderView(headFillView);
        addFooterView(footFillView);
    }

    public void setDataOfAdapter(final ArrayList<View> contentViewGroup) {
        setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return contentViewGroup.size();
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return contentViewGroup.get(i);
            }
        });
    }

    public void setHeaderHeight(int height) {
        AbsListView.LayoutParams params = (AbsListView.LayoutParams) headFillView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = height;
        headFillView.setLayoutParams(params);
    }

    public void setFooterHeight(int height) {
        AbsListView.LayoutParams params = (AbsListView.LayoutParams) footFillView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = height;
        footFillView.setLayoutParams(params);
    }

    private View fillView() {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 10);
        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }
}
