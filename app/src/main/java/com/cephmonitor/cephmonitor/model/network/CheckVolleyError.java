package com.cephmonitor.cephmonitor.model.network;

import android.content.Context;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.resourcelibrary.model.log.ShowLog;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by chriske on 2015/11/12.
 */
public class CheckVolleyError {
    public static final String ERROR_UNKNOWN = "ERROR_UNKNOWN";
    public static final String ERROR_TIME_OUT = "ERROR_TIME_OUT";
    public static final String ERROR_SERVER_ERROR = "ERROR_SERVER_ERROR";
    public static final String ERROR_404 = "404";
    public static final String ERROR_401 = "401";

    private Context context;
    private VolleyError volleyError;
    private String responseBody;
    private int statusCode;
    private HashMap<String, OnError> errorMap;
    private HashMap<Class, String> volleyErrorClassMap;

    public CheckVolleyError(Context context) {
        this.context = context;
        this.errorMap = new HashMap<>();
        this.volleyErrorClassMap = new HashMap<>();
        volleyErrorClassMap.put(TimeoutError.class, ERROR_TIME_OUT);
        volleyErrorClassMap.put(NoConnectionError.class, ERROR_SERVER_ERROR);
    }

    public Context getContext() {
        return context;
    }

    private String getFlag(Class cls) {
        if (volleyErrorClassMap.containsKey(cls)) {
            return volleyErrorClassMap.get(cls);
        } else {
            return ERROR_UNKNOWN;
        }
    }

    private OnError getErrorEvent(String flag) {
        if (errorMap.containsKey(flag)) {
            return errorMap.get(flag);
        } else {
            return defaultError;
        }
    }

    public CheckVolleyError setEvent(String flag, OnError onError) {
        errorMap.put(flag, onError);
        return this;
    }


    public void done(VolleyError volleyError) {
        dealWithInfo(volleyError);

        if (volleyError.networkResponse == null) {
            dealWithResponseNull();
            ShowLog.d("VolleyError Message: " + volleyError.getMessage());
            ShowLog.d("VolleyError LocalizedMessage: " + volleyError.getLocalizedMessage());
            volleyError.printStackTrace();
        } else {
            ShowLog.d("Status Code: " + statusCode + ", Message: " + responseBody);
            dealWithResponseGeneral();
        }
    }

    private void dealWithInfo(VolleyError volleyError) {
        this.volleyError = volleyError;
        if (volleyError.networkResponse == null) return;

        this.statusCode = volleyError.networkResponse.statusCode;
        try {
            this.responseBody = new String(volleyError.networkResponse.data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dealWithResponseNull() {
        String message = volleyError.getMessage();
        message = (message == null) ? "Empty Message." : message;
        Class volleyExceptionClass = volleyError.getClass();
        String flag = getFlag(volleyExceptionClass);
        OnError onError = getErrorEvent(flag);
        onError.onError(message);
        ShowLog.d("VolleyError Type: " + flag);
    }

    private void dealWithResponseGeneral() {
        String responseBody;
        try {
            responseBody = new String(volleyError.networkResponse.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            responseBody = "Response Body Unsupported Encoding To UTF-8.";
        }
        String flag = String.valueOf(statusCode);
        OnError onError = getErrorEvent(flag);
        onError.onError(responseBody);
        ShowLog.d("VolleyError Type: " + flag);
    }

    public static interface OnError {
        void onError(String responseBody);
    }

    public OnError defaultError = new OnError() {
        @Override
        public void onError(String responseBody) {
        }
    };
}
