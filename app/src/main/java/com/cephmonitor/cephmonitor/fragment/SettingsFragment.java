package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cephmonitor.cephmonitor.ActivityLauncher;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.SettingsLayout;
import com.cephmonitor.cephmonitor.model.database.StoreNotifications;
import com.cephmonitor.cephmonitor.model.database.data.RemoveResolvedData;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;

public class SettingsFragment extends Fragment {
    public SettingsLayout layout;
    private SettingStorage settingStorage;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new SettingsLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        settingStorage = new SettingStorage(getActivity());
        layout.languageItem.setValue(getString(settingStorage.getLanguageResource()));
        layout.dateFormatsItem.setValue(getString(settingStorage.getDateFormatsResource()));
        layout.notificationsItem.checkbox.setChecked(settingStorage.getNotifications());
        layout.autoDeleteItem.checkbox.setChecked(settingStorage.getAutoDelete());

        layout.languageDialog.setOkClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityLauncher.goLoginActivity(getActivity());
                LoginParams loginInfo = new LoginParams(getActivity());
                loginInfo.setIsLogin(false);
                getActivity().finish();
            }
        });
        layout.dateFormatsDialog.setSaveClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = layout.dateFormatsDialog.getSelectedId();
                settingStorage.setDateFormats(id);
                layout.dateFormatsItem.setValue(getString(settingStorage.getDateFormatsResource()));
            }
        });
        layout.alertTriggers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentLauncher.goAlertTriggerFragment(getActivity(), null);
            }
        });
        layout.notificationsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = !layout.notificationsItem.checkbox.isChecked();
                layout.notificationsItem.checkbox.setChecked(check);
                settingStorage.setNotifications(check);
            }
        });
        layout.autoDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = !layout.autoDeleteItem.checkbox.isChecked();
                layout.autoDeleteItem.checkbox.setChecked(check);
                settingStorage.setAutoDelete(check);
                if (check) {
                    StoreNotifications store = new StoreNotifications(getActivity());
                    SQLiteDatabase database = store.getWritableDatabase();
                    RemoveResolvedData removeResolvedData = new RemoveResolvedData();
                    removeResolvedData.remove(database);
                }
            }
        });
        layout.emailNotificationsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = !layout.emailNotificationsItem.checkbox.isChecked();
                layout.emailNotificationsItem.checkbox.setChecked(check);
            }
        });
        layout.timePeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentLauncher.goTimePeriodFragment(getActivity(), null);
            }
        });
    }
}
