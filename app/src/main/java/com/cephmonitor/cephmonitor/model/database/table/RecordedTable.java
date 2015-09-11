package com.cephmonitor.cephmonitor.model.database.table;

import android.database.sqlite.SQLiteDatabase;

import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.database.DataResourceName;

/**
 * Created by User on 2015/9/1.
 */
public class RecordedTable extends DatabaseTable {
    // 資料表名稱。
    public static final DataResourceName TABLE_NAME = new DataResourceName("recorded");
    // SPEC 上有的欄位。
    public static final DataResourceName FIELD_ID = new DataResourceName("id");
    public static final DataResourceName FIELD_CODE = new DataResourceName("code");
    public static final DataResourceName FIELD_COUNT = new DataResourceName("count");
    public static final DataResourceName FIELD_LEVEL = new DataResourceName("level");
    public static final DataResourceName FIELD_STATUS = new DataResourceName("status");
    public static final DataResourceName FIELD_TRIGGERED = new DataResourceName("triggered");
    public static final DataResourceName FIELD_RESOLVED = new DataResourceName("resolved");
    // 實現功能額外的欄位。
    public static final DataResourceName FIELD_ORIGINAL_COUNT = new DataResourceName("origin_count");
    public static final DataResourceName FIELD_PREVIOUS_COUNT = new DataResourceName("previous_count");
    public static final DataResourceName FIELD_LAST_MESSAGE_ID = new DataResourceName("last_message_id");
    public static final DataResourceName FIELD_LAST_TITLE_ID = new DataResourceName("last_title_id");
    public static final DataResourceName FIELD_LAST_ERROR_MESSAGE_ID = new DataResourceName("last_error_message_id");
    public static final DataResourceName FIELD_LAST_ERROR_TITLE_ID = new DataResourceName("last_error_title_id");
    public static final DataResourceName FIELD_WARNING_TYPE = new DataResourceName("waring_type");
    public static final DataResourceName FIELD_MONITOR_TYPE = new DataResourceName("monitor_type");
    public static final DataResourceName FIELD_MONITOR_NUMBER = new DataResourceName("monitor_number");
    public static final DataResourceName FIELD_PARAMS_TITLE = new DataResourceName("params_title");
    public static final DataResourceName FIELD_PARAMS_MESSAGE = new DataResourceName("params_message");
    public static final DataResourceName FIELD_OTHER_PARAMS = new DataResourceName("other_params");

    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        "  [" + FIELD_ID + "] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                        "  ,[" + FIELD_CODE + "] TEXT NOT NULL ON CONFLICT REPLACE DEFAULT ''" +
                        "  ,[" + FIELD_COUNT + "] FLOAT NOT NULL ON CONFLICT REPLACE DEFAULT 0" +
                        "  ,[" + FIELD_LEVEL + "] INT NOT NULL ON CONFLICT REPLACE DEFAULT " + CephNotificationConstant.WARING_LEVEL_INFO +
                        "  ,[" + FIELD_STATUS + "] TEXT NOT NULL ON CONFLICT REPLACE DEFAULT '" + CephNotificationConstant.STATUS_PENDING + "'" +
                        "  ,[" + FIELD_TRIGGERED + "] DATETIME NOT NULL ON CONFLICT REPLACE DEFAULT CURRENT_TIMESTAMP" +
                        "  ,[" + FIELD_RESOLVED + "] DATETIME " +

                        "  ,[" + FIELD_ORIGINAL_COUNT + "] FLOAT NOT NULL ON CONFLICT REPLACE DEFAULT 0" +
                        "  ,[" + FIELD_PREVIOUS_COUNT + "] FLOAT NOT NULL ON CONFLICT REPLACE DEFAULT 0" +
                        "  ,[" + FIELD_LAST_MESSAGE_ID + "] INT NOT NULL ON CONFLICT REPLACE DEFAULT 0" +
                        "  ,[" + FIELD_LAST_TITLE_ID + "] INT NOT NULL ON CONFLICT REPLACE DEFAULT 0" +
                        "  ,[" + FIELD_LAST_ERROR_MESSAGE_ID + "] INT NOT NULL ON CONFLICT REPLACE DEFAULT 0" +
                        "  ,[" + FIELD_LAST_ERROR_TITLE_ID + "] INT NOT NULL ON CONFLICT REPLACE DEFAULT 0" +
                        "  ,[" + FIELD_WARNING_TYPE + "] INT NOT NULL ON CONFLICT REPLACE DEFAULT " + CephNotificationConstant.WARNING_TYPE_WARNING +
                        "  ,[" + FIELD_MONITOR_TYPE + "] INT NOT NULL ON CONFLICT REPLACE DEFAULT 0" +
                        "  ,[" + FIELD_MONITOR_NUMBER + "] INT NOT NULL ON CONFLICT REPLACE DEFAULT 0" +
                        "  ,[" + FIELD_PARAMS_TITLE + "] TEXT NOT NULL ON CONFLICT REPLACE DEFAULT ''" +
                        "  ,[" + FIELD_PARAMS_MESSAGE + "] TEXT NOT NULL ON CONFLICT REPLACE DEFAULT ''" +
                        "  ,[" + FIELD_OTHER_PARAMS + "] TEXT NOT NULL ON CONFLICT REPLACE DEFAULT ''" +

                        " ); ";
        db.execSQL(sql);
    }
}
