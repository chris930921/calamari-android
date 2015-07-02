package com.resourcelibrary.model.network.api.ceph;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.resourcelibrary.model.log.ShowLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2/3/2015.
 */
public class CephGetRequest extends StringRequest {
    private String session;

    public CephGetRequest(String session, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
        this.session = session;
        ShowLog.d("網址: " + url);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Cookie", "calamari_sessionid=" + session + ";");
        return headers;
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }
}
