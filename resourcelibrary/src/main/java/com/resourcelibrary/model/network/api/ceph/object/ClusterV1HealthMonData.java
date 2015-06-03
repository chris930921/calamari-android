package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV1HealthMonData extends PortableJsonObject {
    public ClusterV1HealthMonData(String Json) throws JSONException {
        super(Json);
    }

    public String getName() throws JSONException {
        return json.getString("name");
    }

    public String getHealth() throws JSONException {
        return json.getString("health");
    }

}
