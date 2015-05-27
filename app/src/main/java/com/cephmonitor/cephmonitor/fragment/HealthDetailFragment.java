package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.fragment.HealthDetailLayout;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV1HealthData;
import com.resourcelibrary.model.view.item.LeftImageRightTextItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthDetailFragment extends Fragment {
    private HealthDetailLayout layout;
    private ClusterV1HealthData data;
    private ArrayList<String> status;
    private ArrayList<String> contents;
    private HashMap<String, Integer> icons;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new HealthDetailLayout(getActivity());
            init();
        }
        return layout;
    }

    public void init() {
        icons = new HashMap<>();
        icons.put(ClusterV1HealthData.HEALTH_WARN, R.drawable.icon022);
        icons.put(ClusterV1HealthData.HEALTH_ERR, R.drawable.icon023);

        try {
            data = new ClusterV1HealthData("{}");
            data.inBox(getArguments());
            status = data.getSummarySeverities();
            contents = data.getSummaryDescriptions();
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        for (int i = status.size() - 1; i >= 0; i--) {
            String statusText = status.get(i);
            if (statusText.equals(ClusterV1HealthData.HEALTH_OK)) {
                status.remove(i);
                contents.remove(i);
            }
        }

        layout.list.setAdapter(getAdapter);
    }

    private BaseAdapter getAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return status.size();
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
            String statusText = status.get(i);
            int images = icons.get(statusText);
            String content = contents.get(i);

            LeftImageRightTextItem item = new LeftImageRightTextItem(getActivity());
            item.setItemValue(images, content);
            return item;
        }
    };
}
