package com.cephmonitor.cephmonitor.layout.listitem;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.TextTypeface;
import com.resourcelibrary.model.view.WH;


public class NotificationItem extends RelativeLayout {
    private Context context;
    private TextTypeface textType;
    private WH ruler;

    public ImageView leftImage;
    public TextView rightText;


    public NotificationItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.textType = new TextTypeface(context);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);

        addView(leftImage = leftImage());
        addView(rightText = rightText(leftImage));
    }

    public ImageView leftImage() {
        LayoutParams params = new LayoutParams(ruler.getW(17.66), ruler.getW(17.66));
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_TOP);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(0, ruler.getW(5), ruler.getW(5), ruler.getW(5));

        return v;
    }

    public TextView rightText(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        params.addRule(RIGHT_OF, leftView.getId());
        params.setMargins(0, ruler.getW(5), 0, ruler.getW(5));

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTextColor(Color.parseColor("#666666"));

        return v;
    }

    public void setItemValue(int imageResource, String content) {
        leftImage.setImageResource(imageResource);
        rightText.setText(content);
    }


}
