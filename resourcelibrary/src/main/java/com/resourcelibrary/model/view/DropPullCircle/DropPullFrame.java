package com.resourcelibrary.model.view.DropPullCircle;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.resourcelibrary.model.logic.RandomId;


/**
 * Created by User on 2/26/2015.
 */
public class DropPullFrame extends FrameLayout {
    private Context context;
    public DropPullCircle circle;
    public DropPullPage page;

    public DropPullFrame(Context context) {
        super(context);
        this.context = context;

        addView(circle = circle());
        addView(page = page());
    }


    private DropPullCircle circle() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        DropPullCircle circle = new DropPullCircle(context);
        circle.setId(RandomId.get());
        circle.setLayoutParams(params);

        return circle;
    }

    private DropPullPage page() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        DropPullPage page = new DropPullPage(context);
        page.setId(RandomId.get());
        page.setLayoutParams(params);
        page.setOnDropPageChange(event);

        return page;
    }

    private OnDropPageChange event = new OnDropPageChange() {
        @Override
        public void onChange(PageData singleData) {
            circle.setPercent((float) ((double) singleData.tag));
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        circle.onTouchEvent(event);
        page.onTouchEvent(event);
        return true;
    }
}
