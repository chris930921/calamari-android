package com.cephmonitor.cephmonitor.model.database.data;

import android.database.Cursor;

import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.database.DatabaseParser;
import com.cephmonitor.cephmonitor.model.database.table.RecordedTable;

/**
 * Created by User on 2015/9/1.
 */
public class FindPendingData extends DatabaseData {
    public int monitorType;
    public int level;
    public int monitorNumber;
    public String status = CephNotificationConstant.STATUS_PENDING;
    public boolean isExist;
    public int recordId;

    @Override
    public void boxing(Cursor cursor) {
        isExist = cursor.getCount() != 0;

        if (isExist) {
            RecordedData recorded = new RecordedData();
            recorded.boxing(cursor);
            recordId = recorded.id;
        }
    }

    @Override
    public String selectScript() {
        String sql = "SELECT * FROM ? WHERE ? = ? AND ? = ? AND ? = ? AND ? = ? ";
        Object[] paramGroup = {
                RecordedTable.TABLE_NAME,
                RecordedTable.FIELD_MONITOR_TYPE, monitorType,
                RecordedTable.FIELD_LEVEL, level,
                RecordedTable.FIELD_MONITOR_NUMBER, monitorNumber,
                RecordedTable.FIELD_STATUS, status,
        };
        return DatabaseParser.replaceSymbolToParam(sql, paramGroup);
    }
}