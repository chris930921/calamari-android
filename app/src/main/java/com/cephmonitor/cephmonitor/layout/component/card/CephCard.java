package com.cephmonitor.cephmonitor.layout.component.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by User on 4/17/2015.
 */
public class CephCard extends RelativeLayout {
    private WH ruler;
    private Context context;
    private int leftValue;
    private int rightValue;
    private boolean isCompare;
    private boolean isChangeLeftValueColor;
    private boolean isChangeRightValueColor;
    private boolean isChangeCenterValueColor;

    public RelativeLayout contentContainer;
    public RelativeLayout topContainer;
    public RelativeLayout bottomContainer;
    public LinearLayout twoValueContainer;

    public TextView title;
    public ImageView icon;
    public ImageView arrow;

    public View titleBottomLine;
    public View twoValuesTopLine;
    public View twoValueCenterLine;

    public RelativeLayout centerValueContainer;
    public RelativeLayout centerCenterContainer;
    public TextView centerValueText;
    public TextView centerText;

    public RelativeLayout leftValueContainer;
    public RelativeLayout leftCenterContainer;
    public TextView leftText;
    public TextView leftValueText;

    public RelativeLayout rightValueContainer;
    public RelativeLayout rightCenterContainer;
    public TextView rightValueText;
    public TextView rightText;

    private int radius;
    private int borderWidth;
    private Paint paint;
    private RectF bounds;

    public CephCard(Context context) {
        super(context);
        this.context = context;
        ruler = new WH(context);
        paint = new Paint();
        bounds = new RectF();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ColorTable._8DC41F);
        radius = 10;
        borderWidth = 5;
        leftValue = 0;
        rightValue = 0;
        isCompare = true;
        isChangeLeftValueColor = true;
        isChangeRightValueColor = true;
        isChangeCenterValueColor = false;

        setBackgroundColor(Color.TRANSPARENT);

        addView(contentContainer = contentContainer());
        contentContainer.addView(topContainer = topContainer());
        topContainer.addView(icon = icon());
        topContainer.addView(arrow = arrow());
        topContainer.addView(title = title(icon, arrow));

        contentContainer.addView(titleBottomLine = titleBottomLine(topContainer));
        contentContainer.addView(bottomContainer = bottomContainer());
        bottomContainer.addView(twoValuesTopLine = twoValueTopLine());
        bottomContainer.addView(twoValueCenterLine = twoValueCenterLine());
        bottomContainer.addView(twoValueContainer = twoValueContainer());

        twoValueContainer.addView(leftValueContainer = leftValueContainer());
        leftValueContainer.addView(leftCenterContainer = leftCenterContainer());
        leftCenterContainer.addView(leftValueText = leftValue());
        leftCenterContainer.addView(leftText = leftText(leftValueText));

        twoValueContainer.addView(rightValueContainer = rightValueContainer());
        rightValueContainer.addView(rightCenterContainer = rightCenterContainer());
        rightCenterContainer.addView(rightValueText = rightValue());
        rightCenterContainer.addView(rightText = rightText(rightValueText));

        contentContainer.addView(centerValueContainer = centerValueContainer(topContainer, bottomContainer));
        centerValueContainer.addView(centerCenterContainer = centerCenterContainer());
        centerCenterContainer.addView(centerValueText = centerValueText());
        centerCenterContainer.addView(centerText = centerText(centerValueText));
    }


    public RelativeLayout contentContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(CENTER_IN_PARENT);
        params.setMargins(borderWidth, borderWidth, borderWidth, borderWidth);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(Color.WHITE);

        return v;
    }

    public RelativeLayout topContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(10.84));
        params.addRule(ALIGN_PARENT_TOP);
        params.setMargins(borderWidth, borderWidth, borderWidth, borderWidth);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public ImageView icon() {
        LayoutParams params = new LayoutParams(ruler.getW(10.45), ruler.getW(10.45));
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_IN_PARENT);
        params.setMargins(ruler.getW(4.08), 0, 0, 0);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        return v;
    }

    public ImageView arrow() {
        LayoutParams params = new LayoutParams(ruler.getW(10.45), ruler.getW(10.45));
        params.addRule(ALIGN_PARENT_RIGHT);
        params.addRule(CENTER_IN_PARENT);
        params.setMargins(0, 0, ruler.getW(4.08), 0);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        return v;
    }

    public TextView title(View leftView, View rightView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RIGHT_OF, leftView.getId());
        params.addRule(LEFT_OF, rightView.getId());
        params.addRule(CENTER_VERTICAL);
        params.setMargins(ruler.getW(4.08), 0, 0, 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(30));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTypeface(null, Typeface.BOLD);

        return v;
    }

    public View titleBottomLine(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(0.36));
        params.addRule(ALIGN_BOTTOM, topView.getId());
        params.addRule(CENTER_HORIZONTAL);
        params.setMargins(ruler.getW(5.10), 0, ruler.getW(5.10), 0);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._D9D9D9);

        return v;
    }

    public RelativeLayout bottomContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(12.65));
        params.addRule(ALIGN_PARENT_BOTTOM);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._F3F3F3);

        return v;
    }

    public View twoValueTopLine() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(0.36));
        params.addRule(ALIGN_PARENT_TOP);
        params.addRule(CENTER_HORIZONTAL);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._D9D9D9);

        return v;
    }

    public View twoValueCenterLine() {
        LayoutParams params = new LayoutParams(ruler.getH(0.36), LayoutParams.MATCH_PARENT);
        params.addRule(CENTER_IN_PARENT);
        params.setMargins(0, ruler.getH(3.61), 0, ruler.getH(3.61));

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._D9D9D9);

        return v;
    }

    public LinearLayout twoValueContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        LinearLayout v = new LinearLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setGravity(Gravity.CENTER);
        v.setWeightSum(2);

        return v;
    }

    public RelativeLayout leftValueContainer() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.weight = 1;

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public RelativeLayout leftCenterContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public TextView leftValue() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(30));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTypeface(null, Typeface.BOLD);
        v.setTextColor(ColorTable._999999);

        return v;
    }

    public TextView leftText(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());
        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTextColor(ColorTable._999999);

        return v;
    }


    public RelativeLayout rightValueContainer() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.weight = 1;

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public RelativeLayout rightCenterContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public TextView rightValue() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(30));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTypeface(null, Typeface.BOLD);
        v.setTextColor(ColorTable._999999);

        return v;
    }

    public TextView rightText(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());
        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTextColor(ColorTable._999999);

        return v;
    }

    public RelativeLayout centerValueContainer(View topView, View bottomView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(BELOW, topView.getId());
        params.addRule(ABOVE, bottomView.getId());
        params.setMargins(ruler.getW(5.10), 0, ruler.getW(5.10), 0);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(Color.WHITE);

        return v;
    }

    public RelativeLayout centerCenterContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public TextView centerValueText() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(30));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTypeface(null, Typeface.BOLD);
        v.setTextColor(ColorTable._8DC41F);

        return v;
    }

    public TextView centerText(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());
        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTextColor(ColorTable._999999);

        return v;
    }

    public void setIcon(int image) {
        icon.setImageResource(image);
    }

    public void setArrow(int image) {
        arrow.setImageResource(image);
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void setCompareMode(boolean isCompare) {
        this.isCompare = isCompare;
    }

    public void setChangeCenterValueColor(boolean isChange) {
        this.isChangeCenterValueColor = isChange;
    }

    public void setChangeTwoValueColor(boolean isChangeLeftValueColor, boolean isChangeRightValueColor) {
        this.isChangeLeftValueColor = isChangeLeftValueColor;
        this.isChangeRightValueColor = isChangeRightValueColor;
    }

    public void setValue(int leftValue, int rightValue) {
        if (leftValue == 0) {
            leftValueText.setTextColor(ColorTable._999999);
        } else if (isChangeLeftValueColor) {
            leftValueText.setTextColor(ColorTable._F7B500);
        }
        leftValueText.setText(leftValue + "");

        if (rightValue == 0) {
            rightValueText.setTextColor(ColorTable._999999);
        } else if (isChangeRightValueColor) {
            rightValueText.setTextColor(ColorTable._E63427);
        }
        rightValueText.setText(rightValue + "");

        if (isCompare && rightValue == 0 && leftValue == 0) {
            changeGreenBorder();
        } else if (isCompare && rightValue != 0) {
            changeRedBorder();
        } else if (isCompare) {
            changeOrangeBorder();
        }
    }


    public void setCenterValueText(String text) {
        centerValueText.setText(text);
    }

    public void setLeftText(String text) {
        leftText.setText(text);
    }

    public void setRightText(String text) {
        rightText.setText(text);
    }

    public void setCenterText(String text) {
        centerText.setText(text);
    }

    public void changeRedBorder() {
        setBorderColor(ColorTable._E63427);
        if (isChangeCenterValueColor) {
            centerValueText.setTextColor(ColorTable._E63427);
        }
    }

    public void changeOrangeBorder() {
        setBorderColor(ColorTable._F7B500);
        if (isChangeCenterValueColor) {
            centerValueText.setTextColor(ColorTable._F7B500);
        }
    }

    public void changeGreenBorder() {
        setBorderColor(ColorTable._8DC41F);
        if (isChangeCenterValueColor) {
            centerValueText.setTextColor(ColorTable._8DC41F);
        }
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        invalidate();
    }

    public void setBorderColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        bounds.set(0, 0, width, height);
        canvas.drawRoundRect(bounds, radius, radius, paint);
        super.onDraw(canvas);
    }

    public void setTitleOnClickListener(OnClickListener event) {
        topContainer.setOnClickListener(event);
    }
}