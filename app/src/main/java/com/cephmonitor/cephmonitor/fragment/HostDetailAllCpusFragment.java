package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartLine;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.AreaLineAdapter;
import com.cephmonitor.cephmonitor.layout.fragment.HostDetailAllCpusLayout;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.HostDetailAllCpusItem;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.cephmonitor.cephmonitor.model.network.SequenceTask;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.RequestVolleyTask;
import com.resourcelibrary.model.network.api.ceph.object.GraphiteFindData;
import com.resourcelibrary.model.network.api.ceph.object.GraphiteFindListData;
import com.resourcelibrary.model.network.api.ceph.object.GraphiteRenderData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.GraphiteMetricsFindPools;
import com.resourcelibrary.model.network.api.ceph.single.GraphiteRenderRequest;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class HostDetailAllCpusFragment extends Fragment {
    private HostDetailAllCpusLayout layout;
    private ArrayList<ArrayList<String>> targetListGroup;
    private ArrayList<GraphiteFindData> metricsGroup;
    private HashMap<Integer, HostDetailAllCpusItem> itemGroup;
    private HashMap<Integer, ArrayList<ChartLine>> adapterListGroup;
    private SequenceTask taskGroup;
    private LoadingDialog loadingDialog;

    final int[] lineTextGroup = HostDetailAllCpusLayout.textGroup;
    final int[] lineTextColorGroup = HostDetailAllCpusLayout.textColorGroup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new HostDetailAllCpusLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        loadingDialog = new LoadingDialog(getActivity());
        metricsGroup = new ArrayList<>();
        targetListGroup = new ArrayList<>();
        adapterListGroup = new HashMap<>();
        itemGroup = new HashMap<>();
        taskGroup = new SequenceTask();
        taskGroup = new SequenceTask();
        taskGroup.setOnEveryTaskFinish(new SequenceTask.CallBack() {
            @Override
            public void onTotalStart() {
            }

            @Override
            public void onEveryTaskFinish(RequestVolleyTask task, int taskIndex, boolean isTaskSuccess) {

            }

            @Override
            public void onTotalFinish(int taskSize, boolean isTotalSuccess) {
                LoadingDialog.delayCancel(layout, loadingDialog);
            }
        });

        layout.list.setAdapter(defaultAdapter);
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle arg = getArguments();
                String hostName = arg.getString("0");
                requestTargetGroups("servers." + hostName + ".cpu.*");
                loadingDialog.show();
            }
        }, 300);
    }

    private void requestTargetGroups(String query) {
        AnalyzeListener<String> success = new AnalyzeListener<String>() {
            @Override
            public synchronized boolean doInBackground(String s) {
                try {
                    GraphiteFindListData data = new GraphiteFindListData(s);
                    metricsGroup = data.getOrderNumberBehindNameList();

                    for (int i = 0; i < metricsGroup.size(); i++) {
                        GraphiteFindData metrics = metricsGroup.get(i);
                        String target = metrics.getId();

                        ArrayList<String> targetGroup = new ArrayList<>();
                        targetGroup.add(target + ".system");
                        targetGroup.add(target + ".user");
                        targetGroup.add(target + ".nice");
                        targetGroup.add(target + ".idle");
                        targetGroup.add(target + ".iowait");
                        targetGroup.add(target + ".irq");
                        targetGroup.add(target + ".softirq");
                        targetGroup.add(target + ".steal");

                        targetListGroup.add(targetGroup);
                    }
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public void onPostExecute() {
                for (int i = 0; i < metricsGroup.size(); i++) {
                    request(i);
                }
                taskGroup.start();
                layout.list.setAdapter(adapter);
            }
        };

        Response.ErrorListener fail = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        };

        LoginParams requestParams = new LoginParams(getActivity());
        requestParams.setGraphiteQuery(query);

        GraphiteMetricsFindPools spider = new GraphiteMetricsFindPools(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(success, fail);
    }

    private void request(final int index) {
        final ArrayList<String> targetGroup = targetListGroup.get(index);

        AnalyzeListener<String> success = new AnalyzeListener<String>() {
            @Override
            public synchronized boolean doInBackground(String s) {
                ArrayList<ChartLine> adapterGroup = new ArrayList<>();
                try {
                    GraphiteRenderData renderData = new GraphiteRenderData(s);
                    ArrayList<Long> timeGroup = renderData.getTimestampArray();

                    for (int i = 0; i < targetGroup.size(); i++) {
                        int color = lineTextColorGroup[i];

                        int dataPointIndex = i + 1;
                        ArrayList<Double> valueGroup = renderData.getSumValueArray(dataPointIndex);

                        ChartLine adapter = new AreaLineAdapter();
                        adapter.setColor(color);
                        adapter.setData(valueGroup, timeGroup);

                        ShowLog.d("資料: 數值" + valueGroup);
                        ShowLog.d("資料: 時間" + timeGroup);

                        adapterGroup.add(adapter);
                    }
//                        adapterListGroup.put(poolId, new TestLineAdapter());
                    adapterListGroup.put(index, adapterGroup);
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public void onPostExecute() {
                HostDetailAllCpusItem item = itemGroup.get(index);

                if (item == null) return;
                if (item.getTag().equals(index)) {
                    item.setData(adapterListGroup.get(index));
                }
            }
        };

        Response.ErrorListener fail = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        };

        LoginParams requestParams = new LoginParams(getActivity());
        requestParams.setGraphitePeriod("-1d");
        requestParams.setGraphiteTargets(targetGroup);

        GraphiteRenderRequest spider = new GraphiteRenderRequest(getActivity());
        spider.setRequestParams(requestParams);
        taskGroup.add(spider, success, fail);
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return targetListGroup.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HostDetailAllCpusItem item;
            if (view == null) {
                item = new HostDetailAllCpusItem(getActivity());
            } else {
                item = (HostDetailAllCpusItem) view;
            }
            item.setTag(i);
            itemGroup.put(i, item);
            item.setName(metricsGroup.get(i).getName());
            item.setLineText(lineTextGroup, lineTextColorGroup);
            if (adapterListGroup.get(i) != null) {
                item.setData(adapterListGroup.get(i));
            }
            return item;
        }
    };

    private BaseAdapter defaultAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HostDetailAllCpusItem item = new HostDetailAllCpusItem(getActivity());
            return item;
        }
    };
}
