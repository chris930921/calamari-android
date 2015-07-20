package com.cephmonitor.cephmonitor.layout.listitem.fixed;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.network.api.ceph.object.CephStaticValue;
import com.resourcelibrary.model.view.WH;

import java.util.HashMap;


public class PgStatusItem extends RelativeLayout {
    private static final HashMap<String, Integer> choiceColor;

    private Context context;
    private WH ruler;
    private RectF bounds;
    private int borderWidth = 1;
    private int radius = 10;
    private Paint strokePaint;
    private Paint backgroundPaint;
    private Paint leftBorderPaint;

    public View topFillView;
    public TextView pgStatus;
    public TextView pgValue;
    public TextView fieldValue;
    public TextView fieldUnit;

    public RelativeLayout contentContainer;
    public RelativeLayout leftTextContainer;
    public RelativeLayout rightTextContainer;

    private int statusBarWidth;

    private DesignSpec designSpec;
    private TextViewStyle bodyTwo;
    private TextViewStyle bodyOne;
    private TextViewStyle subhead;
    private int backgroundThreeColor;
    private float topBottomPaddingOne;
    private float leftRightPaddingOne;

    static {
        choiceColor = new HashMap<>();
        for (String state : CephStaticValue.PG_STATUS_CRITICAL) {
            choiceColor.put(state, ColorTable._E63427);
        }
        for (String state : CephStaticValue.PG_STATUS_WARN) {
            choiceColor.put(state, ColorTable._F7B500);
        }
        for (String state : CephStaticValue.PG_STATUS_OK) {
            choiceColor.put(state, ColorTable._8DC41F);
        }
    }

    public PgStatusItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.bounds = new RectF();
        this.designSpec = ThemeManager.getStyle(context);
        bodyTwo = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        bodyOne = new TextViewStyle(designSpec.getStyle().getBodyOne());
        subhead = new TextViewStyle(designSpec.getStyle().getSubhead());
        backgroundThreeColor = designSpec.getPrimaryColors().getBackgroundThree();
        topBottomPaddingOne = designSpec.getPadding().getTopBottomOne();
        leftRightPaddingOne = designSpec.getPadding().getLeftRightOne();

        statusBarWidth = ruler.getW(3);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(GenerateViewId.get());
        setLayoutParams(params);

        strokePaint = new Paint();
        strokePaint.setColor(ColorTable._D9D9D9);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(borderWidth);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundThreeColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        leftBorderPaint = new Paint();
        leftBorderPaint.setAntiAlias(true);
        leftBorderPaint.setColor(ColorTable._8DC41F);

        topFillView = topFillView();
        contentContainer = contentContainer(topFillView);
        rightTextContainer = rightTextContainer();
        fieldValue = fieldValue();
        fieldUnit = fieldUnit(fieldValue);
        leftTextContainer = leftTextContainer(rightTextContainer);
        pgStatus = pgStatus();
        pgValue = pgValue(pgStatus);


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
            protected void dispatchDraw(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();

                int left = borderWidth;
                int top = borderWidth;
                int right = width - left;
                int bottom = height - top;
                bounds.set(left, top, right, bottom);

                canvas.drawRoundRect(bounds, radius, radius, backgroundPaint);
                canvas.drawRoundRect(bounds, radius, radius, strokePaint);

                bounds.set(0, 0, statusBarWidth, height);
                canvas.drawRoundRect(bounds, radius, radius, leftBorderPaint);

                bounds.set(statusBarWidth / 2, 0, statusBarWidth, height);
                canvas.drawRect(bounds, leftBorderPaint);

                super.dispatchDraw(canvas);
            }
        };
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setPadding(
                statusBarWidth + ruler.getW(leftRightPaddingOne), ruler.getW(topBottomPaddingOne),
                ruler.getW(leftRightPaddingOne), ruler.getW(topBottomPaddingOne));

        return v;
    }

    private RelativeLayout leftTextContainer(View rightView) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_VERTICAL);
        params.addRule(RIGHT_OF, rightView.getId());

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
        v.setGravity(Gravity.CENTER_VERTICAL);
        subhead.style(v);

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
        v.setGravity(Gravity.CENTER_VERTICAL);
        bodyOne.style(v);

        return v;
    }

    private RelativeLayout rightTextContainer() {
        LayoutParams params = new LayoutParams(ruler.getW(15), LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.addRule(CENTER_VERTICAL);
        params.setMargins(ruler.getW(leftRightPaddingOne), 0, 0, 0);

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
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setSingleLine(true);
        bodyTwo.style(v);

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
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setSingleLine(true);
        v.setText(R.string.pg_status__osd);
        bodyOne.style(v);

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


    public void setData(String pgStatus, int pgValue, int osdCount) {
        char firstCharUpCase = Character.toUpperCase(pgStatus.charAt(0));
        String shoeText = firstCharUpCase + pgStatus.substring(1);

        this.pgStatus.setText(shoeText);
        this.pgValue.setText(changeValue(pgValue) + " pgs");
        this.fieldValue.setText(String.valueOf(osdCount));
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
