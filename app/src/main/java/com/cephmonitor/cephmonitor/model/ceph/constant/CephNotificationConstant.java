package com.cephmonitor.cephmonitor.model.ceph.constant;

import android.content.Context;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;

import java.util.HashMap;

/**
 * Created by User on 2015/9/1.
 */
public class CephNotificationConstant {
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_RESOLVED = "Resolved";

    public static final int WARING_LEVEL_INFO = 4;
    public static final int WARING_LEVEL_WARING = 3;
    public static final int WARING_LEVEL_ERROR = 2;
    public static final int WARING_LEVEL_CRITICAL = 1;

    public static final int WARNING_TYPE_WARNING = 0;
    public static final int WARNING_TYPE_ERROR = 1;

    public static class StatusConstant {
        private HashMap<Integer, Integer> statusColorGroup;
        private HashMap<String, Integer> statusIconGroup;
        private HashMap<String, Integer> statusTextColorGroup;

        public StatusConstant(Context context) {
            statusColorGroup = new HashMap<>();
            statusIconGroup = new HashMap<>();
            statusTextColorGroup = new HashMap<>();
            DesignSpec designSpec = ThemeManager.getStyle(context);

            statusColorGroup.put(CephNotificationConstant.WARING_LEVEL_ERROR, designSpec.getAccentColors().getError());
            statusColorGroup.put(CephNotificationConstant.WARING_LEVEL_WARING, designSpec.getAccentColors().getWarning());
            statusIconGroup.put(CephNotificationConstant.STATUS_RESOLVED, R.drawable.icon035);
            statusIconGroup.put(CephNotificationConstant.STATUS_PENDING, R.drawable.icon036);
            statusTextColorGroup.put(CephNotificationConstant.STATUS_PENDING, designSpec.getAccentColors().getError());
            statusTextColorGroup.put(CephNotificationConstant.STATUS_RESOLVED, designSpec.getAccentColors().getNormal());
        }

        public HashMap<Integer, Integer> getStatusColorGroup() {
            return statusColorGroup;
        }

        public HashMap<String, Integer> getStatusIconGroup() {
            return statusIconGroup;
        }

        public HashMap<String, Integer> getStatusTextColorGroup() {
            return statusTextColorGroup;
        }
    }
}
