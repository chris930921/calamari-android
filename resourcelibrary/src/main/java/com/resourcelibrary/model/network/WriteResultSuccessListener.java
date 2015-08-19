package com.resourcelibrary.model.network;

import android.os.Environment;

import com.android.volley.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Created by User on 2015/8/9.
 */
public class WriteResultSuccessListener {

    public static Response.Listener<String> insert(final String url, final String method, final Response.Listener<String> listener) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                writeToSdcardFile(url, method, s);
                listener.onResponse(s);
            }
        };
    }

    public static void writeToSdcardFile(String url, String method, String result) {
        try {
            File sdCardDir = new File(Environment.getExternalStorageDirectory() + "/ceph_monitor_api");
            sdCardDir.mkdirs();

            Calendar time = Calendar.getInstance();
            File saveFile = new File(sdCardDir, time.getTimeInMillis() + ".txt");
            PrintWriter pw = new PrintWriter(saveFile);
            pw.write(url + "\n");
            pw.write(method + "\n");
            pw.write(result);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
