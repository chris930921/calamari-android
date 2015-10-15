package com.cephmonitor.cephmonitor.model.tool;

import java.util.ArrayList;

/**
 * Created by chriske on 2015/10/2.
 */
public class RefreshViewManager {
    public ArrayList<Runnable> refreshTaskGroup;

    public RefreshViewManager() {
        refreshTaskGroup = new ArrayList<>();
    }

    public void addTask(Runnable task) {
        refreshTaskGroup.add(task);
    }

    public void refresh() {
        for (int i = 0; i < refreshTaskGroup.size(); i++) {
            refreshTaskGroup.get(i).run();
        }
    }

    public static interface Interface {
        public RefreshViewManager refreshViewManager = new RefreshViewManager();
    }
}
