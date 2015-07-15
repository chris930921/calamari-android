package com.resourcelibrary.model.network;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.resourcelibrary.R;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

/**
 * Created by User on 2/9/2015.
 */
public class GeneralError {
    public static Response.ErrorListener callback(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse == null) {
                    if (TimeoutError.class.equals(volleyError.getClass())) {
                        ShowLog.d("VolleyError: 時間太長。");
                    } else if (NoConnectionError.class.equals(volleyError.getClass()) && volleyError.getMessage().contains("No authentication challenges found")) {
                        ShowLog.d("VolleyError: 權限問題。");
                    } else if (NoConnectionError.class.equals(volleyError.getClass())) {
                        ShowLog.d("VolleyError: 網路問題");
                    }
                    ShowLog.d("VolleyError:" + volleyError.getMessage());
                    ShowLog.d("VolleyError:" + volleyError.getLocalizedMessage());
                    volleyError.printStackTrace();
                } else {
                    try {
                        String responseBody = new String(volleyError.networkResponse.data, "utf-8");
                        ShowLog.d("狀態碼: " + volleyError.networkResponse.statusCode + " 訊息: " + responseBody);
                        Toast.makeText(context, context.getResources().getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public static Response.ErrorListener callback(final Context context, final View layout, final LoadingDialog loadingDialog) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LoadingDialog.delayCancel(layout, loadingDialog);
                if (volleyError.networkResponse == null) {
                    if (TimeoutError.class.equals(volleyError.getClass())) {
                        ShowLog.d("VolleyError: 時間太長。");
                    } else if (NoConnectionError.class.equals(volleyError.getClass()) && volleyError.getMessage().contains("No authentication challenges found")) {
                        ShowLog.d("VolleyError: 權限問題。");
                    } else if (NoConnectionError.class.equals(volleyError.getClass())) {
                        ShowLog.d("VolleyError: 網路問題");
                    }
                    ShowLog.d("VolleyError:" + volleyError.getMessage());
                    ShowLog.d("VolleyError:" + volleyError.getLocalizedMessage());
                    volleyError.printStackTrace();
                } else {
                    try {
                        String responseBody = new String(volleyError.networkResponse.data, "utf-8");
                        ShowLog.d("狀態碼: " + volleyError.networkResponse.statusCode + " 訊息: " + responseBody);
                        Toast.makeText(context, context.getResources().getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public static final void showStatusCode(Activity activity, VolleyError volleyError) {
        int status = volleyError.networkResponse.statusCode;
        String errorMessage = activity.getResources().getString(R.string.login_fail_other_error) + status;
        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public static final void showUnauthorizedStatus(Activity activity) {
        String errorMessage = activity.getResources().getString(R.string.login_fail_other_error) + 401;
        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
