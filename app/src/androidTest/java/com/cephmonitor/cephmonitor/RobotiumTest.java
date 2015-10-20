package com.cephmonitor.cephmonitor;

import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.robotium.solo.Solo;

/**
 * Created by User on 2015/8/5.
 */
public class RobotiumTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private String LOG_TAG = "RobotiumTest";

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
        solo.waitForActivity(LoginActivity.class.getSimpleName(), 200000);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                LoginParams requestParams = new LoginParams(getActivity());
//                Toast.makeText(getActivity(), requestParams.isLogin() + "", Toast.LENGTH_LONG).show();
//            }
//        });
//        TouchUtils.clickView(this, mClickMeButton);
        final View rootView = getActivity().findViewById(android.R.id.content).getRootView();
//        rootView.dispatchTouchEvent(newTouch);

//        TextView text = new TextView(getActivity());
//        text.setBackgroundColor(Color.parseColor("#999999"));
//        text.setTextColor(Color.WHITE);
//        final PopupWindow mPopupWindow = new PopupWindow(100, 130);
//        mPopupWindow.setContentView(text);

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                //                mPopupWindow.showAtLocation(rootView, Gravity.RIGHT | Gravity.TOP, 0, 0);
//                WindowManager windowManager = (WindowManager) getActivity().getSystemService(Activity.WINDOW_SERVICE);
//                TextView text = new TextView(getActivity());
//                text.setBackgroundColor(Color.parseColor("#999999"));
//                text.setTextColor(Color.WHITE);
//                text.setText("Yes");
//
//                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                        WindowManager.LayoutParams.WRAP_CONTENT,
//                        WindowManager.LayoutParams.WRAP_CONTENT,
//                        WindowManager.LayoutParams.TYPE_PHONE,
//                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                        PixelFormat.TRANSLUCENT);
//
//                params.gravity = Gravity.TOP | Gravity.LEFT;
//                params.x = 0;
//                params.y = 100;
//
//                windowManager.addView(text, params);
//            }
//        });

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
