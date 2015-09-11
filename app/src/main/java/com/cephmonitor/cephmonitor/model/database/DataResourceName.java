package com.cephmonitor.cephmonitor.model.database;

/**
 * Created by User on 2015/9/2.
 */
public class DataResourceName {
    private String name;

    public DataResourceName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
