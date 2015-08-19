package com.cephmonitor.cephmonitor;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Toast;

import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.robotium.solo.Solo;

/**
 * Created by User on 2015/8/5.
 */
public class RobotiumTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    @SuppressWarnings("unchecked")
    public RobotiumTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testRun() {
        getInstrumentation().addMonitor(LoginActivity.class.getName(), null, true);
        solo.waitForActivity(LoginActivity.class.getSimpleName(), 2000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoginParams requestParams = new LoginParams(getActivity());
                Toast.makeText(getActivity(), requestParams.isLogin() + "", Toast.LENGTH_LONG).show();
            }
        });
    }
}
