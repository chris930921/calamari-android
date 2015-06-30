package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV1OsdData extends PortableJsonObject {

    public ClusterV1OsdData(String Json) throws JSONException {
        super(Json);
    }

    public LinkedHashMap<String, Integer> getPgStateCounts() {
        JSONObject pgStateCounts;
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();

        try {
            pgStateCounts = json.getJSONObject("pg_state_counts");
        } catch (JSONException e) {
            e.printStackTrace();
            return result;
        }

        for (int i = CephStaticValue.PG_STATUS.length - 1; i >= 0; i--) {
            String state = CephStaticValue.PG_STATUS[i];
            try {
                int count = pgStateCounts.getInt(state);
                result.put(state, count);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
