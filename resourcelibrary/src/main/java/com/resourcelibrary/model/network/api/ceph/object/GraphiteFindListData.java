package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public ArrayList<GraphiteFindData> getOrderList(final ArrayList<String> orderGroup) throws JSONException {
        ArrayList<GraphiteFindData> list = new ArrayList<>();
        final ArrayList<String> indexGroup = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            GraphiteFindData data = new GraphiteFindData(singleData);
            list.add(data);
            if (!orderGroup.contains(data.getId())) {
                indexGroup.add(data.getId());
            }
        }
        indexGroup.addAll(0, orderGroup);

        Collections.sort(list, new Comparator<GraphiteFindData>() {
            public int compare(GraphiteFindData one, GraphiteFindData other) {
                int oneIndex = indexGroup.indexOf(one.getId());
                int twoIndex = indexGroup.indexOf(other.getId());

                return oneIndex - twoIndex;
            }
        });

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

    public ArrayList<GraphiteFindData> getOrderNumberBehindNameList() throws JSONException {
        HashMap<Integer, GraphiteFindData> numberMapData = new HashMap<>();
        ArrayList<GraphiteFindData> noNumberAfterName = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\d]+$");
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            GraphiteFindData findData = new GraphiteFindData(singleData);
            Matcher matcher = pattern.matcher(findData.getName());
            if (matcher.find()) {
                String result = matcher.group();
                numberMapData.put(Integer.parseInt(result), findData);
            } else {
                noNumberAfterName.add(findData);
            }
        }
        ArrayList<GraphiteFindData> list = new ArrayList<>();

        ArrayList<Integer> sortNumber = new ArrayList<>(numberMapData.keySet());
        Collections.sort(sortNumber);
        list.addAll(noNumberAfterName);
        for (int i = 0; i < sortNumber.size(); i++) {
            int number = sortNumber.get(i);
            list.add(numberMapData.get(number));
        }
        return list;
    }
}
