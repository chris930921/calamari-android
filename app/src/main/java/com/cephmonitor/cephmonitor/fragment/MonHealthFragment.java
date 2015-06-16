package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.Response;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.listitem.MonHealthCard;
import com.cephmonitor.cephmonitor.layout.fragment.MonHealthLayout;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.GeneralError;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthMonData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2MonData;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2MonListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ClusterV2MonListRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MonHealthFragment extends Fragment {
    private MonHealthLayout layout;
    private LoginParams requestParams;
    private HashMap<String, ClusterV2MonData> mons;
    private LinkedHashMap<String, ClusterV1HealthMonData> monsStatus;
    private ArrayList<String> monsStatusKeys;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new MonHealthLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        Bundle arg = getArguments();
        try {
            ClusterV1HealthData healthData = new ClusterV1HealthData("{}");
            healthData.inBox(arg);
            monsStatus = healthData.getOrderMonMap();
            monsStatusKeys = new ArrayList<>(monsStatus.keySet());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        requestParams = new LoginParams(getActivity());
        requestMonList();
    }

    private void requestMonList() {
        ClusterV2MonListRequest spider = new ClusterV2MonListRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(successMonList(), GeneralError.callback(getActivity()));
    }

    private Response.Listener<String> successMonList() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    ShowLog.d("requestFirstClusterId" + " 結果:" + s);
                    dealWithMonList(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void dealWithMonList(String response) throws JSONException {
        ClusterV2MonListData list = new ClusterV2MonListData(response);
        mons = list.getMap();
        layout.list.setAdapter(getAdapter);
    }

    private BaseAdapter getAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return monsStatusKeys.size();
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
            String key = monsStatusKeys.get(i);
            ClusterV1HealthMonData monStatusData = monsStatus.get(key);
            ClusterV2MonData monData = mons.get(key);
            MonHealthCard card;
            if (view == null) {
                card = new MonHealthCard(getActivity());
            } else {
                card = (MonHealthCard) view;
            }
            try {
                card.setData(monStatusData.getHealth(), monData.getName(), monData.getAddr());
            } catch (JSONException e) {
                card.setData(ClusterV1HealthData.HEALTH_OK, "", "");
            }
            return card;
        }
    };
}
