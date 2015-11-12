package com.cephmonitor.cephmonitor.layout.component.calculator;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by chriske on 2015/10/9.
 */
public class CalculatorLayout extends RelativeLayout {
    private WH ruler;
    private DesignSpec designSpec;
    private TextViewStyle styleBodyTwo;
    private TextViewStyle styleLargeButton;
    private TextViewStyle styleNote;

    public RelativeLayout fieldResult;
    public TextView unitHeightFillView;
    public TextView fieldValue;
    public TextView fieldUnit;
    public RelativeLayout fieldCondition;
    public RelativeLayout numberBoard;
    public TextView numberEight;
    public TextView numberSeven;
    public TextView numberNine;
    public TextView numberFive;
    public TextView numberFour;
    public TextView numberSix;
    public TextView numberTwo;
    public TextView numberOne;
    public TextView numberThree;
    public TextView numberZero;
    public TextView buttonClear;

    public View lineBetweenColumnOneTwo;
    public View lineBetweenColumnTwoThree;
    public View lineBetweenRowZeroOne;
    public View lineBetweenRowOneTwo;
    public View lineBetweenRowTwoThree;
    public View lineBetweenRowThreeFour;

    public CalculatorLayout(Context context) {
        super(context);
        ruler = new WH(getContext());
        designSpec = ThemeManager.getStyle(getContext());
        styleBodyTwo = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        styleLargeButton = new TextViewStyle(designSpec.getStyle().getLargeButton());
        styleNote = new TextViewStyle(designSpec.getStyle().getNote());

        setPadding(
                (int) designSpec.getHorizontal().getHorizontalOneHeight(), 0,
                (int) designSpec.getHorizontal().getHorizontalOneHeight(), 0);

        fieldResult = fieldResult();
        unitHeightFillView = unitHeightFillView();
        fieldValue = fieldValue(unitHeightFillView);
        fieldUnit = fieldUnit(fieldValue, unitHeightFillView);
        fieldCondition = fieldCondition(fieldResult);
        numberBoard = numberBoard(fieldCondition);
        numberEight = numberEight();
        numberSeven = numberSeven(numberEight);
        numberNine = numberNine(numberEight);
        numberFive = numberFive(numberEight);
        numberFour = numberFour(numberFive);
        numberSix = numberSix(numberFive);
        numberTwo = numberTwo(numberFive);
        numberOne = numberOne(numberTwo);
        numberThree = numberThree(numberTwo);
        buttonClear = buttonClear(numberTwo);
        numberZero = numberZero(buttonClear);
        lineBetweenColumnOneTwo = getTableVerticalLine(numberSeven, numberOne);
        lineBetweenColumnTwoThree = getTableVerticalLine(numberEight, numberZero);
        lineBetweenRowZeroOne = getTableHorizontalLine(numberSeven, numberNine);
        lineBetweenRowOneTwo = getTableHorizontalLine(numberFour, numberSix);
        lineBetweenRowTwoThree = getTableHorizontalLine(numberOne, numberThree);
        lineBetweenRowThreeFour = getTableHorizontalLine(numberZero, buttonClear);

        addView(fieldResult);
        fieldResult.addView(unitHeightFillView);
        fieldResult.addView(fieldUnit);
        fieldResult.addView(fieldValue);
        addView(fieldCondition);
        addView(numberBoard);
        numberBoard.addView(numberEight);
        numberBoard.addView(numberSeven);
        numberBoard.addView(numberNine);
        numberBoard.addView(numberFive);
        numberBoard.addView(numberFour);
        numberBoard.addView(numberSix);
        numberBoard.addView(numberTwo);
        numberBoard.addView(numberOne);
        numberBoard.addView(numberThree);
        numberBoard.addView(buttonClear);
        numberBoard.addView(numberZero);

        numberBoard.addView(lineBetweenColumnOneTwo);
        numberBoard.addView(lineBetweenColumnTwoThree);
        numberBoard.addView(lineBetweenRowZeroOne);
        numberBoard.addView(lineBetweenRowOneTwo);
        numberBoard.addView(lineBetweenRowTwoThree);
        numberBoard.addView(lineBetweenRowThreeFour);
    }

    protected RelativeLayout fieldResult() {
        return getRelativeContainer(null);
    }

    protected TextView unitHeightFillView() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(ALIGN_PARENT_RIGHT);

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        styleNote.style(v);
        v.setText("UNIT");
        v.setVisibility(INVISIBLE);

        return v;
    }

    protected TextView fieldValue(View rightView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(LEFT_OF, rightView.getId());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        styleBodyTwo.style(v);
        v.setText("NUM");

        return v;
    }

    protected TextView fieldUnit(View leftView, View backgroundView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(ALIGN_LEFT, backgroundView.getId());
        params.addRule(ALIGN_RIGHT, backgroundView.getId());
        params.addRule(ALIGN_BOTTOM, leftView.getId());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        styleNote.style(v);
        v.setText("UNIT");

        return v;
    }

    protected RelativeLayout fieldCondition(View topView) {
        return getRelativeContainer(topView);
    }

    protected RelativeLayout numberBoard(View topView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(0, ruler.getW(designSpec.getPadding().getTopBottomOne()), 0, 0);

        return v;
    }

    protected TextView numberEight() {
        return getCenterNumber(R.string.other_calculater_number_eight, null, 8);
    }

    protected TextView numberSeven(View centerView) {
        return getLeftOfCenterNumber(R.string.other_calculater_number_seven, centerView, 7);
    }

    protected TextView numberNine(View centerView) {
        return getRightOfCenterNumber(R.string.other_calculater_number_nine, centerView, 9);
    }

    protected TextView numberFive(View topView) {
        return getCenterNumber(R.string.other_calculater_number_five, topView, 5);
    }

    protected TextView numberFour(View centerView) {
        return getLeftOfCenterNumber(R.string.other_calculater_number_four, centerView, 4);
    }

    protected TextView numberSix(View centerView) {
        return getRightOfCenterNumber(R.string.other_calculater_number_six, centerView, 6);
    }

    protected TextView numberTwo(View topView) {
        return getCenterNumber(R.string.other_calculater_number_two, topView, 2);
    }

    protected TextView numberOne(View centerView) {
        return getLeftOfCenterNumber(R.string.other_calculater_number_one, centerView, 1);
    }

    protected TextView numberThree(View centerView) {
        return getRightOfCenterNumber(R.string.other_calculater_number_three, centerView, 3);
    }

    protected TextView buttonClear(View topView) {
        TextView v = getNumberButton(R.string.other_calculater_button_clear, -1);
        LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.addRule(BELOW, topView.getId());
        params.addRule(ALIGN_PARENT_RIGHT);
        v.setLayoutParams(params);

        return v;
    }

    protected TextView numberZero(View rightView) {
        TextView v = getNumberButton(R.string.other_calculater_number_zero, 0);
        LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.addRule(CENTER_HORIZONTAL);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.addRule(ALIGN_TOP, rightView.getId());
        params.addRule(LEFT_OF, rightView.getId());
        params.addRule(ALIGN_PARENT_LEFT);

        return v;
    }

    private RelativeLayout getRelativeContainer(View topView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        if (topView == null) {
            params.addRule(ALIGN_PARENT_TOP);
        } else {
            params.addRule(BELOW, topView.getId());
        }

        RelativeLayout v = new RelativeLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setPadding(
                ruler.getW(designSpec.getPadding().getLeftRightOne()),
                ruler.getW(designSpec.getPadding().getLeftRightOne()),
                ruler.getW(designSpec.getPadding().getTopBottomOne()),
                0
        );

        return v;
    }

    private TextView getCenterNumber(int resourceText, View topView, int number) {
        TextView v = getNumberButton(resourceText, number);
        LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.addRule(CENTER_HORIZONTAL);
        if (topView == null) {
            params.addRule(ALIGN_PARENT_TOP);
        } else {
            params.addRule(BELOW, topView.getId());
        }
        v.setLayoutParams(params);

        return v;
    }

    private TextView getLeftOfCenterNumber(int resourceText, View centerView, int number) {
        TextView v = getNumberButton(resourceText, number);
        LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.addRule(LEFT_OF, centerView.getId());
        params.addRule(ALIGN_TOP, centerView.getId());
        params.addRule(ALIGN_PARENT_LEFT);
        v.setLayoutParams(params);

        return v;
    }

    private TextView getRightOfCenterNumber(int resourceText, View centerView, int number) {
        TextView v = getNumberButton(resourceText, number);
        LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.addRule(RIGHT_OF, centerView.getId());
        params.addRule(ALIGN_TOP, centerView.getId());
        params.addRule(ALIGN_PARENT_RIGHT);
        v.setLayoutParams(params);

        return v;
    }

    private TextView getNumberButton(int resourceText, int number) {
        LayoutParams params = new LayoutParams(
                ruler.getW(31),
                ruler.getW(23)
        );

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(ColorTable._D5D5D5));
        states.addState(new int[]{android.R.attr.state_focused}, new ColorDrawable(ColorTable._D5D5D5));
        states.addState(new int[]{}, new ColorDrawable(designSpec.getPrimaryColors().getBackgroundOne()));

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackground(states);
        v.setGravity(Gravity.CENTER);
        v.setText(resourceText);
        v.setTag(number);
        v.setTextColor(ColorTable._666666);
        styleLargeButton.style(v);

        return v;
    }

    private View getTableHorizontalLine(View leftBelowView, View rightBelowView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                (int) designSpec.getHorizontal().getHorizontalOneHeight()
        );
        params.addRule(ALIGN_LEFT, leftBelowView.getId());
        params.addRule(ALIGN_RIGHT, rightBelowView.getId());
        params.addRule(ALIGN_TOP, leftBelowView.getId());

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(designSpec.getPrimaryColors().getHorizontalOne());

        return v;
    }

    private View getTableVerticalLine(View topRightOfView, View bottomRightOfView) {
        LayoutParams params = new LayoutParams(
                (int) designSpec.getHorizontal().getHorizontalOneHeight(),
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(ALIGN_TOP, topRightOfView.getId());
        params.addRule(ALIGN_BOTTOM, bottomRightOfView.getId());
        params.addRule(ALIGN_RIGHT, topRightOfView.getId());

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(designSpec.getPrimaryColors().getHorizontalOne());

        return v;
    }
}
