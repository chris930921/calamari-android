package com.resourcelibrary.model.network.api.ceph;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 2/3/2015.
 */
public class CephPostRequest extends StringRequest {
    private static final String PROTOCOL_CHARSET = "utf-8";
    private String rawData;
    private String session;
    private String token;

    public CephPostRequest(String session, String token, String rawData, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, url, listener, errorListener);
        this.rawData = rawData;
        this.session = session;
        this.token = token;
        setShouldCache(false);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        if (!token.equals("")) {
            headers.put("X-XSRF-TOKEN", token);
            headers.put("XSRF-TOKEN", token);
        }
        Pattern pattern = Pattern.compile("\\d*\\.\\d*\\.\\d*\\.\\d*");
        Matcher mather = pattern.matcher(getUrl());
        if (mather.find()) {
            String host = mather.group();
            headers.put("referer", "https://" + host + "/");
        }
        if (!session.equals("")) {
            headers.put("Cookie", "calamari_sessionid=" + session + "; XSRF-TOKEN=" + token);
        }
        return headers;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return rawData == null ? null : rawData.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    rawData, PROTOCOL_CHARSET);
            return null;
        }
    }
}
