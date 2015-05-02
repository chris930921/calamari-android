package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
}
