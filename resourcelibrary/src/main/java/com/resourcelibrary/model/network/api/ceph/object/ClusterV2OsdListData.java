package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2OsdListData extends PortableJsonArray {

    public ClusterV2OsdListData(String Json) throws JSONException {
        super(Json);
    }

    public ArrayList<ClusterV2OsdData> getList() throws JSONException {
        ArrayList<ClusterV2OsdData> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            list.add(new ClusterV2OsdData(singleData));
        }
        return list;
    }

}
