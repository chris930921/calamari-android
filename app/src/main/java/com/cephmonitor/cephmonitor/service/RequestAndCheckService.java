package com.cephmonitor.cephmonitor.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.BuildConfig;
import com.cephmonitor.cephmonitor.model.logic.ConditionNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification.MonCountErrorNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification.MonCountWarnNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification.OsdCountErrorNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification.OsdCountWarnNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification.PgCountErrorNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification.PgCountWarnNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification.UsagePercentErrorNotification;
import com.cephmonitor.cephmonitor.model.logic.ceph.condition.notification.UsagePercentWarnNotification;
import com.cephmonitor.cephmonitor.receiver.ChangePeriodReceiver;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.RequestVolleyTask;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1Space;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2Data;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthCounterRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1SpaceRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV2ListRequest;
import com.resourcelibrary.model.network.api.ceph.single.LoginPostRequest;

import org.json.JSONException;

import java.util.ArrayList;

public class RequestAndCheckService extends Service {
    private LoginParams requestParams;
    private ArrayList<ConditionNotification> healthCountCheckList;
    private ArrayList<ConditionNotification> clusterSpaceCheckList;

    @Override
    public void onCreate() {
        RequestVolleyTask.enableFakeValue(BuildConfig.IS_LOCALHOST);

        healthCountCheckList = new ArrayList<>();
        healthCountCheckList.add(new MonCountErrorNotification(this));
        healthCountCheckList.add(new MonCountWarnNotification(this));
        healthCountCheckList.add(new OsdCountErrorNotification(this));
        healthCountCheckList.add(new OsdCountWarnNotification(this));
        healthCountCheckList.add(new PgCountErrorNotification(this));
        healthCountCheckList.add(new PgCountWarnNotification(this));

        clusterSpaceCheckList = new ArrayList<>();
        clusterSpaceCheckList.add(new UsagePercentErrorNotification(this));
        clusterSpaceCheckList.add(new UsagePercentWarnNotification(this));
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        requestParams = new LoginParams(this);
        ShowLog.d("位址:" + requestParams.getHost());
        ShowLog.d("Port:" + requestParams.getPort());
        ShowLog.d("名稱:" + requestParams.getName());
        ShowLog.d("密碼:" + requestParams.getPassword());
        requestLoginPost();

        return START_REDELIVER_INTENT;
    }

    private void requestLoginPost() {
        LoginPostRequest spider = new LoginPostRequest(this);
        spider.setRequestParams(requestParams);
        spider.request(successLoginPost, failLoginPost);
    }

    private Response.Listener<String> successLoginPost = new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            requestFirstClusterId();
        }
    };

    public final Response.ErrorListener failLoginPost = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            ShowLog.d("背景檢查服務登入失敗，取消接下來的檢查動作。");
            stopSelf();
        }
    };

    private void requestFirstClusterId() {
        ClusterV2ListRequest spider = new ClusterV2ListRequest(this);
        spider.setRequestParams(requestParams);
        spider.request(successFirstClusterId, failRequestResource);
    }

    private Response.Listener<String> successFirstClusterId = new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            try {
                dealWithFirstClusterExists(s);
                requestOsdMonStatus();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public final Response.ErrorListener failRequestResource = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            ShowLog.d("伺服器錯誤導致無法取得遠端資源，取消接下來的檢查動作，，轉為伺服器錯誤週期。");
            ChangePeriodReceiver.sendServerErrorMessage(RequestAndCheckService.this);
            stopSelf();
        }
    };

    private void dealWithFirstClusterExists(String response) throws JSONException {
        ClusterV2ListData clusterList = new ClusterV2ListData(response);
        ClusterV2Data cluster = clusterList.getList().get(0);
        String clusterId = cluster.getId();
        requestParams.setClusterId(clusterId);
    }

    private void requestOsdMonStatus() {
        ClusterV1HealthCounterRequest spider = new ClusterV1HealthCounterRequest(this);
        spider.setRequestParams(requestParams);
        spider.request(successOsdMonStatus, failRequestResource);
    }

    private Response.Listener<String> successOsdMonStatus = new Response.Listener<String>() {
        @Override
        public void onResponse(final String s) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dealWithStatusCount(s);
                        requestClusterSpace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };

    private ClusterV1HealthCounterData v1HealthCounterData;

    private void dealWithStatusCount(final String response) throws JSONException {
        v1HealthCounterData = new ClusterV1HealthCounterData(response);
    }

    private void requestClusterSpace() {
        ClusterV1SpaceRequest spider = new ClusterV1SpaceRequest(this);
        spider.setRequestParams(requestParams);
        spider.request(successClusterSpace, failRequestResource);
    }

    private Response.Listener<String> successClusterSpace = new Response.Listener<String>() {
        @Override
        public void onResponse(final String s) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dealWithClusterSpace(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };

    private ClusterV1Space v1Space;

    private void dealWithClusterSpace(final String response) throws JSONException {
        ShowLog.d("成功取得所有資料時，恢復正常檢查週期。");
        ChangePeriodReceiver.sendDefaultMessage(this);
        v1Space = new ClusterV1Space(response);

        int trueCount = 0;
        ShowLog.d("執行空間檢查，檢查數量: " + clusterSpaceCheckList.size());
        for (ConditionNotification checker : clusterSpaceCheckList) {
            boolean result = checker.check(v1Space);
            trueCount = (result) ? trueCount : trueCount + 1;
        }
        ShowLog.d("執行數量檢查，檢查數量: " + healthCountCheckList.size());
//        NotificationFragment.send(RequestAndCheckService.this);
        for (ConditionNotification checker : healthCountCheckList) {
            boolean result = checker.check(v1HealthCounterData);
            trueCount = (result) ? trueCount : trueCount + 1;
        }
//        NotificationFragment.send(RequestAndCheckService.this);
        if (trueCount != clusterSpaceCheckList.size() + healthCountCheckList.size()) {
            ShowLog.d("檢查到錯誤，進入錯誤檢查週期。");
            ChangePeriodReceiver.sendCheckMessage(this);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
