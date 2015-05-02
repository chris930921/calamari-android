package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2ListData extends PortableJsonArray {
    public ClusterV2ListData(String Json) throws JSONException {
        super(Json);
    }

    public ArrayList<ClusterV2Data> getList() throws JSONException {
        ArrayList<ClusterV2Data> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            list.add(new ClusterV2Data(singleData));
        }
        return list;
    }
}
