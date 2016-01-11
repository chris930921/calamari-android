package com.resourcelibrary.model.logic;

/**
 * Created by User on 5/26/2015.
 */
public class ByteUnit {
    public static final long EB = (long) Math.pow(1024, 6);
    public static final long PB = (long) Math.pow(1024, 5);
    public static final long TB = (long) Math.pow(1024, 4);
    public static final long GB = (long) Math.pow(1024, 3);
    public static final long MB = (long) Math.pow(1024, 2);
    public static final long KB = (long) Math.pow(1024, 1);
    public static final long B = 1;

    public static final String TEXT_EB = "EB";
    public static final String TEXT_PB = "PB";
    public static final String TEXT_TB = "TB";
    public static final String TEXT_GB = "GB";
    public static final String TEXT_MB = "MB";
    public static final String TEXT_KB = "KB";

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

        } else {
            result = value + "";

        }

        return result;
    }

    private static String combineResult(double count, String unit) {
        String result;
        if (count >= 100) {
            result = (int) count + "";
        } else if (count >= 10) {
            result = String.format("%.1f", count);
        } else {
            result = String.format("%.2f", count);
        }
        return result + " " + unit;
    }
}
