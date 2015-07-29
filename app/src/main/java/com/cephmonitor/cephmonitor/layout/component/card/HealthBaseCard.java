package com.cephmonitor.cephmonitor.layout.component.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by User on 4/17/2015.
 */
public class HealthBaseCard extends RelativeLayout {
    protected WH ruler;
    protected Context context;
    private DesignSpec designSpec;

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
    private Paint borderPaint;
    private RectF bounds;

    private int activeColor;
    private int warningColor;
    private int errorColor;
    private int backgroundTwoColor;
    private int backgroundThreeColor;
    private int horizontalTwoColor;
    private float subheadIconSize;
    private float leftRightPaddingOne;
    private float topBottomPaddingOne;
    private float topBottomPaddingTwo;
    private float horizontalTwoHeight;

    private TextViewStyle subhead;
    private TextViewStyle headline;
    private TextViewStyle bodyTwo;
    private TextViewStyle bodyOne;
    private TextViewStyle note;

    public HealthBaseCard(Context context) {
        super(context);
        this.context = context;
        ruler = new WH(context);
        designSpec = ThemeManager.getStyle(context);
        radius = 10;
        borderWidth = 3;
        leftValue = 0;
        rightValue = 0;
        isCompare = true;
        isChangeLeftValueColor = true;
        isChangeRightValueColor = true;
        isChangeCenterValueColor = false;

        activeColor = designSpec.getAccentColors().getActive();
        warningColor = designSpec.getAccentColors().getWarning();
        errorColor = designSpec.getAccentColors().getError();
        backgroundTwoColor = designSpec.getPrimaryColors().getBackgroundTwo();
        backgroundThreeColor = designSpec.getPrimaryColors().getBackgroundThree();
        horizontalTwoColor = designSpec.getPrimaryColors().getHorizontalTwo();
        horizontalTwoHeight = designSpec.getHorizontal().getHorizontalTwoHeight();
        subheadIconSize = designSpec.getIconSize().getSubhead();

        subhead = new TextViewStyle(designSpec.getStyle().getSubhead());
        headline = new TextViewStyle(designSpec.getStyle().getHeadline());
        bodyOne = new TextViewStyle(designSpec.getStyle().getBodyOne());
        bodyTwo = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        note = new TextViewStyle(designSpec.getStyle().getNote());

        leftRightPaddingOne = designSpec.getPadding().getLeftRightOne();
        topBottomPaddingOne = designSpec.getPadding().getTopBottomOne();
        topBottomPaddingTwo = designSpec.getPadding().getTopBottomTwo();

        bounds = new RectF();
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(activeColor);
        borderPaint.setStrokeWidth(borderWidth);

        setBackgroundColor(Color.TRANSPARENT);

        addView(contentContainer = contentContainer());
        contentContainer.addView(topContainer = topContainer());
        topContainer.addView(icon = icon());
        topContainer.addView(arrow = arrow());
        topContainer.addView(title = title(icon, arrow));

        contentContainer.addView(centerValueContainer = centerValueContainer(topContainer));
        centerValueContainer.addView(centerCenterContainer = centerCenterContainer());
        centerCenterContainer.addView(centerValueText = centerValueText());
        centerCenterContainer.addView(centerText = centerText(centerValueText));

        contentContainer.addView(titleBottomLine = titleBottomLine(topContainer));
        contentContainer.addView(bottomContainer = bottomContainer(centerValueContainer));
        contentContainer.addView(twoValuesTopLine = twoValueTopLine(bottomContainer));
        bottomContainer.addView(twoValueContainer = twoValueContainer());

        twoValueContainer.addView(leftValueContainer = leftValueContainer());
        leftValueContainer.addView(leftCenterContainer = leftCenterContainer());
        leftCenterContainer.addView(leftValueText = leftValue());
        leftCenterContainer.addView(leftText = leftText(leftValueText));

        twoValueContainer.addView(rightValueContainer = rightValueContainer());
        rightValueContainer.addView(rightCenterContainer = rightCenterContainer());
        rightCenterContainer.addView(rightValueText = rightValue());
        rightCenterContainer.addView(rightText = rightText(rightValueText));
    }

    public RelativeLayout contentContainer() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(CENTER_IN_PARENT);

        final Paint roundPaint = new Paint();
        final Path path = new Path();
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        final Paint backgroundPaint = new Paint();
        final RectF backgroundBounds = new RectF();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(backgroundThreeColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        RelativeLayout v = new RelativeLayout(context) {

            @Override
            protected void dispatchDraw(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                float halfBorder = borderWidth / 2F;
                float containBorderRadius = radius;

                backgroundBounds.set(0, 0, width, height);
                canvas.drawRect(backgroundBounds, backgroundPaint);

                super.dispatchDraw(canvas);

                path.moveTo(0, 0);
                path.lineTo(containBorderRadius, 0);
                path.quadTo(0, 0, 0, containBorderRadius);
                path.lineTo(0, 0);

                path.moveTo(width, 0);
                path.lineTo(width - containBorderRadius, 0);
                path.quadTo(width, 0, width, containBorderRadius);
                path.lineTo(width, 0);

                path.moveTo(width, height);
                path.lineTo(width, height - containBorderRadius);
                path.quadTo(width, height, width - containBorderRadius, height);
                path.lineTo(width, height);

                path.moveTo(0, height);
                path.lineTo(0, height - containBorderRadius);
                path.quadTo(0, height, containBorderRadius, height);
                path.lineTo(0, height);

                canvas.drawPath(path, roundPaint);

                bounds.set(halfBorder, halfBorder, width - halfBorder, height - halfBorder);
                canvas.drawRoundRect(bounds, radius, radius, borderPaint);
            }
        };
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        return v;
    }

    public RelativeLayout topContainer() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_TOP);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(
                ruler.getW(topBottomPaddingOne),
                ruler.getW(leftRightPaddingOne),
                ruler.getW(topBottomPaddingOne),
                ruler.getW(leftRightPaddingOne));

        return v;
    }

    public ImageView icon() {
        LayoutParams params = new LayoutParams(
                ruler.getW(subheadIconSize),
                ruler.getW(subheadIconSize));
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_IN_PARENT);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        return v;
    }

    public ImageView arrow() {
        LayoutParams params = new LayoutParams(
                ruler.getW(subheadIconSize),
                ruler.getW(subheadIconSize));
        params.addRule(ALIGN_PARENT_RIGHT);
        params.addRule(CENTER_IN_PARENT);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        return v;
    }

    public TextView title(View leftView, View rightView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(RIGHT_OF, leftView.getId());
        params.addRule(LEFT_OF, rightView.getId());
        params.addRule(CENTER_VERTICAL);
        params.setMargins(ruler.getW(4.08), 0, 0, 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        subhead.style(v);

        return v;
    }

    public View titleBottomLine(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                (int) horizontalTwoHeight);
        params.addRule(ALIGN_BOTTOM, topView.getId());
        params.addRule(CENTER_HORIZONTAL);
        params.setMargins(
                ruler.getW(leftRightPaddingOne), 0,
                ruler.getW(leftRightPaddingOne), 0);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(horizontalTwoColor);

        return v;
    }

    public RelativeLayout bottomContainer(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(backgroundTwoColor);
        v.setPadding(
                0, ruler.getW(topBottomPaddingOne),
                0, ruler.getW(topBottomPaddingOne));

        return v;
    }

    public View twoValueTopLine(View alignTop) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                1);
        params.addRule(ALIGN_TOP, alignTop.getId());
        params.addRule(CENTER_HORIZONTAL);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._D9D9D9);

        return v;
    }

    public LinearLayout twoValueContainer() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        final Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ColorTable._D9D9D9);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);

        LinearLayout v = new LinearLayout(context) {

            @Override
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                int height = canvas.getHeight();
                int width = canvas.getWidth();

                canvas.drawLine(width / 2, 0, width / 2, height, linePaint);
            }
        };
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setGravity(Gravity.CENTER);
        v.setWeightSum(2);

        return v;
    }

    public RelativeLayout leftValueContainer() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public RelativeLayout leftCenterContainer() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public TextView leftValue() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        bodyTwo.style(v);

        return v;
    }

    public TextView leftText(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());
        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        note.style(v);

        return v;
    }


    public RelativeLayout rightValueContainer() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public RelativeLayout rightCenterContainer() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public TextView rightValue() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        bodyTwo.style(v);

        return v;
    }

    public TextView rightText(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());
        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        note.style(v);

        return v;
    }

    public RelativeLayout centerValueContainer(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(
                0, ruler.getW(topBottomPaddingTwo),
                0, ruler.getW(topBottomPaddingTwo));

        return v;
    }

    public RelativeLayout centerCenterContainer() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    public TextView centerValueText() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        headline.style(v);
        v.setTextColor(ColorTable._8DC41F);

        return v;
    }

    public TextView centerText(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());
        params.addRule(CENTER_HORIZONTAL);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        bodyOne.style(v);

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
            leftValueText.setTextColor(warningColor);
        }
        leftValueText.setText(leftValue + "");

        if (rightValue == 0) {
            rightValueText.setTextColor(ColorTable._999999);
        } else if (isChangeRightValueColor) {
            rightValueText.setTextColor(errorColor);
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
        setBorderColor(errorColor);
        if (isChangeCenterValueColor) {
            centerValueText.setTextColor(errorColor);
        }
    }

    public void changeOrangeBorder() {
        setBorderColor(warningColor);
        if (isChangeCenterValueColor) {
            centerValueText.setTextColor(warningColor);
        }
    }

    public void changeGreenBorder() {
        setBorderColor(activeColor);
        if (isChangeCenterValueColor) {
            centerValueText.setTextColor(activeColor);
        }
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        borderPaint.setStrokeWidth(borderWidth);
        invalidate();
    }

    public void setBorderColor(int color) {
        borderPaint.setColor(color);
        invalidate();
    }

    public void setTitleOnClickListener(OnClickListener event) {
        topContainer.setOnClickListener(event);
    }
}