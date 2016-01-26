package com.cephmonitor.cephmonitor.model.network.remotesetting;

import com.resourcelibrary.model.network.api.ceph.CephParams;

/**
 * Created by chriske on 2016/1/26.
 */
public class RemoteSettingApiUrl {
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";

    private static String getProtocol(String port) {
        int portNumber = 80;
        try {
            portNumber = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return (portNumber == 443) ? HTTPS : HTTP;
    }

    public static String getDomain(CephParams params) {
        String port = params.getPort();
        String protocal = getProtocol(port);
        return protocal + params.getHost() + ":" + params.getPort();
    }

    public static String apiV1UserMeAlertRuleGet(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/alert_rule";
    }

    public static String apiV1UserMeEmailNotify(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/email/notify";
    }

    public static String apiV1UserMePollingGeneral(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/polling/general";
    }

    public static String apiV1UserPollingAbnormalState(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/polling/abnormal_state";
    }

    public static String apiV1UserPollingAbnormalServerStatePost(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/polling/abnormal_server_state";
    }

    public static String apiV1UserMeMonError(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/mon/error";
    }

    public static String apiV1UserMeMonWarning(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/mon/warning";
    }

    public static String apiV1UserMeOsdError(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/osd/error";
    }

    public static String apiV1UserMeOsdWarning(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/osd/warning";
    }

    public static String apiV1UserMePgError(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/pg/error";
    }

    public static String apiV1UserMePgWarning(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/pg/warning";
    }

    public static String apiV1UserMeUsageError(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/usage/error";
    }

    public static String apiV1UserMeUsageWarning(CephParams params) {
        return getDomain(params) + "/api/v1/user/me/usage/warning";
    }
}