package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.OSDHealthDetailLayout;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2OsdData;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1ListData;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class OSDHealthDetailFragment extends Fragment {
    private OSDHealthDetailLayout layout;
    private ClusterV2OsdData osdData;
    private PoolV1ListData poolData;
    private ArrayList<String> names;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new OSDHealthDetailLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        try {
            Bundle arg = getArguments();
            osdData = new ClusterV2OsdData("{}");
            osdData.inBox(arg);
            poolData = new PoolV1ListData("[]");
            poolData.inBox(arg);
            mapPoolsIdToName();

            layout.hostNameContent.setText(osdData.getServer());
            layout.publicIpContent.setText(osdData.getPublicAddr());
            layout.clusterIpContent.setText(osdData.getClusterAddr());
            layout.poolLabels.setData(names);
            layout.reweightContent.setText(((int) Math.ceil(osdData.getReweight() * 100.0)) + "%");
            layout.uuidContent.setText(osdData.getUUID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void mapPoolsIdToName() throws JSONException {
        ArrayList<Integer> ids = osdData.getPools();
        HashMap<Integer, String> idMapName = poolData.getIdMapName();
        names = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            String name = idMapName.get(id);
            names.add(name);
        }
    }
}
