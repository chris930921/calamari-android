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
public class ClusterV2MonStatusRequest extends RequestCephTask {

    public ClusterV2MonStatusRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.clusterV2MonStatus(params);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        return "{ \"election_epoch\": 1, \"name\": \"node-60\", \"monmap\": { \"epoch\": 1, \"mons\": [ { \"name\": \"node-60\", \"rank\": 0, \"addr\": \"192.168.0.8:6789/0\" } ], \"modified\": \"0.000000\", \"fsid\": \"7d0d620a-9365-471f-832c-2883d3ef20cb\", \"created\": \"0.000000\" }, \"rank\": 0, \"outside_quorum\": [], \"state\": \"leader\", \"extra_probe_peers\": [], \"sync_provider\": [], \"quorum\": [ 0 ] }";
    }
}
