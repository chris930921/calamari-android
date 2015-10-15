package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.SettingDialog;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by chriske on 2015/9/20.
 */
public class SettingLanguageDialog extends SettingDialog {
    public TextView description;
    public WH ruler;

    private DesignSpec designSpec;
    private TextViewStyle descriptionStyle;

    public SettingLanguageDialog(Context context) {
        super(context);
        this.ruler = new WH(getContext());
        this.designSpec = ThemeManager.getStyle(getContext());
        descriptionStyle = new TextViewStyle(designSpec.getStyle().getBodyOne());

        description = description();
        setTitle(getContext().getString(R.string.settings_profile_language));
        addContentView(description);
        addButton(R.string.settings_dialog_cancel, ColorTable._666666, new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    private TextView description() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView v = new TextView(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setText(R.string.settings_language_dialog_description);
        v.setPadding(
                ruler.getW(designSpec.getMargin().getLeftRightOne()),
                ruler.getW(designSpec.getMargin().getLeftRightOne()),
                ruler.getW(designSpec.getMargin().getLeftRightOne()),
                ruler.getW(designSpec.getMargin().getLeftRightOne())
        );
        descriptionStyle.style(v);

        return v;
    }


    public void setOkClick(final OnClickListener event) {
        addButton(R.string.settings_dialog_ok, designSpec.getPrimaryColors().getPrimary(), new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                if (event != null) {
                    event.onClick(view);
                }
            }
        });
    }
}
