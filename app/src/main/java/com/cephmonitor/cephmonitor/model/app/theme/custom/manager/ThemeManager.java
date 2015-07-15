package com.cephmonitor.cephmonitor.model.app.theme.custom.manager;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.app.theme.custom.template.Ocean;

import java.util.HashMap;

/**
 * Created by User on 2015/7/14.
 */
public class ThemeManager {
    private static final HashMap<String, DesignSpec> themeGroup;
    private static final String defaultThemeName = Ocean.class.getName();

    static {
        themeGroup = new HashMap<>();
    }

    public static DesignSpec getStyle(Context context) {
        SaveTheme themeInfo = new SaveTheme(context);
        String themeName = themeInfo.getTheme();
        DesignSpec theme;

        if (themeGroup.get(themeName) != null) {
            return themeGroup.get(themeName);
        }


        if (themeName.equals(Ocean.class.getName())) {
            themeGroup.put(themeName, new Ocean(context));
        } else {
            themeGroup.put(defaultThemeName, new Ocean(context));
            themeName = defaultThemeName;
        }

        return themeGroup.get(themeName);
    }
}
