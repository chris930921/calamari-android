package com.cephmonitor.cephmonitor.model.database.data;

import android.database.Cursor;

import com.cephmonitor.cephmonitor.model.database.DatabaseConstant;
import com.cephmonitor.cephmonitor.model.database.DatabaseParser;
import com.cephmonitor.cephmonitor.model.database.table.RecordedTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by User on 2015/9/1.
 */
public class RecordedData extends DatabaseData {
    public int id;
    public String code;
    public float count;
    public int level;
    public String status;
    public Calendar triggered;
    public Calendar resolved;

    public float originalCount;
    public float previousCount;
    public int lastMessageId;
    public int lastTitleId;
    public int lastErrorMessageId;
    public int lastErrorTitleId;
    public int waringType;
    public int monitorType;
    public int monitorNumber;
    private String paramsTitle;
    private String paramsMessage;
    private String otherParams;
    public JSONArray paramsTitleJson;
    public JSONArray paramsMessageJson;
    public JSONObject otherParamsJson;

    @Override
    public void boxing(Cursor cursor) {
        cursor.moveToNext();

        int index = 0;
        id = cursor.getInt(index++);
        code = cursor.getString(index++);
        count = cursor.getFloat(index++);
        level = cursor.getInt(index++);
        status = cursor.getString(index++);
        triggered = DatabaseParser.stringToCalendar(cursor.getString(index++));
        resolved = DatabaseParser.stringToCalendar(cursor.getString(index++));

        originalCount = cursor.getFloat(index++);
        previousCount = cursor.getFloat(index++);
        lastMessageId = cursor.getInt(index++);
        lastTitleId = cursor.getInt(index++);
        lastErrorMessageId = cursor.getInt(index++);
        lastErrorTitleId = cursor.getInt(index++);
        waringType = cursor.getInt(index++);
        monitorType = cursor.getInt(index++);
        monitorNumber = cursor.getInt(index++);

        paramsTitle = cursor.getString(index++);
        paramsMessage = cursor.getString(index++);
        otherParams = cursor.getString(index++);

        try {
            paramsTitleJson = new JSONArray(paramsTitle);
        } catch (JSONException e) {
            e.printStackTrace();
            paramsTitleJson = new JSONArray();
        }
        try {
            paramsMessageJson = new JSONArray(paramsMessage);
        } catch (JSONException e) {
            e.printStackTrace();
            paramsMessageJson = new JSONArray();
        }
        try {
            otherParamsJson = new JSONObject(otherParams);
        } catch (JSONException e) {
            e.printStackTrace();
            otherParamsJson = new JSONObject();
        }
    }

    @Override
    public String updateScript() {
        if (paramsMessageJson == null) {
            paramsMessageJson = null;
        } else {
            paramsMessage = paramsMessageJson.toString();
        }
        if (paramsTitleJson == null) {
            paramsTitle = null;
        } else {
            paramsTitle = paramsTitleJson.toString();
        }
        if (otherParamsJson == null) {
            otherParams = null;
        } else {
            otherParams = otherParamsJson.toString();
        }

        String sql = "UPDATE ? SET " +
                " ? = ? " + ", ? = ? " + ", ? = ? " +
                ", ? = ? " + ", ? = ? " + ", ? = ? " +
                ", ? = ? " + ", ? = ? " + ", ? = ? " +
                ", ? = ? " + ", ? = ? " + ", ? = ? " +
                ", ? = ? " + ", ? = ? " + ", ? = ? " +
                ", ? = ? " + ", ? = ? " + ", ? = ? " +
                "WHERE ? = ? ";
        Object[] paramGroup = {
                RecordedTable.TABLE_NAME,
                RecordedTable.FIELD_CODE, code,
                RecordedTable.FIELD_COUNT, count,
                RecordedTable.FIELD_LEVEL, level,
                RecordedTable.FIELD_STATUS, status,
                RecordedTable.FIELD_TRIGGERED, DatabaseParser.timeToDatabaseValue(triggered),
                RecordedTable.FIELD_RESOLVED, DatabaseParser.timeToDatabaseValue(resolved),
                RecordedTable.FIELD_ORIGINAL_COUNT, originalCount,
                RecordedTable.FIELD_PREVIOUS_COUNT, previousCount,
                RecordedTable.FIELD_LAST_MESSAGE_ID, lastMessageId,
                RecordedTable.FIELD_LAST_TITLE_ID, lastTitleId,
                RecordedTable.FIELD_LAST_ERROR_MESSAGE_ID, lastErrorMessageId,
                RecordedTable.FIELD_LAST_ERROR_TITLE_ID, lastErrorTitleId,
                RecordedTable.FIELD_WARNING_TYPE, waringType,
                RecordedTable.FIELD_MONITOR_TYPE, monitorType,
                RecordedTable.FIELD_MONITOR_NUMBER, monitorNumber,
                RecordedTable.FIELD_PARAMS_TITLE, paramsTitle,
                RecordedTable.FIELD_PARAMS_MESSAGE, paramsMessage,
                RecordedTable.FIELD_OTHER_PARAMS, otherParams,
                RecordedTable.FIELD_ID, id,
        };
        return DatabaseParser.replaceSymbolToParam(sql, paramGroup);
    }

    @Override
    public String selectScript() {
        String sql = "SELECT * FROM ? WHERE ? = ? ";
        Object[] paramGroup = {
                RecordedTable.TABLE_NAME,
                RecordedTable.FIELD_ID,
                id,
        };
        return DatabaseParser.replaceSymbolToParam(sql, paramGroup);
    }

    @Override
    public String insertScript() {
        if (paramsMessageJson == null) {
            paramsMessageJson = null;
        } else {
            paramsMessage = paramsMessageJson.toString();
        }
        if (paramsTitleJson == null) {
            paramsTitle = null;
        } else {
            paramsTitle = paramsTitleJson.toString();
        }
        if (otherParamsJson == null) {
            otherParams = null;
        } else {
            otherParams = otherParamsJson.toString();
        }

        String sql = "INSERT INTO ? VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? );";
        Object[] paramGroup = {
                RecordedTable.TABLE_NAME,
                DatabaseConstant.NULL_VALUE,
                code,
                count,
                level,
                status,
                DatabaseParser.timeToDatabaseValue(triggered),
                DatabaseParser.timeToDatabaseValue(resolved),
                originalCount,
                previousCount,
                lastMessageId,
                lastTitleId,
                lastErrorMessageId,
                lastErrorTitleId,
                waringType,
                monitorType,
                monitorNumber,
                paramsTitle,
                paramsMessage,
                otherParams,
        };
        return DatabaseParser.replaceSymbolToParam(sql, paramGroup);
    }
}