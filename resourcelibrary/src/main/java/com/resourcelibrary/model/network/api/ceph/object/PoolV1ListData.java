package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

    public LinkedHashMap<String, String> getIdStringMapName() throws JSONException {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            PoolV1Data pool = new PoolV1Data(singleData);
            map.put(String.valueOf(pool.getPoolId()), pool.getName());
        }
        return map;
    }

    public ArrayList<String> getGraphiteIdGroup() throws JSONException {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            PoolV1Data pool = new PoolV1Data(singleData);
            list.add("ceph.cluster." + pool.getClusterName() + ".pool." + pool.getPoolId());
        }
        return list;
    }

    public ArrayList<String> getIdGroup() throws JSONException {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            PoolV1Data pool = new PoolV1Data(singleData);
            list.add(String.valueOf(pool.getPoolId()));
        }
        return list;
    }

    public ArrayList<String> getIdGroupContainAggregate() throws JSONException {
        ArrayList<String> list = getIdGroup();
        list.add(0, "all");
        return list;
    }

    public ArrayList<String> getGraphiteIdGroupContainAggregate(String clusterId) throws JSONException {
        ArrayList<String> list = getGraphiteIdGroup();
        list.add(0, "ceph.cluster." + clusterId + ".pool.all");
        return list;
    }

    public LinkedHashMap<String, String> getIdStringMapNameContainAggregate() throws JSONException {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("all", "Aggregate");
        map.putAll(getIdStringMapName());
        return map;
    }
}
