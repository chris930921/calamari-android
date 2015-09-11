package com.cephmonitor.cephmonitor.model.database.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cephmonitor.cephmonitor.model.database.DatabaseParser;
import com.cephmonitor.cephmonitor.model.database.table.RecordedTable;
import com.resourcelibrary.model.log.ShowLog;

import java.util.ArrayList;

/**
 * Created by User on 2015/9/3.
 */
public class KeepLoadRecordedData extends DatabaseData {
    private final int updateOffset = 8;
    private int loadStart = 0;
    private int loadEnd = updateOffset;
    public ArrayList<RecordedData> recordGroup = new ArrayList<>();

    private void loadNextRange() {
        loadStart += updateOffset;
        loadEnd += updateOffset;
        ShowLog.d("更新讀取範圍 start:" + loadStart + "  end:" + loadEnd);
    }

    public void loadOldRecordGroup(SQLiteDatabase database) {
        ShowLog.d("暫存舊的紀錄 :" + recordGroup);
        ArrayList<RecordedData> tmpRecordGroup = recordGroup;
        load(database);
        ShowLog.d("讀取新紀錄舊的紀錄 :" + recordGroup);
        loadNextRange();
        tmpRecordGroup.addAll(recordGroup);
        recordGroup = tmpRecordGroup;
        ShowLog.d("放回舊紀錄 :" + recordGroup);
    }

    @Override
    public void boxing(Cursor cursor) {
        recordGroup = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            RecordedData record = new RecordedData();
            record.boxing(cursor);
            recordGroup.add(record);
        }
    }

    @Override
    public String selectScript() {
        String sql = "SELECT * FROM ? ORDER BY ? DESC LIMIT ? , ? ";
        Object[] paramGroup = {
                RecordedTable.TABLE_NAME,
                RecordedTable.FIELD_TRIGGERED,
                loadStart,
                loadEnd
        };
        return DatabaseParser.replaceSymbolToParam(sql, paramGroup);
    }
}