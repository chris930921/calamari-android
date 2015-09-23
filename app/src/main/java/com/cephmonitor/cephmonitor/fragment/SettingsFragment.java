package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cephmonitor.cephmonitor.ActivityLauncher;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.SettingsLayout;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;

public class SettingsFragment extends Fragment {
    public SettingsLayout layout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new SettingsLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        layout.languageDialog.setOkClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityLauncher.goLoginActivity(getActivity());
                LoginParams loginInfo = new LoginParams(getActivity());
                loginInfo.setIsLogin(false);
                getActivity().finish();
            }
        });
        layout.dateFormatsDialog.setSaveClick(null);
    }
}
