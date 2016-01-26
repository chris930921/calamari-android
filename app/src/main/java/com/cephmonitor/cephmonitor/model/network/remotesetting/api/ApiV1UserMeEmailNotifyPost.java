package com.cephmonitor.cephmonitor.model.network.remotesetting.api;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.network.remotesetting.RemoteSettingApiUrl;
import com.cephmonitor.cephmonitor.model.network.remotesetting.RemoteSettingPost;

/**
 * Created by chriske on 2016/1/26.
 */
public class ApiV1UserMeEmailNotifyPost extends RemoteSettingPost {

    public ApiV1UserMeEmailNotifyPost(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return RemoteSettingApiUrl.apiV1UserMeEmailNotify(getParams());
    }
}
