package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.TimePeriodPickerDialog;
import com.cephmonitor.cephmonitor.layout.fragment.TimePeriodLayout;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.file.io.SettingUpdateThread;
import com.cephmonitor.cephmonitor.model.logic.FullTimeDecorator;
import com.cephmonitor.cephmonitor.model.logic.SecondToTime;
import com.cephmonitor.cephmonitor.model.network.remotesetting.RemoteSettingApiUrl;
import com.cephmonitor.cephmonitor.model.network.remotesetting.api.ApiV1userMePolling;
import com.cephmonitor.cephmonitor.model.network.remotesetting.data.ApiV1UserMeAlertRuleGetData;
import com.cephmonitor.cephmonitor.receiver.ChangePeriodReceiver;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import java.util.HashMap;

public class TimePeriodFragment extends Fragment {
    public TimePeriodLayout layout;
    public static final int REFRESH_ALL = -1;
    public static HashMap<Integer, RequestType> resourceMap;
    private LoginParams params;
    private LoadingDialog loadingDialog;

    class RequestType {
        String url;
        String resourceName;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new TimePeriodLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        params = new LoginParams(getActivity());
        loadingDialog = new LoadingDialog(getActivity());

        initDataMapApi();
        clickPeriodTimer();
        loadingDialog.show();
        SettingUpdateThread.update(getActivity(), accessListener);
    }

    public void initDataMapApi() {
        resourceMap = new HashMap<>();

        RequestType generalRequest = new RequestType();
        generalRequest.url = RemoteSettingApiUrl.apiV1UserMePollingGeneral(params);
        generalRequest.resourceName = "general_polling";
        resourceMap.put(R.string.settings_time_period_normal_title, generalRequest);

        RequestType abnormalRequest = new RequestType();
        abnormalRequest.url = RemoteSettingApiUrl.apiV1UserPollingAbnormalState(params);
        abnormalRequest.resourceName = "abnormal_state_polling";
        resourceMap.put(R.string.settings_time_period_abnormal_title, abnormalRequest);

        RequestType abnormalServerRequest = new RequestType();
        abnormalServerRequest.url = RemoteSettingApiUrl.apiV1UserPollingAbnormalServerStatePost(params);
        abnormalServerRequest.resourceName = "abnormal_server_state_polling";
        resourceMap.put(R.string.settings_time_period_server_abnormal_title, abnormalServerRequest);
    }

    public void clickPeriodTimer() {
        layout.normalPeriod.setOnClickListener(showDialog(R.string.settings_time_period_normal_title));
        layout.abnormalPeriod.setOnClickListener(showDialog(R.string.settings_time_period_abnormal_title));
        layout.serverAbnormalPeriod.setOnClickListener(showDialog(R.string.settings_time_period_server_abnormal_title));
    }

    SettingUpdateThread.AccessListener accessListener = new SettingUpdateThread.AccessListener() {
        @Override
        public void success(ApiV1UserMeAlertRuleGetData data) {
            refreshSubTitle(REFRESH_ALL);
            loadingDialog.cancel();
        }

        @Override
        public void fail(VolleyError volleyError) {
            Toast.makeText(getActivity(), "Save setting configuration failed.", Toast.LENGTH_SHORT).show();
            loadingDialog.cancel();
        }
    };

    private View.OnClickListener showDialog(final int title) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long originValue = getOriginValue(title);
                SecondToTime secondToTime = new SecondToTime(originValue);
                TimePeriodPickerDialog dialog = new TimePeriodPickerDialog(getActivity());
                dialog.setTitle(title);
                dialog.setTime(secondToTime.getHour(), secondToTime.getMinute(), secondToTime.getSecond());
                dialog.setOkClick(clickDialogOk(title, dialog));
                dialog.show();
            }
        };
    }

    private long getOriginValue(int title) {
        SettingStorage settingStorage = new SettingStorage(getActivity());
        if (title == R.string.settings_time_period_normal_title) {
            return settingStorage.getTimePeriodNormal();
        } else if (title == R.string.settings_time_period_abnormal_title) {
            return settingStorage.getTimerPeriodAbnormal();
        } else if (title == R.string.settings_time_period_server_abnormal_title) {
            return settingStorage.getTimerPeriodServerAbnormal();
        } else {
            throw new RuntimeException("Not found any resource map storage.");
        }
    }

    private View.OnClickListener clickDialogOk(final int title, final TimePeriodPickerDialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                ApiV1userMePolling task = new ApiV1userMePolling(getActivity());
                task.setParams(params);

                RequestType type = resourceMap.get(title);
                task.setUrl(type.url);

                String period = String.valueOf(dialog.getSecondPeriod());
                task.addPostParams(type.resourceName, period);

                task.request(new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
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
                });
            }
        };
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


}
