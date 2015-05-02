package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;

/**
 * Created by User on 4/22/2015.
 */
public class PoolV1Data extends PortableJsonObject {
    public PoolV1Data(String Json) throws JSONException {
        super(Json);
    }

    public String getPoolId() throws JSONException {
        return json.getString("pool_id");
    }

    public String getName() throws JSONException {
        return json.getString("name");
    }
}
