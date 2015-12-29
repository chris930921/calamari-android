package com.cephmonitor.cephmonitor.layout.dialog.fixed;

import android.content.Context;
import android.view.View;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.reuse.AlertTriggerCountPercentageDialog;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;

/**
 * Created by chriske on 2015/9/20.
 */
public class AlertTriggerPgErrorDialog extends AlertTriggerCountPercentageDialog {
    public SettingStorage storage;

    public AlertTriggerPgErrorDialog(Context context) {
        super(context);
        storage = new SettingStorage(getContext());


        setTitle(getContext().getString(R.string.settings_alert_triggers_pg_error_dialog_title));
        setCalculatorUnit(getContext().getString(R.string.other_calculater_unit_pg));
        getCalculator().setTotal(storage.getAlertTriggerPgTotal());
        getCalculator().setMaxPercentage(0.8F);
        getCalculator().setMinPercentage(0.2F);
        getCalculator().setPartPercentage(storage.getAlertTriggerPgError());
        setSaveClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginParams params = new LoginParams(getContext());
                final float realValue = getCalculator().getResultValue();
                int value = (int) (realValue * 100);
                start("pg_error", String.valueOf(value), "http://" + params.getHost() + ":" + params.getPort() + "/api/v1/user/me/pg/error", new Runnable() {
                    @Override
                    public void run() {
                        storage.setAlertTriggerPgError(realValue);
                    }
                });
            }
        });
    }
}
