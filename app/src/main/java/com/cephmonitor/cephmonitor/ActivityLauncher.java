package com.cephmonitor.cephmonitor;

import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 4/17/2015.
 */
public class ActivityLauncher {

    public static void goLoginActivity(Context context) {
        Intent nextPage = new Intent(context, LoginActivity.class);
        context.startActivity(nextPage);
    }

    public static void goMainActivity(Context context) {
        Intent nextPage = new Intent(context, MainActivity.class);
        context.startActivity(nextPage);
    }
}
