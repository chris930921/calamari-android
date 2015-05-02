package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2ServerData extends PortableJsonObject {
    public ClusterV2ServerData(String Json) throws JSONException {
        super(Json);
    }

    public String getAddr() throws JSONException {
        return json.getString("addr");
    }
}
