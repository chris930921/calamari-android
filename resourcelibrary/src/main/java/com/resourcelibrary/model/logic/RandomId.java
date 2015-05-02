package com.resourcelibrary.model.logic;

/**
 * Created by User on 1/19/2015.
 */
public class RandomId {
    public static int get() {
        return (int) (Math.random() * 1000000);
    }
}
