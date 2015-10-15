package com.cephmonitor.cephmonitor.layout.listitem.reuse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class SettingCheckboxItem extends SettingItem {
    public WH ruler;
    public TextView filedTitle;
    public TextView filedSubTitle;
    public CheckBox checkbox;

    private DesignSpec designSpec;
    private TextViewStyle nameStyle;
    private TextViewStyle valueStyle;

    public SettingCheckboxItem(Context context) {
        super(context);
        ruler = new WH(getContext());
        designSpec = ThemeManager.getStyle(getContext());
        nameStyle = new TextViewStyle(designSpec.getStyle().getBodyTwo());
        valueStyle = new TextViewStyle(designSpec.getStyle().getBodyOne());

        checkbox = checkbox();
        filedTitle = filedTitle(checkbox);
        filedSubTitle = filedSubTitle(filedTitle);

        addView(checkbox);
        addView(filedTitle);
        addView(filedSubTitle);
    }

    protected CheckBox checkbox() {
        LayoutParams params = new LayoutParams(
                ruler.getW(designSpec.getIconSize().getLargeButton()),
                ruler.getW(designSpec.getIconSize().getLargeButton())
        );
        params.addRule(CENTER_VERTICAL);
        params.addRule(ALIGN_PARENT_RIGHT);

        CheckBox v = new CheckBox(getContext()) {
            Paint backgroundPen = new Paint(Paint.ANTI_ALIAS_FLAG);
            Paint checkPen = new Paint(Paint.ANTI_ALIAS_FLAG);
            Paint borderPen = new Paint(Paint.ANTI_ALIAS_FLAG);

            @Override
            protected void onDraw(Canvas canvas) {
                backgroundPen.setColor((isChecked()) ? designSpec.getPrimaryColors().getSecondary() : Color.TRANSPARENT);
                backgroundPen.setStyle(Paint.Style.FILL);

                float strokeWidth = canvas.getWidth() / 10F;

                checkPen.setColor((isChecked()) ? Color.WHITE : Color.TRANSPARENT);
                checkPen.setStyle(Paint.Style.STROKE);
                checkPen.setStrokeWidth(strokeWidth);

                borderPen.setColor(ColorTable._999999);
                borderPen.setStyle(Paint.Style.STROKE);
                borderPen.setStrokeWidth(strokeWidth);

                canvas.drawRect(strokeWidth, strokeWidth, canvas.getWidth() - strokeWidth, canvas.getHeight() - strokeWidth, borderPen);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPen);
                canvas.drawLine(
                        canvas.getWidth() * 0.1F, canvas.getHeight() * 0.5F,
                        canvas.getWidth() * 0.3F, canvas.getHeight() * 0.7F, checkPen);
                canvas.drawLine(
                        canvas.getWidth() * 0.3F, canvas.getHeight() * 0.7F,
                        canvas.getWidth() * 0.9F, canvas.getHeight() * 0.2F, checkPen);
            }
        };
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        nameStyle.style(v);

        return v;
    }

    protected TextView filedTitle(View rightView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.LEFT_OF, rightView.getId());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        nameStyle.style(v);

        return v;
    }

    protected TextView filedSubTitle(View topView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.BELOW, topView.getId());
        params.addRule(RelativeLayout.ALIGN_RIGHT, topView.getId());

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        valueStyle.style(v);

        return v;
    }

    public void setTitle(String content) {
        filedTitle.setText(content);
    }

    public void setSubTitle(String content) {
        filedSubTitle.setText(content);
    }
}
