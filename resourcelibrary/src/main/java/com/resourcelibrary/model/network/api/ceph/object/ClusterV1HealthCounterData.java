package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV1HealthCounterData extends PortableJsonObject {

    public ClusterV1HealthCounterData(String Json) throws JSONException {
        super(Json);
    }

    public int getMonTotalCount() throws JSONException {
        JSONObject report = json.getJSONObject("mon");
        JSONObject ok = report.getJSONObject("ok");
        JSONObject critical = report.getJSONObject("critical");
        JSONObject warn = report.getJSONObject("warn");
        return ok.getInt("count") + critical.getInt("count") + warn.getInt("count");
    }

    public int getMonOkCount() throws JSONException {
        JSONObject report = json.getJSONObject("mon");
        JSONObject ok = report.getJSONObject("ok");
        return ok.getInt("count");
    }

    public int getMonStateCount() throws JSONException {
        JSONObject report = json.getJSONObject("mon");
        JSONObject ok = report.getJSONObject("ok");
        JSONObject states = ok.getJSONObject("states");
        return states.getInt("in");
    }

    public int getMonWarningCount() throws JSONException {
        JSONObject report = json.getJSONObject("mon");
        JSONObject warn = report.getJSONObject("warn");
        return warn.getInt("count");
    }

    public int getMonErrorCount() throws JSONException {
        JSONObject report = json.getJSONObject("mon");
        JSONObject critical = report.getJSONObject("critical");
        return critical.getInt("count");
    }

    public int getOsdTotalCount() throws JSONException {
        JSONObject report = json.getJSONObject("osd");
        JSONObject ok = report.getJSONObject("ok");
        JSONObject critical = report.getJSONObject("critical");
        JSONObject warn = report.getJSONObject("warn");
        return ok.getInt("count") + critical.getInt("count") + warn.getInt("count");
    }

    public int getOsdOkCount() throws JSONException {
        JSONObject report = json.getJSONObject("osd");
        JSONObject ok = report.getJSONObject("ok");
        return ok.getInt("count");
    }

    public int getOsdStateCount() throws JSONException {
        JSONObject report = json.getJSONObject("osd");
        JSONObject ok = report.getJSONObject("ok");
        JSONObject states = ok.getJSONObject("states");
        return states.getInt("up/in");
    }

    public int getOsdWarningCount() throws JSONException {
        JSONObject report = json.getJSONObject("osd");
        JSONObject warn = report.getJSONObject("warn");
        return warn.getInt("count");
    }


    public int getOsdErrorCount() throws JSONException {
        JSONObject report = json.getJSONObject("osd");
        JSONObject critical = report.getJSONObject("critical");
        return critical.getInt("count");
    }

    public int getPlacmentGroupsWarningCount() throws JSONException {
        JSONObject report = json.getJSONObject("pg");
        JSONObject warn = report.getJSONObject("warn");
        return warn.getInt("count");
    }

    public int getPlacmentGroupsErrorCount() throws JSONException {
        JSONObject report = json.getJSONObject("pg");
        JSONObject critical = report.getJSONObject("critical");
        return critical.getInt("count");
    }

    public int getPlacmentGroupsOkCount() throws JSONException {
        JSONObject report = json.getJSONObject("pg");
        JSONObject critical = report.getJSONObject("ok");
        return critical.getInt("count");
    }

    public int getPlacmentGroupsTotalCount() throws JSONException {
        JSONObject report = json.getJSONObject("pg");
        JSONObject ok = report.getJSONObject("ok");
        JSONObject critical = report.getJSONObject("critical");
        JSONObject warn = report.getJSONObject("warn");
        return ok.getInt("count") + critical.getInt("count") + warn.getInt("count");
    }

    public int getPlacmentGroupsActiveCount() throws JSONException {
        JSONObject report = json.getJSONObject("pg");
        JSONObject ok = report.getJSONObject("ok");
        JSONObject states = ok.getJSONObject("states");
        if (states.has("active")) {
            return states.getInt("active");
        } else {
            return 0;
        }
    }

    public int getPlacmentGroupsCleanCount() throws JSONException {
        JSONObject report = json.getJSONObject("pg");
        JSONObject ok = report.getJSONObject("ok");
        JSONObject states = ok.getJSONObject("states");
        if (states.has("clean")) {
            return states.getInt("clean");
        } else {
            return 0;
        }
    }
}
