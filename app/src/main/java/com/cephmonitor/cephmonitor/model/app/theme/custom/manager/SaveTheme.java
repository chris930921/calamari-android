package com.cephmonitor.cephmonitor.model.app.theme.custom.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.cephmonitor.cephmonitor.model.app.theme.custom.template.Ocean;

/**
 * Created by User on 2015/7/15.
 */
public class SaveTheme {
    private static final String FILE_PATH = SaveTheme.class.getName();
    private SharedPreferences settings;

    public SaveTheme(Context context) {
        this.settings = context.getSharedPreferences(FILE_PATH, Context.MODE_MULTI_PROCESS);
    }

    public void changeOcean() {
        save("theme_name", Ocean.class.getName());
    }

    public String getTheme() {
        return get("theme_name", Ocean.class.getName());
    }

    protected void save(String key, String value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected String get(String key, String defaultValue) {
        return settings.getString(key, defaultValue);
    }
}
