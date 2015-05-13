package com.cephmonitor.cephmonitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cephmonitor.cephmonitor.service.ServiceLauncher;

/**
 * Created by User on 5/14/2015.
 */
public class BootUpReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            ServiceLauncher.startLooperService(context);
        }
    }
}