package com.resourcelibrary.model.network.api.ceph.single;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.resourcelibrary.model.io.ReadAssetsFile;
import com.resourcelibrary.model.network.api.ceph.CephApiUrl;
import com.resourcelibrary.model.network.api.ceph.CephGetRequest;
import com.resourcelibrary.model.network.api.ceph.CephParams;
import com.resourcelibrary.model.network.api.ceph.RequestCephTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 4/16/2015.
 */
public class GraphiteRenderRequest extends RequestCephTask {

    public GraphiteRenderRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.graphiteRender(params)
                .format("json-array")
                .from(params.getGraphitePeriod())
                .targets(params.getGraphiteTargets())
                .build();
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        String result = new ReadAssetsFile(getContext()).readText("api/graphite_render_empty.txt");
        ArrayList<String> targetGroup = params.getGraphiteTargets();
        if (targetGroup.size() == 0) return result;

        boolean check = false;
        if (targetGroup.size() == 2) {
            String targetOne = targetGroup.get(0);
            String targetTwo = targetGroup.get(1);
            check = targetOne.contains("pool");
            check &= targetTwo.contains("pool");
            check &= targetOne.contains("num_read");
            check &= targetTwo.contains("num_write");
        }

        if (check) {
            return getPoolData(params);
        } else {
            return getNormalData(params);
        }
//        return "{ \"targets\": [ \"ceph.cluster." + params.getClusterId() + ".pool.0.num_read\", \"ceph.cluster." + params.getClusterId() + ".pool.0.num_write\" ], \"datapoints\": [] }";
    }

    public String getNormalData(CephParams params) {
        String result = new ReadAssetsFile(getContext()).readText("api/graphite_render_empty.txt");
        try {
            JSONObject total = new JSONObject(result);
            JSONArray targets = total.getJSONArray("targets");
            for (String target : params.getGraphiteTargets()) {
                targets.put(target);
            }

            JSONArray datapoints = total.getJSONArray("datapoints");
            int column = targets.length();
            Calendar time = Calendar.getInstance();
            for (int i = 0; i < 2000; i++) {
                JSONArray item = new JSONArray();
                time.add(Calendar.MINUTE, -10);
                item.put(time.getTimeInMillis() / 1000);

                double value = 500;
                for (int j = 0; j < column; j++) {
                    value += Math.random() * 100;
                    item.put(value);
                }
                datapoints.put(item);
            }
            result = total.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getPoolData(CephParams params) {
        String result = new ReadAssetsFile(getContext()).readText("api/graphite_render_empty.txt");
        try {
            JSONObject total = new JSONObject(result);
            JSONArray targets = total.getJSONArray("targets");
            for (String target : params.getGraphiteTargets()) {
                targets.put(target);
            }

            JSONArray datapoints = total.getJSONArray("datapoints");
            Calendar time = Calendar.getInstance();
            for (int i = 0; i < 2000; i++) {
                JSONArray item = new JSONArray();
                time.add(Calendar.MINUTE, -10);
                item.put(time.getTimeInMillis() / 1000);
                item.put(Math.random() * 100 + 100);
                item.put(Math.random() * 100);
                datapoints.put(item);
            }
            result = total.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
