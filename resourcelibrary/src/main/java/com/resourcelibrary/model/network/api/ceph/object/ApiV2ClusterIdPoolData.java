package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;

/**
 * Created by User on 4/22/2015.
 */
public class ApiV2ClusterIdPoolData extends PortableJsonObject {
    public ApiV2ClusterIdPoolData(String Json) throws JSONException {
        super(Json);
    }

    public String getId() {
        try {
            return String.valueOf(json.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getName() {
        try {
            return json.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public int getPgNum() {
        try {
            return json.getInt("pg_num");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getSize() {
        try {
            return json.getInt("size");
        } catch (JSONException e) {
            return 0;
        }
    }
}
