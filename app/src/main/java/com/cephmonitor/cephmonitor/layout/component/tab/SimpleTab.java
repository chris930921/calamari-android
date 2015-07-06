package com.cephmonitor.cephmonitor.layout.component.tab;

/**
 * Created by User on 2015/7/6.
 */
public class SimpleTab {

    float centerPositionLeft = 0;
    float leftPositionLeft = 0;
    float rightPositionLeft = 0;
    float left = 0;
    float width = 0;
    String name = "";
    int index;
    int tabGroupIndex;
    Object tag;

    OnTabChangeListener listener = new OnTabChangeListener() {
        @Override
        public void onChange(int index, String name, Object tag) {

        }
    };

    public float centerToLeft() {
        return leftPositionLeft - centerPositionLeft;
    }

    public float leftToCenter() {
        return centerPositionLeft - leftPositionLeft;
    }

    public float centerToRight() {
        return rightPositionLeft - centerPositionLeft;
    }

    public float rightToCenter() {
        return centerPositionLeft - rightPositionLeft;
    }
}
