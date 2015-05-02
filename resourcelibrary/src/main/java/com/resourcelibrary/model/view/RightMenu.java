package com.resourcelibrary.model.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public abstract class RightMenu extends RelativeLayout {
    public Activity activity;
    private FrameLayout closeMask;
    private RelativeLayout container;
    public boolean isOpen;

    public abstract int getOffset();

    private WH ruler;

    public RightMenu(Context context) {
        super(context);
        activity = (Activity) context;
        ruler = new WH(activity);
        setVisibility(GONE);


        closeMask = new FrameLayout(activity);
        closeMask.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        closeMask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                close(activity);
            }
        });
    }

    public void trigger() {
        if (isOpen) close(activity);
        else show(activity);
    }

    public void show(Activity activity) {
        isOpen = true;

        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        final ViewGroup content = (ViewGroup) rootView.getChildAt(1);
        content.addView(closeMask);

        setVisibility(VISIBLE);
        content.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        ObjectAnimator animRootView = ObjectAnimator.ofFloat(content, "translationX", 0, -getOffset());
        animRootView.setDuration(300);
        animRootView.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                content.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animRootView.start();
    }

    public void close(Activity activity) {
        final ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        final ViewGroup content = (ViewGroup) rootView.getChildAt(1);
        content.removeView(closeMask);

        content.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        ObjectAnimator animRootView = ObjectAnimator.ofFloat(content, "translationX", -getOffset(), 0);
        animRootView.setDuration(300);
        animRootView.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                content.setLayerType(View.LAYER_TYPE_NONE, null);
                setVisibility(GONE);
                isOpen = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animRootView.start();
    }

    public void init(View content) {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.removeAllViews();
        if (container != null) {
            container.removeAllViews();
        }

        container = new RelativeLayout(activity);
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.setGravity(Gravity.RIGHT);
        container.addView(this);
        rootView.addView(container);
        rootView.addView(content);
    }
}
