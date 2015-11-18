package com.resourcelibrary.model.network.api.ceph.single;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.resourcelibrary.model.network.api.ceph.CephApiUrl;
import com.resourcelibrary.model.network.api.ceph.CephParams;
import com.resourcelibrary.model.network.api.ceph.CephPostRequest;
import com.resourcelibrary.model.network.api.ceph.RequestCephTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 4/16/2015.
 */
public class LoginPostRequest extends RequestCephTask {

    public LoginPostRequest(Context context) {
        super(context);
    }

    @Override
    protected StringRequest taskFlow(final CephParams params, Response.Listener<String> success, Response.ErrorListener fail) {
        String url = CephApiUrl.login(params);
        String emptySession = "";
        String emptyToken = "";
        String rawData = buildRawData(params.getName(), params.getPassword());
        CephPostRequest spider = new CephPostRequest(emptySession, emptyToken, rawData, url, success, fail) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                setSessionToParams(params, response);
                return super.parseNetworkResponse(response);
            }
        };
        return spider;
    }

    @Override
    protected String fakeValue(CephParams params) {
        return "{}";
    }

    private String buildRawData(String name, String password) {
        JSONObject rawData = new JSONObject();
        try {
            rawData.put("username", name);
            rawData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rawData.toString();
    }

    private void setSessionToParams(CephParams params, NetworkResponse response) {
        String totalCookie = response.headers.get("Set-Cookie");
        Matcher matcher = setSearchSessionPattern(totalCookie);
        String Session = getSession(matcher);
        matcher = setSearchTokenPattern(totalCookie);
        String token = getToken(matcher);
        params.setSession(Session);
        params.setCsrfToken(token);
    }

    private Matcher setSearchSessionPattern(String totalCookie) {
        Pattern pattern = Pattern.compile("(?<=calamari_sessionid=)[a-zA-Z\\d]*");
        return pattern.matcher(totalCookie);
    }

    private String getSession(Matcher matcher) {
        String session = "";
        if (matcher.find()) {
            session = matcher.group();
        }
        return session;
    }

    private Matcher setSearchTokenPattern(String totalCookie) {
        Pattern pattern = Pattern.compile("(?<=XSRF-TOKEN=)[a-zA-Z\\d]*");
        return pattern.matcher(totalCookie);
    }

    private String getToken(Matcher matcher) {
        String session = "";
        if (matcher.find()) {
            session = matcher.group();
        }
        return session;
    }
}
