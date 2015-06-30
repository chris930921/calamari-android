package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.LineAdapter;
import com.cephmonitor.cephmonitor.layout.fragment.PoolIopsLayout;
import com.cephmonitor.cephmonitor.layout.listitem.PoolIopsItem;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1Data;
import com.resourcelibrary.model.network.api.ceph.object.PoolV1ListData;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

public class PoolIopsFragment extends Fragment {
    private PoolIopsLayout layout;
    private ArrayList<PoolV1Data> pools;
    LineAdapter lineOne = new LineAdapter();
    LineAdapter lineTwo = new LineAdapter();
    LineAdapter lineThree = new LineAdapter();
    LineAdapter lineFour = new LineAdapter();

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

        ArrayList<Double> valuesOne = new ArrayList<>();
        ArrayList<Double> valuesTwo = new ArrayList<>();
        ArrayList<Double> valuesThree = new ArrayList<>();
        ArrayList<Double> valuesFour = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<>();


        Calendar time = Calendar.getInstance();
        for (int i = 0; i < 2000; i++) {
            time.add(Calendar.MINUTE, -10);
            valuesOne.add(Math.random() * 1000);
            valuesTwo.add(Math.random() * 1500);
            valuesThree.add(Math.random() * 500);
            valuesFour.add(Math.random() * 500 + 1500);
            times.add(time.getTimeInMillis());
        }

        lineOne.setData(valuesOne, times);
        lineTwo.setData(valuesTwo, times);
        lineThree.setData(valuesThree, times);
        lineFour.setData(valuesFour, times);

        lineOne.setColor(ColorTable._8DC41F);
        lineTwo.setColor(ColorTable._F7B500);
        lineThree.setColor(ColorTable._39C0ED);
        lineFour.setColor(ColorTable._CD2626);

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
                ArrayList<LineAdapter> dataGroup = new ArrayList<>();
                dataGroup.add(lineOne);
                dataGroup.add(lineTwo);
                dataGroup.add(lineThree);
                dataGroup.add(lineFour);
                item.setData(pools.get(i).getName(), dataGroup);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return item;
        }
    };
}
