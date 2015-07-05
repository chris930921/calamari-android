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
public class ApiV2ClusterIdPoolRequest extends RequestCephTask {

    public ApiV2ClusterIdPoolRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.apiV2ClusterIdPool(params);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        return "[{\"name\": \"rbd\", \"id\": 0, \"size\": 3, \"pg_num\": 128, \"crush_ruleset\": 0, \"min_size\": 2, \"crash_replay_interval\": 0, \"pgp_num\": 128, \"hashpspool\": true, \"full\": false, \"quota_max_objects\": 0, \"quota_max_bytes\": 0}, {\"name\": \"cephfs_data\", \"id\": 1, \"size\": 3, \"pg_num\": 256, \"crush_ruleset\": 0, \"min_size\": 2, \"crash_replay_interval\": 45, \"pgp_num\": 128, \"hashpspool\": true, \"full\": false, \"quota_max_objects\": 0, \"quota_max_bytes\": 0}, {\"name\": \"cephfs_metadata\", \"id\": 2, \"size\": 3, \"pg_num\": 256, \"crush_ruleset\": 0, \"min_size\": 2, \"crash_replay_interval\": 0, \"pgp_num\": 128, \"hashpspool\": true, \"full\": false, \"quota_max_objects\": 0, \"quota_max_bytes\": 0}]";
    }
}
