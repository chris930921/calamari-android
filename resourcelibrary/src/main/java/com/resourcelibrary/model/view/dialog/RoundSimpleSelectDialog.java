package com.resourcelibrary.model.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resourcelibrary.R;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by User on 4/16/2015.
 */
public class RoundSimpleSelectDialog extends RelativeLayout {
    public Context context;
    private Dialog dialog;
    private WH ruler;
    private LinearLayout itemList;

    private Paint paint;
    private RectF bounds;
    private int radius;

    public RoundSimpleSelectDialog(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        radius = 30;

        paint = new Paint();
        bounds = new RectF();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#EFEFEF"));

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setBackgroundColor(Color.TRANSPARENT);

        addView(itemList = itemList());
    }

    private LinearLayout itemList() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        LinearLayout v = new LinearLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.VERTICAL);
        v.setGravity(Gravity.CENTER_HORIZONTAL);
        v.setPadding(ruler.getW(1.42), ruler.getW(1.42), ruler.getW(1.42), ruler.getW(1.42));

        return v;
    }

    public void addItem(String text, OnClickListener event) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ruler.getW(66.32), ruler.getH(7.22));
        params.weight = 1;

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        v.setTextColor(Color.parseColor("#434343"));
        v.setPadding(ruler.getW(4.08), 0, ruler.getW(4.08), 0);
        v.setText(text);
        v.setBackgroundResource(R.drawable.general_button);
        v.setOnClickListener(clickItem(event));

        itemList.addView(v);
    }

    public View.OnClickListener clickItem(final View.OnClickListener event) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                if (event != null) {
                    event.onClick(view);
                }
            }
        };
    }

    public void setPosition(int x, int y) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = x;
        params.y = y;
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        bounds.set(0, 0, width, height);
        canvas.drawRoundRect(bounds, radius, radius, paint);
        super.onDraw(canvas);
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
}
