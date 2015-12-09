package com.cephmonitor.cephmonitor.layout.dialog.reuse;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.container.RoundDialogContainer;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.tool.RefreshViewManager;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by User on 4/16/2015.
 */
public class SettingDialog extends RoundDialogContainer {
    private Dialog dialog;
    private WH ruler;
    private int borderWidth;

    public TextView title;
    public View bottomTitleLine;
    public FrameLayout dialogContentContainer;
    public View bottomContentLine;
    public LinearLayout buttonContainer;

    private DesignSpec designSpec;
    private TextViewStyle titleStyle;
    private TextViewStyle buttonStyle;
    private int dialogBackgroundColor;
    private int dialogWidth;
    private RefreshViewManager.Interface intermediary;

    public SettingDialog(Context context) {
        super(context);
        this.ruler = new WH(context);
        borderWidth = 3;
        designSpec = ThemeManager.getStyle(getContext());
        titleStyle = new TextViewStyle(designSpec.getStyle().getSubhead());
        buttonStyle = new TextViewStyle(designSpec.getStyle().getDefaultButton());
        dialogBackgroundColor = designSpec.getPrimaryColors().getBackgroundThree();
        dialogWidth = ruler.getW(100) - ruler.getW(designSpec.getMargin().getLeftRightOne()) * 2;
        intermediary = (RefreshViewManager.Interface) context;

        FrameLayout frameContainer = new FrameLayout(getContext());
        frameContainer.addView(this);

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(frameContainer);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(windowParams);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                dialogWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setBorderColor(ColorTable._D9D9D9);
        setBorderWidth(borderWidth);
        setRadius(10);
        setViewBackgroundColor(dialogBackgroundColor);

        title = title();
        bottomTitleLine = fillView(title);
        dialogContentContainer = dialogContentContainer(bottomTitleLine);
        bottomContentLine = fillView(dialogContentContainer);
        buttonContainer = buttonContainer(bottomContentLine);

        addView(title);
        addView(bottomTitleLine);
        addView(dialogContentContainer);
        addView(bottomContentLine);
        addView(buttonContainer);
    }

    private TextView title() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        final TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_HORIZONTAL);
        v.setPadding(
                0, ruler.getW(designSpec.getPadding().getTopBottomOne()),
                0, ruler.getW(designSpec.getPadding().getTopBottomOne()));
        titleStyle.style(v);

        return v;
    }

    private FrameLayout dialogContentContainer(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());

        FrameLayout v = new FrameLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private LinearLayout buttonContainer(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, topView.getId());

        LinearLayout v = new LinearLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setGravity(Gravity.CENTER_VERTICAL);

        return v;
    }

    private View fillView(View topView) {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, 3);
        params.addRule(RelativeLayout.BELOW, topView.getId());
        params.leftMargin = borderWidth;
        params.rightMargin = borderWidth;

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(designSpec.getPrimaryColors().getHorizontalTwo());

        return v;
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void cancel() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setTitle(String titleText) {
        title.setText(titleText);
    }

    public void addContentView(View v) {
        dialogContentContainer.addView(v);
    }

    public TextView addButton(final int resourceId, int color, OnClickListener event) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        final TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOnClickListener(event);
        v.setGravity(Gravity.CENTER_HORIZONTAL);
        v.setPadding(
                0, ruler.getW(designSpec.getPadding().getTopBottomOne()),
                0, ruler.getW(designSpec.getPadding().getTopBottomOne()));
        buttonStyle.style(v);
        v.setText(resourceId);
        v.setTextColor(color);

        buttonContainer.addView(buttonFillView());
        buttonContainer.addView(v);
        intermediary.refreshViewManager.addTask(new Runnable() {
            @Override
            public void run() {
                v.setText(resourceId);
            }
        });

        return v;
    }

    public void setTitleBottomLineColor(int color) {
        bottomTitleLine.setBackgroundColor(color);
    }

    public void setTitleTextColor(int color) {
        title.setTextColor(color);
    }

    public View buttonFillView() {
        LayoutParams params = new LayoutParams(
                3, ViewGroup.LayoutParams.MATCH_PARENT);

        View v = new View(getContext());
        v.setLayoutParams(params);
        v.setBackgroundColor(designSpec.getPrimaryColors().getHorizontalTwo());

        return v;
    }


}
