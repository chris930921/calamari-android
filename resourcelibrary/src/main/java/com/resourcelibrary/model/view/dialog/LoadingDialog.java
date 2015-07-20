package com.resourcelibrary.model.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.resourcelibrary.model.view.WH;

/**
 * Created by User on 4/16/2015.
 */
public class LoadingDialog extends RelativeLayout {
    public Context context;
    private Dialog dialog;
    private WH ruler;
    private ProgressBar progress;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        addView(progress = progress());

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    public ProgressBar progress() {
        LayoutParams params = new LayoutParams(ruler.getW(30), ruler.getW(30));

        ProgressBar v = new ProgressBar(context);
        v.setLayoutParams(params);
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

    public static void delayCancel(View v, final LoadingDialog dialog) {
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        }, 600);
    }
}
