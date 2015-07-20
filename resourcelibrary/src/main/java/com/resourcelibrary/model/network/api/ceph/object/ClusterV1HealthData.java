package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV1HealthData extends PortableJsonObject {
    public static final String HEALTH_OK = "HEALTH_OK";
    public static final String HEALTH_WARN = "HEALTH_WARN";
    public static final String HEALTH_ERR = "HEALTH_ERR";

    public ClusterV1HealthData(String Json) throws JSONException {
        super(Json);
    }

    public String getOverallStatus() throws JSONException {
        JSONObject report = json.getJSONObject("report");
        return report.getString("overall_status");
    }

    public long getLastUpdateTimestamp() throws JSONException {
        return json.getLong("cluster_update_time_unix");
    }

    public int getWarningCount() throws JSONException {
        return getStatusCount(HEALTH_WARN);
    }

    public int getErrorCount() throws JSONException {
        return getStatusCount(HEALTH_ERR);
    }

    public int getStatusCount(String status) throws JSONException {
        JSONObject report = json.getJSONObject("report");
        JSONArray summary = report.getJSONArray("summary");
        int count = 0;
        for (int i = 0; i < summary.length(); i++) {
            JSONObject item = summary.getJSONObject(i);
            String severity = item.getString("severity");
            if (severity.equals(status)) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<String> getSummarySeverities() throws JSONException {
        return getSummaryContent("severity");
    }

    public ArrayList<String> getSummaryDescriptions() throws JSONException {
        return getSummaryContent("summary");
    }

    private ArrayList<String> getSummaryContent(String key) throws JSONException {
        ArrayList<String> results = new ArrayList<>();

        JSONObject report = json.getJSONObject("report");
        JSONArray summary = report.getJSONArray("summary");

        for (int i = 0; i < summary.length(); i++) {
            JSONObject object = summary.getJSONObject(i);
            String severity = object.getString(key);
            results.add(severity);
        }

        return results;
    }

    public HashMap<String, ClusterV1HealthMonData> getMonMap() throws JSONException {
        HashMap<String, ClusterV1HealthMonData> list = new HashMap<>();
        JSONObject report = json.getJSONObject("report");
        JSONObject health = report.getJSONObject("health");
        JSONArray healthServices = health.getJSONArray("health_services");

        if (healthServices.length() == 0) {
            return list;
        }

        JSONObject firstService = healthServices.getJSONObject(0);
        JSONArray mons = firstService.getJSONArray("mons");
        if (mons.length() == 0) {
            return list;
        }

        for (int i = 0; i < mons.length(); i++) {
            String singleData = mons.getJSONObject(i).toString();
            ClusterV1HealthMonData monData = new ClusterV1HealthMonData(singleData);
            list.put(monData.getName(), monData);
        }
        return list;
    }

    public LinkedHashMap<String, ClusterV1HealthMonData> getOrderMonMap() throws JSONException {
        LinkedHashMap<String, ClusterV1HealthMonData> list = new LinkedHashMap<>();

        JSONObject report = json.getJSONObject("report");
        JSONObject health = report.getJSONObject("health");
        JSONArray healthServices = health.getJSONArray("health_services");

        if (healthServices.length() == 0) {
            return list;
        }

        JSONObject firstService = healthServices.getJSONObject(0);
        JSONArray mons = firstService.getJSONArray("mons");
        if (mons.length() == 0) {
            return list;
        }

        HashMap<String, ClusterV1HealthMonData> error = new HashMap<>();
        HashMap<String, ClusterV1HealthMonData> warn = new HashMap<>();
        HashMap<String, ClusterV1HealthMonData> ok = new HashMap<>();

        for (int i = 0; i < mons.length(); i++) {
            String singleData = mons.getJSONObject(i).toString();
            ClusterV1HealthMonData monData = new ClusterV1HealthMonData(singleData);
            if (monData.getHealth().equals(HEALTH_ERR)) {
                error.put(monData.getName(), monData);
            } else if (monData.getHealth().equals(HEALTH_WARN)) {
                warn.put(monData.getName(), monData);
            } else if (monData.getHealth().equals(HEALTH_OK)) {
                ok.put(monData.getName(), monData);
            }
        }
        list.putAll(orderBehindNumber(error));
        list.putAll(orderBehindNumber(warn));
        list.putAll(orderBehindNumber(ok));
        return list;
    }

    public LinkedHashMap<String, ClusterV1HealthMonData> orderBehindNumber(HashMap<String, ClusterV1HealthMonData> map) {
        ArrayList<String> keys = new ArrayList<>(map.keySet());
        LinkedHashMap<Integer, ClusterV1HealthMonData> orderGroups = new LinkedHashMap<>();
        LinkedHashMap<String, ClusterV1HealthMonData> noNumberKeyGroup = new LinkedHashMap<>();
        LinkedHashMap<ClusterV1HealthMonData, String> objectMapKeyGroup = new LinkedHashMap<>();

        Pattern pattern = Pattern.compile("[\\d]+$");
        for (String key : keys) {
            ClusterV1HealthMonData data = map.get(key);
            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                int index = Integer.parseInt(matcher.group());
                orderGroups.put(index, data);
            } else {
                noNumberKeyGroup.put(key, data);
            }
            objectMapKeyGroup.put(data, key);
        }
        ArrayList<Integer> indexGroup = new ArrayList<>(orderGroups.keySet());
        Collections.sort(indexGroup);
        LinkedHashMap<String, ClusterV1HealthMonData> result = new LinkedHashMap<>();
        for (Integer index : indexGroup) {
            ClusterV1HealthMonData data = orderGroups.get(index);
            String key = objectMapKeyGroup.get(data);
            result.put(key, data);
        }
        result.putAll(noNumberKeyGroup);
        return result;
    }
}
