package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.PgStatusLayout;
import com.cephmonitor.cephmonitor.layout.listitem.PgStatusItem;

public class PgStatusFragment extends Fragment {
    private PgStatusLayout layout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new PgStatusLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        layout.list.setAdapter(getAdapter);
    }

    private BaseAdapter getAdapter = new BaseAdapter() {
        String[] pgStatus = {"Down", "Creating", "Clean"};
        int[] pgValues = {999, 1324, 99999};

        @Override
        public int getCount() {
            return 3;
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
            PgStatusItem item = new PgStatusItem(getActivity());
            item.setData(pgStatus[i], pgValues[i], "30", "OSD");
            return item;
        }
    };
}
