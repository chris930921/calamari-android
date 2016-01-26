package com.cephmonitor.cephmonitor.model.network.remotesetting.data;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiV1UserMeAlertRuleGetData {
    private JSONObject total;
    public long osdWarning;
    public long osdError;
    public long monitorWarning;
    public long monitorError;
    public float pgWarning;
    public float pgError;
    public float usageWarning;
    public float usageError;
    public long generalPolling;
    public long abnormalStatePolling;
    public long abnormalServerStatePolling;
    public boolean enableEmailNotify;

    public ApiV1UserMeAlertRuleGetData(String json) {
        total = getJSONObject(json);
        osdWarning = getLong("osd_warning", 1);
        osdError = getLong("osd_error", 1);
        monitorWarning = getLong("mon_warning", 1);
        monitorError = getLong("mon_error", 1);
        pgWarning = getFloat("pg_warning", 0.20F);
        pgError = getFloat("pg_error", 0.20F);
        usageWarning = getFloat("usage_warning", 0.70F);
        usageError = getFloat("usage_error", 0.85F);
        generalPolling = getLong("general_polling", 30);
        abnormalStatePolling = getLong("abnormal_state_polling", 60 * 2);
        abnormalServerStatePolling = getLong("abnormal_server_state_polling", 60 * 60);
        enableEmailNotify = getBoolean("enable_email_notify", true);
    }

    private JSONObject getJSONObject(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    private long getLong(String key, long defaultValue) {
        try {
            return total.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    private float getFloat(String key, float defaultValue) {
        try {
            return (total.getLong(key) / 100F);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        try {
            return total.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}