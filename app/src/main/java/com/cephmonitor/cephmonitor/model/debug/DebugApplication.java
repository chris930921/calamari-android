package com.cephmonitor.cephmonitor.model.debug;

import android.app.Application;
import android.os.Build;
import android.os.Environment;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cephmonitor.cephmonitor.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
                writeToSdcardFile(DebugApplication.this, e);
                postToGoogleDoc(e);
                defaultUncaughtExceptionHandler.uncaughtException(t, e);
            }
        };
        Thread.setDefaultUncaughtExceptionHandler(customUncaughtExceptionHandler);
    }

    public static void writeToSdcardFile(Application app, Throwable e) {
        try {
            File sdCardDir = new File(Environment.getExternalStorageDirectory() + "/" + app.getApplicationContext().getPackageName());
            sdCardDir.mkdirs();

            Calendar time = Calendar.getInstance();
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

    private void postToGoogleDoc(Throwable e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter psw = new PrintWriter(sw);
            e.printStackTrace(psw);
            String stackTrace = sw.toString();
            GoogleDocPostRequest request = new GoogleDocPostRequest(
                    stackTrace,
                    Build.BRAND + "\n" + Build.MODEL + "\n",
                    BuildConfig.IS_LOCALHOST + "",
                    null, null
            );
            RequestQueue queue = Volley.newRequestQueue(DebugApplication.this);
            queue.add(request);
        } catch (Exception e1) {
        }
    }

    class GoogleDocPostRequest extends StringRequest {
        private String device;
        private String errorMessage;
        private String isLocalhost;

        public GoogleDocPostRequest(String errorMessage, String device, String isLocalhost, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(Method.POST, "https://docs.google.com/forms/d/1AxsF91Ext_fEBa9YPC3fia_ucotIeioZ63lyCtVw3wY/formResponse", listener, errorListener);
            this.errorMessage = errorMessage;
            this.device = device;
            this.isLocalhost = isLocalhost;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> params = new HashMap<>();
            params.put("entry.1347410436", device);
            params.put("entry.1938478189", errorMessage);
            params.put("entry.503708045", isLocalhost);
            return params;
        }
    }
}
