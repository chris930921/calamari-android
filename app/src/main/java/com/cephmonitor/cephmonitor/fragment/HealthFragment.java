package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.HealthLayout;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.logic.TimeUnit;
import com.resourcelibrary.model.network.GeneralError;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1Space;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2Data;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ListData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ServerListData;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1ListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthCounterRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1ServerRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1SpaceRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV2ListRequest;
import com.resourcelibrary.model.network.api.ceph.single.PoolV1ListRequest;

import org.json.JSONException;

import java.util.Calendar;

public class HealthFragment extends Fragment {
    private HealthLayout layout;
    private LoginParams requestParams;
    private ClusterV1HealthData healthData;
    private PoolV1ListData poolData;
    private int currentUpdateId;

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

    public int pgCardOkCount;
    public int pgCardTotalCount;
    public int pgCardWorkingCount;
    public int pgCardDirtyCount;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new HealthLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        layout.post(requestFlow(currentUpdateId));
    }

    @Override
    public void onPause() {
        super.onPause();
        currentUpdateId = (currentUpdateId + 1) % 100000;
    }

    private Runnable requestFlow(final int updateId) {
        return new Runnable() {
            @Override
            public void run() {
                if (currentUpdateId == updateId) {
                    requestFirstClusterId();
                    layout.postDelayed(this, 10 * 1000);
                }
                ShowLog.d("Health 頁面更新識別號: " + updateId + " 重新刷新: " + (currentUpdateId == updateId) + " 時間:" + Calendar.getInstance().getTime().toString() + " 。");
            }
        };
    }

    private void init() {
        currentUpdateId = 0;
        layout.healthCard.setCenterValueText("OK");

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

        layout.healthCard.setTitleOnClickListener(healthCardClickEvent);
        layout.osdCard.setTitleOnClickListener(osdCardClickEvent);
        layout.monCard.setTitleOnClickListener(monCardClickEvent);
    }

    private View.OnClickListener healthCardClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (healthData == null) {
                return;
            }

            Bundle arg = new Bundle();
            healthData.outBox(arg);
            FragmentLauncher.goHealthDetailFragment(getActivity(), arg);
        }
    };
    private View.OnClickListener osdCardClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (poolData == null) {
                return;
            }

            Bundle arg = new Bundle();
            poolData.outBox(arg);
            FragmentLauncher.goOsdHealthFragment(getActivity(), arg);
        }
    };
    private View.OnClickListener monCardClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (poolData == null) {
                return;
            }

            Bundle arg = new Bundle();
            healthData.outBox(arg);
            FragmentLauncher.goMonHealthFragment(getActivity(), arg);
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
                    requestStoreSpace();
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
        healthData = new ClusterV1HealthData(response);
        healthCardLastUpdate = healthData.getLastUpdateTimestamp();
        healthCardWarningCount = healthData.getWarningCount();
        healthCardErrorCount = healthData.getErrorCount();
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

        pgCardOkCount = data.getPlacmentGroupsOkCount();
        pgCardTotalCount = data.getPlacmentGroupsTotalCount();
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
        poolData = new PoolV1ListData(response);
        poolCardStatus = poolData.getList().size();
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

    private void requestStoreSpace() {
        ClusterV1SpaceRequest spider = new ClusterV1SpaceRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successStoreSpace(), GeneralError.callback(getActivity()));
    }

    private Response.Listener<String> successStoreSpace() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    ShowLog.d("ClusterV1SpaceRequest" + " 結果:" + s);
                    dealWithStoreSpace(s);
                    updateView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void dealWithStoreSpace(String response) throws JSONException {
        ClusterV1Space data = new ClusterV1Space(response);
        long rightValue = data.getCapacityBytes();
        long leftValue = data.getUsedBytes();
        layout.usageCard.setLongValue(leftValue, rightValue);
    }

    private void updateView() {
        if (healthCardErrorCount != 0) {
            layout.healthCard.setCenterValueText("ERROR");
            layout.healthCard.changeRedBorder();
        } else if (healthCardWarningCount != 0) {
            layout.healthCard.setCenterValueText("WARNING");
            layout.healthCard.changeOrangeBorder();
        } else {
            layout.healthCard.setCenterValueText("OK");
            layout.healthCard.changeGreenBorder();
        }

        long nowTimeStamp = Calendar.getInstance().getTimeInMillis();
        long period = (nowTimeStamp - healthCardLastUpdate) / 1000; // FIXME 確認伺服器時間
        layout.healthCard.setCenterText(TimeUnit.change(period));

        layout.healthCard.setValue(healthCardWarningCount, healthCardErrorCount);

        layout.osdCard.setValue(osdCardWarningCount, osdCardErrorCount);
        String osdStatus = osdCardOkCount + " / " + oadCardTotalCount;
        layout.osdCard.setCenterValueText(osdStatus);

        layout.monCard.setValue(monCardWarningCount, monCardErrorCount);
        String monStatus = monCardOkCount + " / " + monCardTotalCount;
        layout.monCard.setCenterValueText(monStatus);

        layout.poolsCard.setCenterValueText(poolCardStatus + "");

        layout.hostsCard.setValue(hostCardMonCount, hostCardOsdCount);
        layout.hostsCard.setCenterValueText(hostCardStatus + "");

        layout.pgStatusCard.setValue(pgCardWorkingCount, pgCardDirtyCount);
        String pgStatus = pgCardOkCount + " / " + pgCardTotalCount;
        layout.pgStatusCard.setCenterValueText(pgStatus);
    }

    private Runnable showVar() {
        return new Runnable() {
            @Override
            public void run() {
                ShowLog.d("\n所有結果" + "\n" +
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