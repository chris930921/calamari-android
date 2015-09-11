package com.cephmonitor.cephmonitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.cephmonitor.cephmonitor.service.LooperService;
import com.resourcelibrary.model.log.ShowLog;

/**
 * Created by User on 2015/9/3.
 */
public class ChangePeriodReceiver extends BroadcastReceiver {
    public static final String ACTION = ChangePeriodReceiver.class.getName();
    public static final String PERIOD_FLAG = "PeriodFlag";
    public LooperService service;

    public ChangePeriodReceiver(LooperService service) {
        this.service = service;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int flag = intent.getIntExtra(PERIOD_FLAG, 0);
        ShowLog.d("取得週期轉換廣播，Flag: " + flag + "。");
        if (flag == 0) {
            service.changeDefaultPeriod();
        } else if (flag == 1) {
            service.changeCheckPeriod();
        } else if (flag == 2) {
            service.changeServerErrorPeriod();
        }
    }

    public static void sendDefaultMessage(Context context) {
        ShowLog.d("發送轉換預設週期廣播。");
        sendPeriodMessage(context, 0);
    }

    public static void sendCheckMessage(Context context) {
        ShowLog.d("發送轉換檢查週期廣播。");
        sendPeriodMessage(context, 1);
    }

    public static void sendServerErrorMessage(Context context) {
        ShowLog.d("發送轉換伺服器錯誤週期廣播。");
        sendPeriodMessage(context, 2);
    }

    public static void sendPeriodMessage(Context context, int flag) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(PERIOD_FLAG, flag);
        context.sendBroadcast(intent);
    }

    public void register() {
        ShowLog.d("註冊週期轉換接收器。");
        service.registerReceiver(this, new IntentFilter(ACTION));
    }

    public void unregister() {
        ShowLog.d("註銷週期轉換接收器。");
        service.unregisterReceiver(this);
    }
}
