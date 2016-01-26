package com.cephmonitor.cephmonitor.model.network.remotesetting;

import android.content.Context;

import com.android.volley.Response;
import com.resourcelibrary.model.network.api.ceph.CephGetRequest;

/**
 * Created by chriske on 2016/1/26.
 */
public abstract class RemoteSettingGet extends RemoteSettingRequest {

    public RemoteSettingGet(Context context) {
        super(context);
    }

    @Override
    public void requestImpl(Response.Listener success, Response.ErrorListener fail) {
        getQueue().add(new CephGetRequest(getParams().getSession(), getUrl(), success, fail));
    }
}
