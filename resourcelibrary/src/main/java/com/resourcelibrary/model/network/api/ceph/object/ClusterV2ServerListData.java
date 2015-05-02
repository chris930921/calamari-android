package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2ServerListData extends PortableJsonArray {
    public ClusterV2ServerListData(String Json) throws JSONException {
        super(Json);
    }

    public ArrayList<ClusterV2ServerData> getList() throws JSONException {
        ArrayList<ClusterV2ServerData> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            list.add(new ClusterV2ServerData(singleData));
        }
        return list;
    }

    public ArrayList<ClusterV2ServerData> getOsdList() throws JSONException {
        return choiceTypeList("osd");
    }

    public ArrayList<ClusterV2ServerData> getMonList() throws JSONException {
        return choiceTypeList("mon");
    }


    public ArrayList<ClusterV2ServerData> choiceTypeList(String targetYype) throws JSONException {
        ArrayList<ClusterV2ServerData> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            JSONArray services = object.getJSONArray("services");
            JSONObject service = services.getJSONObject(0);
            String type = service.getString("type");
            if (type.equals(targetYype)) {
                String singleData = object.toString();
                list.add(new ClusterV2ServerData(singleData));
            }
        }
        return list;
    }
}
