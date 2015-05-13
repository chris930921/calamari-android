package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.fragment.HealthDetailLayout;
import com.resourcelibrary.model.view.item.LeftImageRightTextItem;

public class HealthDetailFragment extends Fragment {
    private HealthDetailLayout layout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new HealthDetailLayout(getActivity());
        }
        return layout;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layout.list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                String[] contents = {"recovery 79/158 objects degraded (50.000%)", "no osds", "960 pgs stale"};
                int[] images = {R.drawable.icon022, R.drawable.icon023, R.drawable.icon022};
                LeftImageRightTextItem item = new LeftImageRightTextItem(getActivity());
                item.setItemValue(images[i], contents[i]);
                return item;
            }
        });
    }
}
