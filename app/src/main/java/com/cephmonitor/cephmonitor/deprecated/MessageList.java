package com.cephmonitor.cephmonitor.deprecated;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by User on 2015/6/2.
 */
public class MessageList {
    private static final String FILE_PATH = "service_messages";
    private static final String EMPTY_MESSAGE = "empty message.";
    private static final String MESSAGE_SIZE = "messages_size";
    private static final int MESSAGE_MAX = 8;
    private SharedPreferences settings;
    private ArrayList<String> messages;

    public MessageList(Context context) {
        this.settings = context.getSharedPreferences(FILE_PATH, Context.MODE_MULTI_PROCESS);
        loadArray();
    }

    public int loadMessageSize() {
        return settings.getInt(MESSAGE_SIZE, 0);
    }

    public void loadArray() {
        messages = new ArrayList<>();
        int size = loadMessageSize();
        for (int i = 0; i < size; i++) {
            String index = String.valueOf(i);
            String value = settings.getString(index, EMPTY_MESSAGE);
            messages.add(value);
        }
    }

    public void addMessage(String message) {
        if (messages.size() == MESSAGE_MAX) {
            messages.remove(0);
        }
        messages.add(message);
        saveArray();
    }

    public void saveArray() {
        SharedPreferences.Editor editor = settings.edit();

        for (int i = 0; i < messages.size(); i++) {
            String index = String.valueOf(i);
            String value = messages.get(i);
            editor.putString(index, value);
        }
        editor.putInt(MESSAGE_SIZE, messages.size());
        editor.commit();
    }

    public ArrayList<String> getArray() {
        return messages;
    }
}
