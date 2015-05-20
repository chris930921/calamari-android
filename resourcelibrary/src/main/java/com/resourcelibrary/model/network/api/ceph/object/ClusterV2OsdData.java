package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2OsdData extends PortableJsonObject {
    public static final int UP_IN = 0;
    public static final int UP_OUT = 2;
    public static final int DOWN_IN = 1;
    public static final int DOWN = 3;

    public ClusterV2OsdData(String Json) throws JSONException {
        super(Json);
    }

    public int getID() throws JSONException {
        return json.getInt("id");
    }

    public String getUUID() throws JSONException {
        return json.getString("uuid");
    }

    public int getReweight() throws JSONException {
        return json.getInt("reweight");
    }

    public String getPublicAddr() throws JSONException {
        return json.getString("public_addr");
    }

    public String getClusterAddr() throws JSONException {
        return json.getString("cluster_addr");
    }

    public String getServer() throws JSONException {
        return json.getString("server");
    }

    public int getStatus() throws JSONException {
        boolean up = json.getBoolean("up");
        boolean in = json.getBoolean("in");

        if (up & in) {
            return UP_IN;
        } else if (up & !in) {
            return UP_OUT;
        } else if (!up & in) {
            return DOWN_IN;
        } else {
            return DOWN;
        }
    }
}
