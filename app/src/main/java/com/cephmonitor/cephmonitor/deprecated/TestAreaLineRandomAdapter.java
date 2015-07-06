package com.cephmonitor.cephmonitor.deprecated;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.AreaLineAdapter;
import com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line.adapter.LineAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 2015/7/1.
 */
public class TestAreaLineRandomAdapter extends ArrayList<AreaLineAdapter> {
    ArrayList<LineAdapter> lines;

    public TestAreaLineRandomAdapter() {
        lines = new ArrayList<>();

        ArrayList<Long> times = new ArrayList<>();
        ArrayList<Double> valuesOne = new ArrayList<>();
        ArrayList<Double> valuesTwo = new ArrayList<>();
        ArrayList<Double> valuesThree = new ArrayList<>();
        ArrayList<Double> valuesFour = new ArrayList<>();

        Calendar time = Calendar.getInstance();
        for (int i = 0; i < 2000; i++) {
            time.add(Calendar.MINUTE, -10);
            double firstValue = Math.random() * 100 + 500;
            double secondValue = Math.random() * 100 + +firstValue;
            double thirdValue = Math.random() * 100 + +secondValue;
            double forthValue = Math.random() * 100 + +thirdValue;
            valuesOne.add(firstValue);
            valuesTwo.add(secondValue);
            valuesThree.add(thirdValue);
            valuesFour.add(forthValue);
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

        add(lineFour);
        add(lineThree);
        add(lineTwo);
        add(lineOne);
    }
}
