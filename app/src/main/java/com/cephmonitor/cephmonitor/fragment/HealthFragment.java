package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.cephmonitor.cephmonitor.MainActivity;
import com.cephmonitor.cephmonitor.layout.fragment.HealthLayout;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.GeneralError;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2Data;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ListData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ServerListData;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1ListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthCounterRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1ServerRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV2ListRequest;
import com.resourcelibrary.model.network.api.ceph.single.PoolV1ListRequest;

import org.json.JSONException;

import java.util.Calendar;

public class HealthFragment extends Fragment {
    private HealthLayout layout;
    private LoginParams requestParams;
    public String healthCardStatus;
    public long healthCardLastUpdate;
    public int healthCardWarningCount;
    public int healthCardErrorCount;

    public int monCardOkCount;
    public int monCardTotalCount;
    public int monCardWarningCount;
    public int monCardErrorCount;

    public int osdCardOkCount;
    public int oadCardTotalCount;
    public int osdCardWarningCount;
    public int osdCardErrorCount;

    public int poolCardStatus;

    public int hostCardStatus;
    public int hostCardMonCount;
    public int hostCardOsdCount;

    public int pgCardActiveCount;
    public int pgCardCleanCount;
    public int pgCardWorkingCount;
    public int pgCardDirtyCount;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new HealthLayout(getActivity());
        }
        return layout;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        healthCardStatus = "OK";

        monCardOkCount = 0;
        monCardTotalCount = 0;
        monCardWarningCount = 0;
        monCardErrorCount = 0;

        osdCardOkCount = 0;
        oadCardTotalCount = 0;
        osdCardWarningCount = 0;
        osdCardErrorCount = 0;

        poolCardStatus = 0;

        hostCardStatus = 0;
        hostCardMonCount = 0;
        hostCardOsdCount = 0;

        requestParams = new LoginParams(getActivity());
        requestFirstClusterId();

        layout.healthCard.setTitleOnClickListener(healthCardClickEvent);
        layout.osdCard.setTitleOnClickListener(osdCardClickEvent);
    }

    private View.OnClickListener healthCardClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity activity = (MainActivity) getActivity();
            activity.showHealthDetailFragment();
        }
    };
    private View.OnClickListener osdCardClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity activity = (MainActivity) getActivity();
            activity.showOsdHealthFragment();
        }
    };

    private void requestFirstClusterId() {
        ClusterV2ListRequest spider = new ClusterV2ListRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successFirstClusterId(), GeneralError.callback(getActivity()));
    }

    private Response.Listener<String> successFirstClusterId() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    ShowLog.d("ClusterV2FindAllRequest" + " 結果:" + s);
                    dealWithFirstClusterExists(s);
                    requestHealth();
                    requestOsdMonStatus();
                    requestPoolStatus();
                    requestServerList();
                    layout.postDelayed(showVar(), 2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void dealWithFirstClusterExists(String response) throws JSONException {
        ClusterV2ListData clusterList = new ClusterV2ListData(response);
        ClusterV2Data cluster = clusterList.getList().get(0);
        String clusterId = cluster.getId();
        requestParams.setClusterId(clusterId);
    }

    private void requestHealth() {
        ClusterV1HealthRequest spider = new ClusterV1HealthRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successHealth(), GeneralError.callback(getActivity()));
    }

    private Response.Listener<String> successHealth() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    ShowLog.d("ClusterV1HealthRequest" + " 結果:" + s);
                    dealWithHealthStatus(s);
                    updateView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void dealWithHealthStatus(String response) throws JSONException {
        ClusterV1HealthData data = new ClusterV1HealthData(response);
        healthCardStatus = data.getOverallStatus();
        healthCardLastUpdate = data.getLastUpdateTimestamp();
        healthCardWarningCount = data.getWarningCount();
        healthCardErrorCount = data.getErrorCount();
    }

    private void requestOsdMonStatus() {
        ClusterV1HealthCounterRequest spider = new ClusterV1HealthCounterRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successOsdMonStatus(), GeneralError.callback(getActivity()));
    }

    private Response.Listener<String> successOsdMonStatus() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    ShowLog.d("ClusterV1HealthCounterRequest" + " 結果:" + s);
                    dealWithStatusCount(s);
                    updateView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void dealWithStatusCount(String response) throws JSONException {
        ClusterV1HealthCounterData data = new ClusterV1HealthCounterData(response);
        monCardOkCount = data.getMonOkCount();
        monCardTotalCount = data.getMonTotalCount();
        monCardWarningCount = data.getMonWarningCount();
        monCardErrorCount = data.getMonErrorCount();

        osdCardOkCount = data.getOsdOkCount();
        oadCardTotalCount = data.getOsdTotalCount();
        osdCardWarningCount = data.getOsdWarningCount();
        osdCardErrorCount = data.getOsdErrorCount();

        pgCardActiveCount = data.getPlacmentGroupsActiveCount();
        pgCardCleanCount = data.getPlacmentGroupsCleanCount();
        pgCardWorkingCount = data.getPlacmentGroupsWarningCount();
        pgCardDirtyCount = data.getPlacmentGroupsErrorCount();
    }

    private void requestPoolStatus() {
        PoolV1ListRequest spider = new PoolV1ListRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successPoolStatus(), GeneralError.callback(getActivity()));
    }

    private Response.Listener<String> successPoolStatus() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    ShowLog.d("PoolV1ListRequest" + " 結果:" + s);
                    dealWithPoolStatus(s);
                    updateView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void dealWithPoolStatus(String response) throws JSONException {
        PoolV1ListData data = new PoolV1ListData(response);
        poolCardStatus = data.getList().size();
    }

    private void requestServerList() {
        ClusterV1ServerRequest spider = new ClusterV1ServerRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successServerList(), GeneralError.callback(getActivity()));
    }

    private Response.Listener<String> successServerList() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    ShowLog.d("ClusterV1ServerRequest" + " 結果:" + s);
                    dealWithServerCount(s);
                    updateView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void dealWithServerCount(String response) throws JSONException {
        ClusterV2ServerListData data = new ClusterV2ServerListData(response);
        hostCardStatus = data.getList().size();
        hostCardMonCount = data.getMonList().size();
        hostCardOsdCount = data.getOsdList().size();
    }

    private void updateView() {

        if (healthCardStatus.equals(ClusterV1HealthData.HEALTH_OK)) {
            layout.healthCard.setCenterValueText("OK");
            layout.healthCard.changeGreenBorder();
        } else if (healthCardStatus.equals(ClusterV1HealthData.HEALTH_WARN)) {
            layout.healthCard.setCenterValueText("WARNING");
            layout.healthCard.changeOrangeBorder();
        } else if (healthCardStatus.equals(ClusterV1HealthData.HEALTH_ERR)) {
            layout.healthCard.setCenterValueText("ERROR");
            layout.healthCard.changeRedBorder();
        } else {
            layout.healthCard.setCenterValueText(healthCardStatus);
            layout.healthCard.changeRedBorder();
        }

        long nowTimeStamp = Calendar.getInstance().getTimeInMillis();
        long period = (nowTimeStamp - healthCardLastUpdate) / 1000;
        if (period < 0) period = -period; // FIXME 確認伺服器時間
        if (period == 0) {
            layout.healthCard.setCenterText("JUST NOW");
        } else if (period > 31536000) {
            int count = (int) (period / 31536000);
            String plurality = (count > 1) ? "s" : "";
            layout.healthCard.setCenterText(count + " year" + plurality + " ago");
        } else if (period > 259200) {
            int count = (int) (period / 259200);
            String plurality = (count > 1) ? "s" : "";
            layout.healthCard.setCenterText(count + " month" + plurality + " ago");
        } else if (period > 86400) {
            int count = (int) (period / 86400);
            String plurality = (count > 1) ? "s" : "";
            layout.healthCard.setCenterText(count + " day" + plurality + " ago");
        } else if (period > 3600) {
            int count = (int) (period / 3600);
            String plurality = (count > 1) ? "s" : "";
            layout.healthCard.setCenterText(count + " hour" + plurality + " ago");
        } else if (period > 60) {
            int count = (int) (period / 60);
            String plurality = (count > 1) ? "s" : "";
            layout.healthCard.setCenterText(count + " min" + plurality + " ago");
        } else {
            int count = (int) period;
            String plurality = (count > 1) ? "s" : "";
            layout.healthCard.setCenterText(count + " second" + plurality + " ago");
        }
        layout.healthCard.setLeftValueText(healthCardWarningCount);
        layout.healthCard.setRightValueText(healthCardErrorCount);

        layout.osdCard.setLeftValueText(osdCardWarningCount);
        layout.osdCard.setRightValueText(osdCardErrorCount);
        String osdStatus = osdCardOkCount + " / " + oadCardTotalCount;
        layout.osdCard.setCenterValueText(osdStatus);

        layout.monCard.setLeftValueText(monCardWarningCount);
        layout.monCard.setRightValueText(monCardErrorCount);
        String monStatus = monCardOkCount + " / " + monCardTotalCount;
        layout.monCard.setCenterValueText(monStatus);

        layout.poolsCard.setCenterValueText(poolCardStatus + "");

        layout.hostsCard.setLeftValueText(hostCardMonCount);
        layout.hostsCard.setRightValueText(hostCardOsdCount);
        layout.hostsCard.setCenterValueText(hostCardStatus + "");

        layout.pgStatusCard.setLeftValueText(pgCardWorkingCount);
        layout.pgStatusCard.setRightValueText(pgCardDirtyCount);
        String pgStatus = pgCardActiveCount + " / " + pgCardCleanCount;
        layout.pgStatusCard.setCenterValueText(pgStatus);
    }

    private Runnable showVar() {
        return new Runnable() {
            @Override
            public void run() {
                ShowLog.d("\n所有結果" + "\n" +
                                "healthCardStatus:" + healthCardStatus + "\n" +
                                "healthCardLastUpdate:" + healthCardLastUpdate + "\n" +
                                "monCardOkCount:" + monCardOkCount + "\n" +
                                "monCardStateCount:" + monCardTotalCount + "\n" +
                                "monCardWarningCount:" + monCardWarningCount + "\n" +
                                "monCardErrorCount:" + monCardErrorCount + "\n" +
                                "osdCardOkCount:" + osdCardOkCount + "\n" +
                                "oadCardStateCount:" + oadCardTotalCount + "\n" +
                                "osdCardWarningCount:" + osdCardWarningCount + "\n" +
                                "osdCardErrorCount:" + osdCardErrorCount + "\n" +
                                "poolCardStatus:" + poolCardStatus + "\n" +
                                "hostCardStatus:" + hostCardStatus + "\n" +
                                "hostCardMonCount:" + hostCardMonCount + "\n" +
                                "hostCardOsdCount:" + hostCardOsdCount + "\n"
                );
                layout.postDelayed(showVar(), 2000);
            }
        };
    }
}