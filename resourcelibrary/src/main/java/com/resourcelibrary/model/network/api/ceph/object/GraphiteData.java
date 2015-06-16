package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class GraphiteData extends PortableJsonObject {
    public GraphiteData(String Json) throws JSONException {
        super(Json);
    }

    public ArrayList<Double> getValueArray() throws JSONException {
        ArrayList<Double> value = new ArrayList<>();

        JSONArray datapoints = json.getJSONArray("datapoints");
        for (int i = 0; i < datapoints.length(); i++) {
            try {
                JSONArray point = datapoints.getJSONArray(i);
                value.add(point.getDouble(1));
            } catch (JSONException e) {
                e.printStackTrace();
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
                double checkNull = point.getDouble(1);
                value.add(point.getLong(0) * 1000L);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
