package com.cephmonitor.cephmonitor.model.logic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.model.network.remotesetting.api.ApiV1userMeAlertResource;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

/**
 * Created by chriske on 2016/1/26.
 */
public class AlertTriggerDialogRequest {
    public static void start(final Context context,
                             LoginParams params,
                             String url,
                             String resourceName,
                             final long value,
                             final Response.Listener<String> success) {
        ApiV1userMeAlertResource task = new ApiV1userMeAlertResource(context);
        task.setParams(params);
        task.setUrl(url);
        String valueText = String.valueOf(value);
        task.addPostParams(resourceName, valueText);

        final LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.show();
        task.request(new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                success.onResponse(s);
                loadingDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("onErrorResponse", new String(volleyError.networkResponse.data));
                Toast.makeText(context, new String(volleyError.networkResponse.data), Toast.LENGTH_SHORT).show();
                loadingDialog.cancel();
            }
        });
    }
}
