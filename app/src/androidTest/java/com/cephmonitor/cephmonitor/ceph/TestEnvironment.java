package com.cephmonitor.cephmonitor.ceph;

import android.test.AndroidTestCase;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.resourcelibrary.model.network.api.RequestVolleyTask;
import com.resourcelibrary.model.network.api.ceph.CephParams;
import com.resourcelibrary.model.network.api.ceph.single.LoginPostRequest;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 2015/6/28.
 */
public class TestEnvironment extends AndroidTestCase {
    private CephParams params;
    private boolean isSuccess;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RequestVolleyTask.enableFakeValue(false);
        params = HostInfo.getParams();
    }

    public void testLogin() {
        final CountDownLatch lock = new CountDownLatch(1);
        final ArrayList returnVar = new ArrayList();
        returnVar.add(0, "");

        Response.Listener<String> successLoginPost = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println("onResponse");
                returnVar.set(0, s);
                lock.countDown();
            }
        };

        Response.ErrorListener failLoginPost = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                returnVar.set(0, "volleyError");
                lock.countDown();
            }
        };

        LoginPostRequest spider = new LoginPostRequest(getContext());
        spider.setRequestParams(params);
        spider.request(successLoginPost, failLoginPost);

        try {
            lock.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail("Login Api Timeout.");
        }
        assertEquals("{}", returnVar.get(0));
    }
}
