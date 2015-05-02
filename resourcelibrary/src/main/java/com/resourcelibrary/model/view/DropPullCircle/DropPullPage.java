package com.resourcelibrary.model.view.DropPullCircle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resourcelibrary.model.logic.RandomId;

import java.util.ArrayList;

/**
 * Created by User on 1/22/2015.
 */
public class DropPullPage extends RelativeLayout {
    private Context context;
    private int index;
    private ArrayList<PageData> data;
    private ArrayList<RelativeLayout> pages;
    private float offset;
    private boolean isAlphaAniming;
    private boolean isTranslationXAniming;
    private OnDropPageChange event;

    public TextView pageCount;
    public RelativeLayout centerContainer;

    public DropPullPage(Context context) {
        super(context);
        this.context = context;

        addView(centerContainer = centerContainer());
        addView(pageCount = pageCount());
    }

    private RelativeLayout pageContainer(PageData singleData) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        RelativeLayout container = new RelativeLayout(context);
        container.setId(RandomId.get());
        container.setLayoutParams(params);

        View v;
        container.addView(v = title(singleData.title));
        container.addView(v = filedTwo(singleData.filedTwo, v.getId()));
        container.addView(v = filedThree(singleData.filedThree, v.getId()));
        container.addView(filedFour(singleData.filedFour, v.getId()));

        return container;
    }

    private RelativeLayout centerContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        RelativeLayout container = new RelativeLayout(context);
        container.setId(RandomId.get());
        container.setLayoutParams(params);
        container.setGravity(Gravity.CENTER_HORIZONTAL);

        return container;
    }

    private TextView title(String content) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_TOP);

        TextView text = text();
        text.setText(content);
        text.setLayoutParams(params);
        return text;
    }

    private TextView filedTwo(String content, int belowId) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, belowId);

        TextView text = text();
        text.setText(content);
        text.setLayoutParams(params);
        return text;
    }

    private TextView filedThree(String content, int belowId) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, belowId);

        TextView text = text();
        text.setText(content);
        text.setLayoutParams(params);
        return text;
    }

    private TextView filedFour(String content, int belowId) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, belowId);

        TextView text = text();
        text.setText(content);
        text.setLayoutParams(params);
        return text;
    }

    private TextView pageCount() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, centerContainer.getId());
        params.addRule(CENTER_HORIZONTAL);

        TextView text = new TextView(context);
        text.setId(RandomId.get());
        text.setTextSize(10);
        text.setLayoutParams(params);
        return text;
    }

    private TextView text() {
        TextView text = new TextView(context);
        text.setId(RandomId.get());
        text.setTextSize(25);

        return text;
    }

    public void setAdapter(ArrayList<PageData> data) {
        if (data == null) return;
        this.data = data;

        if (data.size() == 0) return;

        isAlphaAniming = false;
        isTranslationXAniming = false;
        index = 0;
        pageCount.setText(getPageCountSymbol());
        pages = new ArrayList<>();
        centerContainer.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            PageData singleData = data.get(i);
            pages.add(pageContainer(singleData));
            pages.get(i).setAlpha(0);
            centerContainer.addView(pages.get(i));
        }
        update();
    }

    private String getPageCountSymbol() {
        String pageCountSymbol = "";

        if (data == null) return pageCountSymbol;
        if (data.size() == 0) return pageCountSymbol;

        for (int i = 0; i < data.size(); i++) {
            if (i == index) {
                pageCountSymbol += "●";
            } else {
                pageCountSymbol += "○";
            }
        }
        return pageCountSymbol;
    }


    public void update() {
        if (data == null) return;
        if (data.size() == 0) return;

        offset = (offset < -100) ? -100 : offset;
        offset = (offset > 100) ? 100 : offset;
        if (index == 0 && offset < 0) {
            pages.get(index).setAlpha(1 - (-offset / 100f));
            pages.get(index).setTranslationX(offset);

            pages.get(index + 1).setAlpha(-offset / 100f);
            pages.get(index + 1).setTranslationX(100 + offset);
        } else if (index == data.size() - 1 && offset > 0) {
            pages.get(index).setAlpha(1 - (offset / 100f));
            pages.get(index).setTranslationX(offset);

            pages.get(index - 1).setAlpha(offset / 100f);
            pages.get(index - 1).setTranslationX(-100 + offset);
        } else if (index > 0 && index < data.size() - 1 && offset < 0) {
            pages.get(index).setAlpha(1 - (-offset / 100f));
            pages.get(index).setTranslationX(offset);

            pages.get(index + 1).setAlpha(-offset / 100f);
            pages.get(index + 1).setTranslationX(100 + offset);
        } else if (index > 0 && index < data.size() - 1 && offset > 0) {
            pages.get(index).setAlpha(1 - (offset / 100f));
            pages.get(index).setTranslationX(offset);

            pages.get(index - 1).setAlpha(offset / 100f);
            pages.get(index - 1).setTranslationX(-100 + offset);
        } else if (offset == 0) {
            for (int i = 0; i < pages.size(); i++) {
                if (index != i) {
                    pages.get(i).setAlpha(0);
                }
            }

            pages.get(index).animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    isAlphaAniming = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isAlphaAniming = false;
                }
            });
            pages.get(index).animate().translationX(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    isTranslationXAniming = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isTranslationXAniming = false;
                }
            });
            if (event != null) event.onChange(data.get(index));
        }
    }

    public void setSingleData(int index, PageData singleData) {
        if (data == null) return;
        if (index >= data.size()) return;

        data.set(index, singleData);
    }

    private float startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAlphaAniming || isTranslationXAniming) return false;
        if (data == null) return false;
        if (data.size() == 0) return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            offset = event.getX() - startX;
            update();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (offset > 0 && index > 0) {
                index--;
            } else if (offset < 0 && index < data.size() - 1) {
                index++;
            }
            offset = 0;
            pageCount.setText(getPageCountSymbol());
            update();
        }
        return false;
    }

    public int getCurrentIndex() {
        return index;
    }

    public void setOnDropPageChange(OnDropPageChange event) {
        this.event = event;
    }
}