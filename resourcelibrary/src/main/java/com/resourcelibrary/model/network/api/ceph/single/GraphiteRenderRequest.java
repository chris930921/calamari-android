package com.resourcelibrary.model.network.api.ceph.single;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.resourcelibrary.model.network.api.ceph.CephApiUrl;
import com.resourcelibrary.model.network.api.ceph.CephGetRequest;
import com.resourcelibrary.model.network.api.ceph.CephParams;
import com.resourcelibrary.model.network.api.ceph.RequestCephTask;

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
        return "{ \"targets\": [ \"ceph.cluster." + params.getClusterId() + ".pool.0.num_read\", \"ceph.cluster." + params.getClusterId() + ".pool.0.num_write\" ], \"datapoints\": [] }";
    }
}
