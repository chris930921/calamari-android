package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV1HealthMonData extends PortableJsonObject {
    public static final String HEALTH_OK = "HEALTH_OK";
    public static final String HEALTH_WARN = "HEALTH_WARN";
    public static final String HEALTH_ERR = "HEALTH_ERR";

    public ClusterV1HealthMonData(String Json) throws JSONException {
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

//    private ArrayList<Mon>
}
