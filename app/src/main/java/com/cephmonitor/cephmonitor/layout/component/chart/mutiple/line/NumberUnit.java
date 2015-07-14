package com.cephmonitor.cephmonitor.layout.component.chart.mutiple.line;

/**
 * Created by User on 5/26/2015.
 */
public class NumberUnit {
    public static final long EB = (long) Math.pow(1000, 6);
    public static final long PB = (long) Math.pow(1000, 5);
    public static final long TB = (long) Math.pow(1000, 4);
    public static final long GB = (long) Math.pow(1000, 3);
    public static final long MB = (long) Math.pow(1000, 2);
    public static final long KB = (long) Math.pow(1000, 1);
    public static final long B = 1;

    public static final String TEXT_EB = "E";
    public static final String TEXT_PB = "P";
    public static final String TEXT_TB = "T";
    public static final String TEXT_GB = "G";
    public static final String TEXT_MB = "M";
    public static final String TEXT_KB = "K";

    public static String change(double value) {
        String result;

        if (value >= EB) {
            double count = value / EB;
            result = combineResult(count, TEXT_EB);

        } else if (value >= PB) {
            double count = value / PB;
            result = combineResult(count, TEXT_PB);

        } else if (value >= TB) {
            double count = value / TB;
            result = combineResult(count, TEXT_TB);

        } else if (value >= GB) {
            double count = value / GB;
            result = combineResult(count, TEXT_GB);

        } else if (value >= MB) {
            double count = value / MB;
            result = combineResult(count, TEXT_MB);

        } else if (value >= KB) {
            double count = value / KB;
            result = combineResult(count, TEXT_KB);

        } else if (value <= 20) {
            result = String.format("%.2f", value);

        } else {
            result = String.valueOf((int) value);
        }

        return result;
    }

    private static String combineResult(double count, String unit) {
        String result;
        result = (int) count + "";
        return result + " " + unit;
    }
}
