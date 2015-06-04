package com.cephmonitor.cephmonitor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by User on 6/3/2015.
 */
public class InitFragment {

    public interface Style {
        public void changeFragmentStyle(Fragment fragment);
    }

    public interface Task {
        public void action(Bundle arg);
    }

    public static void choiceActivity(Activity activity, Fragment fragment) {
        Style style = (Style) activity;
        style.changeFragmentStyle(fragment);
    }
}
