package com.resourcelibrary.model.logic;

import java.util.ArrayList;

/**
 * Created by User on 2/2/2015.
 */
public class CollectionString {
    public static String toConnect(String symbol, ArrayList<String> list) {
        StringBuilder text = new StringBuilder();
        int size = list.size();
        symbol = (symbol == null) ? "" : symbol;

        if (size == 0) return "";

        text.append(list.get(0));
        for (int i = 1; i < size; i++) {
            text.append(symbol);
            text.append(list.get(i));
        }
        return text.toString();
    }
}
