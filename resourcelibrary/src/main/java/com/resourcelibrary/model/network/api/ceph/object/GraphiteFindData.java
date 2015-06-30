package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;

/**
 * Created by User on 4/22/2015.
 */
public class GraphiteFindData extends PortableJsonObject {
    public GraphiteFindData(String Json) throws JSONException {
        super(Json);
    }

    public String getId() {
        try {
            return json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
