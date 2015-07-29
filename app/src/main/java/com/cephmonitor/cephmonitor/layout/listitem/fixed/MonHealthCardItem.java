package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;
import com.resourcelibrary.model.view.WH;

/**
 * Created by User on 6/3/2015.
 */
public class MonHealthCardItem extends RelativeLayout {
    private Context context;
    private WH ruler;
    private Rect bounds;
    private Paint leftBorderPaint;

    public View topFillView;
    public View bottomFillView;
    public View bottomLine;
    public RelativeLayout contentContainer;
    public RelativeLayout textContainer;
    public TextView fieldName;
    public TextView FieldValue;

    private DesignSpec designSpec;
    private int horizontalSize;
    private int horizontalColor;

    public MonHealthCardItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.bounds = new Rect();
        this.designSpec = ThemeManager.getStyle(context);
        horizontalSize = (int) designSpec.getHorizontal().getHorizontalOneHeight();
        horizontalColor = designSpec.getPrimaryColors().getHorizontalOne();

        leftBorderPaint = new Paint();
        leftBorderPaint.setAntiAlias(true);
        leftBorderPaint.setColor(getResources().getColor(R.color.green));

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(RandomId.get());
        setLayoutParams(params);
        setBackgroundColor(Color.TRANSPARENT);

        topFillView = topFillView();
        contentContainer = contentContainer(topFillView);
        bottomFillView = bottomFillView(contentContainer);
        bottomLine = bottomLine(bottomFillView);
        textContainer = textContainer();
        fieldName = fieldName();
        FieldValue = FieldValue(fieldName);

        addView(topFillView);
        addView(contentContainer);
        addView(bottomFillView);
        addView(bottomLine);
        contentContainer.addView(textContainer);
        textContainer.addView(fieldName);
        textContainer.addView(FieldValue);
    }

    private View topFillView() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(5));

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View bottomFillView(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getW(5));
        params.addRule(BELOW, topView.getId());

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View bottomLine(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, horizontalSize);
        params.addRule(BELOW, topView.getId());

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(horizontalColor);

        return v;
    }

    private RelativeLayout contentContainer(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(context) {
            @Override
            protected void onDraw(Canvas canvas) {
                int height = canvas.getHeight();

                bounds.set(0, 0, ruler.getW(3), height);
                canvas.drawRect(bounds, leftBorderPaint);
                super.onDraw(canvas);
            }
        };
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(Color.TRANSPARENT);

        return v;
    }

    private RelativeLayout textContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(CENTER_VERTICAL);
        params.setMargins(ruler.getW(6), 0, 0, 0);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private TextView fieldName() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_TOP);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(16));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTypeface(null, Typeface.BOLD);
        v.setTextColor(ColorTable._666666);

        return v;
    }

    private TextView FieldValue(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTextColor(ColorTable._666666);

        return v;
    }

    public void setData(String status, String field, String value) {
        if (ClusterV1HealthData.HEALTH_OK.equals(status)) {
            leftBorderPaint.setColor(ColorTable._8DC41F);
        } else if (ClusterV1HealthData.HEALTH_WARN.equals(status)) {
            leftBorderPaint.setColor(ColorTable._F7B500);
        } else if (ClusterV1HealthData.HEALTH_ERR.equals(status)) {
            leftBorderPaint.setColor(ColorTable._E63427);
        }
        fieldName.setText(field);
        FieldValue.setText(value);
        invalidate();
    }

}
