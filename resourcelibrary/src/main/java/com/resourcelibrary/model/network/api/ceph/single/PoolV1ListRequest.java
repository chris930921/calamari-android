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
public class PoolV1ListRequest extends RequestCephTask {

    public PoolV1ListRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.poolV1List(params);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        return "[ { \"pool_id\": 0, \"name\": \"data\", \"quota_max_bytes\": 0, \"quota_max_objects\": 0, \"used_objects\": 0, \"used_bytes\": 0, \"id\": 0, \"cluster\": \"313b8bd3-a506-49bd-ad57-f99cf350505f\" }, { \"pool_id\": 1, \"name\": \"metadata\", \"quota_max_bytes\": 0, \"quota_max_objects\": 0, \"used_objects\": 0, \"used_bytes\": 0, \"id\": 1, \"cluster\": \"313b8bd3-a506-49bd-ad57-f99cf350505f\" }, { \"pool_id\": 2, \"name\": \"rbd\", \"quota_max_bytes\": 0, \"quota_max_objects\": 0, \"used_objects\": 0, \"used_bytes\": 0, \"id\": 2, \"cluster\": \"313b8bd3-a506-49bd-ad57-f99cf350505f\" }, { \"pool_id\": 3, \"name\": \"images\", \"quota_max_bytes\": 0, \"quota_max_objects\": 0, \"used_objects\": 79, \"used_bytes\": 612953121, \"id\": 3, \"cluster\": \"313b8bd3-a506-49bd-ad57-f99cf350505f\" }, { \"pool_id\": 4, \"name\": \"volumes\", \"quota_max_bytes\": 0, \"quota_max_objects\": 0, \"used_objects\": 0, \"used_bytes\": 0, \"id\": 4, \"cluster\": \"313b8bd3-a506-49bd-ad57-f99cf350505f\" }, { \"pool_id\": 5, \"name\": \"compute\", \"quota_max_bytes\": 0, \"quota_max_objects\": 0, \"used_objects\": 0, \"used_bytes\": 0, \"id\": 5, \"cluster\": \"313b8bd3-a506-49bd-ad57-f99cf350505f\" } ]";
    }
}
