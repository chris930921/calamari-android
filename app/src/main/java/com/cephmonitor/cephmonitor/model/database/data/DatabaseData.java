package com.cephmonitor.cephmonitor.model.database.data;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 2015/9/1.
 */
public abstract class DatabaseData {

    public void load(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery(selectScript(), null);
        boxing(cursor);
        cursor.close();
    }

    public boolean remove(SQLiteDatabase database) {
        String sql = deleteScript();

        if (sql == null) {
            return false;
        }

        try {
            database.execSQL(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean save(SQLiteDatabase database) {
        String sql = updateScript();

        if (sql == null) {
            return false;
        }

        try {
            database.execSQL(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean create(SQLiteDatabase database) {
        String sql = insertScript();

        if (sql == null) {
            return false;
        }

        try {
            database.execSQL(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public abstract void boxing(Cursor cursor);

    public abstract String selectScript();

    public String updateScript() {
        return null;
    }

    public String insertScript() {
        return null;
    }

    public String deleteScript() {
        return null;
    }
}
