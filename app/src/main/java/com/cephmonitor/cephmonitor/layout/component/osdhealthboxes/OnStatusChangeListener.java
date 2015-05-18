package com.cephmonitor.cephmonitor.layout.component.osdhealthboxes;

import java.util.ArrayList;

/**
 * Created by User on 5/18/2015.
 */
public interface OnStatusChangeListener {
    public void onChange(OsdHealthBoxes boxGroup, ArrayList boxes);
}
