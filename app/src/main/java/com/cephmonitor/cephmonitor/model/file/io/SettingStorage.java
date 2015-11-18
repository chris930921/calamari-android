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

    public void setNotifications(boolean value) {
        set("Notifications", value);
    }

    public void setAutoDelete(boolean value) {
        set("AutoDelete", value);
    }

    public void setAlertTriggerOsdWarning(final long value) {
        set("AlertTriggerOsdWarning", value);
    }

    public void setAlertTriggerOsdError(long value) {
        set("AlertTriggerOsdError", value);
    }

    public void setAlertTriggerOsdTotal(long value) {
        set("AlertTriggerOsdTotal", value);
    }

    public void setAlertTriggerMonWarning(long value) {
        set("AlertTriggerMonWarning", value);
    }

    public void setAlertTriggerMonError(long value) {
        set("AlertTriggerMonError", value);
    }

    public void setAlertTriggerMonTotal(long value) {
        set("AlertTriggerMonTotal", value);
    }

    public void setAlertTriggerPgWarning(float value) {
        set("AlertTriggerPgWarning", value);
    }

    public void setAlertTriggerPgError(float value) {
        set("AlertTriggerPgError", value);
    }

    public void setAlertTriggerPgTotal(long value) {
        set("AlertTriggerPgTotal", value);
    }

    public void setAlertTriggerUsageWarning(float value) {
        set("AlertTriggerUsageWarning", value);
    }

    public void setAlertTriggerUsageError(float value) {
        set("AlertTriggerUsageError", value);
    }

    public void setAlertTriggerUsageTotal(long value) {
        set("AlertTriggerUsageTotal", value);
    }

    public void setTimePeriodNormal(long value) {
        set("TimePeriodNormal", value);
    }

    public void setTimePeriodAbnormal(long value) {
        set("TimePeriodAbnormal", value);
    }

    public void setTimePeriodServerAbnormal(long value) {
        set("TimePeriodServerAbnormal", value);
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

    public void set(String key, boolean value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
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

    public long getAlertTriggerOsdTotal() {
        return settings.getLong("AlertTriggerOsdTotal", 0L);
    }

    public long getAlertTriggerMonWarning() {
        return settings.getLong("AlertTriggerMonWarning", 1);
    }

    public long getAlertTriggerMonError() {
        return settings.getLong("AlertTriggerMonError", 1);
    }

    public long getAlertTriggerMonTotal() {
        return settings.getLong("AlertTriggerMonTotal", 0L);
    }

    public float getAlertTriggerPgWarning() {
        return settings.getFloat("AlertTriggerPgWarning", 0.2F);
    }

    public float getAlertTriggerPgError() {
        return settings.getFloat("AlertTriggerPgError", 0.2F);
    }

    public long getAlertTriggerPgTotal() {
        return settings.getLong("AlertTriggerPgTotal", 0L);
    }

    public float getAlertTriggerUsageWarning() {
        return settings.getFloat("AlertTriggerUsageWarning", 0.7F);
    }

    public float getAlertTriggerUsageError() {
        return settings.getFloat("AlertTriggerUsageError", 0.85F);
    }

    public long getAlertTriggerUsageTotal() {
        return settings.getLong("AlertTriggerUsageTotal", 0L);
    }

    public boolean getNotifications() {
        return settings.getBoolean("Notifications", true);
    }

    public boolean getAutoDelete() {
        return settings.getBoolean("AutoDelete", false);
    }

    public long getTimePeriodNormal() {
        return settings.getLong("TimePeriodNormal", 30);
    }

    public long getTimerPeriodAbnormal() {
        return settings.getLong("TimePeriodAbnormal", 60 * 2);
    }

    public long getTimerPeriodServerAbnormal() {
        return settings.getLong("TimePeriodServerAbnormal", 60 * 60);
    }
}
