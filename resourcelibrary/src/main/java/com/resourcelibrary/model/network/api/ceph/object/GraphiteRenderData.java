package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 4/22/2015.
 */
public class GraphiteRenderData extends PortableJsonObject {
    public GraphiteRenderData(String Json) throws JSONException {
        super(Json);
    }

    public HashMap<String, Integer> getTargets() {
        JSONArray targets;
        HashMap<String, Integer> result = new HashMap<>();
        try {
            targets = json.getJSONArray("targets");
        } catch (JSONException e) {
            e.printStackTrace();
            return result;
        }
        for (int i = 0; i < targets.length(); i++) {
            try {
                String target = targets.getString(i);
                result.put(target, i + 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public ArrayList<Double> getValueArray(int dataPointindex) throws JSONException {
        ArrayList<Double> value = new ArrayList<>();

        JSONArray datapoints = json.getJSONArray("datapoints");
        for (int i = 0; i < datapoints.length(); i++) {
            try {
                JSONArray point = datapoints.getJSONArray(i);
                value.add(point.getDouble(dataPointindex));
            } catch (JSONException e) {
//                e.printStackTrace(); //FIXME
                value.add(0.0);
            }
        }
        return value;
    }

    public ArrayList<Long> getTimestampArray() throws JSONException {
        ArrayList<Long> value = new ArrayList<>();

        JSONArray datapoints = json.getJSONArray("datapoints");
        for (int i = 0; i < datapoints.length(); i++) {
            try {
                JSONArray point = datapoints.getJSONArray(i);
                value.add(point.getLong(0) * 1000L);
            } catch (JSONException e) {
//                e.printStackTrace(); //FIXME
            }
        }
        return value;
    }
}
