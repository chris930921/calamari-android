package com.cephmonitor.cephmonitor.layout.dialog.reuse;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.calculator.OnValidStateChangeEvent;
import com.cephmonitor.cephmonitor.layout.component.calculator.OriginCalculator;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.OnSaveFinishedEvent;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.network.api.MutipleCookieHttpStack;
import com.resourcelibrary.model.network.api.ceph.CephPostRequest;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chriske on 2015/9/20.
 */
public abstract class AlertTriggerOriginCalculatorDialog extends SettingDialog {
    private DesignSpec designSpec;
    protected OriginCalculator calculator;
    private TextView save;

    public AlertTriggerOriginCalculatorDialog(Context context) {
        super(context);
        designSpec = ThemeManager.getStyle(getContext());
        calculator = calculator();

        addContentView(calculator);
        addButton(R.string.settings_dialog_cancel, ColorTable._666666, cancelClickEvent);
        save = addButton(R.string.settings_dialog_save, designSpec.getPrimaryColors().getPrimary(), null);
        calculator.setOnInvalidEvent(new OnValidStateChangeEvent() {
            @Override
            public void onInvalidEvent() {
                save.setTextColor(ColorTable._D5D5D5);
                save.setClickable(false);
            }

            @Override
            public void onValidEvent() {
                save.setTextColor(designSpec.getPrimaryColors().getPrimary());
                save.setClickable(true);
            }
        });
    }

    private OriginCalculator calculator() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        OriginCalculator v = getOriginCalculator();
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private OnClickListener cancelClickEvent = new OnClickListener() {
        @Override
        public void onClick(View view) {
            cancel();
        }
    };

    public void setSaveClick(final OnClickListener event) {
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                event.onClick(view);
                cancel();
            }
        });
    }

    public void setSaveFinishedEvent(OnSaveFinishedEvent task) {
        this.task = task;
    }

    private OnSaveFinishedEvent task = new OnSaveFinishedEvent() {
        @Override
        public void onFinish(AlertTriggerOriginCalculatorDialog dialog) {
        }
    };

    public void setCalculatorUnit(String text) {
        calculator.setUnit(text);
    }

    protected abstract OriginCalculator getOriginCalculator();

    private RequestQueue taskQueue;
    private LoadingDialog loadingDialog;

    public void start(String resourceName, String value, String url, final Runnable successTask) {
        if (taskQueue == null) {
            taskQueue = Volley.newRequestQueue(getContext(), new MutipleCookieHttpStack());
        }
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }

        loadingDialog.show();
        LoginParams params = new LoginParams(getContext());
        JSONObject object = new JSONObject();
        try {
            object.put(resourceName, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        final String rawData = object.toString();
        taskQueue.add(new CephPostRequest(params.getSession(), params.getCsrfToken(), rawData, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                successTask.run();
                loadingDialog.cancel();
                task.onFinish(AlertTriggerOriginCalculatorDialog.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Saved failed.", Toast.LENGTH_SHORT).show();
                loadingDialog.cancel();
            }
        }));
    }
}
