package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cephmonitor.cephmonitor.layout.fragment.OSDHealthDetailLayout;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2OsdData;

import org.json.JSONException;

public class OSDHealthDetailFragment extends Fragment {
    private OSDHealthDetailLayout layout;
    private ClusterV2OsdData data;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new OSDHealthDetailLayout(getActivity());
        }
        return layout;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            Bundle arg = getArguments();
            data = new ClusterV2OsdData("{}");
            data.inBox(arg);

            layout.hostNameContent.setText(data.getServer());
            layout.publicIpContent.setText(data.getPublicAddr());
            layout.clusterIpContent.setText(data.getClusterAddr());
            layout.reweightContent.setText(data.getReweight() + "");
            layout.uuidContent.setText(data.getUUID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
