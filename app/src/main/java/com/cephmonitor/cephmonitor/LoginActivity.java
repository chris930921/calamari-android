package com.cephmonitor.cephmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.layout.activity.LoginLayout;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.LoginLanguageDialog;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.logic.LanguageConfig;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.cephmonitor.cephmonitor.model.tool.RefreshViewManager;
import com.cephmonitor.cephmonitor.service.ServiceLauncher;
import com.resourcelibrary.model.logic.emptycheck.EmptyChecker;
import com.resourcelibrary.model.logic.emptycheck.OnNoValueAction;
import com.resourcelibrary.model.network.api.RequestVolleyTask;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.LoginPostRequest;
import com.resourcelibrary.model.view.dialog.CheckExitDialog;
import com.resourcelibrary.model.view.dialog.LoadingDialog;
import com.resourcelibrary.model.view.dialog.MessageDialog;

import java.util.ArrayList;


public class LoginActivity extends Activity implements RefreshViewManager.Interface {
    public LoginLayout layout;
    public LoginParams loginInfo;
    public EmptyChecker emptyChecker;
    public Activity activity;
    private LoginParams params;
    private LoadingDialog loadingDialog;
    private SettingStorage settingStorage;
    private LoginLanguageDialog loginLanguageDialog;
    private LanguageConfig languageConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingStorage = new SettingStorage(this);
        languageConfig = new LanguageConfig(this);
        languageConfig.setLocale(settingStorage.getLanguage());
        layout = new LoginLayout(this);
        setContentView(layout);

        loginInfo = new LoginParams(this);
        emptyChecker = new EmptyChecker();
        activity = this;
        params = new LoginParams(this);
        loadingDialog = new LoadingDialog(this);

        loginLanguageDialog = new LoginLanguageDialog(this);

        ServiceLauncher.startLooperService(this);
        if (loginInfo.isLogin()) {
            ActivityLauncher.goMainActivity(activity);
            loadingDialog.cancel();
            activity.finish();
        }
        RequestVolleyTask.enableFakeValue(BuildConfig.IS_LOCALHOST);
        loginLanguageDialog.setSaveClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = loginLanguageDialog.getSelectedId();
                settingStorage.setLanguage(id);
                languageConfig.setLocale(id);
                layout.language.setLanguage(id);
                refreshViewManager.refresh();
            }
        });
//        deleteDatabase(StoreNotifications.DB_NAME);
    }

    @Override
    protected void onResume() {
        super.onResume();

        emptyChecker.put(String.valueOf(R.string.login_host), layout.host, noValueInputAction);
        emptyChecker.put(String.valueOf(R.string.login_port), layout.port, noValueInputAction);
        emptyChecker.put(String.valueOf(R.string.login_name), layout.name, noValueInputAction);
        emptyChecker.put(String.valueOf(R.string.login_password), layout.password, noValueInputAction);

        layout.host.setText(loginInfo.getHost());
        layout.port.setText(loginInfo.getPort());
        layout.name.setText(loginInfo.getName());
        layout.password.setText(loginInfo.getPassword());
        layout.language.setLanguage(settingStorage.getLanguage());
        layout.language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginLanguageDialog.show();
            }
        });

        layout.signIn.setOnClickListener(clickSignIn());
        refreshViewManager.refresh();
    }

    private View.OnClickListener clickSignIn() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> noValueField = emptyChecker.checkEmpty();
                if (noValueField.size() == 0) {
                    requestLoginPost();
                } else {
                    int resourceId = Integer.parseInt(noValueField.get(0));
                    showNoValueDialog(resourceId);
                }
            }
        };
    }

    private void requestLoginPost() {
        params.setHost(layout.host.getText().toString().trim());
        params.setPort(layout.port.getText().toString().trim());
        params.setName(layout.name.getText().toString().trim());
        params.setPassword(layout.password.getText().toString().trim());

        LoginPostRequest spider = new LoginPostRequest(this);
        spider.setRequestParams(params);
        spider.request(successLoginPost, failLoginPost);

        loadingDialog.show();
    }

    private AnalyzeListener<String> successLoginPost = new AnalyzeListener<String>() {
        @Override
        public boolean doInBackground(String o) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public void onPostExecute() {
            ActivityLauncher.goMainActivity(activity);
            loginInfo.setIsLogin(true);
            loadingDialog.cancel();
            activity.finish();
        }
    };

    public final Response.ErrorListener failLoginPost = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            loadingDialog.cancel();
            params.failLogin();
            showLoginErrorDialog();
        }
    };

    private void showLoginErrorDialog() {
        MessageDialog dialog = new MessageDialog(activity);
        dialog.setOnConfirmClickListener(null);
        String title = getResources().getString(R.string.login_fail_title);
        String content = getResources().getString(R.string.login_fail_sing_in);
        String confirm = getResources().getString(R.string.login_fail_confirm);
        dialog.show(title, content, confirm);
    }

    private void showNoValueDialog(int noValueInputNameResource) {
        MessageDialog dialog = new MessageDialog(activity);
        dialog.setOnConfirmClickListener(clickNoValueConfirm);
        String title = getResources().getString(R.string.login_fail_title);
        String content = getString(noValueInputNameResource) + getResources().getString(R.string.login_fail_content);
        String confirm = getResources().getString(R.string.login_fail_confirm);
        dialog.show(title, content, confirm);
    }

    private View.OnClickListener clickNoValueConfirm = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            emptyChecker.executeNoValueViewAction(0);
        }
    };

    private OnNoValueAction noValueInputAction = new OnNoValueAction() {
        @Override
        public void onAction(View v) {
            layout.setNoValueInput(v);
        }
    };

    @Override
    public void onBackPressed() {
        CheckExitDialog.create(activity).show();
    }
}
