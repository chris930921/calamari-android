package com.cephmonitor.cephmonitor.model.network.remotesetting.api;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.network.remotesetting.RemoteSettingApiUrl;
import com.cephmonitor.cephmonitor.model.network.remotesetting.RemoteSettingGet;

/**
 * Created by chriske on 2016/1/26.
 */
public class ApiV1UserMeAlertRuleGet extends RemoteSettingGet {

    public ApiV1UserMeAlertRuleGet(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return RemoteSettingApiUrl.apiV1UserMeAlertRuleGet(getParams());
    }
}
