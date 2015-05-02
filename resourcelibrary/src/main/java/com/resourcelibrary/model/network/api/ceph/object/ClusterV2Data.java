package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2Data extends PortableJsonObject {
    public ClusterV2Data(String Json) throws JSONException {
        super(Json);
    }

    public String getId() throws JSONException {
        return json.getString("id");
    }

    public String getUpdateTime() throws JSONException {
        return json.getString("update_time");
    }

    public String getName() throws JSONException {
        return json.getString("name");
    }
}
