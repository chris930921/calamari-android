package com.cephmonitor.cephmonitor.test.ceph;

import android.test.AndroidTestCase;

import com.resourcelibrary.model.network.api.RequestVolleyTask;
import com.resourcelibrary.model.network.api.ceph.object.CephStaticValue;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthCounterData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1OsdData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1HealthCounterRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV1OsdListRequest;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV2ListRequest;
import com.resourcelibrary.model.network.api.ceph.single.LoginPostRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 2015/6/28.
 */
public class TestEnvironment extends AndroidTestCase {
    private LoginParams params;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RequestVolleyTask.enableFakeValue(false);
        params = HostInfo.getParams(getContext());
    }


    public void test_1_Login() throws InterruptedException {
        final CountDownLatch lock = new CountDownLatch(1);
        final Value<String> returnVar = new Value<>();
        final Value<Boolean> isFail = new Value<>();
        returnVar.set(null);
        isFail.set(true);

        LoginPostRequest spider = new LoginPostRequest(getContext());
        spider.setRequestParams(params);
        spider.request(
                DefaultResponse.success(lock, returnVar),
                DefaultResponse.fail(lock, isFail)
        );
        lock.await(10, TimeUnit.SECONDS);
        assertTrue(isFail.get());
        assertNotNull(returnVar.get());
        assertEquals("{}", returnVar.get());
        WriteApiResult.write(getContext(), "api_v2_auth_login_post", returnVar.get());
    }

    public void test_2_FirstCluster() throws JSONException, InterruptedException {
        final CountDownLatch lock = new CountDownLatch(1);
        final Value<String> returnVar = new Value<>();
        final Value<Boolean> isFail = new Value<>();
        returnVar.set(null);
        isFail.set(true);

        ClusterV2ListRequest spider = new ClusterV2ListRequest(getContext());
        spider.setRequestParams(params);
        spider.request(
                DefaultResponse.success(lock, returnVar),
                DefaultResponse.fail(lock, isFail)
        );

        lock.await(10, TimeUnit.SECONDS);
        assertTrue(isFail.get());
        assertNotNull(returnVar.get());

        ClusterV2ListData listData;
        listData = new ClusterV2ListData(returnVar.get());
        String firstClusterId = listData.getList().get(0).getId();

        assertNotSame("", firstClusterId);
        params.setClusterId(firstClusterId);
        WriteApiResult.write(getContext(), "api_v2_cluster_get", returnVar.get());
    }

    public void test_3_ClusterV1OsdList() throws JSONException, InterruptedException {
        final CountDownLatch lock = new CountDownLatch(1);
        final Value<String> returnVar = new Value<>();
        final Value<Boolean> isFail = new Value<>();
        returnVar.set(null);
        isFail.set(true);

        ClusterV1OsdListRequest spider = new ClusterV1OsdListRequest(getContext());
        spider.setRequestParams(params);
        spider.request(
                DefaultResponse.success(lock, returnVar),
                DefaultResponse.fail(lock, isFail)
        );

        lock.await(10, TimeUnit.SECONDS);
        assertTrue(isFail.get());
        assertNotNull(returnVar.get());

        ClusterV1OsdData listData = new ClusterV1OsdData(returnVar.get());
        HashMap<String, Integer> pgStateCount = listData.getPgStateCounts();
        for (String state : CephStaticValue.PG_STATUS) {
            assertNotNull(pgStateCount.get(state));
        }
        WriteApiResult.write(getContext(), "api_v1_cluster_id_osd_get", returnVar.get());
    }

    public void test_4_HealthCount() throws JSONException, InterruptedException {
        final CountDownLatch lock = new CountDownLatch(1);
        final Value<String> returnVar = new Value<>();
        final Value<Boolean> isFail = new Value<>();
        returnVar.set(null);
        isFail.set(true);

        ClusterV1HealthCounterRequest spider = new ClusterV1HealthCounterRequest(getContext());
        spider.setRequestParams(params);
        spider.request(
                DefaultResponse.success(lock, returnVar),
                DefaultResponse.fail(lock, isFail)
        );

        lock.await(10, TimeUnit.SECONDS);
        assertTrue(isFail.get());
        assertNotNull(returnVar.get());

        ClusterV1HealthCounterData listData = new ClusterV1HealthCounterData(returnVar.get());
        HashMap<String, Integer> pgStateCount = listData.getPgStateCounts();
        for (String state : CephStaticValue.PG_STATUS) {
            assertNotNull(pgStateCount.get(state));
        }
        WriteApiResult.write(getContext(), "api_v1_cluster_id_health_counters_get", returnVar.get());
    }
}
