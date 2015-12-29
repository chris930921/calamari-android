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
public class PgStrategy {
    private SettingStorage settingStorage;
    public Context context;
    public CheckResult checkResult;
    public float compareValue;
    public int elementValue;
    public int denominatorValue;
    public int monitorType;
    public int level;
    public int monitorNumber;
    public int createMessageId;
    public int moreMessageId;
    public int relapseMessageId;
    public int finishMessageId;
    public int abnormalTitleId;
    public int normalTitleId;
    public int waringType;
    public float limitValue;

    public int recordedId;

    public void setParams(Context context, CheckResult checkResult, float compareValue,
                          int elementValue, int denominatorValue,
                          int monitorType, int level, int monitorNumber,
                          int createMessageId, int moreMessageId, int relapseMessageId, int finishMessageId,
                          int abnormalTitleId, int normalTitleId,
                          int waringType, float limitValue) {
        this.settingStorage = new SettingStorage(context);
        this.context = context;
        this.checkResult = checkResult;
        this.compareValue = compareValue;
        this.elementValue = elementValue;
        this.denominatorValue = denominatorValue;
        this.monitorType = monitorType;
        this.level = level;
        this.monitorNumber = monitorNumber;
        this.createMessageId = createMessageId;
        this.moreMessageId = moreMessageId;
        this.relapseMessageId = relapseMessageId;
        this.finishMessageId = finishMessageId;
        this.abnormalTitleId = abnormalTitleId;
        this.normalTitleId = normalTitleId;
        this.waringType = waringType;
        this.limitValue = limitValue;
    }

    public void compare() {
        StoreNotifications store = new StoreNotifications(context);
        SQLiteDatabase database = store.getReadableDatabase();

        FindPendingData findPending = new FindPendingData();
        findPending.monitorType = monitorType;
        findPending.level = level;
        findPending.monitorNumber = monitorNumber;
        findPending.load(database);

        if (!findPending.isExist && compareValue > limitValue) {
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
            updateOtherParams(recorded);
            recorded.create(database);
            ShowLog.d("監控訊息: 出現損壞。");
            checkResult.isSendNotification = true;
            checkResult.isCheckError = true;
            return;
        } else if (!findPending.isExist) {
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

        // 持續修復的情況。
        check = true;
        check &= compareValue < recorded.previousCount;
        check &= compareValue <= recorded.originalCount;
        check &= compareValue > limitValue;
        if (check) {
            recorded.count = compareValue;
            recorded.previousCount = compareValue;
            updateOtherParams(recorded);
            recorded.save(database);
            ShowLog.d("監控訊息: 持續修補中。");
            checkResult.isSendNotification = false;
            checkResult.isCheckError = true;
            return;
        }

        // 持續損壞的情況。
        check = true;
        check &= compareValue > recorded.previousCount;
        check &= compareValue >= recorded.originalCount;
        check &= compareValue > limitValue;
        check &= recorded.originalCount > 0;
        if (check) {
            recorded.count = recorded.count + compareValue - recorded.previousCount;
            recorded.triggered = Calendar.getInstance();
            recorded.originalCount = compareValue;
            recorded.previousCount = compareValue;
            recorded.lastMessageId = moreMessageId;
            recorded.lastErrorMessageId = moreMessageId;
            updateOtherParams(recorded);
            recorded.save(database);
            ShowLog.d("監控訊息: 持續損壞中。");
            checkResult.isSendNotification = true;
            checkResult.isCheckError = true;
            return;
        }

        // 上一次修復，這一次又損壞的情況。
        check = true;
        check &= compareValue > recorded.previousCount;
        check &= compareValue <= recorded.originalCount;
        check &= compareValue > limitValue;
        if (check) {
            recorded.count = recorded.count + compareValue - recorded.previousCount;
            recorded.triggered = Calendar.getInstance();
            recorded.previousCount = compareValue;
            recorded.lastMessageId = relapseMessageId;
            recorded.lastErrorMessageId = relapseMessageId;
            updateOtherParams(recorded);
            recorded.save(database);
            ShowLog.d("監控訊息: 修復完又壞了的情況。");
            checkResult.isSendNotification = true;
            checkResult.isCheckError = true;
            return;
        }

        // 全部修復完成的情況。
        check = true;
        check &= compareValue < recorded.previousCount;
        check &= compareValue < recorded.originalCount;
        check &= compareValue < limitValue;
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

    private void updateOtherParams(RecordedData recorded) {
        int description = (recorded.level == 3) ? R.string.notification_detail_warning_ratio : R.string.notification_detail_error_ratio;
        String showPercent = String.format("%.2f", recorded.count * 100);
        RecordedOperator recordedOperator = new RecordedOperator(context);
        recordedOperator.setValue(recorded);
        recordedOperator.addOtherParam("description_title", description);
        recordedOperator.addOtherParam("description", showPercent + "% (" + elementValue + "/" + denominatorValue + ")");
    }
}
