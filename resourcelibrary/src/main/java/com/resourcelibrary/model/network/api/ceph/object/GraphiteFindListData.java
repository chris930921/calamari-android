package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class GraphiteFindListData extends PortableJsonArray {
    public GraphiteFindListData(String Json) throws JSONException {
        super(Json);
    }

    public ArrayList<GraphiteFindData> getList() throws JSONException {
        ArrayList<GraphiteFindData> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            list.add(new GraphiteFindData(singleData));
        }
        return list;
    }

    public ArrayList<GraphiteFindData> getNoNumberInTextList() throws JSONException {
        ArrayList<GraphiteFindData> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            GraphiteFindData findData = new GraphiteFindData(singleData);

            if (findData.getName().matches(".*[0-9].*")) continue;

            list.add(findData);
        }
        return list;
    }
}
