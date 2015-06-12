package com.cephmonitor.cephmonitor;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.cephmonitor.cephmonitor.fragment.FragmentLauncher;
import com.cephmonitor.cephmonitor.layout.activity.MainLayout;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.view.dialog.CheckExitDialog;


public class NotificationActivity extends Activity implements InitFragment.Style {
    public MainLayout layout;
    public LoginParams loginInfo;
    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new MainLayout(this);
        setContentView(layout);
        loginInfo = new LoginParams(this);
        activity = this;

        FragmentLauncher.goHealthFragment(activity);
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            CheckExitDialog.create(activity).show();
        }
    }


    public void setTitle(String title) {
        layout.setTitle(title);
    }

    @Override
    public void changeFragmentStyle(Fragment fragment) {

    }
}