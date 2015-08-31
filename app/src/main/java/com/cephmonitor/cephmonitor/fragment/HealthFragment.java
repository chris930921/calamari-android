package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartLine;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.LineAdapter;
import com.cephmonitor.cephmonitor.layout.component.other.NavigationMenu;
import com.cephmonitor.cephmonitor.layout.fragment.HealthLayout;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.cephmonitor.cephmonitor.receiver.LoadFinishReceiver;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.logic.TimeUnit;
import com.resourcelibrary.model.network.GeneralError;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1Space;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2Data;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ListData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ServerListData;
import com.resourcelibrary.model.network.api.ceph.object.GraphiteRenderData;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1ListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthCounterRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1ServerRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1SpaceRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV2ListRequest;
import com.resourcelibrary.model.network.api.ceph.single.GraphiteRenderRequest;
import com.resourcelibrary.model.network.api.ceph.single.PoolV1ListRequest;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

public class HealthFragment extends Fragment {
    private HealthLayout layout;
    private LoginParams requestParams;
    private ClusterV1HealthData healthData;
    private PoolV1ListData poolData;
    private ClusterV2ServerListData hostData;
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

    private LoadingDialog loadingDialog;

    private LoadFinishReceiver loadFinishReceiver;

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
        layout.postDelayed(requestFlow(currentUpdateId), 1500);
        loadingDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        currentUpdateId = (currentUpdateId + 1) % 100000;
        loadingDialog.cancel();
    }

    private Runnable requestFlow(final int updateId) {
        return new Runnable() {
            @Override
            public void run() {
                if (currentUpdateId == updateId) {
                    requestFirstClusterId();
                    layout.postDelayed(this, 10 * 1000);
                }
                ShowLog.d("Health 頁面更新識別號: " + updateId +
                        " 重新刷新: " + (currentUpdateId == updateId) +
                        " 時間:" + Calendar.getInstance().getTime().toString() + " 。");
            }
        };
    }

    private void init() {
        loadingDialog = new LoadingDialog(getActivity());

        currentUpdateId = 0;
        layout.healthCard.setCenterValueText(getResources().getString(R.string.health_card_status_ok));

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
        layout.hostsCard.setTitleOnClickListener(hostCardClickEvent);
        layout.pgStatusCard.setTitleOnClickListener(pgStatusCardClickEvent);
        layout.usageCard.setTitleOnClickListener(usageStatusClickEvent);
        layout.iopsCard.setTitleOnClickListener(iopsClickEvent);
        layout.poolsCard.setTitleOnClickListener(poolsClickEvent);

        loadFinishReceiver = new LoadFinishReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int resourceId = intent.getExtras().getInt("RECEIVER_ID");
                int[] optionNameResourceGroup = NavigationMenu.optionNameResourceGroup;
                if (resourceId == optionNameResourceGroup[1]) {
                    healthCardClickEvent.onClick(null);
                } else if (resourceId == optionNameResourceGroup[2]) {
                    osdCardClickEvent.onClick(null);
                } else if (resourceId == optionNameResourceGroup[3]) {
                    monCardClickEvent.onClick(null);
                } else if (resourceId == optionNameResourceGroup[4]) {
                    poolsClickEvent.onClick(null);
                } else if (resourceId == optionNameResourceGroup[5]) {
                    hostCardClickEvent.onClick(null);
                } else if (resourceId == optionNameResourceGroup[6]) {
                    pgStatusCardClickEvent.onClick(null);
                } else if (resourceId == optionNameResourceGroup[7]) {
                    usageStatusClickEvent.onClick(null);
                } else if (resourceId == optionNameResourceGroup[8]) {
                    iopsClickEvent.onClick(null);
                }
            }
        };
        loadFinishReceiver.register(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadFinishReceiver.unregister(getActivity());
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
            if (healthData == null) {
                return;
            }

            Bundle arg = new Bundle();
            healthData.outBox(arg);
            FragmentLauncher.goMonHealthFragment(getActivity(), arg);
        }
    };

    private View.OnClickListener hostCardClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (hostData == null) {
                return;
            }

            Bundle arg = new Bundle();
            hostData.outBox(arg);
            FragmentLauncher.goHostHealthFragment(getActivity(), arg);
        }
    };

    private View.OnClickListener pgStatusCardClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentLauncher.goPgStatusFragment(getActivity(), null);
        }
    };

    private View.OnClickListener usageStatusClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentLauncher.goUsageStatusFragment(getActivity(), null);
        }
    };

    private View.OnClickListener iopsClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (requestParams.getClusterId().equals("")) {
                return;
            }
            if (poolData == null) {
                return;
            }

            Bundle arg = new Bundle();
            arg.putString("0", requestParams.getClusterId());
            poolData.outBox(arg);
            FragmentLauncher.goPoolIopsFragment(getActivity(), arg);
        }
    };

    private View.OnClickListener poolsClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (poolData == null) {
                return;
            }

            Bundle arg = new Bundle();
            poolData.outBox(arg);
            FragmentLauncher.goPoolListFragment(getActivity(), arg);
        }
    };

    private void requestFirstClusterId() {
        ClusterV2ListRequest spider = new ClusterV2ListRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successFirstClusterId(), GeneralError.callback(getActivity(), layout, loadingDialog));
    }

    private Response.Listener<String> successFirstClusterId() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    dealWithFirstClusterExists(s);
                    requestHealth();
                    requestOsdMonStatus();
                    requestPoolStatus();
                    requestServerList();
                    requestStoreSpace();
                    requestIopsSum();
                    LoadingDialog.delayCancel(layout, loadingDialog);
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
        spider.request(successHealth, GeneralError.callback(getActivity()));
    }

    private AnalyzeListener<String> successHealth = new AnalyzeListener<String>() {
        @Override
        public boolean doInBackground(String s) {
            try {
                healthData = new ClusterV1HealthData(s);
                healthCardLastUpdate = healthData.getLastUpdateTimestamp();
                healthCardWarningCount = healthData.getWarningCount();
                healthCardErrorCount = healthData.getErrorCount();
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void onPostExecute() {
            layout.removeCallbacks(updateViewTask);
            layout.post(updateViewTask);
        }
    };

    private void requestOsdMonStatus() {
        ClusterV1HealthCounterRequest spider = new ClusterV1HealthCounterRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successOsdMonStatus, GeneralError.callback(getActivity()));
    }

    private AnalyzeListener<String> successOsdMonStatus = new AnalyzeListener<String>() {
        @Override
        public boolean doInBackground(String s) {
            try {
                ClusterV1HealthCounterData data = new ClusterV1HealthCounterData(s);
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
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void onPostExecute() {
            layout.removeCallbacks(updateViewTask);
            layout.post(updateViewTask);
        }
    };

    private void requestPoolStatus() {
        PoolV1ListRequest spider = new PoolV1ListRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successPoolStatus, GeneralError.callback(getActivity()));
    }

    private AnalyzeListener<String> successPoolStatus = new AnalyzeListener<String>() {
        @Override
        public boolean doInBackground(String s) {
            try {
                poolData = new PoolV1ListData(s);
                poolCardStatus = poolData.getList().size();
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void onPostExecute() {
            layout.removeCallbacks(updateViewTask);
            layout.post(updateViewTask);
        }
    };

    private void requestServerList() {
        ClusterV1ServerRequest spider = new ClusterV1ServerRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successServerList, GeneralError.callback(getActivity()));
    }

    private AnalyzeListener<String> successServerList = new AnalyzeListener<String>() {
        @Override
        public boolean doInBackground(String s) {
            try {
                hostData = new ClusterV2ServerListData(s);
                hostCardStatus = hostData.getList().size();
                hostCardMonCount = hostData.getMonServers().size();
                hostCardOsdCount = hostData.getOsdServers().size();
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void onPostExecute() {
            layout.removeCallbacks(updateViewTask);
            layout.post(updateViewTask);
        }
    };

    private void requestStoreSpace() {
        ClusterV1SpaceRequest spider = new ClusterV1SpaceRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successStoreSpace, GeneralError.callback(getActivity()));
    }

    private AnalyzeListener<String> successStoreSpace = new AnalyzeListener<String>() {
        private long rightValue;
        private long leftValue;

        @Override
        public boolean doInBackground(String s) {
            try {
                ClusterV1Space data = new ClusterV1Space(s);
                rightValue = data.getCapacityBytes();
                leftValue = data.getUsedBytes();
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void onPostExecute() {
            layout.usageCard.setLongValue(leftValue, rightValue);
            layout.removeCallbacks(updateViewTask);
            layout.post(updateViewTask);
        }
    };

    private void requestIopsSum() {
        ArrayList<String> targetGroup = new ArrayList<>();
        targetGroup.add("sumSeries(" +
                "ceph.cluster." + requestParams.getClusterId() + ".pool.all.num_read" + "," +
                "ceph.cluster." + requestParams.getClusterId() + ".pool.all.num_write" +
                ")");

        requestParams.setGraphiteTargets(targetGroup);
        requestParams.setGraphitePeriod("-1d");
        GraphiteRenderRequest spider = new GraphiteRenderRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successIopsSum, GeneralError.callback(getActivity()));
    }

    private AnalyzeListener<String> successIopsSum = new AnalyzeListener<String>() {
        private ChartLine adapter;

        @Override
        public boolean doInBackground(String s) {
            try {
                GraphiteRenderData data = new GraphiteRenderData(s);
                ArrayList<Double> values = data.getValueArray(1);
                ArrayList<Long> times = data.getTimestampArray();

                adapter = new LineAdapter();
                adapter.setColor(ColorTable._8DC41F);
                adapter.setData(values, times);

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void onPostExecute() {
            layout.iopsCard.setChartData(adapter);
        }
    };

    private Runnable updateViewTask = new Runnable() {
        @Override
        public void run() {
            if (healthCardErrorCount != 0) {
                layout.healthCard.setCenterValueText(getString(R.string.health_card_status_error));
                layout.healthCard.changeRedBorder();
            } else if (healthCardWarningCount != 0) {
                layout.healthCard.setCenterValueText(getString(R.string.health_card_status_warning));
                layout.healthCard.changeOrangeBorder();
            } else {
                layout.healthCard.setCenterValueText(getString(R.string.health_card_status_ok));
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
    };
}