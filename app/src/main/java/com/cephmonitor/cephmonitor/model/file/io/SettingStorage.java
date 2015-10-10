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

    public void setAlertTriggerOsdWarning(long value) {
        set("AlertTriggerOsdWarning", value);
    }

    public void setAlertTriggerOsdError(long value) {
        set("AlertTriggerOsdError", value);
    }

    public void setAlertTriggerMonWarning(long value) {
        set("AlertTriggerMonWarning", value);
    }

    public void setAlertTriggerMonError(long value) {
        set("AlertTriggerMonError", value);
    }

    public void setAlertTriggerPgWarning(float value) {
        set("AlertTriggerPgWarning", value);
    }

    public void setAlertTriggerPgError(float value) {
        set("AlertTriggerPgError", value);
    }

    public void setAlertTriggerUsageWarning(float value) {
        set("AlertTriggerUsageWarning", value);
    }

    public void setAlertTriggerUsageError(float value) {
        set("AlertTriggerUsageError", value);
    }

    public void set(String key, int value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void set(String key, long value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void set(String key, float value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public int getLanguage() {
        return settings.getInt("Language", 0);
    }

    public int getDateFormats() {
        return settings.getInt("DateFormats", 0);
    }

    public int getLanguageResource() {
        return SettingConstant.getLanguageResource(getLanguage());
    }

    public int getDateFormatsResource() {
        return SettingConstant.getDateFormatsResource(getDateFormats());
    }

    public long getAlertTriggerOsdWarning() {
        return settings.getLong("AlertTriggerOsdWarning", 1);
    }

    public long getAlertTriggerOsdError() {
        return settings.getLong("AlertTriggerOsdError", 1);
    }

    public long getAlertTriggerMonWarning() {
        return settings.getLong("AlertTriggerMonWarning", 1);
    }

    public long getAlertTriggerMonError() {
        return settings.getLong("AlertTriggerMonError", 1);
    }

    public float getAlertTriggerPgWarning() {
        return settings.getFloat("AlertTriggerPgWarning", 0.2F);
    }

    public float getAlertTriggerPgError() {
        return settings.getFloat("AlertTriggerPgError", 0.2F);
    }

    public float getAlertTriggerUsageWarning() {
        return settings.getFloat("AlertTriggerUsageWarning", 0.7F);
    }

    public float getAlertTriggerUsageError() {
        return settings.getFloat("AlertTriggerUsageError", 0.85F);
    }
}
