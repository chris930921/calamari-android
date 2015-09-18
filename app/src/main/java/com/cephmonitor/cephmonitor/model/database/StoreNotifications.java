package com.cephmonitor.cephmonitor.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cephmonitor.cephmonitor.model.database.table.RecordedTable;

/**
 * Created by User on 6/8/2015.
 */
public class StoreNotifications extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "CephMonitorSqlite_v3.db";

    public StoreNotifications(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new RecordedTable().onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }
}
