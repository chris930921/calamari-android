package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.PoolIopsLayout;
import com.cephmonitor.cephmonitor.layout.listitem.PoolIopsItem;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1Data;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1ListData;

import org.json.JSONException;

import java.util.ArrayList;

public class PoolIopsFragment extends Fragment {
    private PoolIopsLayout layout;
    private ArrayList<PoolV1Data> pools;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new PoolIopsLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        try {
            Bundle arg = getArguments();
            PoolV1ListData poolData = new PoolV1ListData("[]");
            poolData.inBox(arg);
            pools = poolData.getList();
        } catch (JSONException e) {
            e.printStackTrace();
            pools = new ArrayList<>();
        }

        layout.list.setAdapter(getAdapter);
    }

    private BaseAdapter getAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return pools.size();
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
            PoolIopsItem item = new PoolIopsItem(getActivity());
            try {
                item.setData(pools.get(i).getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return item;
        }
    };
}
