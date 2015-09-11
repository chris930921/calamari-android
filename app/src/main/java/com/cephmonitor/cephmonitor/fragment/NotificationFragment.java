package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.fragment.NotificationLayout;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.NotificationItem;
import com.cephmonitor.cephmonitor.model.database.StoreNotifications;
import com.cephmonitor.cephmonitor.model.database.data.KeepLoadRecordedData;
import com.cephmonitor.cephmonitor.model.database.data.RecordedData;
import com.cephmonitor.cephmonitor.model.database.operator.RecordedOperator;
import com.resourcelibrary.model.log.ShowLog;

import org.json.JSONException;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {
    private NotificationLayout layout;
    private ArrayList<RecordedData> recordGroup;
    private RecordedOperator recordedOperator;
    private StoreNotifications store;
    private KeepLoadRecordedData keepLoadRecorded;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new NotificationLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        store = new StoreNotifications(getActivity());
        SQLiteDatabase database = store.getReadableDatabase();
        keepLoadRecorded = new KeepLoadRecordedData();
        keepLoadRecorded.loadOldRecordGroup(database);
        database.close();

        if (keepLoadRecorded.recordGroup.size() == 0) {
            layout.showWorkFind();
        } else {
            recordGroup = keepLoadRecorded.recordGroup;
            recordedOperator = new RecordedOperator(getActivity());
            layout.list.setAdapter(listAdapter);
            layout.list.setOnScrollListener(scrollEvent);
        }
    }

    private AbsListView.OnScrollListener scrollEvent = new AbsListView.OnScrollListener() {
        private int prevFirstVisibleItem = -1;

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == prevFirstVisibleItem) {
                ShowLog.d("還看的見第一個項目，不更新。");
                return;
            }
            if (store == null) {
                ShowLog.d("資料庫物件沒有建立，不更新。");
                return;
            }
//            if(totalItemCount == store.getCount()){
//                ShowLog.d("現有資料和資料庫相同，不更新。");
//                return;
//            }
            if (firstVisibleItem == 0 && prevFirstVisibleItem > 0) {
                ShowLog.d("滑到頂部且有新值，進行更新。");
            } else if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0 && (prevFirstVisibleItem < firstVisibleItem)) {
                ShowLog.d("滑到頂部且還有舊值，進行更新。");
                SQLiteDatabase database = store.getReadableDatabase();
                keepLoadRecorded.loadOldRecordGroup(database);
                database.close();
                listAdapter.notifyDataSetChanged();
            }
            prevFirstVisibleItem = firstVisibleItem;
        }
    };

    private BaseAdapter listAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return recordGroup.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            NotificationItem item;
            if (view == null) {
                item = new NotificationItem(getActivity());
            } else {
                item = (NotificationItem) view;
            }
            RecordedData recorded = recordGroup.get(i);
            recordedOperator.setValue(recorded);
            item.setMessage(recordedOperator.getLastMessageWithParam());
            item.setStatus(recorded.status);
            item.setTime(recorded.triggered);
            item.setLevel(recorded.level);
            item.setTag(recorded);
            item.setOnClickListener(clickEvent);
            return item;
        }
    };
    private View.OnClickListener clickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NotificationItem item = (NotificationItem) view;
            RecordedData recorded = (RecordedData) item.getTag();
            RecordedOperator operator = new RecordedOperator(getActivity());
            operator.setValue(recorded);

            Bundle argGroup = new Bundle();
            argGroup.putInt("0", recorded.level);
            argGroup.putString("1", operator.getLastMessageWithParam());
            argGroup.putString("2", recorded.status);
            argGroup.putLong("3", recorded.triggered.getTimeInMillis());
            argGroup.putLong("4", (recorded.resolved == null) ? -1 : recorded.resolved.getTimeInMillis());
            try {
                argGroup.putInt("5", recorded.otherParamsJson.getInt("description_title"));
            } catch (JSONException e) {
                e.printStackTrace();
                argGroup.putInt("5", R.string.something_parse_error);
            }
            try {
                argGroup.putString("6", recorded.otherParamsJson.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
                argGroup.putString("6", getString(R.string.something_parse_error));
            }
            FragmentLauncher.goNotificationDetailFragment(getActivity(), argGroup);
        }
    };
}
