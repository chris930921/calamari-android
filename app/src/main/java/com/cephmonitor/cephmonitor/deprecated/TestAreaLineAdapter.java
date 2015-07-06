package com.cephmonitor.cephmonitor.deprecated;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.AreaLineAdapter;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.LineAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 2015/7/1.
 */
public class TestAreaLineAdapter extends ArrayList<AreaLineAdapter> {
    ArrayList<LineAdapter> lines;

    public TestAreaLineAdapter() {
        lines = new ArrayList<>();

        ArrayList<Long> times = new ArrayList<>();
        ArrayList<Double> valuesOne = new ArrayList<>();
        ArrayList<Double> valuesTwo = new ArrayList<>();
        ArrayList<Double> valuesThree = new ArrayList<>();
        ArrayList<Double> valuesFour = new ArrayList<>();

        Calendar time = Calendar.getInstance();
        for (int i = 0; i < 2000; i++) {
            time.add(Calendar.MINUTE, -10);
            valuesOne.add(100.0);
            valuesTwo.add(80.0);
            valuesThree.add(60.0);
            valuesFour.add(40.0);
            times.add(time.getTimeInMillis());
        }

        AreaLineAdapter lineOne = new AreaLineAdapter();
        AreaLineAdapter lineTwo = new AreaLineAdapter();
        AreaLineAdapter lineThree = new AreaLineAdapter();
        AreaLineAdapter lineFour = new AreaLineAdapter();

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
