package com.cephmonitor.cephmonitor.model.network;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.resourcelibrary.model.network.api.RequestVolleyTask;

import java.util.ArrayList;

/**
 * Created by User on 2015/7/2.
 */
public class SequenceTask {
    protected ArrayList<RequestVolleyTask> taskGroup;
    protected ArrayList<AnalyzeListener<String>> successGroup;
    protected ArrayList<Response.ErrorListener> failGroup;
    protected int taskIndex;
    protected boolean totalSuccess;
    protected CallBack callback;

    public SequenceTask() {
        taskGroup = new ArrayList<>();
        successGroup = new ArrayList<>();
        failGroup = new ArrayList<>();
        callback = new CallBack() {
            @Override
            public void onTotalStart() {

            }

            @Override
            public void onEveryTaskFinish(RequestVolleyTask task, int taskIndex, boolean isTaskSuccess) {

            }

            @Override
            public void onTotalFinish(int taskSize, boolean isTotalSuccess) {

            }
        };
    }

    public void setOnEveryTaskFinish(CallBack callback) {
        this.callback = callback;
    }

    public void clear() {
        taskGroup.clear();
        successGroup.clear();
        failGroup.clear();
    }

    public void add(RequestVolleyTask task, AnalyzeListener<String> success, Response.ErrorListener fail) {
        taskGroup.add(task);
        successGroup.add(success);
        failGroup.add(fail);
    }

    public void start() {
        taskIndex = 0;
        if (taskGroup.size() > 0) {
            totalSuccess = true;
            next();
            callback.onTotalStart();
        }
    }

    public void next() {
        if (taskIndex < taskGroup.size()) {
            RequestVolleyTask task = taskGroup.get(taskIndex);
            task.request(success(taskIndex), fail(taskIndex));
            taskIndex++;
        } else {
            callback.onTotalFinish(taskGroup.size(), totalSuccess);
        }
    }

    private AnalyzeListener<String> success(final int taskIndex) {
        return new AnalyzeListener<String>() {
            @Override
            public boolean doInBackground(String s) {
                return successGroup.get(taskIndex).doInBackground(s);
            }

            @Override
            public void onPostExecute() {
                successGroup.get(taskIndex).onPostExecute();
            }

            @Override
            public void requestFinish(boolean isAnalyzeSuccess) {
                successGroup.get(taskIndex).requestFinish(isAnalyzeSuccess);
                onEveryTaskFinish(taskIndex, isAnalyzeSuccess);
            }
        };
    }

    public Response.ErrorListener fail(final int taskIndex) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                failGroup.get(taskIndex).onErrorResponse(volleyError);
                onEveryTaskFinish(taskIndex, false);
            }
        };
    }

    public void onEveryTaskFinish(int taskIndex, boolean isTaskSuccess) {
        totalSuccess &= isTaskSuccess;
        Log.d("SequenceTask 順序執行", "任務:" + taskGroup.get(taskIndex));
        callback.onEveryTaskFinish(taskGroup.get(taskIndex), taskIndex, isTaskSuccess);
        next();
    }

    public static interface CallBack {
        public void onTotalStart();

        public void onEveryTaskFinish(RequestVolleyTask task, int taskIndex, boolean isTaskSuccess);

        public void onTotalFinish(int taskSize, boolean isTotalSuccess);
    }

}


