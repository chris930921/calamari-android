package com.resourcelibrary.model.view;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.resourcelibrary.model.logic.RandomId;

import java.util.ArrayList;

/**
 * Created by User on 3/11/2015.
 */
public class DragListView extends ScrollView {
    private Context context;

    public ViewGroup list;
    private int selectIndex;
    private float dragOffset;
    private float startY;
    private boolean isDragging;

    public DragListView(Context context) {
        super(context);
        this.context = context;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);

        addView(list = list());
    }

    private ViewGroup list() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        RelativeLayout list = new RelativeLayout(context) {
            private ArrayList<Integer> positionY = new ArrayList<>();

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (positionY.size() == 0) return false;

                    startY = event.getY();
                    for (selectIndex = 0; selectIndex < positionY.size() - 2; selectIndex++) {
                        if (positionY.get(selectIndex + 1) > startY) break;
                    }
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    isDragging = true;
                    dragOffset = event.getY() - startY;
                    int offsetIndex;
                    for (offsetIndex = 0; offsetIndex < positionY.size() - 2; offsetIndex++) {
                        if (positionY.get(offsetIndex + 1) > event.getY()) break;
                    }
                    if (offsetIndex != selectIndex) {
                        View v = getChildAt(selectIndex);
                        removeViewAt(selectIndex);
                        addView(v, offsetIndex);
                        selectIndex = offsetIndex;
                        startY = event.getY();
                    }
                    dragOffset = event.getY() - startY;
                    requestLayout();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    isDragging = false;
                    requestLayout();
                    return false;
                }
                isDragging = false;
                return true;
            }

            @Override
            protected void onLayout(boolean b, int left, int top, int right, int bottom) {
                positionY.clear();

                int marginTop = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    ViewGroup.LayoutParams params = child.getLayoutParams();

                    int childWidth = getMeasuredWidth();
                    int childHeight = params.height;

                    if (childWidth < 0) childWidth = 0;

                    if (selectIndex == i && isDragging) {
                        child.layout(0, marginTop + (int) dragOffset, childWidth, marginTop + childHeight + (int) dragOffset);
                    } else {
                        child.layout(0, marginTop, childWidth, marginTop + childHeight);
                    }
                    positionY.add(marginTop);

                    marginTop += childHeight;
                }
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int height = 0;

                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    ViewGroup.LayoutParams params = child.getLayoutParams();
                    int childHeight = params.height;
                    height += childHeight;
                }
                setMeasuredDimension(((View) getParent()).getMeasuredWidth(), height);
            }
        };
        list.setId(RandomId.get());
        list.setLayoutParams(params);

        return list;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (ev.getX() < 100) {
                return false;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return false;
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            return false;
        }
        return true;
    }

    private TextView text(String content) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);

        TextView text = new TextView(context);
        text.setId(RandomId.get());
        text.setLayoutParams(params);
        text.setText(content);
        text.setBackgroundColor(Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));

        return text;
    }

}
