package com.cephmonitor.cephmonitor.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.GeneralError;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2Data;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthCounterRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV2ListRequest;
import com.resourcelibrary.model.network.api.ceph.single.LoginPostRequest;

import org.json.JSONException;

public class RequestAndCheckService extends Service {
    private Boolean isFirstOpen = true;
    private LoginParams requestParams;

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isFirstOpen) {
            init();
            isFirstOpen = false;
        }
        return START_REDELIVER_INTENT;
    }

    private void init() {
        requestParams = new LoginParams(this);
        requestLoginPost();
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
        spider.request(successFirstClusterId, failFirstClusterId);
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


    public final Response.ErrorListener failFirstClusterId = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            ShowLog.d("無法取得第一個cluster，取消接下來的檢查動作。");
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
        spider.request(successOsdMonStatus, GeneralError.callback(this));
    }

    private Response.Listener<String> successOsdMonStatus = new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            try {
                dealWithStatusCount(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    private void dealWithStatusCount(String response) throws JSONException {
        ClusterV1HealthCounterData data = new ClusterV1HealthCounterData(response);
//        monCardOkCount = data.getMonOkCount();
//        monCardTotalCount = data.getMonTotalCount();
//        monCardWarningCount = data.getMonWarningCount();
//        monCardErrorCount = data.getMonErrorCount();
//
//        osdCardOkCount = data.getOsdOkCount();
//        oadCardTotalCount = data.getOsdTotalCount();
//        osdCardWarningCount = data.getOsdWarningCount();
//        osdCardErrorCount = data.getOsdErrorCount();
//
//        pgCardActiveCount = data.getPlacmentGroupsActiveCount();
//        pgCardCleanCount = data.getPlacmentGroupsCleanCount();
//        pgCardWorkingCount = data.getPlacmentGroupsWarningCount();
//        pgCardDirtyCount = data.getPlacmentGroupsErrorCount();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
