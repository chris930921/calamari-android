package com.resourcelibrary.model.view.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 5/26/2015.
 */
public class FloatLayoutLabel extends View {
    private int radius = 10;
    private RectF boxBounds;
    private Rect textBounds;
    private Paint rectPaint;
    private Paint textPaint;
    private int fillColor;
    private ArrayList<String> texts;

    private int horizonPadding;
    private int verticalPadding;

    private int leftMargin;
    private int topMargin;

    private static final String maxTextHeightExample = "Ij";

    public FloatLayoutLabel(Context context) {
        super(context);
        texts = new ArrayList<>();

        boxBounds = new RectF();
        textBounds = new Rect();

        fillColor = Color.parseColor("#39c0ed");

        rectPaint = new Paint();
        rectPaint.setColor(fillColor);
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.FILL);

        TextView textView = new TextView(context);
        textView.setTextSize(14);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textView.getTextSize());
        textPaint.setAntiAlias(true);
    }

    public void setData(ArrayList<String> texts) {
        this.texts = texts;
    }

    public void setPadding(int horizon, int vertical) {
        horizonPadding = horizon;
        verticalPadding = vertical;
    }

    public void setMargin(int left, int top) {
        leftMargin = left;
        topMargin = top;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int left = 0;
        int top = 0;

        for (int i = 0; i < texts.size(); i++) {
            String text = texts.get(i);

            textPaint.getTextBounds(text + maxTextHeightExample, 0, text.length() + maxTextHeightExample.length(), textBounds);
            int textWidth = (int) textPaint.measureText(text, 0, text.length());
            int textHeight = textBounds.height();

            int right = left + textWidth + horizonPadding * 2;
            int bottom = top + textHeight + verticalPadding * 2;

            if (right > width) {
                left = 0;
                top = bottom + topMargin;
                right = left + textWidth + horizonPadding * 2;
                bottom = top + textHeight + verticalPadding * 2;

                updateHeight(bottom);
            }

            boxBounds.set(left, top, right, bottom);
            canvas.drawRoundRect(boxBounds, radius, radius, rectPaint);

            int textLeft = left + (((right - left) - textWidth) / 2);
            int textBottom = bottom - (((bottom - top) - textHeight) / 2);
            canvas.drawText(text, textLeft, textBottom, textPaint);

            left = right + leftMargin;
        }
    }

    private void updateHeight(final int newHeight) {
        post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = getLayoutParams();
                params.height = newHeight;
                setLayoutParams(params);
            }
        });
    }
}
