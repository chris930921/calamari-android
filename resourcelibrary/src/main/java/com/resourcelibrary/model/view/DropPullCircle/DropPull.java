package com.resourcelibrary.model.view.DropPullCircle;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.resourcelibrary.model.logic.RandomId;


/**
 * Created by User on 2/26/2015.
 */
public class DropPull extends LinearLayout {
    private Context context;
    public DropPullFrame frame;
    private OnDropTrigger event;

    public DropPull(Context context) {
        super(context);
        this.context = context;

        setOrientation(LinearLayout.VERTICAL);
        setWeightSum(10);

        addView(frame = frame());
        addView(stuffView());
    }

    private DropPullFrame frame() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.weight = 2;

        DropPullFrame frame = new DropPullFrame(context);
        frame.setId(RandomId.get());
        frame.setLayoutParams(params);

        return frame;
    }

    private View stuffView() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.weight = 8;

        View v = new View(context);
        v.setLayoutParams(params);
//        v.setBackgroundColor(Color.GREEN);

        return v;
    }

    private float startX, startY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            startY = event.getY();
            return false;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float deltaX = Math.abs(event.getX() - startX);
            float deltaY = Math.abs(event.getY() - startY);
            if (deltaX - deltaY > 50) {
                Log.d("Test專案偵錯", "Top true");
                return false;
            } else if (deltaY - deltaX > 50) {
                Log.d("Test專案偵錯", "Top");
                float y = event.getY() - startY;
                if (deltaY > 0) {
                    y = y - 50;
                    y = (y > 100) ? 100 : y;
                    frame.setScaleX(1 - (y / 100) * 0.2f);
                    frame.setScaleY(1 - (y / 100) * 0.2f);
                    frame.setTranslationY(y * 1.5f);
                }
                if (y == 100 && !frame.circle.isLoading() && this.event != null) {
                    frame.circle.startLoading();
                    this.event.onTrigger();
                }
                return false;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            frame.animate().scaleX(1);
            frame.animate().scaleY(1);
            frame.animate().translationY(0);
            return false;
        }
        return false;
    }

    public void setOnDropTrigger(OnDropTrigger event) {
        this.event = event;
    }
}
