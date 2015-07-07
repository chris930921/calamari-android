package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.HostDetailAllCpusLayout;
import com.cephmonitor.cephmonitor.layout.listitem.HostDetailAllCpusItem;

import java.util.ArrayList;

public class HostDetailAllCpusFragment extends Fragment {
    private HostDetailAllCpusLayout layout;
    private ArrayList<String> targetGroup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new HostDetailAllCpusLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        targetGroup = new ArrayList<>();

        Bundle arg = getArguments();
        String hostName = arg.getString("0");

        targetGroup.add("servers." + hostName + ".cpu.total.system");
        targetGroup.add("servers." + hostName + ".cpu.total.user");
        targetGroup.add("servers." + hostName + ".cpu.total.idle");

        layout.list.setAdapter(adapter);
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return targetGroup.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HostDetailAllCpusItem item;
            if (view == null) {
                item = new HostDetailAllCpusItem(getActivity());
            } else {
                item = (HostDetailAllCpusItem) view;
            }
            return item;
        }
    };
}
