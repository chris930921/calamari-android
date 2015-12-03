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
import com.cephmonitor.cephmonitor.model.logic.FullTimeDecorator;
import com.cephmonitor.cephmonitor.model.logic.SecondToTime;
import com.cephmonitor.cephmonitor.receiver.ChangePeriodReceiver;
import com.resourcelibrary.model.network.api.MutipleCookieHttpStack;
import com.resourcelibrary.model.network.api.ceph.CephGetRequest;
import com.resourcelibrary.model.network.api.ceph.CephPostRequest;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class TimePeriodFragment extends Fragment {
    public TimePeriodLayout layout;
    //    public SettingStorage settingStorage;
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
        LoginParams params = new LoginParams(getActivity());
        taskQueue.add(new CephGetRequest(params.getSession(), "http://" + params.getHost() + ":" + params.getPort() + "/api/v1/user/me/alert_rule", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject total = new JSONObject(s);
                    long osdWarning = Long.parseLong(total.getString("osd_warning"));
                    long osdError = Long.parseLong(total.getString("osd_error"));
                    long monitorWarning = Long.parseLong(total.getString("monitor_warning"));
                    long monitorError = Long.parseLong(total.getString("monitor_error"));
                    float pgWarning = Float.parseFloat(total.getString("pg_warning")) / 100;
                    float pgError = Float.parseFloat(total.getString("pg_error")) / 100;
                    float usageWarning = Float.parseFloat(total.getString("usage_warning")) / 100;
                    float usageError = Float.parseFloat(total.getString("usage_error")) / 100;
                    long generalPolling = Long.parseLong(total.getString("general_polling"));
                    long abnormalStatePolling = Long.parseLong(total.getString("abnormal_state_polling"));
                    long abnormalServerStatePolling = Long.parseLong(total.getString("abnormal_server_state_polling"));

                    SettingStorage settingStorage = new SettingStorage(getActivity());
                    settingStorage.setAlertTriggerOsdWarning(osdWarning);
                    settingStorage.setAlertTriggerOsdError(osdError);
                    settingStorage.setAlertTriggerMonWarning(monitorWarning);
                    settingStorage.setAlertTriggerMonError(monitorError);
                    settingStorage.setAlertTriggerPgWarning(pgWarning);
                    settingStorage.setAlertTriggerPgError(pgError);
                    settingStorage.setAlertTriggerUsageWarning(usageWarning);
                    settingStorage.setAlertTriggerUsageError(usageError);
                    settingStorage.setTimePeriodNormal(generalPolling);
                    settingStorage.setTimePeriodAbnormal(abnormalStatePolling);
                    settingStorage.setTimePeriodServerAbnormal(abnormalServerStatePolling);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refreshSubTitle(REFRESH_ALL);
                loadingDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "Load failed.", Toast.LENGTH_SHORT).show();
                loadingDialog.cancel();
            }
        }));
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
            fullTime = FullTimeDecorator.change(settingStorage.getTimePeriodNormal());
            title = getString(R.string.settings_time_period_normal_content);
            title = String.format(title, fullTime);
            layout.normalPeriod.setSubTitle(title);
        }
        if (resourceId < 0 || resourceId == R.string.settings_time_period_abnormal_title) {
            fullTime = FullTimeDecorator.change(settingStorage.getTimerPeriodAbnormal());
            title = getString(R.string.settings_time_period_abnormal_content);
            title = String.format(title, fullTime);
            layout.abnormalPeriod.setSubTitle(title);
        }
        if (resourceId < 0 || resourceId == R.string.settings_time_period_server_abnormal_title) {
            fullTime = FullTimeDecorator.change(settingStorage.getTimerPeriodServerAbnormal());
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
