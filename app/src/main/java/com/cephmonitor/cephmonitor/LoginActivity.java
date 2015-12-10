package com.cephmonitor.cephmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.layout.activity.LoginLayout;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.LoginFailDialog;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.LoginLanguageDialog;
import com.cephmonitor.cephmonitor.model.file.io.LoginAutoCompleteStorage;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.logic.LanguageConfig;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.cephmonitor.cephmonitor.model.network.CheckVolleyError;
import com.cephmonitor.cephmonitor.model.tool.RefreshViewManager;
import com.cephmonitor.cephmonitor.service.ServiceLauncher;
import com.resourcelibrary.model.logic.emptycheck.EmptyChecker;
import com.resourcelibrary.model.logic.emptycheck.OnNoValueAction;
import com.resourcelibrary.model.network.api.RequestVolleyTask;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.LoginPostRequest;
import com.resourcelibrary.model.view.dialog.CheckExitDialog;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

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
    private LoginAutoCompleteStorage autoCompleteStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingStorage = new SettingStorage(this);
        languageConfig = new LanguageConfig(this);
        languageConfig.setLocale(settingStorage.getLanguage());
        autoCompleteStorage = new LoginAutoCompleteStorage(this);

        ServiceLauncher.startLooperService(this);
        RequestVolleyTask.enableFakeValue(BuildConfig.IS_LOCALHOST);

        activity = this;
        loginInfo = new LoginParams(this);
        loadingDialog = new LoadingDialog(this);
        if (loginInfo.isLogin()) {
            ActivityLauncher.goMainActivity(activity);
            loadingDialog.cancel();
            activity.finish();
            return;
        }

        layout = new LoginLayout(this);
        setContentView(layout);

        emptyChecker = new EmptyChecker();
        params = new LoginParams(this);
        loginLanguageDialog = new LoginLanguageDialog(this);
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
        refreshAutoComplete();

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

    private void refreshAutoComplete() {
        ArrayAdapter<String> hostKeywordGroup = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                autoCompleteStorage.getKeywordGroup(LoginAutoCompleteStorage.HOST));
        ArrayAdapter<String> portKeywordGroup = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                autoCompleteStorage.getKeywordGroup(LoginAutoCompleteStorage.PORT));
        ArrayAdapter<String> accountKeywordGroup = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                autoCompleteStorage.getKeywordGroup(LoginAutoCompleteStorage.ACCOUNT));

        layout.host.setAdapter(hostKeywordGroup);
        layout.port.setAdapter(portKeywordGroup);
        layout.name.setAdapter(accountKeywordGroup);
    }

    private View.OnClickListener clickSignIn() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteStorage.addKeyword(LoginAutoCompleteStorage.HOST, layout.host.getText().toString());
                autoCompleteStorage.addKeyword(LoginAutoCompleteStorage.PORT, layout.port.getText().toString());
                autoCompleteStorage.addKeyword(LoginAutoCompleteStorage.ACCOUNT, layout.name.getText().toString());
                refreshAutoComplete();

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

            new CheckVolleyError(activity).setEvent(CheckVolleyError.ERROR_TIME_OUT, new CheckVolleyError.OnError() {
                @Override
                public void onError(String responseBody) {
                    showServerErrorDialog();
                }
            }).setEvent(CheckVolleyError.ERROR_SERVER_ERROR, new CheckVolleyError.OnError() {
                @Override
                public void onError(String responseBody) {
                    if (responseBody.contains("No authentication challenges found")) {
                        showLoginFailDialog();
                    } else {
                        showServerErrorDialog();
                    }
                }
            }).setEvent(CheckVolleyError.ERROR_404, new CheckVolleyError.OnError() {
                @Override
                public void onError(String responseBody) {
                    showServerErrorDialog();
                }
            }).setEvent(CheckVolleyError.ERROR_UNKNOWN, new CheckVolleyError.OnError() {
                @Override
                public void onError(String responseBody) {
                    showLoginFailDialog();
                }
            }).done(volleyError);
        }
    };

    private void showServerErrorDialog() {
        LoginFailDialog dialog = new LoginFailDialog(activity);
        dialog.setConfirmClickEvent(null);
        dialog.setContent(getResources().getString(R.string.login_fail_server_error));
        dialog.show();
    }

    private void showLoginFailDialog() {
        LoginFailDialog dialog = new LoginFailDialog(activity);
        dialog.setConfirmClickEvent(null);
        dialog.setContent(getResources().getString(R.string.login_fail_sing_in));
        dialog.show();
    }

    private void showNoValueDialog(int noValueInputNameResource) {
        String content = getString(noValueInputNameResource) + getResources().getString(R.string.login_fail_content);

        LoginFailDialog dialog = new LoginFailDialog(activity);
        dialog.setConfirmClickEvent(clickNoValueConfirm);
        dialog.setContent(content);
        dialog.show();

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
