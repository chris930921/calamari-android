package com.cephmonitor.cephmonitor.deprecated;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.LineAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 2015/7/1.
 */
public class TestLineRandomAdapter extends ArrayList<LineAdapter> {
    ArrayList<LineAdapter> lines;

    public TestLineRandomAdapter() {
        lines = new ArrayList<>();

        ArrayList<Long> times = new ArrayList<>();
        ArrayList<Double> valuesOne = new ArrayList<>();
        ArrayList<Double> valuesTwo = new ArrayList<>();
        ArrayList<Double> valuesThree = new ArrayList<>();
        ArrayList<Double> valuesFour = new ArrayList<>();

        Calendar time = Calendar.getInstance();
        for (int i = 0; i < 2000; i++) {
            time.add(Calendar.MINUTE, -10);
            valuesOne.add(Math.random() * 1000);
            valuesTwo.add(Math.random() * 1500);
            valuesThree.add(Math.random() * 500);
            valuesFour.add(Math.random() * 500 + 1500);
            times.add(time.getTimeInMillis());
        }

        LineAdapter lineOne = new LineAdapter();
        LineAdapter lineTwo = new LineAdapter();
        LineAdapter lineThree = new LineAdapter();
        LineAdapter lineFour = new LineAdapter();

        lineOne.setData(valuesOne, times);
        lineTwo.setData(valuesTwo, times);
        lineThree.setData(valuesThree, times);
        lineFour.setData(valuesFour, times);

        lineOne.setColor(ColorTable._8DC41F);
        lineTwo.setColor(ColorTable._F7B500);
        lineThree.setColor(ColorTable._39C0ED);
        lineFour.setColor(ColorTable._CD2626);

        add(lineOne);
        add(lineTwo);
        add(lineThree);
        add(lineFour);
    }
}
