package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.NotificationDetailLayout;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;

import java.util.Calendar;

public class NotificationDetailFragment extends Fragment {
    private NotificationDetailLayout layout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new NotificationDetailLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        int value = 0;
        Bundle argGroup = getArguments();
        Calendar triggeredTime = Calendar.getInstance();
        triggeredTime.setTimeInMillis(argGroup.getLong("3"));
        String status = argGroup.getString("2");

        Calendar resolvedTime = Calendar.getInstance();
        if (status.equals(CephNotificationConstant.STATUS_RESOLVED)) {
            resolvedTime.setTimeInMillis(argGroup.getLong("4"));
        }

        layout.setMessage(argGroup.getString("1"));
        layout.setStatus(argGroup.getString("2"), resolvedTime);
        layout.setDescription(/*argGroup.getInt("7")*/value, getString(argGroup.getInt("5")), argGroup.getString("6"));
        layout.setTriggered(triggeredTime);
    }
}
