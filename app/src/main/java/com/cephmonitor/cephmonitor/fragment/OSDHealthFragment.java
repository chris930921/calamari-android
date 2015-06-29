package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OnOsdBoxClickListener;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OnStatusChangeListener;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OsdBox;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OsdHealthBoxes;
import com.cephmonitor.cephmonitor.layout.fragment.OSDHealthLayout;
import com.resourcelibrary.model.network.GeneralError;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2OsdData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2OsdListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV2OsdListRequest;

import org.json.JSONException;

import java.util.ArrayList;

public class OSDHealthFragment extends Fragment {
    private OSDHealthLayout layout;
    private LoginParams requestParams;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new OSDHealthLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        requestParams = new LoginParams(getActivity());

        layout.leftButton.setOnClickListener(clickLeftButton);
        layout.rightButton.setOnClickListener(clickRightButton);
        layout.centerButton.setOnClickListener(clickCenterButton);

        layout.leftButton.changeClickColor();

        requestOsdList();
    }

    private OnOsdBoxClickListener clickOsdBox = new OnOsdBoxClickListener() {
        @Override
        public void onClick(OsdHealthBoxes boxGroup, OsdBox box) {
            Bundle arg = getArguments();
            arg.putInt("0", box.value);
            arg.putInt("1", box.getColor());

            ClusterV2OsdData osdData = box.osdData;
            osdData.outBox(arg);

            FragmentLauncher.goOSDHealthDetailFragment(getActivity(), arg);
        }
    };

    private OnStatusChangeListener changeShowedBox = new OnStatusChangeListener() {
        @Override
        public void onChange(OsdHealthBoxes boxGroup, ArrayList boxes) {
            if (boxes.size() > 0) {
                layout.hideWorkFind();
            } else {
                layout.showWorkFind();
            }
        }
    };

    private View.OnClickListener clickLeftButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layout.recoverButtons();
            layout.boxesContainer.setShowStatus(OsdHealthBoxes.ALL);
        }
    };

    private View.OnClickListener clickCenterButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layout.recoverButtons();
            layout.boxesContainer.setShowStatus(OsdHealthBoxes.WARN);
        }
    };

    private View.OnClickListener clickRightButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layout.recoverButtons();
            layout.boxesContainer.setShowStatus(OsdHealthBoxes.ERROR);
        }
    };

    private void requestOsdList() {
        ClusterV2OsdListRequest spider = new ClusterV2OsdListRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successOsdList(), GeneralError.callback(getActivity()));
    }

    private Response.Listener<String> successOsdList() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    dealWithOsdList(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void dealWithOsdList(String response) throws JSONException {
        ClusterV2OsdListData clusterList = new ClusterV2OsdListData(response);
        ArrayList<ClusterV2OsdData> osdList = clusterList.getList();
        ArrayList<OsdBox> boxes = new ArrayList<>();
        for (int i = 0; i < osdList.size(); i++) {
            OsdBox box = new OsdBox();
            ClusterV2OsdData osd = osdList.get(i);
            int osdStatus = osd.getStatus();
            int boxStatus = box.changeStatus(osdStatus);
            box.setStatus(boxStatus);
            box.value = osd.getID();
            box.osdData = osd;
            boxes.add(box);
        }

        layout.boxesContainer.setData(boxes);
        layout.boxesContainer.setOnOsdBoxClickListener(clickOsdBox);
        layout.boxesContainer.setOnStatusChangeListener(changeShowedBox);

        if (boxes.size() == 0) {
            layout.showWorkFind();
        }
    }
}
