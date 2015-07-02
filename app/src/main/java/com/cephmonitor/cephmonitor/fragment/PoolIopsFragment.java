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
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.LineAdapter;
import com.cephmonitor.cephmonitor.layout.fragment.PoolIopsLayout;
import com.cephmonitor.cephmonitor.layout.listitem.PoolIopsItem;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.ceph.object.GraphiteRenderData;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1Data;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1ListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.GraphitePoolReadWriteRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class PoolIopsFragment extends Fragment {
    private PoolIopsLayout layout;
    private ArrayList<PoolV1Data> pools;
    private HashMap<Integer, PoolIopsItem> poolIopsItemGroup;
    private LoginParams requestParams;
    final int[] colorGroup = {ColorTable._8DC41F, ColorTable._F7B500};
    private HashMap<Integer, ArrayList<LineAdapter>> adapterListGroup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new PoolIopsLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        poolIopsItemGroup = new HashMap<>();
        adapterListGroup = new HashMap<>();
        requestParams = new LoginParams(getActivity());
        try {
            Bundle arg = getArguments();
            PoolV1ListData poolData = new PoolV1ListData("[]");
            poolData.inBox(arg);
            pools = poolData.getList();
        } catch (JSONException e) {
            e.printStackTrace();
            pools = new ArrayList<>();
        }
        layout.list.setAdapter(getAdapter);

        if (pools.size() != 0) {
            requestPoolReadWrite(0);
        }
    }

    private void requestPoolReadWrite(final int index) {
        try {
            final int poolId = pools.get(index).getPoolId();
            final ArrayList<String> targetGroup = new ArrayList<>();
            targetGroup.add("ceph.cluster." + requestParams.getClusterId() + ".pool." + poolId + ".num_read");
            targetGroup.add("ceph.cluster." + requestParams.getClusterId() + ".pool." + poolId + ".num_write");

            requestParams.setGraphitePeriod("-1d");
            requestParams.setGraphiteTargets(targetGroup);

            AnalyzeListener<String> success = new AnalyzeListener<String>() {
                @Override
                public synchronized boolean doInBackground(String s) {
                    ArrayList<LineAdapter> adapterGroup = new ArrayList<>();
                    try {
                        GraphiteRenderData renderData = new GraphiteRenderData(s);
                        HashMap<String, Integer> targetIndexGroup = renderData.getTargets();
                        ArrayList<Long> timeGroup = renderData.getTimestampArray();
                        ShowLog.d(targetGroup + "");

                        for (int i = 0; i < targetGroup.size(); i++) {
                            String target = targetGroup.get(i);
                            int color = colorGroup[i];
                            if (targetIndexGroup.get(target) == null) continue;

                            int dataPointIndex = targetIndexGroup.get(target);
                            LineAdapter adapter = new LineAdapter();
                            adapter.setColor(color);
                            ArrayList<Double> valueGroup = renderData.getValueArray(dataPointIndex);
                            adapter.setData(valueGroup, timeGroup);
                            ShowLog.d("資料: 數值" + valueGroup);
                            ShowLog.d("資料: 時間" + timeGroup);
                            adapterGroup.add(adapter);
                        }
//                        adapterListGroup.put(poolId, new TestLineAdapter());
                        adapterListGroup.put(poolId, adapterGroup);
                        return true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                @Override
                public void onPostExecute() {
                    PoolIopsItem item = poolIopsItemGroup.get(poolId);
                    if ((index + 1) < pools.size()) {
                        requestPoolReadWrite(index + 1);
                    }
                    if (item == null) return;
                    if (item.getTag().equals(poolId)) {
                        item.setData(adapterListGroup.get(poolId));
                    }
                }
            };

            Response.ErrorListener fail = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            };

            GraphitePoolReadWriteRequest spider = new GraphitePoolReadWriteRequest(getActivity());
            spider.setRequestParams(requestParams);
            spider.request(success, fail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private BaseAdapter getAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return pools.size();
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
            try {
                String name = pools.get(i).getName();
                int poolId = pools.get(i).getPoolId();
                poolIopsItemGroup.put(poolId, item);
                item.setTag(poolId);
                item.setName(name);
                ArrayList<LineAdapter> adapterGroup = adapterListGroup.get(poolId);
                if (adapterGroup != null) {
                    item.setData(adapterGroup);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return item;
        }
    };
}
