package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class ApiV2ClusterIdPoolListData extends PortableJsonArray {

    public ApiV2ClusterIdPoolListData(String Json) throws JSONException {
        super(Json);
    }

    public ArrayList<ApiV2ClusterIdPoolData> getList() throws JSONException {
        ArrayList<ApiV2ClusterIdPoolData> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            list.add(new ApiV2ClusterIdPoolData(singleData));

        }
        return list;
    }
}
