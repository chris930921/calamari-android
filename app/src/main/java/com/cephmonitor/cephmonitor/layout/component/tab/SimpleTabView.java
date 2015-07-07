package com.cephmonitor.cephmonitor.layout.component.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.resourcelibrary.model.view.WH;

import java.util.ArrayList;

/**
 * Created by User on 2015/6/30.
 */
public class SimpleTabView extends View {
    private ArrayList<SimpleTab> tabGroup;
    private ArrayList<SimpleTab> dataTabGroup;
    private SimpleTab leftHideTab;
    private SimpleTab leftTab;
    private SimpleTab centerTab;
    private SimpleTab rightHideTab;
    private SimpleTab rightTab;

    private float center;
    private float leftTextRightSide;
    private float rightTextLeftSide;
    private WH ruler;
    private int width;
    private int height;
    private int index;
    private Paint textPaint;
    private Paint unSelectedTextPaint;
    private Rect textBounds;

    float centerMove;
    float leftMove;
    float leftHideMove;
    float rightMove;
    float rightHideMove;
    int moveCount = 0;

    float animTime = 100;
    float frame = 8F;
    int frameTime = (int) (animTime / frame);

    public SimpleTabView(Context context) {
        super(context);
        this.tabGroup = new ArrayList<>();
        this.dataTabGroup = new ArrayList<>();
        this.index = 2;
        this.textBounds = new Rect();
        this.ruler = new WH(getContext());

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);

        unSelectedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        unSelectedTextPaint.setColor(ColorTable._D9D9D9);
        unSelectedTextPaint.setTextSize(30);

        setBackgroundColor(Color.BLACK);
        clear();
    }

    public void clear() {
        dataTabGroup.clear();
        this.index = 2;
    }

    public void update() {
        if (width == 0 && height == 0) return;
        if (dataTabGroup.size() == 0) return;

        tabGroup.clear();
        for (int i = 0; i < dataTabGroup.size(); i++) {
            SimpleTab tab = dataTabGroup.get(i);
            tab.tabGroupIndex = i;
            tab.leftPositionLeft = (tab.width > leftTextRightSide) ? leftTextRightSide - tab.width : 0;
            tab.centerPositionLeft = center - (tab.width / 2);
            tab.rightPositionLeft = (tab.width > ruler.getW(15)) ? rightTextLeftSide : width - tab.width;
            tabGroup.add(tab);
        }

        tabGroup.add(0, new SimpleTab());
        tabGroup.add(0, new SimpleTab());
        tabGroup.add(new SimpleTab());
        tabGroup.add(new SimpleTab());

        leftHideTab = tabGroup.get(index - 2);
        leftTab = tabGroup.get(index - 1);
        centerTab = tabGroup.get(index);
        rightTab = tabGroup.get(index + 1);
        rightHideTab = tabGroup.get(index + 2);

        leftTab.left = leftTab.leftPositionLeft;
        centerTab.left = centerTab.centerPositionLeft;
        rightTab.left = rightTab.rightPositionLeft;

        leftHideTab.left = 0 - leftHideTab.width;
        rightHideTab.left = width + rightHideTab.width;

        invalidate();
    }

    public void add(String name, Object tag, OnTabChangeListener onTabChangeListener) {
        SimpleTab tab = new SimpleTab();
        tab.name = name;
        tab.tag = tag;
        tab.index = tabGroup.size();
        tab.listener = onTabChangeListener;
        tab.width = (int) textPaint.measureText(tab.name, 0, tab.name.length());
        dataTabGroup.add(tab);

        update();
    }

    public void setTextSize(int size) {
        String textHeightExample = "Ajg";
        textPaint.getTextBounds(textHeightExample, 0, textHeightExample.length(), textBounds);
        int textHeight = textBounds.height();

        TextView text = new TextView(getContext());
        text.setTextSize(size);
        textPaint.setTextSize(text.getTextSize());
        unSelectedTextPaint.setTextSize(text.getTextSize());

        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = textHeight + ruler.getW(2) * 2;
        setLayoutParams(params);

        update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (tabGroup.size() < 5) return false;
            if (moveCount != 0) return false;

            if (event.getX() > center) {
                if (index == tabGroup.size() - 3) return false;

                centerMove = centerTab.centerToLeft() / frame;
                leftMove = centerMove;
                leftHideMove = centerMove;
                rightMove = rightTab.rightToCenter() / frame;
                rightHideMove = centerMove;
                rightHideTab.left = rightHideTab.rightPositionLeft + centerTab.leftToCenter();
                index++;
                SimpleTab tab = tabGroup.get(index);
                tab.listener.onChange(tab.index, tab.name, tab.tag);
                post(moveTask);

            } else {
                if (index == 2) return false;

                centerMove = centerTab.centerToRight() / frame;
                leftMove = leftTab.leftToCenter() / frame;
                leftHideMove = centerMove;
                rightMove = centerMove;
                rightHideMove = centerMove;
                leftHideTab.left = leftHideTab.leftPositionLeft + centerTab.rightToCenter();
                index--;
                SimpleTab tab = tabGroup.get(index);
                tab.listener.onChange(tab.index, tab.name, tab.tag);
                post(moveTask);
            }
            return false;
        }
        return true;
    }

    private Runnable moveTask = new Runnable() {

        @Override
        public void run() {
            if (moveCount >= frame) {
                leftHideTab = tabGroup.get(index - 2);
                leftTab = tabGroup.get(index - 1);
                centerTab = tabGroup.get(index);
                rightTab = tabGroup.get(index + 1);
                rightHideTab = tabGroup.get(index + 2);

                leftTab.left = leftTab.leftPositionLeft;
                centerTab.left = centerTab.centerPositionLeft;
                rightTab.left = rightTab.rightPositionLeft;

                leftHideTab.left = 0 - leftHideTab.width;
                rightHideTab.left = width + rightHideTab.width;

                moveCount = 0;
            } else {
                centerTab.left += centerMove;
                leftHideTab.left += leftHideMove;
                leftTab.left += leftMove;
                rightHideTab.left += rightHideMove;
                rightTab.left += rightMove;
                moveCount++;
                postDelayed(moveTask, frameTime);
            }
            invalidate();
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        center = w / 2;
        leftTextRightSide = ruler.getW(15);
        rightTextLeftSide = w - ruler.getW(15);

        update();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (tabGroup.size() < 5) return;

        drawTextByRightAndVerticalCenter(canvas, leftHideTab.left, height / 2, leftHideTab.name, unSelectedTextPaint);
        drawTextByRightAndVerticalCenter(canvas, leftTab.left, height / 2, leftTab.name, (leftTab.tabGroupIndex == index) ? textPaint : unSelectedTextPaint);
        drawTextByRightAndVerticalCenter(canvas, centerTab.left, height / 2, centerTab.name, (centerTab.tabGroupIndex == index) ? textPaint : unSelectedTextPaint);
        drawTextByRightAndVerticalCenter(canvas, rightTab.left, height / 2, rightTab.name, (rightTab.tabGroupIndex == index) ? textPaint : unSelectedTextPaint);
        drawTextByRightAndVerticalCenter(canvas, rightHideTab.left, height / 2, rightHideTab.name, unSelectedTextPaint);
    }

    private void drawTextByRightAndVerticalCenter(Canvas canvas, float left, float verticalCenter, String text, Paint textPaint) {
        String textHeightExample = "a";

        textPaint.getTextBounds(textHeightExample, 0, textHeightExample.length(), textBounds);
        float textHeight = textBounds.height();

        float textLeft = left;
        float textBottom = verticalCenter + (textHeight / 2f);

        canvas.drawText(text, textLeft, textBottom, textPaint);
    }
}
