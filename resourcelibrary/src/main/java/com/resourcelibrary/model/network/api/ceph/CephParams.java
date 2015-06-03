package com.resourcelibrary.model.network.api.ceph;

/**
 * Created by User on 4/16/2015.
 */
public class CephParams {
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String value) {
        this.host = value;
    }

    private String port;

    public String getPort() {
        return port;
    }

    public void setPort(String value) {
        this.port = value;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    private String session;

    public String getSession() {
        return session;
    }

    public void setSession(String value) {
        this.session = value;
    }

    private String clusterId;

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String value) {
        this.clusterId = value;
    }

    private String monitorId;

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String value) {
        this.monitorId = value;
    }
}
