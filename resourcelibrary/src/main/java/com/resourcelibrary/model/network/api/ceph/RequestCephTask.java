package com.resourcelibrary.model.network.api.ceph;

import android.content.Context;

import com.resourcelibrary.model.network.api.RequestVolleyTask;

/**
 * Created by User on 4/16/2015.
 */
public abstract class RequestCephTask extends RequestVolleyTask<CephParams> {
    //RequestVolleyTask 框架，使用與 Ceph 有關的參數
    public RequestCephTask(Context context) {
        super(context);
    }
}
