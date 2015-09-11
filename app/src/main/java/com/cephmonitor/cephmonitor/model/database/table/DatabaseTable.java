package com.cephmonitor.cephmonitor.model.database.table;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 2015/9/1.
 */
public abstract class DatabaseTable {

    public abstract void onCreate(SQLiteDatabase db);
}
