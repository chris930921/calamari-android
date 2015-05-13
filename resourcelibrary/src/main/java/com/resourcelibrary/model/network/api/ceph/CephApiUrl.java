package com.resourcelibrary.model.network.api.ceph;

public class CephApiUrl {
    private String Url;

    private CephApiUrl(String rootUrl) {
        Url = rootUrl;
    }

    public static String login(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v2/auth/login";
    }

    public static String clusterV2FindAll(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v2/cluster";
    }

    public static String clusterV2FindOne(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v2/cluster/" + params.getClusterId();
    }

    public static String clusterV1Health(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/health";
    }

    public static String clusterV1Space(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/space";
    }

    public static String clusterV1HealthCounter(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/health_counters";
    }

    public static String poolV1List(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/pool";
    }

    public static String clusterV1Server(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/server";
    }
}
