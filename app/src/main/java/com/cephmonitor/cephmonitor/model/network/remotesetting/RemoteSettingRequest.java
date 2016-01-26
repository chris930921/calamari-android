package com.cephmonitor.cephmonitor.model.network.remotesetting;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.resourcelibrary.model.network.api.MutipleCookieHttpStack;
import com.resourcelibrary.model.network.api.ceph.CephParams;

/**
 * Created by chriske on 2016/1/26.
 */
public abstract class RemoteSettingRequest {
    private static RequestQueue queue;
    private static CephParams params;

    public RemoteSettingRequest(Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context, new MutipleCookieHttpStack());
        }
    }

    public void setParams(CephParams params) {
        this.params = params;
    }

    public CephParams getParams() {
        return params;
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public abstract String getUrl();

    public void request(Response.Listener success, Response.ErrorListener fail) {
        if (params == null) {
            throw new RuntimeException("Params are empty. Use setParams() to setup.");
        } else {
            requestImpl(success, fail);
        }
    }

    protected abstract void requestImpl(Response.Listener success, Response.ErrorListener fail);
}
