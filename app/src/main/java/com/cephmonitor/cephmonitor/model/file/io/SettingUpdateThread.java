package com.cephmonitor.cephmonitor.model.file.io;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.model.network.remotesetting.api.ApiV1UserMeAlertRuleGet;
import com.cephmonitor.cephmonitor.model.network.remotesetting.data.ApiV1UserMeAlertRuleGetData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;


/**
 * Created by chriske on 2016/1/26.
 */
public class SettingUpdateThread {

    private SettingUpdateThread() {
    }

    public static void update(final Context context, final AccessListener listener) {
        final Handler handler = new Handler();

        LoginParams params = new LoginParams(context);
        ApiV1UserMeAlertRuleGet task = new ApiV1UserMeAlertRuleGet(context);
        task.setParams(params);
        task.requestImpl(new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                update(handler, context, s, listener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = "Load /api/me/alert/rule Api failed.";
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public static void update(final Handler handler,
                              final Context context,
                              final String textData,
                              final AccessListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiV1UserMeAlertRuleGetData data = new ApiV1UserMeAlertRuleGetData(textData);
                setStorage(context, data);
                callListener(handler, listener, data);

            }
        }).start();
    }

    private static void setStorage(Context context, ApiV1UserMeAlertRuleGetData data) {
        SettingStorage settingStorage = new SettingStorage(context);
        settingStorage.setAlertTriggerOsdWarning(data.osdWarning);
        settingStorage.setAlertTriggerOsdError(data.osdError);
        settingStorage.setAlertTriggerMonWarning(data.monitorWarning);
        settingStorage.setAlertTriggerMonError(data.monitorError);
        settingStorage.setAlertTriggerPgWarning(data.pgWarning);
        settingStorage.setAlertTriggerPgError(data.pgError);
        settingStorage.setAlertTriggerUsageWarning(data.usageWarning);
        settingStorage.setAlertTriggerUsageError(data.usageError);
        settingStorage.setTimePeriodNormal(data.generalPolling);
        settingStorage.setTimePeriodAbnormal(data.abnormalStatePolling);
        settingStorage.setTimePeriodServerAbnormal(data.abnormalServerStatePolling);
        settingStorage.setEnableEmailNotify(data.enableEmailNotify);
    }

    private static void callListener(final Handler handler, final AccessListener listener, final ApiV1UserMeAlertRuleGetData data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.success(data);
            }
        });
    }

    public interface AccessListener {
        void success(ApiV1UserMeAlertRuleGetData data);

        void fail(VolleyError volleyError);
    }
}
