package com.cephmonitor.cephmonitor.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.cephmonitor.cephmonitor.layout.activity.MainLayout;

/**
 * Created by User on 4/17/2015.
 */
public class FragmentChange {

    public static void goHealthFragment(Activity activity) {
        String fragmentName = HealthFragment.class.getName();
        boolean isClosed = checkFragmentOpening(activity, fragmentName);
        if (isClosed) {
            HealthFragment page = new HealthFragment();
            cleanAllPopFragment(activity);
            change(activity, MainLayout.CONTAINER_ID, page);
        }
    }

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
}
