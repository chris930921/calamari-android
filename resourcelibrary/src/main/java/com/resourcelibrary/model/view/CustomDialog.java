package com.resourcelibrary.model.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.FrameLayout.LayoutParams;

public class CustomDialog extends Dialog {
    /**
     * APP的環境變數
     */
    private Context context;
    /**
     * 此通知框的最大框架
     */
    public RelativeLayout Container;
    /**
     * 通知框的背景
     */
    public ImageView Background;

    /**
     * 通知框的設定、增加子介面
     *
     * @param context
     *            APP的環境變數
     */
    public CustomDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        Window window = getWindow();
        window.setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        addContainer();
    }

    /**
     * 增加背景的框架並設定
     */
    private void addContainer() {
        Container = new RelativeLayout(context);
        Container.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        addBackground();
        setContentView(Container);
    }

    /**
     * 增加背景的圖片控制項
     */
    private void addBackground() {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        Params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        Params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        Background = new ImageView(context);
        Background.setLayoutParams(Params);

        Container.addView(Background);
    }

    /**
     * 設定通知框的大小
     *
     * @param width
     *            寬
     * @param height
     *            高
     */
    public void setSize(int width, int height) {
        WindowManager.LayoutParams Params = getWindow().getAttributes();
        Params.width = width; // 寬度
        Params.height = height; // 高度
        getWindow().setAttributes(Params);
        // getWindow().setLayout(width, height);
    }
}