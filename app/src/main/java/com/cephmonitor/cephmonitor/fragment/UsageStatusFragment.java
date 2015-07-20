package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.ChartLine;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.AreaLineAdapter;
import com.cephmonitor.cephmonitor.layout.fragment.UsageStatusLayout;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.resourcelibrary.model.network.api.ceph.object.GraphiteRenderData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.GraphiteRenderRequest;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class UsageStatusFragment extends Fragment {
    private UsageStatusLayout layout;
    private LoginParams requestParams;
    private LoadingDialog loadingDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new UsageStatusLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        loadingDialog = new LoadingDialog(getActivity());
        requestParams = new LoginParams(getActivity());
        requestUsageStatus();
        loadingDialog.show();
    }

    private void requestUsageStatus() {
        final ArrayList<String> targetGroup = new ArrayList<>();
        final int[] colorGroup = {ColorTable._8DC41F, ColorTable._F7B500};
        targetGroup.add("scale(ceph.cluster." + requestParams.getClusterId() + ".df.total_avail,1024)");
        targetGroup.add("scale(ceph.cluster." + requestParams.getClusterId() + ".df.total_used,1024)");

        AnalyzeListener<String> success = new AnalyzeListener<String>() {
            ArrayList<Long> timeGroup;
            HashMap<String, Integer> targetIndexGroup;
            ArrayList<ChartLine> adapterGroup;

            @Override
            public synchronized boolean doInBackground(String s) {
                adapterGroup = new ArrayList<>();
                try {
                    GraphiteRenderData renderData = new GraphiteRenderData(s);
                    targetIndexGroup = renderData.getTargets();
                    timeGroup = renderData.getTimestampArray();
                    for (int i = 0; i < targetGroup.size(); i++) {
                        String target = targetGroup.get(i);
                        int color = colorGroup[i];
                        if (targetIndexGroup.get(target) == null) continue;

                        int dataPointIndex = targetIndexGroup.get(target);
                        AreaLineAdapter adapter = new AreaLineAdapter();
                        adapter.setColor(color);
                        ArrayList<Double> valueGroup = renderData.getSumValueArray(dataPointIndex);
                        adapter.setData(valueGroup, timeGroup);
                        adapterGroup.add(adapter);
                    }
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public void onPostExecute() {
                layout.table.cleanData();
                layout.table.setMaxTime(Calendar.getInstance());
                for (int i = 0; i < adapterGroup.size(); i++) {
                    ChartLine adapter = adapterGroup.get(i);
                    layout.table.addAdapter(adapter);
                }
            }

            @Override
            public void requestFinish(boolean isAnalyzeSuccess) {
                super.requestFinish(isAnalyzeSuccess);
                LoadingDialog.delayCancel(layout, loadingDialog);
            }
        };

        Response.ErrorListener fail = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LoadingDialog.delayCancel(layout, loadingDialog);
            }
        };

        requestParams.setGraphitePeriod("-1d");
        requestParams.setGraphiteTargets(targetGroup);

        GraphiteRenderRequest spider = new GraphiteRenderRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(success, fail);
    }

}
