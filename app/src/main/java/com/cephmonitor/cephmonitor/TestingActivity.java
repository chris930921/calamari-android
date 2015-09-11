package com.cephmonitor.cephmonitor;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.cephmonitor.cephmonitor.model.database.StoreNotifications;
import com.cephmonitor.cephmonitor.model.database.data.FindPendingData;
import com.cephmonitor.cephmonitor.model.database.data.RecordedData;
import com.cephmonitor.cephmonitor.model.database.data.ResolvedRecordedData;

import java.util.Calendar;


public class TestingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deleteDatabase(StoreNotifications.DB_NAME);

        StoreNotifications store = new StoreNotifications(this);
        SQLiteDatabase database = store.getWritableDatabase();

        RecordedData recorderData = new RecordedData();
        recorderData.code = "01";
        recorderData.create(database);
        recorderData.create(database);
        recorderData.create(database);
        recorderData.create(database);
        recorderData.create(database);
        recorderData.create(database);

        RecordedData readRecorderData = new RecordedData();
        readRecorderData.id = 1;
        readRecorderData.load(database);

        readRecorderData.count = 1;
        readRecorderData.originalCount = 2;
        readRecorderData.previousCount = 3;
        readRecorderData.monitorType = 5;
        readRecorderData.level = 5;
        readRecorderData.resolved = Calendar.getInstance();
        readRecorderData.save(database);

        FindPendingData finPending = new FindPendingData();
        finPending.level = 0;
        finPending.monitorType = 0;
        finPending.monitorNumber = 0;
        finPending.load(database);

        ResolvedRecordedData notification = new ResolvedRecordedData();
        notification.load(database);

        String result = notification.recordGroup.toString();
    }
}
