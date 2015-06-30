package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV1OsdData extends PortableJsonObject {

    public ClusterV1OsdData(String Json) throws JSONException {
        super(Json);
    }

    public HashMap<String, Integer> getPgStateCounts() {
        JSONObject pgStateCounts;
        HashMap<String, Integer> result = new HashMap<>();

        for (String state : CephStaticValue.PG_STATUS) {
            result.put(state, 0);
        }

        try {
            pgStateCounts = new JSONObject("pg_state_counts");
        } catch (JSONException e) {
            e.printStackTrace();
            return result;
        }

        for (String state : CephStaticValue.PG_STATUS) {
            try {
                int count = pgStateCounts.getInt(state);
                result.put(state, count);
            } catch (JSONException e) {
                e.printStackTrace();
                result.put(state, 0);
            }
        }
        return result;
    }
}
