package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2MonListData extends PortableJsonArray {

    public ClusterV2MonListData(String Json) throws JSONException {
        super(Json);
    }

    public ArrayList<ClusterV2MonData> getList() throws JSONException {
        ArrayList<ClusterV2MonData> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            list.add(new ClusterV2MonData(singleData));
        }
        return list;
    }

}
