package com.cephmonitor.cephmonitor.layout.component.container;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.resourcelibrary.model.logic.RandomId;

public abstract class DrawerRelativeLayout extends DrawerLayout {
    private RelativeLayout content;
    private View option;

    public DrawerRelativeLayout(Context context) {
        super(context);

        content = content();
        super.addView(content);
    }

    protected RelativeLayout content() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        RelativeLayout v = new RelativeLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    @Override
    public boolean isDrawerOpen(View drawer) {
        return super.isDrawerOpen(drawer);
    }

    public void changeNavigationStatus() {
        if (isDrawerOpen(option)) {
            closeDrawer(option);
        } else {
            openDrawer(option);
        }
    }

    public void setLeftSideView(View option) {
        this.option = option;
        super.addView(option);
    }

    @Override
    public void addView(@NonNull View child) {
        content.addView(child);
    }
}
