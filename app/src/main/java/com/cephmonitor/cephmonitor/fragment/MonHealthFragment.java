package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.Response;
import com.cephmonitor.cephmonitor.layout.component.card.MonHealthCard;
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

public class MonHealthFragment extends Fragment {
    private MonHealthLayout layout;
    private LoginParams requestParams;
    private ArrayList<ClusterV2MonData> mons;
    private HashMap<String, ClusterV1HealthMonData> monsStatus;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new MonHealthLayout(getActivity());
            init();
        }
        return layout;
    }

    public void init() {
        Bundle arg = getArguments();
        try {
            ClusterV1HealthData healthData = new ClusterV1HealthData("{}");
            healthData.inBox(arg);
            monsStatus = healthData.getMonMap();
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
        mons = list.getList();
        layout.list.setAdapter(getAdapter);
    }

    private BaseAdapter getAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mons.size();
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
            ClusterV2MonData data = mons.get(i);
            MonHealthCard card;
            if (view == null) {
                card = new MonHealthCard(getActivity());
            } else {
                card = (MonHealthCard) view;
            }
            try {
                ClusterV1HealthMonData monStatus = monsStatus.get(data.getName());
                card.setData(monStatus.getHealth(), data.getName(), data.getAddr());
            } catch (JSONException e) {
                card.setData(ClusterV1HealthData.HEALTH_OK, "", "");
            }
            return card;
        }
    };

}
