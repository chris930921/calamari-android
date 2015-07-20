package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.HostHealthLayout;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.HostHealthItem;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ServerData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2ServerListData;

import org.json.JSONException;

import java.util.ArrayList;

public class HostHealthFragment extends Fragment {
    private HostHealthLayout layout;
    private ClusterV2ServerListData healthData;
    private ArrayList<ClusterV2ServerData> osdList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new HostHealthLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        Bundle arg = getArguments();
        try {
            healthData = new ClusterV2ServerListData("[]");
            healthData.inBox(arg);
            osdList = healthData.getOsdServers();
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        layout.list.setAdapter(getAdapter);
    }

    private BaseAdapter getAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return osdList.size();
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
            final ClusterV2ServerData data = osdList.get(i);
            HostHealthItem item;
            if (view == null) {
                item = new HostHealthItem(getActivity());
            } else {
                item = (HostHealthItem) view;
            }
            try {
                final String hostName = data.getHostName();
                item.setData(
                        hostName,
                        data.getOsdServices().size() + ""
                );
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle arg = new Bundle();
                        arg.putString("0", hostName);
                        FragmentLauncher.goHostDetailFragment(getActivity(), arg);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return item;
        }
    };
}
