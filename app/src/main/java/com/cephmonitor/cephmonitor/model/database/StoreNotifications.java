package com.cephmonitor.cephmonitor.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.resourcelibrary.model.log.ShowLog;

import java.util.ArrayList;

/**
 * Created by User on 6/8/2015.
 */
public class StoreNotifications extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "CephMonitorSqlite.db";
    private static final String TABLE_NOTIFICATION = "notification";
    private static final String NOTIFICATION_ID = "id";
    private static final String NOTIFICATION_TITLE = "title";
    private static final String NOTIFICATION_CONTENT = "content";
    private static final String NOTIFICATION_CREATED_DATE = "created_date";
    private static final String NOTIFICATION_IS_SEEN = "is_seen";
    private static final String NOTIFICATION_STATUS = "status";

    public static final int FETCH_COUNT = 15;

    private Context context;
    private int nowCount;
    private int nowMaxId;
    private String nowMaxDateTime;

    protected int readOldDataStart;

    public StoreNotifications(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        getNowDataInfo();
        readOldDataStart = 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE " + TABLE_NOTIFICATION + " (" +
                        "  [" + NOTIFICATION_ID + "] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "  [" + NOTIFICATION_TITLE + "] TEXT NOT NULL, " +
                        "  [" + NOTIFICATION_CONTENT + "] TEXT NOT NULL, " +
                        "  [" + NOTIFICATION_CREATED_DATE + "] DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                        "  [" + NOTIFICATION_IS_SEEN + "] BOOL NOT NULL DEFAULT false, " +
                        "  [" + NOTIFICATION_STATUS + "] TEXT NOT NULL);";
        db.execSQL(sql);
    }

    public void removeDatabase() {
        context.deleteDatabase(DB_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }

    public long save(String title, String context, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues saveMap = new ContentValues();

        saveMap.put(NOTIFICATION_TITLE, title);
        saveMap.put(NOTIFICATION_CONTENT, context);
        saveMap.put(NOTIFICATION_STATUS, status);

        long insertId = db.insertOrThrow(TABLE_NOTIFICATION, null, saveMap);
        db.close();

        return insertId;
    }

    public ArrayList<NotificationRow> find() {
        ArrayList<NotificationRow> result = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *,datetime(" + NOTIFICATION_CREATED_DATE + ",'localtime') FROM " + TABLE_NOTIFICATION + " ORDER BY " + NOTIFICATION_CREATED_DATE + " DESC", null);
        while (cursor.moveToNext()) {
            NotificationRow item = new NotificationRow();
            item.id = cursor.getInt(0);
            item.title = cursor.getString(1);
            item.content = cursor.getString(2);
            item.isSeen = cursor.getInt(4) > 0;
            item.status = cursor.getString(5);
            item.setCreatedDate(cursor.getString(6));
            result.add(item);
        }
        cursor.close();
        return result;
    }

    public ArrayList<NotificationRow> findTopEight() {
        ArrayList<NotificationRow> result = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *,datetime(" + NOTIFICATION_CREATED_DATE + ",'localtime') FROM " + TABLE_NOTIFICATION + " ORDER BY " + NOTIFICATION_CREATED_DATE + " DESC , " + NOTIFICATION_ID + " DESC" + " LIMIT 8", null);
        while (cursor.moveToNext()) {
            NotificationRow item = new NotificationRow();
            item.id = cursor.getInt(0);
            item.title = cursor.getString(1);
            item.content = cursor.getString(2);
            item.isSeen = cursor.getInt(4) > 0;
            item.status = cursor.getString(5);
            item.setCreatedDate(cursor.getString(6));
            result.add(item);
        }
        cursor.close();
        return result;
    }

    public ArrayList<NotificationRow> findBeforeData() {
        ArrayList<NotificationRow> result = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT *,datetime(" + NOTIFICATION_CREATED_DATE + ",'localtime') " +
                " FROM " + TABLE_NOTIFICATION +
                " WHERE " + NOTIFICATION_CREATED_DATE + " <= '" + nowMaxDateTime + "'" +
                " AND " + NOTIFICATION_ID + " <= " + nowMaxId +
                " ORDER BY " + NOTIFICATION_CREATED_DATE + " DESC, " + NOTIFICATION_ID + " DESC" +
                " LIMIT " + readOldDataStart + " , " + FETCH_COUNT;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            NotificationRow item = new NotificationRow();
            item.id = cursor.getInt(0);
            item.title = cursor.getString(1);
            item.content = cursor.getString(2);
            item.isSeen = cursor.getInt(4) > 0;
            item.status = cursor.getString(5);
            item.setCreatedDate(cursor.getString(6));
            result.add(item);
        }
        cursor.close();
        readOldDataStart += StoreNotifications.FETCH_COUNT;

        return result;
    }

    public ArrayList<NotificationRow> updateNewData() {
        int minId = nowMaxId;
        getNowDataInfo();
        ArrayList<NotificationRow> result = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT *,datetime(" + NOTIFICATION_CREATED_DATE + ",'localtime') " +
                " FROM " + TABLE_NOTIFICATION +
//                " WHERE " + NOTIFICATION_CREATED_DATE + " > '" + nowMaxDateTime + "'" +
//                " AND " + NOTIFICATION_ID + " > " + nowMaxId +
                " WHERE " + NOTIFICATION_ID + " <= " + nowMaxId +
                " AND " + NOTIFICATION_ID + " > " + minId +
                " ORDER BY " + NOTIFICATION_CREATED_DATE + " DESC, " + NOTIFICATION_ID + " DESC";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            NotificationRow item = new NotificationRow();
            item.id = cursor.getInt(0);
            item.title = cursor.getString(1);
            item.content = cursor.getString(2);
            item.isSeen = cursor.getInt(4) > 0;
            item.status = cursor.getString(5);
            item.setCreatedDate(cursor.getString(6));
            result.add(item);
        }
        cursor.close();

        if (result.size() > 0) {
            readOldDataStart += result.size();
        }
        return result;
    }

    public boolean removeNotification(int index) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "delete from notification where id = " + index + ";";

        try {
            db.execSQL(sql);
            readOldDataStart -= 1;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public void getNowDataInfo() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) ,MAX(created_date) ,MAX(id) FROM notification ORDER BY created_date DESC, id DESC;", null);
        while (cursor.moveToNext()) {
            nowCount = cursor.getInt(0);
            nowMaxDateTime = cursor.getString(1);
            nowMaxId = cursor.getInt(2);
        }
        cursor.close();
    }

    public int getCount() {
        return nowCount;
    }
}
