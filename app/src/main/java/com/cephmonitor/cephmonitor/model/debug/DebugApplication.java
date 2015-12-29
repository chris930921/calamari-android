package com.cephmonitor.cephmonitor.model.debug;

import android.app.Application;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Created by User on 6/14/2015.
 */
public class DebugApplication extends Application {
    protected Thread.UncaughtExceptionHandler customUncaughtExceptionHandler;
    protected Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        customUncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // 抓取所有錯誤

                // 寫入到檔案中
                writeToSdcardFile(DebugApplication.this, e);
                // 照常拋出錯誤
                defaultUncaughtExceptionHandler.uncaughtException(t, e);
            }
        };
        Thread.setDefaultUncaughtExceptionHandler(customUncaughtExceptionHandler);
    }

    public static void writeToSdcardFile(Application app, Throwable e) {
        try {
            // 寫入到 /sdcard/<application package name>
            File sdCardDir = new File(Environment.getExternalStorageDirectory() + "/" + app.getApplicationContext().getPackageName());
            sdCardDir.mkdirs();

            Calendar time = Calendar.getInstance();
            // 檔案取名 error_output_  加上 timestamp
            File saveFile = new File(sdCardDir, "error_output_" + time.getTimeInMillis() + ".txt");
            PrintWriter pw = new PrintWriter(saveFile);
            pw.write(time.getTime().toString() + "\n");
            pw.write(Build.BRAND + "\n" + Build.MODEL + "\n");
            pw.write("==================================================================\n");
            e.printStackTrace(pw);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e1) {
        }
    }
}
