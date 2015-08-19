package com.cephmonitor.cephmonitor.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Created by User on 5/14/2015.
 */
public abstract class LoadFinishReceiver extends BroadcastReceiver {
    private static final String ACTION = LoadFinishReceiver.class.getName();

    public void register(Activity activity) {
        activity.registerReceiver(this, new IntentFilter(ACTION));
    }

    public void unregister(Activity activity) {
        activity.unregisterReceiver(this);
    }

    public static void sendBroadcast(Context context, Bundle message) {
        Intent intent = new Intent(ACTION);
        intent.putExtras(message);
        context.sendBroadcast(intent);
    }
}