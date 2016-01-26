package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.OnSaveFinishedEvent;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.AlertTriggerOriginCalculatorDialog;
import com.cephmonitor.cephmonitor.layout.fragment.AlertTriggersLayout;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.SettingDescriptionItem;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.file.io.SettingUpdateThread;
import com.cephmonitor.cephmonitor.model.network.remotesetting.data.ApiV1UserMeAlertRuleGetData;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import java.util.LinkedHashMap;

public class AlertTriggersFragment extends Fragment {
    public AlertTriggersLayout layout;
    public SettingStorage storage;
    public Object[] paramsGroup;
    private LinkedHashMap<SettingDescriptionItem, Integer> updateDescriptionMap;
    private LoadingDialog loadingDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new AlertTriggersLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        loadingDialog = new LoadingDialog(getActivity());
        updateDescriptionMap = new LinkedHashMap<>();
        storage = new SettingStorage(getActivity());

        updateDescriptionMap.put(layout.osdWarning, R.string.settings_alert_triggers_osd_warning_description);
        updateDescriptionMap.put(layout.osdError, R.string.settings_alert_triggers_osd_error_description);
        updateDescriptionMap.put(layout.monitorWarning, R.string.settings_alert_triggers_monitor_warning_description);
        updateDescriptionMap.put(layout.monitorError, R.string.settings_alert_triggers_monitor_error_description);
        updateDescriptionMap.put(layout.pgWarning, R.string.settings_alert_triggers_pg_warning_description);
        updateDescriptionMap.put(layout.pgError, R.string.settings_alert_triggers_pg_error_description);
        updateDescriptionMap.put(layout.usageWarning, R.string.settings_alert_triggers_usage_warning_description);
        updateDescriptionMap.put(layout.usageError, R.string.settings_alert_triggers_usage_error_description);

        layout.setDialogSaveFinishedEvent(onSaveFinished);

        loadingDialog.show();
        SettingUpdateThread.update(getActivity(), accessListener);
    }

    private OnSaveFinishedEvent onSaveFinished = new OnSaveFinishedEvent() {
        @Override
        public void onFinish(AlertTriggerOriginCalculatorDialog dialog) {
            updateAllDescription();
        }
    };

    private SettingUpdateThread.AccessListener accessListener = new SettingUpdateThread.AccessListener() {
        @Override
        public void success(ApiV1UserMeAlertRuleGetData data) {
            updateAllDescription();
            loadingDialog.cancel();
        }

        @Override
        public void fail(VolleyError volleyError) {
            Toast.makeText(getActivity(), "Save setting configuration failed.", Toast.LENGTH_SHORT).show();
            loadingDialog.cancel();
        }
    };

    private void updateAllDescription() {
        paramsGroup = new Object[]{
                storage.getAlertTriggerOsdWarning(),
                storage.getAlertTriggerOsdError(),
                storage.getAlertTriggerMonWarning(),
                storage.getAlertTriggerMonError(),
                (int) (storage.getAlertTriggerPgWarning() * 100),
                (int) (storage.getAlertTriggerPgError() * 100),
                (int) (storage.getAlertTriggerUsageWarning() * 100),
                (int) (storage.getAlertTriggerUsageError() * 100),
        };
        int i = 0;
        for (SettingDescriptionItem item : updateDescriptionMap.keySet()) {
            int resourceText = updateDescriptionMap.get(item);
            String textNoParams = getString(resourceText);
            String text = String.format(textNoParams, new Object[]{paramsGroup[i]});
            item.setSubTitle(text);
            i++;
        }
    }
}
