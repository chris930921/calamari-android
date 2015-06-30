package com.cephmonitor.cephmonitor.test.ceph;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.concurrent.CountDownLatch;

/**
 * Created by User on 2015/6/29.
 */
public class DefaultResponse {

    public static Response.Listener<String> success(final CountDownLatch lock, final Value<String> returnVar) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                returnVar.set(s);
                lock.countDown();
            }
        };
    }

    public static Response.ErrorListener fail(final CountDownLatch lock, final Value<Boolean> returnVar) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                returnVar.set(true);
                lock.countDown();
            }
        };
    }
}
