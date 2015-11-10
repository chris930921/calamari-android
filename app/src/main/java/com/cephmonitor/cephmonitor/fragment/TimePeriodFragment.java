package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.dialog.fixed.TimePeriodPickerDialog;
import com.cephmonitor.cephmonitor.layout.fragment.TimePeriodLayout;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.logic.FullTimeDecorator;
import com.cephmonitor.cephmonitor.model.logic.SecondToTime;
import com.cephmonitor.cephmonitor.receiver.ChangePeriodReceiver;

public class TimePeriodFragment extends Fragment {
    public TimePeriodLayout layout;
    public SettingStorage settingStorage;
    public static final int REFRESH_ALL = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new TimePeriodLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        settingStorage = new SettingStorage(getActivity());
        refreshSubTitle(REFRESH_ALL);

        layout.normalPeriod.setOnClickListener(showDialog(
                settingStorage.getTimePeriodNormal(),
                R.string.settings_time_period_normal_title));
        layout.abnormalPeriod.setOnClickListener(showDialog(
                settingStorage.getTimerPeriodAbnormal(),
                R.string.settings_time_period_abnormal_title));
        layout.serverAbnormalPeriod.setOnClickListener(showDialog(
                settingStorage.getTimerPeriodServerAbnormal(),
                R.string.settings_time_period_server_abnormal_title));
    }

    private View.OnClickListener showDialog(final long originValue, final int title) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondToTime secondToTime = new SecondToTime(originValue);
                final TimePeriodPickerDialog dialog = new TimePeriodPickerDialog(getActivity());
                dialog.setTitle(title);
                dialog.setTime(secondToTime.getHour(), secondToTime.getMintune(), secondToTime.getSecond());
                dialog.setOkClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        storeTimePeriod(title, dialog);
                        refreshSubTitle(title);
                    }
                });
                dialog.show();
            }
        };
    }

    public void storeTimePeriod(int title, TimePeriodPickerDialog dialog) {
        if (title == R.string.settings_time_period_normal_title) {
            settingStorage.setTimePeriodNormal(dialog.getSecondPeriod());
        } else if (title == R.string.settings_time_period_abnormal_title) {
            settingStorage.setTimePeriodAbnormal(dialog.getSecondPeriod());
        } else if (title == R.string.settings_time_period_server_abnormal_title) {
            settingStorage.setTimePeriodServerAbnormal(dialog.getSecondPeriod());
        }
        ChangePeriodReceiver.sendTimePeriodChangedMessage(getActivity());
    }

    public void refreshSubTitle(int resourceId) {
        String title, fullTime;

        if (resourceId < 0 || resourceId == R.string.settings_time_period_normal_title) {
            fullTime = FullTimeDecorator.change(settingStorage.getTimePeriodNormal());
            title = getString(R.string.settings_time_period_normal_content);
            title = String.format(title, fullTime);
            layout.normalPeriod.setSubTitle(title);
        }
        if (resourceId < 0 || resourceId == R.string.settings_time_period_abnormal_title) {
            fullTime = FullTimeDecorator.change(settingStorage.getTimerPeriodAbnormal());
            title = getString(R.string.settings_time_period_abnormal_content);
            title = String.format(title, fullTime);
            layout.abnormalPeriod.setSubTitle(title);
        }
        if (resourceId < 0 || resourceId == R.string.settings_time_period_server_abnormal_title) {
            fullTime = FullTimeDecorator.change(settingStorage.getTimerPeriodServerAbnormal());
            title = getString(R.string.settings_time_period_server_abnormal_content);
            title = String.format(title, fullTime);
            layout.serverAbnormalPeriod.setSubTitle(title);
        }
    }
}
