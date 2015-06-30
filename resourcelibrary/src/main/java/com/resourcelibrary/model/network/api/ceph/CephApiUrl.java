package com.resourcelibrary.model.network.api.ceph;

import java.util.ArrayList;

public class CephApiUrl {
    private String url;

    private CephApiUrl(String rootUrl) {
        url = rootUrl;
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

    public static String clusterV1OsdList(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/osd";
    }

    public static String clusterV2OsdList(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v2/cluster/" + params.getClusterId() + "/osd";
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

    public static String clusterV2MonList(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v2/cluster/" + params.getClusterId() + "/mon";
    }

    public static String clusterV2MonStatus(CephParams params) {
        return "http://" + params.getHost() + ":" + params.getPort() + "/api/v2/cluster/" + params.getClusterId() + "/mon/" + params.getMonitorId() + "/status";
    }

    public static CephApiUrl graphiteRender(CephParams params) {
        String url = "http://" + params.getHost() + ":" + params.getPort() + "/graphite/render/?";
        return new CephApiUrl(url);
    }

    public static CephApiUrl graphiteMetricsFind(CephParams params) {
        String url = "http://" + params.getHost() + ":" + params.getPort() + "/graphite/metrics/find?";
        return new CephApiUrl(url);
    }

    public CephApiUrl format(String value) {
        url += "format=" + value + "&";
        return this;
    }

    public CephApiUrl from(String value) {
        url += "from=" + value + "&";
        return this;
    }

    public CephApiUrl target(String value) {
        url += "target=" + value + "&";
        return this;
    }

    public CephApiUrl query(String value) {
        url += "query=" + value + "&";
        return this;
    }

    public CephApiUrl targets(ArrayList<String> targets) {
        for (String target : targets) {
            url += "target=" + target + "&";
        }
        return this;
    }

    public String build() {
        return url;
    }
}
