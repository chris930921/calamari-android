package com.cephmonitor.cephmonitor.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.cephmonitor.cephmonitor.layout.activity.MainLayout;

/**
 * Created by User on 4/17/2015.
 */
public class FragmentLauncher {

    private static boolean checkFragmentOpening(Activity activity, String fragmentName) {
        return activity.getFragmentManager().findFragmentByTag(fragmentName) == null;
    }

    private static void cleanAllPopFragment(Activity activity) {
        FragmentManager manager = activity.getFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); ++i) {
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private static void change(Activity activity, int containerId, Fragment fragment) {
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, fragment, fragment.getClass().getName());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    public static void goHealthFragment(Activity activity) {
        String fragmentName = HealthFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new HealthFragment();
            cleanAllPopFragment(activity);
            change(activity, MainLayout.CONTAINER_ID, page);
        }
    }

    public static void goHealthDetailFragment(Activity activity, Bundle arg) {
        String fragmentName = HealthDetailFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new HealthDetailFragment();
            page.setArguments(arg);
            cleanAllPopFragment(activity);
            change(activity, MainLayout.CONTAINER_ID, page);
        }
    }

    public static void goOsdHealthFragment(Activity activity, Bundle arg) {
        String fragmentName = OSDHealthFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new OSDHealthFragment();
            page.setArguments(arg);
            cleanAllPopFragment(activity);
            change(activity, MainLayout.CONTAINER_ID, page);
        }
    }

    public static void goOSDHealthDetailFragment(Activity activity, Bundle arg) {
        String fragmentName = OSDHealthDetailFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            Fragment page = new OSDHealthDetailFragment();
            page.setArguments(arg);
            cleanAllPopFragment(activity);
            change(activity, MainLayout.CONTAINER_ID, page);
        }
    }


}
