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
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.LineAdapter;
import com.cephmonitor.cephmonitor.layout.fragment.HostDetailSummaryLayout;
import com.cephmonitor.cephmonitor.layout.listitem.HostDetailItem;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.cephmonitor.cephmonitor.model.network.SequenceTask;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.ceph.object.GraphiteRenderData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.GraphitePoolReadWriteRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class HostDetailSummaryFragment extends Fragment {
    private HostDetailSummaryLayout layout;
    private ArrayList<String> summaryTargetGroup;
    private ArrayList<String> loadAverageTargetGroup;
    private ArrayList<String> memoryTargetGroup;
    private ArrayList<ArrayList<String>> targetListGroup;
    private HashMap<Integer, HostDetailItem> itemGroup;
    private HashMap<Integer, ArrayList<ChartLine>> adapterListGroup;
    private SequenceTask taskGroup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new HostDetailSummaryLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        taskGroup = new SequenceTask();
        loadAverageTargetGroup = new ArrayList<>();
        memoryTargetGroup = new ArrayList<>();
        itemGroup = new HashMap<>();
        adapterListGroup = new HashMap<>();

        Bundle arg = getArguments();
        String hostName = arg.getString("0");

        summaryTargetGroup = new ArrayList<>();
        summaryTargetGroup.add("servers." + hostName + ".cpu.total.system");
        summaryTargetGroup.add("servers." + hostName + ".cpu.total.user");
        summaryTargetGroup.add("servers." + hostName + ".cpu.total.idle");

        loadAverageTargetGroup = new ArrayList<>();
        loadAverageTargetGroup.add("servers." + hostName + ".loadavg.01");
        loadAverageTargetGroup.add("servers." + hostName + ".loadavg.05");
        loadAverageTargetGroup.add("servers." + hostName + ".loadavg.15");

        memoryTargetGroup = new ArrayList<>();
        memoryTargetGroup.add("servers." + hostName + ".memory.Active");
        memoryTargetGroup.add("servers." + hostName + ".memory.Buffers");
        memoryTargetGroup.add("servers." + hostName + ".memory.Cached");
        memoryTargetGroup.add("servers." + hostName + ".memory.MemFree");

        targetListGroup = new ArrayList<>();
        targetListGroup.add(summaryTargetGroup);
        targetListGroup.add(loadAverageTargetGroup);
        targetListGroup.add(memoryTargetGroup);

        for (int i = 0; i < targetListGroup.size(); i++) {
            request(i);
        }

        taskGroup.start();
        layout.list.setAdapter(adapter);
    }

    private void request(final int index) {
        final ArrayList<String> targetGroup = targetListGroup.get(index);
        final int[] colorGroup = layout.colorGroup[index];

        AnalyzeListener<String> success = new AnalyzeListener<String>() {
            @Override
            public synchronized boolean doInBackground(String s) {
                ArrayList<ChartLine> adapterGroup = new ArrayList<>();
                try {
                    GraphiteRenderData renderData = new GraphiteRenderData(s);
                    ArrayList<Long> timeGroup = renderData.getTimestampArray();

                    for (int i = 0; i < targetGroup.size(); i++) {
                        int color = colorGroup[i];

                        int dataPointIndex = i + 1;
                        ArrayList<Double> valueGroup = getValueGroup(index, renderData, dataPointIndex);

                        ChartLine adapter = getChartLine(index);
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
                HostDetailItem item = itemGroup.get(index);

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

        GraphitePoolReadWriteRequest spider = new GraphitePoolReadWriteRequest(getActivity());
        spider.setRequestParams(requestParams);
        taskGroup.add(spider, success, fail);
    }

    private ArrayList<Double> getValueGroup(int index, GraphiteRenderData renderData, int dataPointIndex) {
        if (index == 0) {
            return renderData.getSumValueArray(dataPointIndex);
        } else if (index == 1) {
            return renderData.getValueArray(dataPointIndex);
        } else {
            return renderData.getSumValueArray(dataPointIndex);
        }
    }

    private ChartLine getChartLine(int index) {
        if (index == 0) {
            return new AreaLineAdapter();
        } else if (index == 1) {
            return new LineAdapter();
        } else {
            return new AreaLineAdapter();
        }
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
            HostDetailItem item;

            if (view == null) {
                item = new HostDetailItem(getActivity());
            } else {
                item = (HostDetailItem) view;
            }
            item.setTag(i);
            itemGroup.put(i, item);
            item.setName(layout.titleGroup[i]);
            item.setLineText(layout.lineTextGroup[i], layout.colorGroup[i]);
            if (adapterListGroup.get(i) != null) {
                item.setData(adapterListGroup.get(i));
            }
            return item;
        }
    };
}
