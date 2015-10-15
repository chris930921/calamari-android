package com.cephmonitor.cephmonitor.model.database.operator;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.database.data.RecordedData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by User on 2015/9/2.
 */
public class RecordedOperator {
    private Context context;
    private RecordedData data;

    public RecordedOperator(Context context) {
        this.context = context;

    }

    public void setValue(RecordedData data) {
        this.data = data;
    }

    public void addMessageParam(String param) {
        if (param == null) {
            return;
        }
        if (data.paramsMessageJson == null) {
            data.paramsMessageJson = new JSONArray();
        }
        data.paramsMessageJson.put(param);
    }


    public void addTitleParam(String param) {
        if (param == null) {
            return;
        }
        if (data.paramsTitleJson == null) {
            data.paramsTitleJson = new JSONArray();
        }
        data.paramsTitleJson.put(param);
    }

    public void addOtherParam(String key, Object value) {
        if (key == null) {
            return;
        }
        if (data.otherParamsJson == null) {
            data.otherParamsJson = new JSONObject();
        }
        try {
            data.otherParamsJson.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void clearMessageParamGroup() {
        data.paramsMessageJson = new JSONArray();
    }

    public void clearTitleParamGroup() {
        data.paramsTitleJson = new JSONArray();
    }

    public String getMessageWithParam() {
        int count = data.paramsMessageJson.length();
        Object[] paramGroup = new Object[count];
        for (int i = 0; i < count; i++) {
            try {
                paramGroup[i] = data.paramsMessageJson.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
                paramGroup[i] = "";
            }
        }
        return String.format(context.getResources().getString(data.lastMessageId), paramGroup);
    }

    public String getLastMessageWithParam() {
        int count = data.paramsMessageJson.length();
        Object[] paramGroup = new Object[count];
        for (int i = 0; i < count; i++) {
            try {
                paramGroup[i] = data.paramsMessageJson.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
                paramGroup[i] = "";
            }
        }
        return String.format(context.getResources().getString(data.lastErrorMessageId), paramGroup);
    }

    public String getTitleWithParam() {
        int count = data.paramsTitleJson.length();
        Object[] paramGroup = new Object[count];
        for (int i = 0; i < count; i++) {
            try {
                paramGroup[i] = data.paramsTitleJson.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
                paramGroup[i] = "";
            }
        }
        return String.format(context.getResources().getString(data.lastTitleId), paramGroup);
    }

    public Object getOtherParamWithKey(String key) {
        try {
            return data.otherParamsJson.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Calendar getTimeWithStatus(){
        if(data.status.equals(CephNotificationConstant.STATUS_PENDING)){
            return data.triggered;
        }else{
            return data.resolved;
        }
    }
}
