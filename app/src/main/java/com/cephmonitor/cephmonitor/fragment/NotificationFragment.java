package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.NotificationLayout;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.NotificationItem;
import com.cephmonitor.cephmonitor.model.database.NotificationRow;
import com.cephmonitor.cephmonitor.model.database.StoreNotifications;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {
    private static final String ACTION = NotificationFragment.class.getName();
    private static final IntentFilter UPDATE_RECEIVER_FILTER = new IntentFilter(ACTION);
    private static final Intent UPDATE_INTENT = new Intent(ACTION);
    private NotificationLayout layout;
    protected ArrayList<NotificationRow> notifications;
    protected StoreNotifications database;

    public static void send(Context context) {
        context.sendBroadcast(UPDATE_INTENT);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new NotificationLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        database = new StoreNotifications(getActivity());
        notifications = new ArrayList<>();
        layout.list.setAdapter(listAdapter);
        layout.list.setOnScrollListener(scrollEvent);
    }

    public void reloadNotification() {
        database = new StoreNotifications(getActivity());
        notifications.clear();
        notifications.addAll(database.findBeforeData());
        checkEmpty();
    }

    public void loadNewNotifications() {
        ArrayList<NotificationRow> updateNotifications = database.updateNewData();

        for (int i = 0; i < updateNotifications.size(); i++) {
            notifications.add(i, updateNotifications.get(i));
        }
        checkEmpty();
    }

    public void loadOldNotifications() {
        notifications.addAll(database.findBeforeData());
        checkEmpty();

    }

    public void checkEmpty() {
        if (notifications.size() == 0) {
            layout.showWorkFind();
        } else {
            layout.hideWorkFind();
            listAdapter.notifyDataSetChanged();
        }
    }

    private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION)) {
                if (layout.list.getFirstVisiblePosition() == 0 && notifications.size() != 0) {
                    loadNewNotifications();
                } else if (layout.list.getFirstVisiblePosition() == 0) {
                    reloadNotification();
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(updateReceiver, UPDATE_RECEIVER_FILTER);
        reloadNotification();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(updateReceiver);
    }

    private View.OnClickListener clickDelete = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NotificationRow row = (NotificationRow) view.getTag();
            boolean isSuccessRemove = database.removeNotification(row.id);
            if (isSuccessRemove) {
                notifications.remove(row);
                if (layout.list.getLastVisiblePosition() == notifications.size() - 1) {
                    loadOldNotifications();
                }
                checkEmpty();
            }
        }
    };

    private AbsListView.OnScrollListener scrollEvent = new AbsListView.OnScrollListener() {
        private int prevFirstVisibleItem = -1;

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == prevFirstVisibleItem) return;

            if (database == null) {
            } else if (totalItemCount == database.getCount()) {
            } else if (firstVisibleItem == 0 && prevFirstVisibleItem > 0) {
                loadNewNotifications();
            } else if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0 && (prevFirstVisibleItem < firstVisibleItem)) {
                loadOldNotifications();
            }
            prevFirstVisibleItem = firstVisibleItem;
        }
    };

    private BaseAdapter listAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return notifications.size();
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
            if (view != null) {
                item = (NotificationItem) view;
            } else {
                item = new NotificationItem(getActivity());
            }
            NotificationRow row = notifications.get(i);
            item.setItemValue(
                    row,
                    row.status,
                    row.content,
                    row.getCreatedDate()
            );
            item.setRightImageClickEvent(clickDelete);
            return item;
        }
    };
}
