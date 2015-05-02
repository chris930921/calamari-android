package com.resourcelibrary.model.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.resourcelibrary.model.logic.RandomId;

import java.util.ArrayList;

/**
 * Created by User on 3/11/2015.
 */
public class DragListNoScroll extends ViewGroup {
    private Context context;

    private int selectIndex;
    private float dragOffset;
    private float startY;
    private ArrayList<String> tags;
    public boolean isDragging;
    private ArrayList<Integer> positionY = new ArrayList<>();
    private OnDragChangeEvent event;

    public DragListNoScroll(Context context) {
        super(context);
        this.context = context;
        this.tags = new ArrayList<>();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setId(RandomId.get());
        setLayoutParams(params);
    }

    public void addItem(View v, String tag) {
        addView(v);
        tags.add(tag);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (positionY.size() == 0) return false;
            if (motionEvent.getX() > 100) return false;

            startY = motionEvent.getY();
            for (selectIndex = 0; selectIndex < positionY.size(); selectIndex++) {
                if (selectIndex == positionY.size() - 1) break;
                if (positionY.get(selectIndex + 1) > startY) break;
            }
            return true;
        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            isDragging = true;
            dragOffset = motionEvent.getY() - startY;
            int offsetIndex;
            for (offsetIndex = 0; offsetIndex < positionY.size(); offsetIndex++) {
                if (offsetIndex == positionY.size() - 1) break;
                if (positionY.get(offsetIndex + 1) > motionEvent.getY()) break;
            }
            if (offsetIndex != selectIndex) {
                View v = getChildAt(selectIndex);
                String tag = tags.get(selectIndex);

                removeAt(selectIndex);
                addAt(v, tag, offsetIndex);

                selectIndex = offsetIndex;
                startY = motionEvent.getY();
            }
            dragOffset = motionEvent.getY() - startY;
            requestLayout();
            return true;
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            isDragging = false;
            requestLayout();
            if (event != null) event.onDrag(tags);
            return false;
        }
        isDragging = false;
        return true;
    }

    public void removeAt(int index) {
        removeViewAt(index);
        tags.remove(index);
    }

    public void addAt(View v, String tag, int index) {
        addView(v, index);
        tags.add(index, tag);
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        positionY.clear();

        int marginTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams params = child.getLayoutParams();

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
            LayoutParams params = child.getLayoutParams();
            int childHeight = params.height;
            height += childHeight;
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(((View) getParent()).getMeasuredWidth(), height);
    }

    public void setOnDragChangeEvent(OnDragChangeEvent event) {
        this.event = event;
    }

    public static interface OnDragChangeEvent {
        public void onDrag(ArrayList<String> tags);
    }
}
