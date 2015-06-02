package com.cephmonitor.cephmonitor.model.file.io;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 2015/6/2.
 */
public class ClassSelfStatus {
    private Class cls;
    private Context context;

    public ClassSelfStatus(Class cls, Context context) {
        this.cls = cls;
        this.context = context;
    }

    public void saveStatus(String status) {
        String filePath = cls.getName() + "_status";
        SharedPreferences settings = context.getSharedPreferences(filePath, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("status", status);
        editor.commit();
    }

    public String loadStatus() {
        String filePath = cls.getName() + "_status";
        SharedPreferences settings = context.getSharedPreferences(filePath, Context.MODE_MULTI_PROCESS);
        return settings.getString("status", "");
    }

}
