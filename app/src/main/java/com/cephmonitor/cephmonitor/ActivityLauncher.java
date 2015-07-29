package com.cephmonitor.cephmonitor;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

    public static PendingIntent goMainActivityNotificationPending(Context context) {
        Bundle arg = new Bundle();
        arg.putString("init_mode", "Notification Page");

        Intent nextPage = new Intent(context, MainActivity.class);
        nextPage.putExtras(arg);
        nextPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return PendingIntent.getActivity(context, 0, nextPage, 0);
    }
}
