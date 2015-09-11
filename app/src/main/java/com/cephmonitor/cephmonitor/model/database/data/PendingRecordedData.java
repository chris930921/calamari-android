package com.cephmonitor.cephmonitor.model.database.data;

import android.database.Cursor;

import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.database.DatabaseParser;
import com.cephmonitor.cephmonitor.model.database.table.RecordedTable;

import java.util.ArrayList;

/**
 * Created by User on 2015/9/1.
 */
public class PendingRecordedData extends DatabaseData {
    public int limitCount = 8;
    public ArrayList<RecordedData> recordGroup;

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
        String sql = "SELECT * FROM ? WHERE ? = ? ORDER BY ? DESC LIMIT ? ";
        Object[] paramGroup = {
                RecordedTable.TABLE_NAME,
                RecordedTable.FIELD_STATUS,
                CephNotificationConstant.STATUS_PENDING,
                RecordedTable.FIELD_TRIGGERED,
                limitCount
        };
        return DatabaseParser.replaceSymbolToParam(sql, paramGroup);
    }
}