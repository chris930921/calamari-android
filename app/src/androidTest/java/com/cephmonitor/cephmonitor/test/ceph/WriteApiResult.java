package com.cephmonitor.cephmonitor.test.ceph;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by User on 2015/6/30.
 */
public class WriteApiResult {
    public static void write(Context context, String fileName, String content) {
        try {
            File sdCardDir = new File(Environment.getExternalStorageDirectory() + "/" + context.getApplicationContext().getPackageName() + "/api");
            sdCardDir.mkdirs();

            File saveFile = new File(sdCardDir, fileName + ".txt");
            FileOutputStream overwriteFileStream = new FileOutputStream(saveFile, false);
            PrintWriter pw = new PrintWriter(overwriteFileStream);
            pw.write(content);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e1) {
        }
    }
}
