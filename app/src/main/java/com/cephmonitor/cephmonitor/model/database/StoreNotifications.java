package com.cephmonitor.cephmonitor.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cephmonitor.cephmonitor.model.database.table.RecordedTable;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;

/**
 * Created by User on 6/8/2015.
 */
public class StoreNotifications extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "CephMonitorSqlite_v4_";
    public static final String EXTENSION = ".db";

    public StoreNotifications(Context context) {
        super(context, DB_NAME + new LoginParams(context).getHost() + EXTENSION, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new RecordedTable().onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }
}
