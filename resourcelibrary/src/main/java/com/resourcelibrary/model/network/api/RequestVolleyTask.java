package com.resourcelibrary.model.network.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by User on 2/3/2015.
 */
public class RequestVolleyTask<T> {
    //是否啟用測試資料。
    private static boolean isFakeValue = false;
    //測試資料的回應時間。
    private int fakeRequestTime = 300;
    //Volley的請求佇列，只使用唯一實體。
    private static RequestQueue taskQueue;
    //API需要的參數。
    private T params;

    //沒有佇列參考就取新的實體，有就略過。
    public RequestVolleyTask(Context context) {
        if (taskQueue == null) {
            taskQueue = Volley.newRequestQueue(context, new MutipleCookieHttpStack());
        }
    }

    //開啟或關閉假資料測試。
    public static void enableFakeValue(boolean enable) {
        isFakeValue = enable;
    }

    //設定API傳遞的參數。
    public void setRequestParams(T params) {
        this.params = params;
    }

    //開啟要求API內容。
    public void request(Response.Listener<String> success, Response.ErrorListener fail) {
        if (isFakeValue) {
            fakeRequest(success, fail);
        } else {
            StringRequest request = taskFlow(params, success, fail);
            if (request != null) {
                taskQueue.add(request);
            }
        }
    }

    //假資料回傳動作，透過handler加上延遲來模擬網路請求。
    private void fakeRequest(Response.Listener<String> success, Response.ErrorListener fail) {
        final Response.Listener<String> successFake = success;
        Handler work = new Handler(Looper.getMainLooper());
        work.postDelayed(new Runnable() {
            @Override
            public void run() {
                successFake.onResponse(fakeValue(params));
            }
        }, fakeRequestTime);
    }

    //假資料內容，如需要就在子類別中覆寫此方法。
    protected String fakeValue(T params) {
        return "";
    }

    //實際的API存取設定，應該在子類別中實作這方法。
    protected StringRequest taskFlow(T params, Response.Listener<String> success, Response.ErrorListener fail) {
        return null;
    }
}
