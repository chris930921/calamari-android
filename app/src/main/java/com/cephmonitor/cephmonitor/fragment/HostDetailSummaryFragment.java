package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.HostDetailSummaryLayout;

public class HostDetailSummaryFragment extends Fragment {
    private HostDetailSummaryLayout layout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new HostDetailSummaryLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
//        layout.table.setMaxTime(Calendar.getInstance());
//        for (ChartLine adapter : new TestAreaLineRandomAdapter()) {
//            layout.table.addAdapter(adapter);
//        }
//        getFragm
    }
}
