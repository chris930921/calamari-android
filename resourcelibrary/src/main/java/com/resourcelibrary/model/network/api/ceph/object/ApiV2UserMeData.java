package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonObject;

import org.json.JSONException;

/**
 * Created by chriske on 2015/12/2.
 */
public class ApiV2UserMeData extends PortableJsonObject {

    public ApiV2UserMeData(String Json) throws JSONException {
        super(Json);
    }

    public String getEmail() {
        try {
            return String.valueOf(json.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}