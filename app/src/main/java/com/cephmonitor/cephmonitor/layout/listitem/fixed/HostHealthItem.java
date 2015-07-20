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
import com.resourcelibrary.model.view.WH;


public class HostHealthItem extends RelativeLayout {
    private Context context;
    private WH ruler;
    private RectF bounds;
    private Paint strokePaint;
    private Paint backgroundPaint;

    public View topFillView;
    public TextView hostName;
    public TextView fieldValue;
    public TextView fieldUnit;

    public RelativeLayout contentContainer;
    public RelativeLayout rightTextContainer;

    private int borderWidth;
    private int borderColor;
    private int borderRadius;

    private DesignSpec designSpec;
    private TextViewStyle bodyTwo;
    private TextViewStyle bodyOne;
    private TextViewStyle subhead;
    private int backgroundThreeColor;
    private float topBottomPaddingOne;
    private float leftRightPaddingOne;

    public HostHealthItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.bounds = new RectF();
        designSpec = ThemeManager.getStyle(context);
        bodyTwo = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        bodyOne = new TextViewStyle(designSpec.getStyle().getBodyOne());
        subhead = new TextViewStyle(designSpec.getStyle().getSubhead());
        backgroundThreeColor = designSpec.getPrimaryColors().getBackgroundThree();
        topBottomPaddingOne = designSpec.getPadding().getTopBottomOne();
        leftRightPaddingOne = designSpec.getPadding().getLeftRightOne();
        borderWidth = 1;
        borderColor = ColorTable._D9D9D9;
        borderRadius = 10;

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(GenerateViewId.get());
        setLayoutParams(params);

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(borderColor);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(borderWidth);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundThreeColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        topFillView = topFillView();
        contentContainer = contentContainer(topFillView);
        rightTextContainer = rightTextContainer();
        hostName = hostName(rightTextContainer);
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
            protected void dispatchDraw(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                int left = borderWidth;
                int top = borderWidth;
                int right = width - left;
                int bottom = height - top;
                bounds.set(left, top, right, bottom);

                canvas.drawRoundRect(bounds, borderRadius, borderRadius, backgroundPaint);
                canvas.drawRoundRect(bounds, borderRadius, borderRadius, strokePaint);
                super.dispatchDraw(canvas);
            }
        };
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setPadding(
                ruler.getW(leftRightPaddingOne), ruler.getW(topBottomPaddingOne),
                ruler.getW(leftRightPaddingOne), ruler.getW(topBottomPaddingOne));

        return v;
    }

    private TextView hostName(View rightView) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_VERTICAL);
        params.addRule(RIGHT_OF, rightView.getId());

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        subhead.style(v);

        return v;
    }

    private RelativeLayout rightTextContainer() {
        LayoutParams params = new LayoutParams(ruler.getW(15), LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_RIGHT);
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
        v.setText(R.string.host_health_osd);
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


    public void setData(String hostName, String id) {
        this.hostName.setText(hostName);
        this.fieldValue.setText(id);
    }
}
