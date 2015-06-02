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
public class ClusterV1SpaceRequest extends RequestCephTask {

    public ClusterV1SpaceRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.clusterV1Space(params);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        String[] used_values = {"181830676480", "281830676480", "381830676480", "477288345600", "509107568640"};
        int index = (int) (Math.random() * 5);
        return "{ \"space\": { \"free_bytes\": 631986425856, \"used_bytes\": " + used_values[index] + ", \"capacity_bytes\": 636384460800 } }";
    }
}
