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
public class ClusterV1HealthRequest extends RequestCephTask {

    public ClusterV1HealthRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.clusterV1Health(params);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        return "{ \"report\": { \"detail\": [], \"timechecks\": { \"round_status\": \"finished\", \"epoch\": 1, \"round\": 0 }, \"health\": { \"health_services\": [ { \"mons\": [ { \"last_updated\": \"2015-04-22 18:41:21.080840\", \"name\": \"node-12\", \"avail_percent\": 86, \"kb_total\": 49083200, \"kb_avail\": 42266688, \"health\": \"HEALTH_OK\", \"kb_used\": 4300136, \"store_stats\": { \"bytes_total\": 2108360, \"bytes_log\": 983040, \"last_updated\": \"0.000000\", \"bytes_misc\": 65552, \"bytes_sst\": 1059768 } } ] } ] }, \"overall_status\": \"HEALTH_ERR\", \"summary\": [{ \"severity\": \"HEALTH_WARN\", \"summary\": \"960 pgs degraded\" },{ \"severity\": \"HEALTH_WARN\", \"summary\": \"recovery 79/158 objects degraded (50.000%)\" }, { \"severity\": \"HEALTH_ERR\", \"summary\": \"no osds\" }] }, \"cluster_update_time\": \"2015-04-22T18:41:52.995546+00:00\", \"cluster_update_time_unix\": 1429728112000 }";
    }
}
