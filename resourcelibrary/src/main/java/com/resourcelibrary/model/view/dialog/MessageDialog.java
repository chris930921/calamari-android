package com.resourcelibrary.model.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by User on 4/16/2015.
 */
public class MessageDialog extends RelativeLayout {
    public Context context;
    private Dialog dialog;
    private WH ruler;

    private TextView title;
    private TextView content;
    private Button confirm;

    private OnClickListener otherClickAction;

    public MessageDialog(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ruler.getW(88);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        addView(title = title());
        addView(content = content(title));
        addView(confirm = confirm(content));

        confirm.setOnClickListener(clickConfirm);
    }

    public TextView title() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(8.43));
        params.addRule(ALIGN_PARENT_TOP);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(25));
        v.setBackgroundColor(Color.parseColor("#e63427"));
        v.setTextColor(Color.parseColor("#ffffff"));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setPadding(ruler.getW(5.10), 0, ruler.getW(5.10), 0);
        return v;
    }

    public TextView content(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, relativeView.getId());

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(25));
        v.setPadding(ruler.getW(5.10), ruler.getH(3.01), ruler.getW(5.10), ruler.getH(3.01));
        v.setBackgroundColor(Color.WHITE);
        v.setTextColor(Color.parseColor("#434343"));
        return v;
    }

    public Button confirm(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(8.43));
        params.addRule(BELOW, relativeView.getId());

        Button v = new Button(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(25));
        v.setBackgroundColor(Color.parseColor("#efefef"));
        v.setTextColor(Color.parseColor("#666666"));
        v.setGravity(Gravity.CENTER);
        return v;
    }

    public void show(String title, String content, String confirm) {
        if (dialog != null && !dialog.isShowing()) {
            this.title.setText(title);
            this.content.setText(content);
            this.confirm.setText(confirm);
            dialog.show();
        }
    }

    public void cancel() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private OnClickListener clickConfirm = new OnClickListener() {
        private View view;

        @Override
        public void onClick(View view) {
            this.view = view;
            checkShowing();
        }

        private void checkShowing() {
            if (dialog != null && dialog.isShowing()) {
                cancel();
                checkOtherAction();
            }
        }

        private void checkOtherAction() {
            if (otherClickAction != null) {
                otherClickAction.onClick(view);
            }
        }
    };

    public void setOnConfirmClickListener(OnClickListener otherClickAction) {
        this.otherClickAction = otherClickAction;
    }
}
