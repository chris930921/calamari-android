package com.cephmonitor.cephmonitor.model.tool;

import android.graphics.Path;

/**
 * Created by chriske on 2015/11/13.
 */
public class ClearRoundAreaPath {
    public static void get(Path path,float width, float height, float radius) {
        path.moveTo(0, 0);
        path.lineTo(radius, 0);
        path.quadTo(0, 0, 0, radius);
        path.lineTo(0, 0);

        path.moveTo(width, 0);
        path.lineTo(width - radius, 0);
        path.quadTo(width, 0, width, radius);
        path.lineTo(width, 0);

        path.moveTo(width, height);
        path.lineTo(width, height - radius);
        path.quadTo(width, height, width - radius, height);
        path.lineTo(width, height);

        path.moveTo(0, height);
        path.lineTo(0, height - radius);
        path.quadTo(0, height, radius, height);
        path.lineTo(0, height);
    }
}
