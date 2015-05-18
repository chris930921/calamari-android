package com.resourcelibrary.model.logic;

import android.view.MotionEvent;

/**
 * Created by User on 5/5/2015.
 */
public abstract class GestureAdapter {
    private float startX;
    private boolean isMove;
    private MotionEvent event;

    public GestureAdapter() {
        isMove = false;
    }

    public boolean onTouchEvent(MotionEvent event, boolean defaultOnTouch) {
        this.event = event;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            onDown(event);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            actionMove();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            actionUP();
            isMove = false;
            return false;
        }
        return defaultOnTouch;
    }

    public void actionMove() {
        if (isMove) {
            onMoving(event);
        } else {
            actionMoveAndStopping();
        }
    }

    public void actionUP() {
        if (isMove) {
            onMoveEnd(event);
        } else {
            onClick(event);
        }
    }

    public void actionMoveAndStopping() {
        float moveX = event.getX() - startX;
        float moveLimit = 20;
        if (moveX > moveLimit || moveX < -moveLimit) {
            isMove = true;
            onMoveStart(event);
        }
    }

    public abstract void onDown(MotionEvent event);

    public abstract void onMoveStart(MotionEvent event);

    public abstract void onMoving(MotionEvent event);

    public abstract void onClick(MotionEvent event);

    public abstract void onMoveEnd(MotionEvent event);
}
