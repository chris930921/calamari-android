package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

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

    public ArrayList<ClusterV2ServerServicesData> getOsdServices() throws JSONException {
        return choiceType("osd");
    }

    public ArrayList<ClusterV2ServerServicesData> getMonServices() throws JSONException {
        return choiceType("mon");
    }

    public ArrayList<ClusterV2ServerServicesData> choiceType(String type) throws JSONException {
        ArrayList<ClusterV2ServerServicesData> result = new ArrayList<>();
        JSONArray services = json.getJSONArray("services");
        for (int i = 0; i < services.length(); i++) {
            ClusterV2ServerServicesData service = new ClusterV2ServerServicesData(services
                    .getJSONObject(i)
                    .toString()
            );
            if (service.getType().equals(type)) {
                result.add(service);
            }
        }
        return result;
    }
}
