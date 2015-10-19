package com.cephmonitor.cephmonitor.model.database.data;

import android.database.Cursor;

import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.database.DatabaseParser;
import com.cephmonitor.cephmonitor.model.database.table.RecordedTable;

/**
 * Created by User on 2015/9/1.
 */
public class RemoveResolvedData extends DatabaseData {

    @Override
    public void boxing(Cursor cursor) {
    }

    @Override
    public String selectScript() {
        return null;
    }

    @Override
    public String deleteScript() {
        String sql = "DELETE FROM ? WHERE ? = ? ";
        Object[] paramGroup = {
                RecordedTable.TABLE_NAME,
                RecordedTable.FIELD_STATUS,
                CephNotificationConstant.STATUS_RESOLVED,
        };
        return DatabaseParser.replaceSymbolToParam(sql, paramGroup);
    }
}