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
public class ClusterV1OsdListRequest extends RequestCephTask {

    public ClusterV1OsdListRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.clusterV1OsdList(params);
        String session = params.getSession();

        CephGetRequest spider = new CephGetRequest(session, url, success, fail);
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        return "{\"osds\": [{\"fqdn\": \"ceph-node1\", \"uuid\": \"89380e06-3ab1-4bcf-8889-f3946264ec12\", \"pools\": [\"cephfs_metadata\", \"rbd\", \"cephfs_data\"], \"heartbeat_front_addr\": \"10.21.20.161:6803/11693\", \"heartbeat_back_addr\": \"10.21.20.161:6802/11693\", \"up\": 1, \"osd\": 0, \"up_from\": 4, \"host\": \"ceph-node1\", \"in\": 1, \"public_addr\": \"10.21.20.161:6800/11693\", \"pg_states\": {\"active\": 266, \"clean\": 266}, \"cluster_addr\": \"10.21.20.161:6801/11693\", \"id\": 0}, {\"fqdn\": \"ceph-node1\", \"uuid\": \"e6af37ae-aa5a-448c-9399-2f30a9e38f13\", \"pools\": [\"cephfs_metadata\", \"rbd\", \"cephfs_data\"], \"heartbeat_front_addr\": \"10.21.20.161:6807/12089\", \"heartbeat_back_addr\": \"10.21.20.161:6806/12089\", \"up\": 1, \"osd\": 1, \"up_from\": 8, \"host\": \"ceph-node1\", \"in\": 1, \"public_addr\": \"10.21.20.161:6804/12089\", \"pg_states\": {\"active\": 184, \"clean\": 184}, \"cluster_addr\": \"10.21.20.161:6805/12089\", \"id\": 1}, {\"fqdn\": \"ceph-node2\", \"uuid\": \"d85124de-1052-4520-9567-424ecebe1c06\", \"pools\": [\"cephfs_metadata\", \"rbd\", \"cephfs_data\"], \"heartbeat_front_addr\": \"10.21.20.162:6803/11125\", \"heartbeat_back_addr\": \"10.21.20.162:6802/11125\", \"up\": 1, \"osd\": 2, \"up_from\": 11, \"host\": \"ceph-node2\", \"in\": 1, \"public_addr\": \"10.21.20.162:6800/11125\", \"pg_states\": {\"active\": 286, \"clean\": 286}, \"cluster_addr\": \"10.21.20.162:6801/11125\", \"id\": 2}, {\"fqdn\": \"ceph-node2\", \"uuid\": \"2b5c5f92-9d02-4018-b91a-e6cc1a153836\", \"pools\": [\"cephfs_metadata\", \"rbd\", \"cephfs_data\"], \"heartbeat_front_addr\": \"10.21.20.162:6807/11531\", \"heartbeat_back_addr\": \"10.21.20.162:6806/11531\", \"up\": 1, \"osd\": 3, \"up_from\": 17, \"host\": \"ceph-node2\", \"in\": 1, \"public_addr\": \"10.21.20.162:6804/11531\", \"pg_states\": {\"active\": 194, \"clean\": 194}, \"cluster_addr\": \"10.21.20.162:6805/11531\", \"id\": 3}, {\"fqdn\": \"ceph-node3\", \"uuid\": \"faec9d93-f805-4079-b6a0-8d761f1bc510\", \"pools\": [\"cephfs_metadata\", \"rbd\", \"cephfs_data\"], \"heartbeat_front_addr\": \"10.21.20.163:6803/10508\", \"heartbeat_back_addr\": \"10.21.20.163:6802/10508\", \"up\": 1, \"osd\": 4, \"up_from\": 22, \"host\": \"ceph-node3\", \"in\": 1, \"public_addr\": \"10.21.20.163:6800/10508\", \"pg_states\": {\"active\": 308, \"clean\": 308}, \"cluster_addr\": \"10.21.20.163:6801/10508\", \"id\": 4}, {\"fqdn\": \"ceph-node3\", \"uuid\": \"6eec9192-348d-4848-b864-278d9bbca166\", \"pools\": [\"cephfs_metadata\", \"rbd\", \"cephfs_data\"], \"heartbeat_front_addr\": \"10.21.20.163:6807/10926\", \"heartbeat_back_addr\": \"10.21.20.163:6806/10926\", \"up\": 1, \"osd\": 5, \"up_from\": 27, \"host\": \"ceph-node3\", \"in\": 1, \"public_addr\": \"10.21.20.163:6804/10926\", \"pg_states\": {\"active\": 205, \"clean\": 205}, \"cluster_addr\": \"10.21.20.163:6805/10926\", \"id\": 5}, {\"fqdn\": \"ceph-node4\", \"uuid\": \"2701f0d4-5ce8-4b47-8cca-2e453bf894aa\", \"pools\": [\"cephfs_metadata\", \"rbd\", \"cephfs_data\"], \"heartbeat_front_addr\": \"10.21.20.164:6803/10462\", \"heartbeat_back_addr\": \"10.21.20.164:6802/10462\", \"up\": 1, \"osd\": 6, \"up_from\": 31, \"host\": \"ceph-node4\", \"in\": 1, \"public_addr\": \"10.21.20.164:6800/10462\", \"pg_states\": {\"active\": 302, \"clean\": 302}, \"cluster_addr\": \"10.21.20.164:6801/10462\", \"id\": 6}, {\"fqdn\": \"ceph-node4\", \"uuid\": \"2eb2a413-bb89-448b-8c3e-83be4a625fc8\", \"pools\": [\"cephfs_metadata\", \"rbd\", \"cephfs_data\"], \"heartbeat_front_addr\": \"10.21.20.164:6807/10898\", \"heartbeat_back_addr\": \"10.21.20.164:6806/10898\", \"up\": 1, \"osd\": 7, \"up_from\": 35, \"host\": \"ceph-node4\", \"in\": 1, \"public_addr\": \"10.21.20.164:6804/10898\", \"pg_states\": {\"active\": 175, \"clean\": 175}, \"cluster_addr\": \"10.21.20.164:6805/10898\", \"id\": 7}], \"pg_state_counts\": {\"active\": 8, \"clean\": 8}}";
    }
}
