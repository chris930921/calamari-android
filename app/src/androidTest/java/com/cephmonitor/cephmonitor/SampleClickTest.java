package com.cephmonitor.cephmonitor;

import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.MotionEvent;

import com.robotium.solo.Solo;

/**
 * Created by User on 2015/8/5.
 */
public class SampleClickTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private String LOG_TAG = "RobotiumTest";

    @SuppressWarnings("unchecked")
    public SampleClickTest() {
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
        solo.waitForActivity(LoginActivity.class.getSimpleName(), 200000);
        sleep(10000);

        MotionEvent newTouch;
        while (true) {
            newTouch = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                    MotionEvent.ACTION_DOWN, 755, 1055, 0);
            getInstrumentation().sendPointerSync(newTouch);
            newTouch = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                    MotionEvent.ACTION_UP, 755, 1055, 0);
            getInstrumentation().sendPointerSync(newTouch);
            sleep(10000);
            newTouch = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                    MotionEvent.ACTION_DOWN, 96, 156, 0);
            getInstrumentation().sendPointerSync(newTouch);
            newTouch = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                    MotionEvent.ACTION_UP, 96, 156, 0);
            getInstrumentation().sendPointerSync(newTouch);
            sleep(10000);
        }
    }

    private void sleep(long time) {
        try {
            if (time >= 1000) {
                for (int i = 0; i < time / 1000; i++) {
                    final int second = i;
                    Log.d(LOG_TAG, second + " second.");
                    Thread.sleep(1000);
                }
            } else {
                Thread.sleep(time);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
