package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by User on 4/22/2015.
 */
public class GraphiteRenderData extends PortableJsonObject {
    public GraphiteRenderData(String Json) throws JSONException {
        super(Json);
    }

    public HashMap<String, Integer> getTargets() {
        JSONArray targets;
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
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

    public ArrayList<Double> getValueArray(int dataPointIndex) {
        ArrayList<Double> value = new ArrayList<>();

        JSONArray datapoints = null;
        try {
            datapoints = json.getJSONArray("datapoints");
        } catch (JSONException e) {
            e.printStackTrace();
            return value;
        }
        for (int i = 0; i < datapoints.length(); i++) {
            try {
                JSONArray point = datapoints.getJSONArray(i);
                for (int j = 0; j < point.length(); j++) {
                    double checkNull = point.getDouble(j);
                }
                value.add(point.getDouble(dataPointIndex));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public ArrayList<Double> getSumValueArray(int dataPointIndex) {
        ArrayList<Double> value = new ArrayList<>();

        JSONArray datapoints = null;
        try {
            datapoints = json.getJSONArray("datapoints");
        } catch (JSONException e) {
            e.printStackTrace();
            return value;
        }
        for (int i = 0; i < datapoints.length(); i++) {
            double sum = 0;
            try {
                JSONArray point = datapoints.getJSONArray(i);
                for (int j = 0; j < point.length(); j++) {
                    double checkNull = point.getDouble(j);
                }
                for (int j = dataPointIndex; j < point.length(); j++) {
                    sum += point.getDouble(j);
                }
                value.add(sum);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public ArrayList<Long> getTimestampArray() {
        ArrayList<Long> value = new ArrayList<>();

        JSONArray datapoints = null;
        try {
            datapoints = json.getJSONArray("datapoints");
        } catch (JSONException e) {
            e.printStackTrace();
            return value;
        }
        for (int i = 0; i < datapoints.length(); i++) {
            try {
                JSONArray point = datapoints.getJSONArray(i);
                for (int j = 0; j < point.length(); j++) {
                    double checkNull = point.getDouble(j);
                }
                value.add(point.getLong(0) * 1000L);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
