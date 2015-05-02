package com.resourcelibrary.model.network;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.resourcelibrary.R;
import com.resourcelibrary.model.log.ShowLog;

/**
 * Created by User on 2/9/2015.
 */
public class GeneralError {
    public static final Response.ErrorListener callback(final Activity activity) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse == null) {
                    ShowLog.d("volleyError.networkResponse 為空值，可能為錯誤碼: 401。");
                    return;
                }

                try {
                    String responseBody = new String(volleyError.networkResponse.data, "utf-8");
                    ShowLog.d("狀態碼: " + volleyError.networkResponse.statusCode + " 訊息: " + responseBody);
                    Toast.makeText(activity, activity.getResources().getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
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
