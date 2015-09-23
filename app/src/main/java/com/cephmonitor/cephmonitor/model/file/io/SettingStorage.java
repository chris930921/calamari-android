package com.cephmonitor.cephmonitor.model.file.io;

import android.content.Context;
import android.content.SharedPreferences;

import com.cephmonitor.cephmonitor.model.ceph.constant.SettingConstant;

/**
 * Created by User on 2015/6/2.
 */
public class SettingStorage {
    private Context context;
    private SharedPreferences settings;

    public SettingStorage(Context context) {
        this.context = context;
        settings = context.getSharedPreferences(SettingStorage.class.getName(), Context.MODE_MULTI_PROCESS);
    }

    public void setLanguage(int languageId) {
        set("Language", languageId);
    }

    public void setDateFormats(int dateFormatsId) {
        set("DateFormats", dateFormatsId);
    }

    public void set(String key, int value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getLanguage() {
        return settings.getInt("Language", 0);
    }

    public int getDateFormats() {
        return settings.getInt("DateFormats", 0);
    }

    public int getLanguageResource(){
        return SettingConstant.getLanguageResource(getLanguage());
    }

    public int getDateFormatsResource(){
        return SettingConstant.getDateFormatsResource(getDateFormats());
    }
}
