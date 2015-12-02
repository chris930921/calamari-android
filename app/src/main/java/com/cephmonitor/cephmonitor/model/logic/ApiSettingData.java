package com.cephmonitor.cephmonitor.model.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiSettingData {
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

    public ApiSettingData(String json) {
        total = getJSONObject(json);
        osdWarning = getLong("osd_warning", 1);
        osdError = getLong("osd_warning", 1);
        monitorWarning = getLong("monitor_warning", 1);
        monitorError = getLong("monitor_error", 1);
        pgWarning = getFloat("pg_warning", 20) / 100;
        pgError = getFloat("pg_error", 20) / 100;
        usageWarning = getFloat("usage_warning", 70) / 100;
        usageError = getFloat("usage_error", 85) / 100;
        generalPolling = getLong("general_polling", 30);
        abnormalStatePolling = getLong("abnormal_state_polling", 60 * 2);
        enableEmailNotify = getLong("enable_email_notify", 1) != 0;
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
            return Long.parseLong(total.getString(key));
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    private float getFloat(String key, float defaultValue) {
        try {
            return Float.parseFloat(total.getString(key));
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}