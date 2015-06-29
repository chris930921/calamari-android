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

import java.util.HashMap;


public class PgStatusItem extends RelativeLayout {
    private static final HashMap<String, Integer> choiceColor = new HashMap<>();
    private static final HashMap<String, Integer> choiceTitle = new HashMap<>();

    private Context context;
    private WH ruler;
    private RectF bounds;
    private int strokeWidth = 1;
    private int radius = 10;
    private Paint strokePaint;
    private Paint leftBorderPaint;

    public View topFillView;
    public TextView pgStatus;
    public TextView pgValue;
    public TextView fieldValue;
    public TextView fieldUnit;

    public RelativeLayout contentContainer;
    public RelativeLayout leftTextContainer;
    public RelativeLayout rightTextContainer;

    static {
        choiceColor.put("Down", ColorTable._E63427);
        choiceColor.put("Stale", ColorTable._E63427);
        choiceColor.put("Peering", ColorTable._E63427);
        choiceColor.put("Incomplete", ColorTable._E63427);
        choiceColor.put("Inconsistent", ColorTable._E63427);
        choiceColor.put("Creating", ColorTable._F7B500);
        choiceColor.put("Replaying", ColorTable._F7B500);
        choiceColor.put("Splitting", ColorTable._F7B500);
        choiceColor.put("Scrubbing", ColorTable._F7B500);
        choiceColor.put("Degraded", ColorTable._F7B500);
        choiceColor.put("Repair", ColorTable._F7B500);
        choiceColor.put("Recovering", ColorTable._F7B500);
        choiceColor.put("Backfill", ColorTable._F7B500);
        choiceColor.put("Wait-Backfill", ColorTable._F7B500);
        choiceColor.put("Remapped", ColorTable._F7B500);
        choiceColor.put("Clean", ColorTable._8DC41F);
        choiceColor.put("Active", ColorTable._8DC41F);

        choiceTitle.put("Down", R.string.pg_status_down);
        choiceTitle.put("Stale", R.string.pg_status_down);
        choiceTitle.put("Peering", R.string.pg_status_down);
        choiceTitle.put("Incomplete", R.string.pg_status_down);
        choiceTitle.put("Inconsistent", R.string.pg_status_down);
        choiceTitle.put("Creating", R.string.pg_status_degraded);
        choiceTitle.put("Replaying", R.string.pg_status_degraded);
        choiceTitle.put("Splitting", R.string.pg_status_degraded);
        choiceTitle.put("Scrubbing", R.string.pg_status_degraded);
        choiceTitle.put("Degraded", R.string.pg_status_degraded);
        choiceTitle.put("Repair", R.string.pg_status_degraded);
        choiceTitle.put("Recovering", R.string.pg_status_degraded);
        choiceTitle.put("Backfill", R.string.pg_status_degraded);
        choiceTitle.put("Wait-Backfill", R.string.pg_status_degraded);
        choiceTitle.put("Remapped", R.string.pg_status_degraded);
        choiceTitle.put("Clean", R.string.pg_status_clean);
        choiceTitle.put("Active", R.string.pg_status_clean);
    }

    public PgStatusItem(Context context) {
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

        leftBorderPaint = new Paint();
        leftBorderPaint.setAntiAlias(true);
        leftBorderPaint.setColor(ColorTable._8DC41F);

        topFillView = topFillView();
        contentContainer = contentContainer(topFillView);
        leftTextContainer = leftTextContainer();
        pgStatus = pgStatus();
        pgValue = pgValue(pgStatus);
        rightTextContainer = rightTextContainer();
        fieldValue = fieldValue();
        fieldUnit = fieldUnit(fieldValue);

        addView(topFillView);
        addView(contentContainer);
        contentContainer.addView(leftTextContainer);
        contentContainer.addView(rightTextContainer);
        leftTextContainer.addView(pgStatus);
        leftTextContainer.addView(pgValue);
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

                bounds.set(0, 0, ruler.getW(3), height);
                canvas.drawRoundRect(bounds, radius, radius, leftBorderPaint);

                bounds.set(ruler.getW(1.5), 0, ruler.getW(3), height);
                canvas.drawRect(bounds, leftBorderPaint);

                int left = strokeWidth;
                int top = strokeWidth;
                int right = width - left;
                int bottom = height - top;
                bounds.set(left, top, right, bottom);
                canvas.drawRoundRect(bounds, radius, radius, strokePaint);

                super.onDraw(canvas);
            }
        };
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._F9F9F9);

        return v;
    }

    private RelativeLayout leftTextContainer() {
        LayoutParams params = new LayoutParams(ruler.getW(63), LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_VERTICAL);
        params.setMargins(ruler.getW(6), 0, ruler.getW(3), 0);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    private TextView pgStatus() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setTextColor(ColorTable._666666);
        v.setTypeface(null, Typeface.BOLD);
        v.setGravity(Gravity.CENTER_VERTICAL);

        return v;
    }

    private TextView pgValue(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_LEFT, topView.getId());
        params.addRule(ALIGN_RIGHT, topView.getId());
        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setTextColor(ColorTable._666666);
        v.setGravity(Gravity.CENTER_VERTICAL);

        return v;
    }

    private RelativeLayout rightTextContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.addRule(CENTER_VERTICAL);
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


    public void setData(String pgStatus, float pgValue, String id, String type) {
        this.pgStatus.setText(choiceTitle.get(pgStatus));
        this.pgValue.setText(changeValue(pgValue) + " pgs");
        this.fieldValue.setText(id);
        this.fieldUnit.setText(type.toUpperCase());
        leftBorderPaint.setColor(choiceColor.get(pgStatus));
        invalidate();
    }

    private String changeValue(float value) {
        if (value < 1000) {
            return (int) value + "";
        } else {
            float newValue = value - (value % 100);
            newValue = newValue / 1000F;
            return String.format("%.1f", newValue) + "K";
        }
    }
}
