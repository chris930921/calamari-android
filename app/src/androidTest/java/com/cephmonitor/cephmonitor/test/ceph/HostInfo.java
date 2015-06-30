package com.cephmonitor.cephmonitor.test.ceph;

import android.content.Context;

import com.resourcelibrary.model.network.api.ceph.params.LoginParams;

/**
 * Created by User on 2015/6/28.
 */
public class HostInfo {
    public static final String hostIp = "163.17.136.249";
    public static final String port = "8080";
    public static final String user = "admin";
    public static final String password = "nutcimac";

    public static LoginParams getParams(Context context) {
        LoginParams params = new LoginParams(context);
        params.setHost(hostIp);
        params.setPort(port);
        params.setName(user);
        params.setPassword(password);
        return params;
    }
}
