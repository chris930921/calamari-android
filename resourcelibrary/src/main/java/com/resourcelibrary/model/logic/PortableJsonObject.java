package com.resourcelibrary.model.logic;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 4/22/2015.
 */
public class PortableJsonObject {
    public JSONObject json;

    public PortableJsonObject(String Json) throws JSONException {
        json = new JSONObject(Json);
    }

    public void outBox(Bundle message) {
        message.putString(getClass().getName(), json.toString());
    }

    public void inBox(Bundle message) throws JSONException {
        String jsonContent = message.getString(getClass().getName());
        json = new JSONObject(jsonContent);
    }
}
