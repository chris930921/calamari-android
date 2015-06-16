package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public String getHostName() throws JSONException {
        return json.getString("hostname");
    }

    public String getType() throws JSONException {
        JSONArray services = json.getJSONArray("services");
        JSONObject first = services.getJSONObject(0);
        return first.getString("type");
    }

    public String getServiceId() throws JSONException {
        JSONArray services = json.getJSONArray("services");
        JSONObject first = services.getJSONObject(0);
        return first.getString("service_id");
    }
}
