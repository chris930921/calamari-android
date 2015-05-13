package com.cephmonitor.cephmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cephmonitor.cephmonitor.fragment.FragmentLauncher;
import com.cephmonitor.cephmonitor.layout.activity.MainLayout;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.view.dialog.CheckExitDialog;
import com.resourcelibrary.model.view.dialog.LoadingDialog;
import com.resourcelibrary.model.view.dialog.RoundSimpleSelectDialog;


public class MainActivity extends Activity {
    public MainLayout layout;
    public LoginParams loginInfo;
    public Activity activity;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new MainLayout(this);
        setContentView(layout);
        loginInfo = new LoginParams(this);
        activity = this;
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setTitle("Health");
        FragmentLauncher.goHealthFragment(activity);
        layout.health.setBackgroundResource(R.drawable.icon06);
        layout.health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.recoverAllButton();
                layout.health.setBackgroundResource(R.drawable.icon06);
                FragmentLauncher.goHealthFragment(activity);
            }
        });

        layout.usage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.recoverAllButton();
                layout.usage.setBackgroundResource(R.drawable.icon08);
            }
        });

        layout.performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.recoverAllButton();
                layout.performance.setBackgroundResource(R.drawable.icon10);
            }
        });

        layout.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoundSimpleSelectDialog dialog = new RoundSimpleSelectDialog(activity);
                dialog.setPosition(0, layout.bottomBar.getHeight());

                dialog.addItem("Node List", null);
                dialog.addItem("Pool List", null);
                dialog.addItem("Logs", null);
                dialog.addItem("Alert", null);
                dialog.addItem("Setting", null);
                dialog.addItem("Logout", null);

                dialog.show();
            }
        });
    }


    public void setTitle(String title) {
        layout.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        CheckExitDialog.create(activity).show();
    }

    public void showHealthDetailFragment() {
        FragmentLauncher.goHealthDetailFragment(activity);
        layout.bottomBar.setVisibility(View.GONE);
        layout.showBack(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentLauncher.goHealthFragment(activity);
                layout.bottomBar.setVisibility(View.VISIBLE);
                layout.hideBack();
            }
        });
    }
}