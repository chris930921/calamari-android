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
public class ClusterV1ServerRequest extends RequestCephTask {

    public ClusterV1ServerRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.clusterV1Server(params);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        return "[ { \"addr\": \"node-12.domain.tld\", \"hostname\": \"node-12\", \"name\": \"node-12\", \"services\": [ { \"type\": \"mon\", \"service_id\": \"node-12\", \"name\": \"mon.node-12\" } ] }, { \"addr\": \"node-11\", \"hostname\": \"node-11\", \"name\": \"node-11\", \"services\": [ { \"type\": \"osd\", \"service_id\": \"2\", \"name\": \"osd.2\" } ] }, { \"addr\": \"node-18\", \"hostname\": \"node-18\", \"name\": \"node-18\", \"services\": [ { \"type\": \"osd\", \"service_id\": \"0\", \"name\": \"osd.0\" } ] }, { \"addr\": \"node-17\", \"hostname\": \"node-17\", \"name\": \"node-17\", \"services\": [ { \"type\": \"osd\", \"service_id\": \"1\", \"name\": \"osd.1\" } ] } ]";
    }
}
