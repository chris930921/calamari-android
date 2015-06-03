package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.NotificationLayout;
import com.cephmonitor.cephmonitor.layout.listitem.NotificationItem;

public class NotificationFragment extends Fragment {
    private NotificationLayout layout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new NotificationLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        layout.list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 2;
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
                NotificationItem item = new NotificationItem(getActivity());
                if (i == 0) {
                    item.setItemValue(NotificationItem.WARNING, "3 個 OSD 異常!", "2015/5/31 14:38");
                } else {
                    item.setItemValue(NotificationItem.ERROR, "1 個 OSD 損毀!", "2015/5/21 14:38");
                }
                return item;
            }
        });
    }
}
