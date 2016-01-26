package com.cephmonitor.cephmonitor.model.network.remotesetting;

import android.content.Context;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.network.remotesetting.data.ApiV1UserMeAlertRuleGetData;
import com.resourcelibrary.model.network.api.MutipleCookieHttpStack;
import com.resourcelibrary.model.network.api.ceph.CephGetRequest;
import com.resourcelibrary.model.network.api.ceph.CephParams;


/**
 * Created by chriske on 2015/12/2.
 */
public class RemoteSettingToLocal {
    private CephParams requestParams;
    private Context context;

    public RemoteSettingToLocal(Context context, CephParams requestParams) {
        this.context = context;
        this.requestParams = requestParams;
    }

    public void access(final AccessListener listener) {
        String url = "http://" + requestParams.getHost() + ":" + requestParams.getPort() + "/api/v1/user/me/alert_rule";
        RequestQueue taskQueue = Volley.newRequestQueue(context, new MutipleCookieHttpStack());

        taskQueue.add(new CephGetRequest(requestParams.getSession(), url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String s) {
                new Thread(new Runnable() {
                    private Handler handler = new Handler();

                    @Override
                    public void run() {
                        final ApiV1UserMeAlertRuleGetData data = new ApiV1UserMeAlertRuleGetData(s);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
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
                                listener.success(data);
                            }
                        });
                    }
                }).start();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.fail(volleyError);
            }
        }));
    }


    public interface AccessListener {
        void success(ApiV1UserMeAlertRuleGetData data);

        void fail(VolleyError volleyError);
    }
}
