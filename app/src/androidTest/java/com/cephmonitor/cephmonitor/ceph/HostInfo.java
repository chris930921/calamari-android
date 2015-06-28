package com.cephmonitor.cephmonitor.ceph;

import com.resourcelibrary.model.network.api.ceph.CephParams;

/**
 * Created by User on 2015/6/28.
 */
public class HostInfo {
    public static final String hostIp = "163.17.136.249";
    public static final String port = "8080";
    public static final String user = "admin";
    public static final String password = "nutcimac";

    public static CephParams getParams() {
        CephParams params = new CephParams();
        params.setHost(hostIp);
        params.setPort(port);
        params.setName(user);
        params.setPassword(password);

        return params;
    }
}
