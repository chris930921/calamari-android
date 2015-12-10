package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.TimePeriodPickerDialog;
import com.cephmonitor.cephmonitor.layout.fragment.TimePeriodLayout;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.logic.ApiSettingData;
import com.cephmonitor.cephmonitor.model.logic.FullTimeDecorator;
import com.cephmonitor.cephmonitor.model.logic.SecondToTime;
import com.cephmonitor.cephmonitor.model.network.RemoteSettingToLocal;
import com.cephmonitor.cephmonitor.receiver.ChangePeriodReceiver;
import com.resourcelibrary.model.network.api.MutipleCookieHttpStack;
import com.resourcelibrary.model.network.api.ceph.CephPostRequest;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class TimePeriodFragment extends Fragment {
    public TimePeriodLayout layout;
    public static final int REFRESH_ALL = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new TimePeriodLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        taskQueue = Volley.newRequestQueue(getActivity(), new MutipleCookieHttpStack());
        loadingDialog = new LoadingDialog(getActivity());

        layout.normalPeriod.setOnClickListener(showDialog(R.string.settings_time_period_normal_title));
        layout.abnormalPeriod.setOnClickListener(showDialog(R.string.settings_time_period_abnormal_title));
        layout.serverAbnormalPeriod.setOnClickListener(showDialog(R.string.settings_time_period_server_abnormal_title));

        loadingDialog.show();
        RemoteSettingToLocal remoteSettingToLocal = new RemoteSettingToLocal(getActivity(), new LoginParams(getActivity()));
        remoteSettingToLocal.access(new RemoteSettingToLocal.AccessListener() {
            @Override
            public void success(ApiSettingData data) {
                refreshSubTitle(REFRESH_ALL);
                loadingDialog.cancel();
            }

            @Override
            public void fail(VolleyError volleyError) {
                Toast.makeText(getActivity(), "Load failed.", Toast.LENGTH_SHORT).show();
                loadingDialog.cancel();
            }
        });
    }

    private View.OnClickListener showDialog(final int title) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long originValue = 0;
                SettingStorage settingStorage = new SettingStorage(getActivity());
                if (title == R.string.settings_time_period_normal_title) {
                    originValue = settingStorage.getTimePeriodNormal();
                } else if (title == R.string.settings_time_period_abnormal_title) {
                    originValue = settingStorage.getTimerPeriodAbnormal();
                } else if (title == R.string.settings_time_period_server_abnormal_title) {
                    originValue = settingStorage.getTimerPeriodServerAbnormal();
                }
                SecondToTime secondToTime = new SecondToTime(originValue);
                final TimePeriodPickerDialog dialog = new TimePeriodPickerDialog(getActivity());
                dialog.setTitle(title);
                dialog.setTime(secondToTime.getHour(), secondToTime.getMinute(), secondToTime.getSecond());
                dialog.setOkClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start(getResourceName(title), String.valueOf(dialog.getSecondPeriod()), getUrl(title), title, dialog);
                    }
                });
                dialog.show();
            }
        };
    }

    public String getUrl(int title) {
        if (title == R.string.settings_time_period_normal_title) {
            return "http://" + new LoginParams(getActivity()).getHost() + "/api/v1/user/me/polling/general";
        } else if (title == R.string.settings_time_period_abnormal_title) {
            return "http://" + new LoginParams(getActivity()).getHost() + "/api/v1/user/me/polling/abnormal_state";
        } else {
            return "http://" + new LoginParams(getActivity()).getHost() + "/api/v1/user/me/polling/abnormal_server_state";
        }
    }

    public String getResourceName(int title) {
        if (title == R.string.settings_time_period_normal_title) {
            return "general_polling";
        } else if (title == R.string.settings_time_period_abnormal_title) {
            return "abnormal_state_polling";
        } else {
            return "abnormal_server_state_polling";
        }
    }

    public void storeTimePeriod(int title, TimePeriodPickerDialog dialog) {
        SettingStorage settingStorage = new SettingStorage(getActivity());
        if (title == R.string.settings_time_period_normal_title) {
            settingStorage.setTimePeriodNormal(dialog.getSecondPeriod());
        } else if (title == R.string.settings_time_period_abnormal_title) {
            settingStorage.setTimePeriodAbnormal(dialog.getSecondPeriod());
        } else if (title == R.string.settings_time_period_server_abnormal_title) {
            settingStorage.setTimePeriodServerAbnormal(dialog.getSecondPeriod());
        }
        ChangePeriodReceiver.sendTimePeriodChangedMessage(getActivity());
    }

    public void refreshSubTitle(int resourceId) {
        SettingStorage settingStorage = new SettingStorage(getActivity());
        String title, fullTime;

        if (resourceId < 0 || resourceId == R.string.settings_time_period_normal_title) {
            fullTime = FullTimeDecorator.change(getActivity(), settingStorage.getTimePeriodNormal());
            title = getString(R.string.settings_time_period_normal_content);
            title = String.format(title, fullTime);
            layout.normalPeriod.setSubTitle(title);
        }
        if (resourceId < 0 || resourceId == R.string.settings_time_period_abnormal_title) {
            fullTime = FullTimeDecorator.change(getActivity(), settingStorage.getTimerPeriodAbnormal());
            title = getString(R.string.settings_time_period_abnormal_content);
            title = String.format(title, fullTime);
            layout.abnormalPeriod.setSubTitle(title);
        }
        if (resourceId < 0 || resourceId == R.string.settings_time_period_server_abnormal_title) {
            fullTime = FullTimeDecorator.change(getActivity(), settingStorage.getTimerPeriodServerAbnormal());
            title = getString(R.string.settings_time_period_server_abnormal_content);
            title = String.format(title, fullTime);
            layout.serverAbnormalPeriod.setSubTitle(title);
        }
    }

    private RequestQueue taskQueue;
    private LoadingDialog loadingDialog;

    public void start(String resourceName, String value, String url, final int title, final TimePeriodPickerDialog dialog) {
        loadingDialog.show();
        LoginParams params = new LoginParams(getActivity());
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
                storeTimePeriod(title, dialog);
                refreshSubTitle(title);
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
