package com.resourcelibrary.model.network.api.ceph;

import java.util.ArrayList;

public class CephApiUrl {
    private String url;
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";

    private CephApiUrl(String rootUrl) {
        url = rootUrl;
    }

    public static String getProtocol(String port) {
        int portNumber = 80;
        try {
            portNumber = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return (portNumber == 443) ? HTTPS : HTTP;
    }

    public static String login(CephParams params) {
        return getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v2/auth/login";
    }

    public static String clusterV2FindAll(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v2/cluster";
    }

    public static String clusterV2FindOne(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v2/cluster/" + params.getClusterId();
    }

    public static String clusterV1OsdList(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/osd";
    }

    public static String clusterV2OsdList(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v2/cluster/" + params.getClusterId() + "/osd";
    }

    public static String clusterV1Health(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/health";
    }

    public static String clusterV1Space(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/space";
    }

    public static String clusterV1HealthCounter(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/health_counters";
    }

    public static String poolV1List(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/pool";
    }

    public static String apiV2ClusterIdPool(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v2/cluster/" + params.getClusterId() + "/pool";
    }

    public static String clusterV1Server(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v1/cluster/" + params.getClusterId() + "/server";
    }

    public static String clusterV2MonList(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v2/cluster/" + params.getClusterId() + "/mon";
    }

    public static String clusterV2MonStatus(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v2/cluster/" + params.getClusterId() + "/mon/" + params.getMonitorId() + "/status";
    }

    public static String apiV2UserMe(CephParams params) {
        return  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/api/v1/user/me";
    }

    public static CephApiUrl graphiteRender(CephParams params) {
        String url =  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/graphite/render/?";
        return new CephApiUrl(url);
    }

    public static CephApiUrl graphiteMetricsFind(CephParams params) {
        String url =  getProtocol(params.getPort()) + params.getHost() + ":" + params.getPort() + "/graphite/metrics/find?";
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
