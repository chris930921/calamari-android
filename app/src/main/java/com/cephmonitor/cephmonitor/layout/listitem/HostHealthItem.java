package com.cephmonitor.cephmonitor.layout.listitem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;


public class HostHealthItem extends RelativeLayout {
    private Context context;
    private WH ruler;
    private RectF bounds;
    private int strokeWidth = 1;
    private Paint strokePaint;

    public View topFillView;
    public TextView hostName;
    public TextView fieldValue;
    public TextView fieldUnit;

    public RelativeLayout contentContainer;
    public RelativeLayout rightTextContainer;

    public HostHealthItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.bounds = new RectF();

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(GenerateViewId.get());
        setLayoutParams(params);


        strokePaint = new Paint();
        strokePaint.setColor(ColorTable._D9D9D9);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeWidth);

        topFillView = topFillView();
        contentContainer = contentContainer(topFillView);
        hostName = hostName();
        rightTextContainer = rightTextContainer();
        fieldValue = fieldValue();
        fieldUnit = fieldUnit(fieldValue);


        addView(topFillView);
        addView(contentContainer);
        contentContainer.addView(rightTextContainer);
        contentContainer.addView(hostName);
        rightTextContainer.addView(fieldValue);
        rightTextContainer.addView(fieldUnit);
    }

    private RelativeLayout contentContainer(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(context) {
            @Override
            protected void onDraw(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                int left = strokeWidth;
                int top = strokeWidth;
                int right = width - left;
                int bottom = height - top;
                bounds.set(left, top, right, bottom);

                canvas.drawRect(bounds, strokePaint);

                super.onDraw(canvas);
            }
        };
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._F9F9F9);
        v.setPadding(0, ruler.getW(3), 0, ruler.getW(3));

        return v;
    }

    private TextView hostName() {
        LayoutParams params = new LayoutParams(ruler.getW(66), LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_VERTICAL);
        params.setMargins(ruler.getW(3), 0, ruler.getW(3), 0);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(16));
        v.setTextColor(ColorTable._666666);
        v.setTypeface(null, Typeface.BOLD);
        v.setGravity(Gravity.CENTER_VERTICAL);

        return v;
    }

    private RelativeLayout rightTextContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.setMargins(0, 0, ruler.getW(3), 0);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    private TextView fieldValue() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_TOP);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setTextColor(ColorTable._666666);
        v.setTypeface(null, Typeface.BOLD);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setSingleLine(true);

        return v;
    }

    private TextView fieldUnit(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setTextColor(ColorTable._999999);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setSingleLine(true);
        v.setText(R.string.host_health_osd);

        return v;
    }

    private View topFillView() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(5));
        params.addRule(ALIGN_PARENT_TOP);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }


    public void setData(String hostName, String id) {
        this.hostName.setText(hostName);
        this.fieldValue.setText(id);
    }
}
