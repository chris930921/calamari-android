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
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartLine;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.AreaLineAdapter;
import com.cephmonitor.cephmonitor.layout.fragment.PoolIopsLayout;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.PoolIopsItem;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.cephmonitor.cephmonitor.model.network.SequenceTask;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.RequestVolleyTask;
import com.resourcelibrary.model.network.api.ceph.object.GraphiteRenderData;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1ListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.GraphiteRenderRequest;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PoolIopsFragment extends Fragment {
    private PoolIopsLayout layout;
    private ArrayList<ArrayList<String>> targetListGroup;
    private HashMap<Integer, PoolIopsItem> itemGroup;
    private HashMap<Integer, ArrayList<ChartLine>> adapterListGroup;
    private SequenceTask taskGroup;
    private LinkedHashMap<String, String> pools;
    private LoadingDialog loadingDialog;
    private ArrayList<String> graphiteIdGroup;
    private ArrayList<String> idGroup;

    final int[] lineTextColorGroup = {ColorTable._8DC41F, ColorTable._F7B500};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new PoolIopsLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        loadingDialog = new LoadingDialog(getActivity());
        targetListGroup = new ArrayList<>();
        adapterListGroup = new HashMap<>();
        itemGroup = new HashMap<>();
        pools = new LinkedHashMap<>();
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

        try {
            Bundle arg = getArguments();
            String clusterId = arg.getString("0");
            PoolV1ListData poolData = new PoolV1ListData("[]");
            poolData.inBox(arg);
            pools = poolData.getIdStringMapNameContainAggregate();
            idGroup = poolData.getIdGroupContainAggregate();
            graphiteIdGroup = poolData.getGraphiteIdGroupContainAggregate(clusterId);

            layout.list.setAdapter(defaultAdapter);
            layout.postDelayed(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i < graphiteIdGroup.size(); i++) {
                        String id = graphiteIdGroup.get(i);
                        ArrayList<String> targetGroup = new ArrayList<>();
                        targetGroup.add(id + ".num_read");
                        targetGroup.add(id + ".num_write");

                        targetListGroup.add(targetGroup);
                    }
                    for (int i = 0; i < targetListGroup.size(); i++) {
                        request(i);
                    }
                    taskGroup.start();
                    layout.list.setAdapter(adapter);
                }
            }, 300);
            loadingDialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                        ArrayList<Double> valueGroup = renderData.getValueArray(dataPointIndex);

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
                PoolIopsItem item = itemGroup.get(index);

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
            PoolIopsItem item;
            if (view == null) {
                item = new PoolIopsItem(getActivity());
            } else {
                item = (PoolIopsItem) view;
            }
            String poolName = pools.get(idGroup.get(i));
            item.setTag(i);
            itemGroup.put(i, item);
            item.setName(poolName);
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
            PoolIopsItem item = new PoolIopsItem(getActivity());
            return item;
        }
    };
}
