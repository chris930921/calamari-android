package com.cephmonitor.cephmonitor.model.network.remotesetting.api;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.network.remotesetting.RemoteSettingPost;

/**
 * Created by chriske on 2016/1/26.
 */
public class ApiV1userMeAlertResource extends RemoteSettingPost {
    private String url;

    public ApiV1userMeAlertResource(Context context) {
        super(context);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
