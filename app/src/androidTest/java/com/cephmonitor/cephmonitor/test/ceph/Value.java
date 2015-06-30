package com.cephmonitor.cephmonitor.test.ceph;

/**
 * Created by User on 2015/6/29.
 */
public class Value<T> {
    private T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}