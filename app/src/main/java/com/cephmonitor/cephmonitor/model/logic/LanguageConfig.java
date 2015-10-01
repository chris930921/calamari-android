package com.cephmonitor.cephmonitor.model.logic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.cephmonitor.cephmonitor.model.ceph.constant.SettingConstant;

import java.util.Locale;

/**
 * Created by User on 1/26/2015.
 */
public class LanguageConfig {
    private Activity activity;
    private static final String FILE_PATH = "select_language_file";
    private static final String LANGUAGE_ID = "language_id";
    public SharedPreferences setting;

    public LanguageConfig(Activity activity) {
        this.activity = activity;
        setting = activity.getSharedPreferences(FILE_PATH, Context.MODE_MULTI_PROCESS);
    }

    private void setLanguage(int id) {
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt(LANGUAGE_ID, id);
        editor.commit();
        Locale locale = SettingConstant.getLocale(id);
        setLocale(locale);
    }

    public void setLocale(int id){
        Locale locale = SettingConstant.getLocale(id);
        setLocale(locale);
    }

    private void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getResources().updateConfiguration(config, activity.getResources().getDisplayMetrics());
    }

    public void loadLanguage() {
        int id = setting.getInt(LANGUAGE_ID, SettingConstant.LANGUAGE_ENGLISH);
        Locale locale = SettingConstant.getLocale(id);
        setLocale(locale);
    }
}