package com.cephmonitor.cephmonitor.model.network.remotesetting;

import android.content.Context;

import com.android.volley.Response;
import com.resourcelibrary.model.network.api.ceph.CephPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chriske on 2016/1/26.
 */
public abstract class RemoteSettingPost extends RemoteSettingRequest {
    private JSONObject postParams = new JSONObject();

    public RemoteSettingPost(Context context) {
        super(context);
    }

    @Override
    public void requestImpl(Response.Listener success, Response.ErrorListener fail) {
        getQueue().add(new CephPostRequest(
                getParams().getSession(),
                getParams().getCsrfToken(),
                getRawData(),
                getUrl(),
                success, fail));
    }

    public void addPostParams(String key, String value) {
        try {
            postParams.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getRawData() {
        return postParams.toString();
    }
}
