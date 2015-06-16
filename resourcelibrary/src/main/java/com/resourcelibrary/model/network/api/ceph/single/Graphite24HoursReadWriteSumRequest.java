package com.resourcelibrary.model.network.api.ceph.single;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.ceph.CephApiUrl;
import com.resourcelibrary.model.network.api.ceph.CephGetRequest;
import com.resourcelibrary.model.network.api.ceph.CephParams;
import com.resourcelibrary.model.network.api.ceph.RequestCephTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by User on 4/16/2015.
 */
public class Graphite24HoursReadWriteSumRequest extends RequestCephTask {

    public Graphite24HoursReadWriteSumRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.graphite(params)
                .format("json-array")
                .from("-1d")
                .target("sumSeries(ceph.cluster." + params.getClusterId() + ".pool.all.num_read,ceph.cluster." + params.getClusterId() + ".pool.all.num_write)")
                .build();
        ShowLog.d("Graphite24HoursReadWriteSumRequest" + " 結果:" + url);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        try {
            JSONObject json = new JSONObject();
            JSONArray targets = new JSONArray();
            targets.put("sumSeries(ceph.cluster." + params.getClusterId() + ".pool.all.num_read,ceph.cluster." + params.getClusterId() + ".pool.all.num_write)");
            json.put("targets", targets);

            Calendar now = Calendar.getInstance();
            JSONArray datapoints = new JSONArray();
            for (int i = 0; i < 1500; i++) {
                JSONArray point = new JSONArray();
                point.put(now.getTimeInMillis() / 1000);
                try {
                    point.put(Math.random() * 2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                datapoints.put(point);
                now.add(Calendar.MINUTE, -1);
            }
            json.put("datapoints", datapoints);
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{ \"targets\": [ \"sumSeries(ceph.cluster." + params.getClusterId() + ".pool.all.num_read,ceph.cluster." + params.getClusterId() + ".pool.all.num_write)\" ], \"datapoints\": [] }";
        }
    }
}
