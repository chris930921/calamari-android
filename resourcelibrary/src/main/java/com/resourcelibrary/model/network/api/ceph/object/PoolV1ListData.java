package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

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

    public HashMap<Integer, String> getIdMapName() throws JSONException {
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            PoolV1Data pool = new PoolV1Data(singleData);
            map.put(pool.getPoolId(), pool.getName());
        }
        return map;
    }

    public HashMap<String, String> getIdStringMapName() throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            PoolV1Data pool = new PoolV1Data(singleData);
            map.put(String.valueOf(pool.getPoolId()), pool.getName());
        }
        return map;
    }
}
