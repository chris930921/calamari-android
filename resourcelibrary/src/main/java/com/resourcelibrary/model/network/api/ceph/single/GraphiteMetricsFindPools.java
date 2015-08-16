package com.resourcelibrary.model.network.api.ceph.single;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.resourcelibrary.model.io.ReadAssetsFile;
import com.resourcelibrary.model.network.api.ceph.CephApiUrl;
import com.resourcelibrary.model.network.api.ceph.CephGetRequest;
import com.resourcelibrary.model.network.api.ceph.CephParams;
import com.resourcelibrary.model.network.api.ceph.RequestCephTask;

/**
 * Created by User on 4/16/2015.
 */
public class GraphiteMetricsFindPools extends RequestCephTask {

    public GraphiteMetricsFindPools(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.graphiteMetricsFind(params)
                .query(params.getGraphiteQuery())
                .build();
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        String path;
        if (params.getGraphiteQuery().contains("iostat")) {
            path = "api/graphite_mettics_find_servers_ceph_node2_iostat_all.txt";
        } else if (params.getGraphiteQuery().contains("cpu")) {
            path = "api/graphite_mettics_find_servers_ceph_node2_cpu_all.txt";
        } else if (params.getGraphiteQuery().contains("pool")) {
            path = "api/graphite_mettics_find_servers_ceph_cluster_id_pool_all.txt";
        } else {
            return "[]";
        }
        String result = new ReadAssetsFile(getContext()).readText(path);
        return result;
    }
}
