package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class PoolV1ListData extends PortableJsonArray {
    public PoolV1ListData(String Json) throws JSONException {
        super(Json);
    }

    public ArrayList<PoolV1Data> getList() throws JSONException {
        ArrayList<PoolV1Data> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            list.add(new PoolV1Data(singleData));
        }
        return list;
    }
}
