package com.cephmonitor.cephmonitor.model.file.io;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 2015/6/2.
 */
public class LoginAutoCompleteStorage {
    public final static String HOST = "HOST";
    public final static String PORT = "PORT";
    public final static String ACCOUNT = "ACCOUNT";

    private Context context;

    public LoginAutoCompleteStorage(Context context) {
        this.context = context;
    }

    public void addKeyword(String type, String key) {
        SharedPreferences settings = context.getSharedPreferences(LoginAutoCompleteStorage.class.getName(), Context.MODE_MULTI_PROCESS);
        Set<String> keywordGroup = settings.getStringSet(type, new HashSet<String>());
        int count = keywordGroup.size();
        keywordGroup.add(key);
        if (keywordGroup.size() > count) {
            setKeywordGroup(type, keywordGroup);
        }
    }

    public void setKeywordGroup(String type, Set<String> keywordGroup) {
        SharedPreferences settings = context.getSharedPreferences(LoginAutoCompleteStorage.class.getName(), Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(type);
        editor.apply();
        editor.putStringSet(type, keywordGroup);
        editor.apply();
    }

    public String[] getKeywordGroup(String type) {
        SharedPreferences settings = context.getSharedPreferences(LoginAutoCompleteStorage.class.getName(), Context.MODE_MULTI_PROCESS);
        Set<String> keywordGroup = settings.getStringSet(type, new HashSet<String>());
        return keywordGroup.toArray(new String[keywordGroup.size()]);
    }
}
