package com.resourcelibrary.model.network.api;


import android.content.Context;

import com.android.volley.Response;
import com.resourcelibrary.model.network.api.ceph.CephParams;

/**
 * Created by User on 2/3/2015.
 */
public abstract class MultipleRequestTask extends RequestVolleyTask<CephParams> {
    private Context context;
    private Response.Listener<String> success;
    private Response.ErrorListener fail;

    public MultipleRequestTask(Context context) {
        super(context);
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    @Override
    public void request(Response.Listener<String> success, Response.ErrorListener fail) {
        this.success = success;
        this.fail = fail;
        start();
    }

    protected abstract void start();

    protected Response.Listener<String> getSuccess() {
        return success;
    }

    protected Response.ErrorListener getFail() {
        return fail;
    }
}
