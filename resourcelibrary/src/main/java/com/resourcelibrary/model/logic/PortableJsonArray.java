package com.resourcelibrary.model.logic;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by User on 4/22/2015.
 */
public class PortableJsonArray {
    public JSONArray json;

    public PortableJsonArray(String Json) throws JSONException {
        json = new JSONArray(Json);
    }

    public void outBox(Bundle message) {
        message.putString(getClass().getName(), json.toString());
    }

    public void inBox(Bundle message) throws JSONException {
        String jsonContent = message.getString(getClass().getName());
        json = new JSONArray(jsonContent);
    }
}
