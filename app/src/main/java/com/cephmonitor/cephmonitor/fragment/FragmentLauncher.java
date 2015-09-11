package com.cephmonitor.cephmonitor.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.activity.MainLayout;
import com.resourcelibrary.model.log.ShowLog;

/**
 * Created by User on 4/17/2015.
 */
public class FragmentLauncher {
    public static final String FIRST_FRAGMENT_LAYER = "First Fragment Layer";
    public static final String SECOND_FRAGMENT_LAYER = "Second Fragment Layer";
    public static final String THIRD_FRAGMENT_LAYER = "Third Fragment Layer";

    private static boolean checkFragmentOpening(Activity activity, String fragmentName) {
        return activity.getFragmentManager().findFragmentByTag(fragmentName) == null;
    }

    public static void backFragment(Activity activity) {
        activity.getFragmentManager().popBackStackImmediate();
        printBackStack(activity);
    }

    public static void backFragment(Activity activity, Class fragmentClass) {
        activity.getFragmentManager().popBackStackImmediate(fragmentClass.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        printBackStack(activity);
    }

//    private static void cleanAllPopFragment(Activity activity, Fragment fragment) {
//        FragmentManager manager = activity.getFragmentManager();
//        manager.popBackStack(fragment.getClass().getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
//    }

    private static void cleanToTragetFragment(Activity activity, String fragmentTag) {
        FragmentManager manager = activity.getFragmentManager();
        manager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private static void printBackStack(Activity activity) {
        FragmentManager manager = activity.getFragmentManager();
        ShowLog.d("print back stack:===========================================");
        for (int i = 0; i < manager.getBackStackEntryCount(); ++i) {
            ShowLog.d("print back stack:" + manager.getBackStackEntryAt(i).getName());
        }
        ShowLog.d("print back stack:===========================================");
    }

    private static void change(Activity activity, int containerId, Fragment fragment) {
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, fragment, fragment.getClass().getName());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    private static void changeFadeIn(Activity activity, int containerId, Fragment fragment) {
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(containerId, fragment, fragment.getClass().getName());
        transaction.commit();
    }

    private static void changeAndBackFadeSlide(Activity activity, int containerId, Fragment fragment, String backStackTag) {
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.anim.fade_slide_left_in, R.anim.slide_left_out, R.anim.fade_slide_right_in, R.anim.slide_right_out);
        transaction.replace(containerId, fragment, fragment.getClass().getName());
        transaction.addToBackStack(backStackTag);
        transaction.commit();
    }

    private static void changeAndBackFade(Activity activity, int containerId, Fragment fragment, String backStackTag) {
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.hide_out, R.anim.fade_in, R.anim.hide_out);
        transaction.replace(containerId, fragment, fragment.getClass().getName());
        transaction.addToBackStack(backStackTag);
        transaction.commit();
    }

    public static void goHealthFragment(Activity activity) {
        String fragmentName = HealthFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new HealthFragment();
            cleanToTragetFragment(activity, FIRST_FRAGMENT_LAYER);
            changeFadeIn(activity, MainLayout.CONTAINER_ID, page);
        }
    }

    public static void goHealthDetailFragment(Activity activity, Bundle arg) {
        String fragmentName = HealthDetailFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new HealthDetailFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, SECOND_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, SECOND_FRAGMENT_LAYER);

        }
    }

    public static void goOsdHealthFragment(Activity activity, Bundle arg) {
        String fragmentName = OSDHealthFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new OSDHealthFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, SECOND_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, SECOND_FRAGMENT_LAYER);
        }
    }

    public static void goOSDHealthDetailFragment(Activity activity, Bundle arg) {
        String fragmentName = OSDHealthDetailFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new OSDHealthDetailFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, THIRD_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, THIRD_FRAGMENT_LAYER);
        }
    }

    public static void goMonHealthFragment(Activity activity, Bundle arg) {
        String fragmentName = MonHealthFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new MonHealthFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, SECOND_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, SECOND_FRAGMENT_LAYER);
        }
    }

    public static void goNotificationFragment(Activity activity, Bundle arg) {
        String fragmentName = NotificationFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new NotificationFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, SECOND_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, SECOND_FRAGMENT_LAYER);
        }
    }

    public static void goNotificationDetailFragment(Activity activity, Bundle arg) {
        String fragmentName = NotificationDetailFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new NotificationDetailFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, THIRD_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, THIRD_FRAGMENT_LAYER);
        }
    }

    public static void goHostHealthFragment(Activity activity, Bundle arg) {
        String fragmentName = HostHealthFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new HostHealthFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, SECOND_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, SECOND_FRAGMENT_LAYER);
        }
    }

    public static void goPgStatusFragment(Activity activity, Bundle arg) {
        String fragmentName = PgStatusFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new PgStatusFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, SECOND_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, SECOND_FRAGMENT_LAYER);
        }
    }

    public static void goUsageStatusFragment(Activity activity, Bundle arg) {
        String fragmentName = UsageStatusFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new UsageStatusFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, SECOND_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, SECOND_FRAGMENT_LAYER);
        }
    }

    public static void goPoolIopsFragment(Activity activity, Bundle arg) {
        String fragmentName = PoolIopsFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new PoolIopsFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, SECOND_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, SECOND_FRAGMENT_LAYER);
        }
    }

    public static void goPoolListFragment(Activity activity, Bundle arg) {
        String fragmentName = PoolListFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new PoolListFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, SECOND_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, SECOND_FRAGMENT_LAYER);
        }
    }

    public static void goHostDetailFragment(Activity activity, Bundle arg) {
        String fragmentName = HostDetailFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new HostDetailFragment();
            page.setArguments(arg);
            cleanToTragetFragment(activity, THIRD_FRAGMENT_LAYER);
            changeAndBackFadeSlide(activity, MainLayout.CONTAINER_ID, page, THIRD_FRAGMENT_LAYER);
        }
        printBackStack(activity);
    }

    public static void goHostDetailSummaryFragment(Activity activity, Bundle arg) {
        Fragment page = new HostDetailSummaryFragment();
        page.setArguments(arg);
        changeAndBackFade(activity, MainLayout.CONTAINER_ID, page, THIRD_FRAGMENT_LAYER);
    }

    public static void goHostDetailAllCpusFragment(Activity activity, Bundle arg) {
        Fragment page = new HostDetailAllCpusFragment();
        page.setArguments(arg);
        changeAndBackFade(activity, MainLayout.CONTAINER_ID, page, THIRD_FRAGMENT_LAYER);
    }

    public static void goHostDetailIopsFragment(Activity activity, Bundle arg) {
        Fragment page = new HostDetailIopsFragment();
        page.setArguments(arg);
        changeAndBackFade(activity, MainLayout.CONTAINER_ID, page, THIRD_FRAGMENT_LAYER);
    }
}
