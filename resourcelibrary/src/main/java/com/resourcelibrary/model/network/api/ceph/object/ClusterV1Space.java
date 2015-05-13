package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV1Space extends PortableJsonObject {

    public ClusterV1Space(String Json) throws JSONException {
        super(Json);
    }

    public long getFreeBytes() throws JSONException {
        JSONObject space = json.getJSONObject("space");
        return space.getLong("free_bytes");
    }

    public long getUsedBytes() throws JSONException {
        JSONObject space = json.getJSONObject("space");
        return space.getLong("used_bytes");
    }

    public long getCapacityBytes() throws JSONException {
        JSONObject space = json.getJSONObject("space");
        return space.getLong("capacity_bytes");
    }
}
