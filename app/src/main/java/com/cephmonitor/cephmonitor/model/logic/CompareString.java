package com.cephmonitor.cephmonitor.model.logic;

/**
 * Created by User on 2015/6/2.
 */
public class CompareString {

    public static boolean notEqualInt(String status, int compareValue) {
        try {
            return compareValue != Integer.parseInt(status);
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static boolean notEqualFloat(String status, float compareValue) {
        try {
            return compareValue != Float.parseFloat(status);
        } catch (NumberFormatException e) {
            return true;
        }
    }

}
