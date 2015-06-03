package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2MonData extends PortableJsonObject {
    public static final int UP_IN = 0;
    public static final int UP_OUT = 2;
    public static final int DOWN_IN = 1;
    public static final int DOWN = 3;

    public ClusterV2MonData(String Json) throws JSONException {
        super(Json);
    }

    public int getID() throws JSONException {
        return json.getInt("id");
    }

    public String getUUID() throws JSONException {
        return json.getString("uuid");
    }

    public double getReweight() throws JSONException {
        return json.getDouble("reweight");
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

    public ArrayList<Integer> getPools() throws JSONException {
        ArrayList<Integer> list = new ArrayList<>();
        JSONArray pools = json.getJSONArray("pools");
        for (int i = 0; i < pools.length(); i++) {
            list.add(pools.getInt(i));
        }
        return list;
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
