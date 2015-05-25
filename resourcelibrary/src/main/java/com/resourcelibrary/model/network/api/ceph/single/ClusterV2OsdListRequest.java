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
public class ClusterV2OsdListRequest extends RequestCephTask {

    public ClusterV2OsdListRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.clusterV2OsdList(params);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        return "[ { \"uuid\": \"d1e5be30-77eb-4c3c-8d46-43281f4ef85f\", \"up\": true, \"in\": true, \"id\": 0, \"reweight\": 1, \"server\": \"node-65\", \"pools\": [ 0, 1, 2, 3, 4, 5 ], \"valid_commands\": [ \"scrub\", \"deep_scrub\", \"repair\" ], \"public_addr\": \"192.168.0.11:6800/10299\", \"cluster_addr\": \"192.168.1.11:6800/10299\", \"crush_node_ancestry\": [ [ -2, -1 ] ] }, { \"uuid\": \"f55788e8-1779-4d36-9a08-4e1634a3020a\", \"up\": true, \"in\": true, \"id\": 1, \"reweight\": 1, \"server\": \"node-61\", \"pools\": [ 0, 1, 2, 3, 4, 5 ], \"valid_commands\": [ \"scrub\", \"deep_scrub\", \"repair\" ], \"public_addr\": \"192.168.0.9:6800/10287\", \"cluster_addr\": \"192.168.1.9:6800/10287\", \"crush_node_ancestry\": [ [ -3, -1 ] ] }, { \"uuid\": \"7cbb588f-caf8-4784-9f1e-cce5a49190ff\", \"up\": true, \"in\": true, \"id\": 2, \"reweight\": 1, \"server\": \"node-58\", \"pools\": [ 0, 1, 2, 3, 4, 5 ], \"valid_commands\": [ \"scrub\", \"deep_scrub\", \"repair\" ], \"public_addr\": \"192.168.0.7:6800/10019\", \"cluster_addr\": \"192.168.1.7:6800/10019\", \"crush_node_ancestry\": [ [ -4, -1 ] ] } ,{ \"uuid\": \"f55788e8-1779-4d36-9a08-4e1634a3020b\", \"up\": false, \"in\": true, \"id\": 3, \"reweight\": 1, \"server\": \"node-61\", \"pools\": [ 0, 1, 2, 3, 4, 5 ], \"valid_commands\": [ \"scrub\", \"deep_scrub\", \"repair\" ], \"public_addr\": \"192.168.0.9:6800/10287\", \"cluster_addr\": \"192.168.1.9:6800/10287\", \"crush_node_ancestry\": [ [ -3, -1 ] ] },{ \"uuid\": \"f55788e8-1779-4d36-9a08-4e1634a3020c\", \"up\": false, \"in\": true, \"id\": 4, \"reweight\": 1, \"server\": \"node-61\", \"pools\": [ 0, 1, 2, 3, 4, 5 ], \"valid_commands\": [ \"scrub\", \"deep_scrub\", \"repair\" ], \"public_addr\": \"192.168.0.9:6800/10287\", \"cluster_addr\": \"192.168.1.9:6800/10287\", \"crush_node_ancestry\": [ [ -3, -1 ] ] }]";
    }
}
