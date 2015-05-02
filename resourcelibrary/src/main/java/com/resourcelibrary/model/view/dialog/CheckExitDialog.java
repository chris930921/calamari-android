package com.resourcelibrary.model.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by User on 4/17/2015.
 */
public class CheckExitDialog {
    public static final int DELAY = 100;

    public static AlertDialog create(final Activity activity) {
        return new AlertDialog.Builder(activity)
                .setTitle("Exit")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", clickOk(activity)).create();
    }

    public static DialogInterface.OnClickListener clickOk(final Activity activity) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.findViewById(android.R.id.content).getRootView().postDelayed(delayFinish(activity), DELAY);
            }
        };
    }

    public static Runnable delayFinish(final Activity activity) {
        return new Runnable() {
            @Override
            public void run() {
                activity.finish();
            }
        };
    }
}
