package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.ActivityLauncher;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.fragment.SettingsLayout;
import com.cephmonitor.cephmonitor.model.database.StoreNotifications;
import com.cephmonitor.cephmonitor.model.database.data.RemoveResolvedData;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.file.io.SettingUpdateThread;
import com.cephmonitor.cephmonitor.model.network.remotesetting.api.ApiV1UserMeEmailNotifyPost;
import com.cephmonitor.cephmonitor.model.network.remotesetting.data.ApiV1UserMeAlertRuleGetData;
import com.resourcelibrary.model.network.api.ceph.object.ApiV2UserMeData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ApiV1UserMeRequest;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;

public class SettingsFragment extends Fragment {
    public SettingsLayout layout;
    private SettingStorage settingStorage;
    private LoadingDialog loadingDialog;

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
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();

        SettingUpdateThread.update(getActivity(), accessListener);
        updateEmail();
    }

    SettingUpdateThread.AccessListener accessListener = new SettingUpdateThread.AccessListener() {
        @Override
        public void success(ApiV1UserMeAlertRuleGetData data) {
            updateSettings();

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

                    LoginParams params = new LoginParams(getActivity());
                    ApiV1UserMeEmailNotifyPost task = new ApiV1UserMeEmailNotifyPost(getActivity());
                    task.setParams(params);
                    task.addPostParams("enable_email_notify", value);

                    loadingDialog.show();
                    task.requestImpl(new Response.Listener<String>() {
                                         @Override
                                         public void onResponse(String s) {
                                             loadingDialog.cancel();
                                             layout.emailNotificationsItem.checkbox.setChecked(check);
                                             settingStorage.setEnableEmailNotify(check);
                                         }
                                     }, new Response.ErrorListener() {
                                         @Override
                                         public void onErrorResponse(VolleyError volleyError) {
                                             Toast.makeText(getActivity(), "Saved failed.", Toast.LENGTH_SHORT).show();
                                             loadingDialog.cancel();
                                         }
                                     }
                    );
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
            Toast.makeText(getActivity(), "Save setting configuration failed.", Toast.LENGTH_SHORT).show();
            loadingDialog.cancel();
        }
    };

    private void updateSettings() {
        SettingStorage settingStorage = new SettingStorage(getActivity());
        layout.languageItem.setValue(getString(settingStorage.getLanguageResource()));
        layout.dateFormatsItem.setValue(getString(settingStorage.getDateFormatsResource()));
        layout.notificationsItem.checkbox.setChecked(settingStorage.getNotifications());
        layout.emailNotificationsItem.checkbox.setChecked(settingStorage.getEnableEmailNotify());
        layout.autoDeleteItem.checkbox.setChecked(settingStorage.getAutoDelete());
    }

    private void updateEmail() {
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
                Toast.makeText(getActivity(), "Load email failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
