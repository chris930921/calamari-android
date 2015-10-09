package com.cephmonitor.cephmonitor.layout.listitem.reuse;

import android.content.Context;
import android.graphics.Color;

import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.listitem.reuse.DynamicRoundBorderItem;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.resourcelibrary.model.view.WH;

public class SettingItem extends DynamicRoundBorderItem {

    public SettingItem(Context context) {
        super(context);
        WH ruler = new WH(getContext());

        setBorderColor(ColorTable._D9D9D9);
        setBorderWidth(3);
        setRadius(10);
        setViewBackgroundColor(Color.WHITE);

        DesignSpec designSpec = ThemeManager.getStyle(getContext());
        int horizontalPadding = ruler.getW(designSpec.getPadding().getTopBottomOne());
        int verticalPadding = ruler.getW(designSpec.getPadding().getTopBottomOne());

        setViewPadding(
                horizontalPadding,
                verticalPadding,
                horizontalPadding,
                verticalPadding);
    }
}
