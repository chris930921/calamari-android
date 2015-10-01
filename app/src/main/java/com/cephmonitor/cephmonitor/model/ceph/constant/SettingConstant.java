package com.cephmonitor.cephmonitor.model.ceph.constant;

import com.cephmonitor.cephmonitor.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by chriske on 2015/9/24.
 */
public class SettingConstant {
    public static final int LANGUAGE_ENGLISH = 0;
    public static final int LANGUAGE_CHINESE = 1;
    public static final int LANGUAGE_JAPANESE = 2;

    private static final HashMap<Integer, Integer> languageIdMapResource = new HashMap<>();
    private static final HashMap<Integer, Integer> languageIdMapFlag = new HashMap<>();

    public static final int DATE_FORMATS_YEAR_MONTH_DAY = 0;
    public static final int DATE_FORMATS_MONTH_DAY_YEAR = 1;
    public static final int DATE_FORMATS_DAY_MONTH_YEAR = 2;
    private static final HashMap<Integer, Integer> lateFormatsIdMapResource = new HashMap<>();

    private static final HashMap<Integer, Locale> localeMap = new HashMap<>();
    private static final HashMap<Integer, SimpleDateFormat> dateFormatMap = new HashMap<>();

    static {
        languageIdMapResource.put(LANGUAGE_ENGLISH, R.string.settings_language_dialog_option_english);
        languageIdMapResource.put(LANGUAGE_CHINESE, R.string.settings_language_dialog_option_chinese);
        languageIdMapResource.put(LANGUAGE_JAPANESE, R.string.settings_language_dialog_option_japanese);

        languageIdMapFlag.put(LANGUAGE_ENGLISH, R.drawable.icon041);
        languageIdMapFlag.put(LANGUAGE_CHINESE, R.drawable.icon040);
        languageIdMapFlag.put(LANGUAGE_JAPANESE, R.drawable.icon039);

        lateFormatsIdMapResource.put(DATE_FORMATS_YEAR_MONTH_DAY, R.string.settings_date_format_dialog_year_month_day);
        lateFormatsIdMapResource.put(DATE_FORMATS_MONTH_DAY_YEAR, R.string.settings_date_format_dialog_month_day_year);
        lateFormatsIdMapResource.put(DATE_FORMATS_DAY_MONTH_YEAR, R.string.settings_date_format_dialog_day_month_year);

        localeMap.put(LANGUAGE_ENGLISH, new Locale("en", ""));
        localeMap.put(LANGUAGE_CHINESE, new Locale("zh", ""));
        localeMap.put(LANGUAGE_JAPANESE, new Locale("ja", ""));

        dateFormatMap.put(DATE_FORMATS_YEAR_MONTH_DAY, new SimpleDateFormat("yyyy/MM/dd HH:mm"));
        dateFormatMap.put(DATE_FORMATS_MONTH_DAY_YEAR, new SimpleDateFormat("MM/dd/yyyy HH:mm"));
        dateFormatMap.put(DATE_FORMATS_DAY_MONTH_YEAR, new SimpleDateFormat("dd/MM/yyyy HH:mm"));
    }

    public static int getLanguageResource(int id) {
        return languageIdMapResource.get(id);
    }

    public static int getDateFormatsResource(int id) {
        return lateFormatsIdMapResource.get(id);
    }

    public static int getLanguageFlag(int id) {
        return languageIdMapFlag.get(id);
    }

    public static Locale getLocale(int id) {
        return localeMap.get(id);
    }

    public static SimpleDateFormat getSimpleFormat(int id) {
        return dateFormatMap.get(id);
    }
}
