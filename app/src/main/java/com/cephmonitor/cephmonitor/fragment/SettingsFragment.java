package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cephmonitor.cephmonitor.ActivityLauncher;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.fragment.SettingsLayout;
import com.cephmonitor.cephmonitor.model.database.StoreNotifications;
import com.cephmonitor.cephmonitor.model.database.data.RemoveResolvedData;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.logic.ApiSettingData;
import com.cephmonitor.cephmonitor.model.network.RemoteSettingToLocal;
import com.resourcelibrary.model.network.api.MutipleCookieHttpStack;
import com.resourcelibrary.model.network.api.ceph.CephPostRequest;
import com.resourcelibrary.model.network.api.ceph.object.ApiV2UserMeData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ApiV1UserMeRequest;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

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
        taskQueue = Volley.newRequestQueue(getActivity(), new MutipleCookieHttpStack());
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        RemoteSettingToLocal remoteSettingToLocal = new RemoteSettingToLocal(getActivity(), new LoginParams(getActivity()));
        remoteSettingToLocal.access(new RemoteSettingToLocal.AccessListener() {
            @Override
            public void success(ApiSettingData data) {
                settingStorage = new SettingStorage(getActivity());
                layout.languageItem.setValue(getString(settingStorage.getLanguageResource()));
                layout.dateFormatsItem.setValue(getString(settingStorage.getDateFormatsResource()));
                layout.notificationsItem.checkbox.setChecked(settingStorage.getNotifications());
                layout.emailNotificationsItem.checkbox.setChecked(settingStorage.getEnableEmailNotify());
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
                        final boolean check = !layout.emailNotificationsItem.checkbox.isChecked();
                        String value = check ? "1" : "0";
                        start("enable_email_notify", value, "api/v1/user/me/email/notify", new Runnable() {
                            @Override
                            public void run() {
                                layout.emailNotificationsItem.checkbox.setChecked(check);
                                settingStorage.setEnableEmailNotify(check);
                            }
                        });
                    }
                });
                layout.timePeriod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentLauncher.goTimePeriodFragment(getActivity(), null);
                    }
                });
                loadingDialog.cancel();
            }

            @Override
            public void fail(VolleyError volleyError) {
                Toast.makeText(getActivity(), "Load failed.", Toast.LENGTH_SHORT).show();
                loadingDialog.cancel();
            }
        });

        ApiV1UserMeRequest request = new ApiV1UserMeRequest(getActivity());
        request.setRequestParams(new LoginParams(getActivity()));
        request.request(new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    ApiV2UserMeData data = new ApiV2UserMeData(s);
                    String result = getString(R.string.settings_alert_email_notifications_description) + " " + data.getEmail();
                    layout.emailNotificationsItem.setSubTitle(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }

    private RequestQueue taskQueue;
    private LoadingDialog loadingDialog;

    public void start(String resourceName, String value, String behindUrl, final Runnable task) {
        loadingDialog.show();
        LoginParams params = new LoginParams(getActivity());
        String url = "http://" + params.getHost() + ":" + params.getPort() + "/" + behindUrl;
        JSONObject object = new JSONObject();
        try {
            object.put(resourceName, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String rawData = object.toString();
        taskQueue.add(new CephPostRequest(params.getSession(), params.getCsrfToken(), rawData, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                loadingDialog.cancel();
                task.run();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "Saved failed.", Toast.LENGTH_SHORT).show();
                loadingDialog.cancel();
            }
        }));
    }
}
