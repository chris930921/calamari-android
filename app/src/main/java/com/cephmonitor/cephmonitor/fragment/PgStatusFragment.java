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
import com.cephmonitor.cephmonitor.layout.fragment.PgStatusLayout;
import com.cephmonitor.cephmonitor.layout.listitem.PgStatusItem;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1OsdData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthCounterRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1OsdListRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PgStatusFragment extends Fragment {
    private PgStatusLayout layout;
    private LoginParams requestParams;
    private LinkedHashMap<String, Integer> pgStateCount;
    private LinkedHashMap<String, Integer> healthCounts;
    private ArrayList<String> stateKeys;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new PgStatusLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }


    public void init() {
        requestParams = new LoginParams(getActivity());
        requestPgStatus();
    }

    private void requestPgStatus() {
        AnalyzeListener<String> success = new AnalyzeListener<String>() {

            @Override
            public boolean doInBackground(String s) {
                try {
                    ClusterV1OsdData listData = new ClusterV1OsdData(s);
                    pgStateCount = listData.getPgStateCounts();
                    stateKeys = new ArrayList<>(pgStateCount.keySet());
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public void onPostExecute() {
                requestHealthCount();
            }
        };

        Response.ErrorListener fail = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestPgStatus();
                    }
                }, 2000);
            }
        };

        ClusterV1OsdListRequest spider = new ClusterV1OsdListRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(success, fail);
    }

    private void requestHealthCount() {
        AnalyzeListener<String> success = new AnalyzeListener<String>() {

            @Override
            public boolean doInBackground(String s) {
                try {
                    ClusterV1HealthCounterData countData = new ClusterV1HealthCounterData(s);
                    healthCounts = countData.getPgStateCounts();
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public void onPostExecute() {
                layout.list.setAdapter(getAdapter);
            }
        };

        Response.ErrorListener fail = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestHealthCount();
                    }
                }, 2000);
            }
        };

        ClusterV1HealthCounterRequest spider = new ClusterV1HealthCounterRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(success, fail);
    }


    private BaseAdapter getAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return stateKeys.size();
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
            PgStatusItem item = new PgStatusItem(getActivity());
            String key = stateKeys.get(i);
            int pgCount = healthCounts.get(key);
            int osdCount = pgStateCount.get(key);
            item.setData(key, pgCount, osdCount);
            return item;
        }
    };
}
