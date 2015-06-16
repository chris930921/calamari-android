package com.cephmonitor.cephmonitor.layout.listitem;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.resourcelibrary.model.logic.GestureAdapter;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;
import com.resourcelibrary.model.view.WH;


public class NotificationItem extends RelativeLayout {
    public static final int WARNING = 0;
    public static final int ERROR = 1;

    private Context context;
    private WH ruler;

    public View topFillView;
    public View bottomImageFillView;
    public View bottomTextFillView;
    public ImageView leftImage;
    public ImageView rightImage;
    public RelativeLayout textContainer;
    public TextView centerTopText;
    public TextView centerBottomText;

    public NotificationItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        topFillView = topFillView();
        leftImage = leftImage(topFillView);
        rightImage = rightImage(leftImage);
        bottomImageFillView = bottomImageFillView(leftImage);
        textContainer = textContainer(leftImage, rightImage);
        bottomTextFillView = bottomTextFillView(textContainer);
        centerTopText = centerTopText();
        centerBottomText = centerBottomText(centerTopText);

        addView(topFillView);
        addView(leftImage);
        addView(bottomImageFillView);
        addView(rightImage);
        addView(textContainer);
        addView(bottomTextFillView);
        textContainer.addView(centerTopText);
        textContainer.addView(centerBottomText);
    }

    private View topFillView() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(5));
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_TOP);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View bottomImageFillView(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(5));
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(BELOW, topView.getId());

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View bottomTextFillView(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(5));
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(BELOW, topView.getId());

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private ImageView leftImage(View topView) {
        LayoutParams params = new LayoutParams(ruler.getW(10), ruler.getW(10));
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(BELOW, topView.getId());

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private ImageView rightImage(View alignView) {
        LayoutParams params = new LayoutParams(ruler.getW(10), ruler.getW(10));
        params.addRule(ALIGN_PARENT_RIGHT);
        params.addRule(ALIGN_TOP, alignView.getId());
        params.addRule(ALIGN_BOTTOM, alignView.getId());

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon027);

        return v;
    }

    private RelativeLayout textContainer(View leftView, View rightView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        params.addRule(ALIGN_TOP, leftView.getId());
        params.addRule(RIGHT_OF, leftView.getId());
        params.addRule(LEFT_OF, rightView.getId());
        params.setMargins(ruler.getW(3), 0, ruler.getW(3), 0);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private TextView centerTopText() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTextColor(getResources().getColor(R.color.text_color));

        return v;
    }

    private TextView centerBottomText(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(12));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTextColor(getResources().getColor(R.color.sub_text_color));

        return v;
    }

    public void setItemValue(Object tag, int status, String topContent, String bottomContent) {
        if (status == WARNING) {
            leftImage.setImageResource(R.drawable.icon022);
        } else if (status == ERROR) {
            leftImage.setImageResource(R.drawable.icon023);
        }
        setTag(tag);
        centerTopText.setText(topContent);
        centerBottomText.setText(bottomContent);
    }

    public void setItemValue(Object tag, String status, String topContent, String bottomContent) {
        if (ClusterV1HealthData.HEALTH_WARN.equals(status)) {
            setItemValue(tag, WARNING, topContent, bottomContent);
        } else if (ClusterV1HealthData.HEALTH_ERR.equals(status)) {
            setItemValue(tag, ERROR, topContent, bottomContent);
        }
    }

    public OnClickListener rightImageClickEvent;
    protected GestureAdapter guesture = new GestureAdapter() {
        @Override
        public void onDown(MotionEvent event) {

        }

        @Override
        public void onMoveStart(MotionEvent event) {

        }

        @Override
        public void onMoving(MotionEvent event) {

        }

        @Override
        public void onClick(MotionEvent event) {
            if (event.getX() > getMeasuredWidth() - ruler.getW(10)) {
                if (rightImageClickEvent != null) {
                    rightImageClickEvent.onClick(NotificationItem.this);
                }
            }
        }

        @Override
        public void onMoveEnd(MotionEvent event) {

        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return guesture.onTouchEvent(event, super.onTouchEvent(event));
    }

    public void setRightImageClickEvent(OnClickListener event) {
        rightImageClickEvent = event;
    }

}
