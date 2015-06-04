package com.cephmonitor.cephmonitor.layout.component.osdhealthboxes;

import android.os.Bundle;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.resourcelibrary.model.network.api.ceph.object.ClusterV2OsdData;

/**
 * Created by User on 5/18/2015.
 */
public class OsdBox {
    public static final int NORMAL_STATUS = 0;
    public static final int WARN_STATUS = 1;
    public static final int ERROR_STATUS = 2;

    public int value;
    public int status;
    private int color;
    public ClusterV2OsdData osdData;

    public int getColor() {
        return color;
    }

    public void setStatus(int status) {
        this.status = status;
        if (status == NORMAL_STATUS) {
            color = ColorTable._8DC41F;
        } else if (status == WARN_STATUS) {
            color = ColorTable._F7B500;
        } else if (status == ERROR_STATUS) {
            color = ColorTable._E63427;
        }
    }

    public int changeStatus(int status) {
        if (status == ClusterV2OsdData.UP_IN) {
            return NORMAL_STATUS;
        } else if (status == ClusterV2OsdData.UP_OUT) {
            return WARN_STATUS;
        } else if (status == ClusterV2OsdData.DOWN_IN) {
            return WARN_STATUS;
        } else if (status == ClusterV2OsdData.DOWN) {
            return ERROR_STATUS;
        }
        return -1;
    }

    public void boxing(Bundle bundle) {
        String className = OsdBox.class.getName();
        bundle.putInt(className + "0", value);
        bundle.putInt(className + "1", status);
        bundle.putInt(className + "2", color);
    }

    public static OsdBox unboxing(Bundle bundle) {
        String className = OsdBox.class.getName();
        OsdBox box = new OsdBox();
        box.value = bundle.getInt(className + "0");
        box.status = bundle.getInt(className + "1");
        box.color = bundle.getInt(className + "2");
        return box;
    }

    @Override
    public String toString() {
        return "value:" + value + ", status:" + status + ", color:" + color;
    }
}
