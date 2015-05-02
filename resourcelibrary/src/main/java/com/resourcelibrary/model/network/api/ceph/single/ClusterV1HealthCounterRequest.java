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
public class ClusterV1HealthCounterRequest extends RequestCephTask {

    public ClusterV1HealthCounterRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.clusterV1HealthCounter(params);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        return "{ \"pg\": { \"warn\": { \"count\": 0, \"states\": {} }, \"critical\": { \"count\": 0, \"states\": {} }, \"ok\": { \"count\": 960, \"states\": { \"active\": 960, \"clean\": 960 } } }, \"mds\": { \"up_not_in\": 0, \"not_up_not_in\": 0, \"total\": 0, \"up_in\": 0 }, \"mon\": { \"warn\": { \"count\": 0, \"states\": {} }, \"critical\": { \"count\": 0, \"states\": {} }, \"ok\": { \"count\": 1, \"states\": { \"in\": 1 } } }, \"osd\": { \"warn\": { \"count\": 1, \"states\": {} }, \"critical\": { \"count\": 2, \"states\": {} }, \"ok\": { \"count\": 3, \"states\": { \"up/in\": 3 } } }, \"cluster_update_time\": \"2015-04-22T18:42:23.046870+00:00\", \"cluster_update_time_unix\": 1429728143000 }";
    }
}
