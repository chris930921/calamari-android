package com.resourcelibrary.model.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public abstract class LeftMenu extends RelativeLayout {
    public Activity activity;

    public abstract int getOffset();

    public LeftMenu(Context context) {
        super(context);
        activity = (Activity) context;
        setVisibility(GONE);
    }

    public void show(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        final View content = rootView.getChildAt(1);

        setVisibility(VISIBLE);
        content.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        ObjectAnimator animRootView = ObjectAnimator.ofFloat(content, "translationX", 0, getOffset());
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
        final View content = rootView.getChildAt(1);

        content.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        ObjectAnimator animRootView = ObjectAnimator.ofFloat(content, "translationX", getOffset(), 0);
        animRootView.setDuration(300);
        animRootView.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                content.setLayerType(View.LAYER_TYPE_NONE, null);
                setVisibility(GONE);
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
        rootView.addView(this);
        rootView.addView(content);
    }
}
