package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2ServerServicesData extends PortableJsonObject {
    public ClusterV2ServerServicesData(String Json) throws JSONException {
        super(Json);
    }

    public String getType() throws JSONException {
        return json.getString("type");
    }

    public String getServiceId() throws JSONException {
        return json.getString("service_id");
    }

    public String getName() throws JSONException {
        return json.getString("name");
    }
}
