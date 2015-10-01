package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.SettingDialog;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.LoginLanguageChoiceItem;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.ceph.constant.SettingConstant;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.tool.RefreshViewManager;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by chriske on 2015/9/20.
 */
public class LoginLanguageDialog extends SettingDialog {
    public WH ruler;
    public LinearLayout dateContainer;
    private DesignSpec designSpec;
    private LoginLanguageChoiceItem english;
    private LoginLanguageChoiceItem chinese;
    private LoginLanguageChoiceItem japanese;
    private int selectedId;
    private LoginLanguageChoiceItem current;
    private RefreshViewManager.Interface intermediary;

    public LoginLanguageDialog(Context context) {
        super(context);
        this.ruler = new WH(getContext());
        this.designSpec = ThemeManager.getStyle(getContext());
        intermediary = (RefreshViewManager.Interface) context;

        dateContainer = dateContainer();
        english = english();
        chinese = chinese();
        japanese = japanese();

        dateContainer.addView(english);
        dateContainer.addView(fillView());
        dateContainer.addView(chinese);
        dateContainer.addView(fillView());
        dateContainer.addView(japanese);

        setTitle(getContext().getString(R.string.settings_profile_formats));
        addContentView(dateContainer);
        addButton(R.string.settings_dialog_cancel, ColorTable._666666, new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        RefreshViewManager.Interface intermediary = (RefreshViewManager.Interface) context;
        intermediary.refreshViewManager.addTask(new Runnable() {
            @Override
            public void run() {
                setTitle(getContext().getString(R.string.settings_profile_formats));
                english.setName(getContext().getString(R.string.settings_language_dialog_option_english));
                chinese.setName(getContext().getString(R.string.settings_language_dialog_option_chinese));
                japanese.setName(getContext().getString(R.string.settings_language_dialog_option_japanese));
            }
        });
    }

    private LinearLayout dateContainer() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout v = new LinearLayout(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.VERTICAL);
        v.setGravity(Gravity.CENTER);

        return v;
    }

    private View fillView() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, 3);

        View v = new View(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(designSpec.getPrimaryColors().getHorizontalTwo());

        return v;
    }

    private LoginLanguageChoiceItem english() {
        LoginLanguageChoiceItem v = getChoiceItem(SettingConstant.LANGUAGE_ENGLISH, R.string.settings_language_dialog_option_english);
        return v;
    }

    private LoginLanguageChoiceItem chinese() {
        return getChoiceItem(SettingConstant.LANGUAGE_CHINESE, R.string.settings_language_dialog_option_chinese);
    }

    private LoginLanguageChoiceItem japanese() {
        return getChoiceItem(SettingConstant.LANGUAGE_JAPANESE, R.string.settings_language_dialog_option_japanese);
    }

    private LoginLanguageChoiceItem getChoiceItem(final int id, int resource) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        LoginLanguageChoiceItem v = new LoginLanguageChoiceItem(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setName(getContext().getString(resource));
        v.setTag(id);
        v.setFlag(id);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                english.filedValue.setState(false);
                chinese.filedValue.setState(false);
                japanese.filedValue.setState(false);
                current = (LoginLanguageChoiceItem) view;
                current.filedValue.setState(true);
                selectedId = (int) current.getTag();
            }
        });

        return v;
    }

    @Override
    public void show() {
        SettingStorage settingStorage = new SettingStorage(getContext());
        selectedId = settingStorage.getLanguage();
        english.filedValue.setState(english.getTag() == selectedId);
        chinese.filedValue.setState(chinese.getTag() == selectedId);
        japanese.filedValue.setState(japanese.getTag() == selectedId);
        super.show();
    }

    public int getSelectedId() {
        return selectedId;
    }

    public void setSaveClick(final OnClickListener event) {
        addButton(R.string.settings_dialog_save, designSpec.getPrimaryColors().getPrimary(), new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                if (current == null)
                    return;

                if (event != null) {
                    event.onClick(view);
                }
            }
        });
    }
}
