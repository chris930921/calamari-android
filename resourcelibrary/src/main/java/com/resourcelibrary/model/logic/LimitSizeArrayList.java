package com.resourcelibrary.model.logic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by User on 2015/6/2.
 */
public class LimitSizeArrayList<T> implements Iterable<T> {
    private ArrayList<T> queue;
    private int limitSize;

    public LimitSizeArrayList(int size) {
        limitSize = size;
        queue = new ArrayList<T>();
    }

    public void push(T data) {
        if (queue.size() == limitSize) {
            queue.remove(0);
        }
        queue.add(data);
    }

    public int size() {
        return queue.size();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = -1;

            @Override
            public boolean hasNext() {
                return index == limitSize - 1;
            }

            @Override
            public T next() {
                index++;
                return queue.get(index);
            }

            @Override
            public void remove() {
            }
        };
    }
}
