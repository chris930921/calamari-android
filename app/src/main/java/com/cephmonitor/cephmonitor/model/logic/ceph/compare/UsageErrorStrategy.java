package com.cephmonitor.cephmonitor.model.logic.ceph.compare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.database.StoreNotifications;
import com.cephmonitor.cephmonitor.model.database.data.FindPendingData;
import com.cephmonitor.cephmonitor.model.database.data.RecordedData;
import com.cephmonitor.cephmonitor.model.database.operator.RecordedOperator;
import com.cephmonitor.cephmonitor.model.file.io.SettingStorage;
import com.cephmonitor.cephmonitor.model.logic.CheckResult;
import com.resourcelibrary.model.log.ShowLog;

import java.util.Calendar;

/**
 * Created by User on 2015/9/2.
 */
public class UsageErrorStrategy {
    private SettingStorage settingStorage;
    public Context context;
    public CheckResult checkResult;
    public int compareValue;
    public int monitorType;
    public int level;
    public int monitorNumber;
    public int createMessageId;
    public int finishMessageId;
    public int abnormalTitleId;
    public int normalTitleId;
    public int waringType;

    public int limit;
    public int recordedId;

    public void setParams(Context context, CheckResult checkResult, int compareValue,
                          int monitorType, int level, int monitorNumber,
                          int createMessageId, int finishMessageId,
                          int abnormalTitleId, int normalTitleId,
                          int waringType, int limit) {
        this.settingStorage = new SettingStorage(context);
        this.context = context;
        this.checkResult = checkResult;
        this.compareValue = compareValue;
        this.monitorType = monitorType;
        this.level = level;
        this.monitorNumber = monitorNumber;
        this.createMessageId = createMessageId;
        this.finishMessageId = finishMessageId;
        this.abnormalTitleId = abnormalTitleId;
        this.normalTitleId = normalTitleId;
        this.waringType = waringType;
        this.limit = limit;
    }

    public void compare() {
        StoreNotifications store = new StoreNotifications(context);
        SQLiteDatabase database = store.getReadableDatabase();

        FindPendingData findPending = new FindPendingData();
        findPending.monitorType = monitorType;
        findPending.level = level;
        findPending.monitorNumber = monitorNumber;
        findPending.load(database);

        if (!findPending.isExist && compareValue >= limit) {
            RecordedData recorded = new RecordedData();
            recorded.count = compareValue;
            recorded.level = level;
            recorded.status = CephNotificationConstant.STATUS_PENDING;
            recorded.triggered = Calendar.getInstance();
            recorded.resolved = null;

            recorded.originalCount = compareValue;
            recorded.previousCount = compareValue;
            recorded.lastMessageId = createMessageId;
            recorded.lastTitleId = abnormalTitleId;
            recorded.lastErrorMessageId = createMessageId;
            recorded.lastErrorTitleId = abnormalTitleId;
            recorded.waringType = waringType;
            recorded.monitorType = monitorType;
            recorded.monitorNumber = monitorNumber;

            String showPercent = String.format("%.2f", recorded.count / 100);
            RecordedOperator recordedOperator = new RecordedOperator(context);
            recordedOperator.setValue(recorded);
            recordedOperator.addOtherParam("description_title", R.string.notification_detail_usage);
            recordedOperator.addOtherParam("description", showPercent + "%");

            recorded.create(database);
            ShowLog.d("監控訊息: 出現損壞。");
            checkResult.isSendNotification = true;
            checkResult.isCheckError = true;
            return;
        } else if (!findPending.isExist && compareValue < limit) {
            ShowLog.d("監控訊息: 正常運作中。");
            checkResult.isSendNotification = false;
            checkResult.isCheckError = false;
            return;
        }

        RecordedData recorded = new RecordedData();
        recorded.id = findPending.recordId;
        recorded.load(database);
        recordedId = findPending.recordId;

        boolean check;
        // 全部修復完成的情況。
        check = true;
        check &= compareValue < recorded.previousCount;
        check &= compareValue < recorded.originalCount;
        check &= compareValue < limit;
        if (check) {
            recorded.status = CephNotificationConstant.STATUS_RESOLVED;
            recorded.resolved = Calendar.getInstance();
            recorded.originalCount = 0;
            recorded.previousCount = 0;
            recorded.lastMessageId = finishMessageId;
            recorded.lastTitleId = normalTitleId;

            ShowLog.d("監控訊息: 全部修復完成。");
            if (settingStorage.getAutoDelete()) {
                ShowLog.d("監控訊息: 自動刪除開啟中，刪除資訊。");
                recorded.remove(database);
                checkResult.isSendNotification = false;
                checkResult.isCheckError = false;
            } else {
                ShowLog.d("監控訊息: 自動刪除關閉中，保存資訊。");
                recorded.save(database);
                checkResult.isSendNotification = true;
                checkResult.isCheckError = false;
            }
            return;
        }

        ShowLog.d("監控訊息: 維持損壞中，無異狀。");
        checkResult.isSendNotification = false;
        checkResult.isCheckError = true;
        return;
    }
}
