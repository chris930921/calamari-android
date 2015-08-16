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
public class ClusterV1SpaceRequest extends RequestCephTask {
    public static int fakeValueIndex;

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
        String[] useValue = {
                "api/api_v1_cluster_id_space_28%.txt",
                "api/api_v1_cluster_id_space_44%.txt",
                "api/api_v1_cluster_id_space_60%.txt",
                "api/api_v1_cluster_id_space_75%.txt",
                "api/api_v1_cluster_id_space_85%.txt"};
        fakeValueIndex = (fakeValueIndex + 1) % 5;
        String result = new ReadAssetsFile(getContext()).readText(useValue[fakeValueIndex]);
        return result;
    }
}
