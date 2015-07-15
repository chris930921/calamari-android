package com.cephmonitor.cephmonitor.model.app.theme.custom.prototype;

import android.content.Context;

import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.AccentColors;
import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.Horizontal;
import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.IconButton;
import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.IconSize;
import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.Labels;
import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.Margin;
import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.Padding;
import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.PrimaryColors;
import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.Style;
import com.cephmonitor.cephmonitor.model.app.theme.custom.attribute.TextButton;

/**
 * Created by User on 2015/7/14.
 */
public abstract class DesignSpec {
    private Context context;
    private PrimaryColors primaryColors;
    private AccentColors accentColors;
    private Style style;
    private Horizontal horizontal;
    private Margin margin;
    private Padding padding;
    private IconSize iconSize;
    private Labels labels;
    private TextButton testButton;
    private IconButton iconButton;

    public DesignSpec(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public PrimaryColors getPrimaryColors() {
        return (primaryColors == null) ? primaryColors = initPrimaryColors() : primaryColors;
    }

    public AccentColors getAccentColors() {
        return (accentColors == null) ? accentColors = initAccentColors() : accentColors;
    }

    public Style getStyle() {
        return (style == null) ? style = initStyle() : style;
    }

    public Horizontal getHorizontal() {
        return (horizontal == null) ? horizontal = initHorizontal() : horizontal;
    }

    public Margin getMargin() {
        return (margin == null) ? margin = initMargin() : margin;
    }

    public Padding getPadding() {
        return (padding == null) ? padding = initPadding() : padding;
    }

    public IconSize getIconSize() {
        return (iconSize == null) ? iconSize = initIconSize() : iconSize;
    }

    public Labels getLabels() {
        return (labels == null) ? labels = initLabels() : labels;
    }

    public TextButton getTextButton() {
        return (testButton == null) ? testButton = initTextButton() : testButton;
    }

    public IconButton getIconButton() {
        return (iconButton == null) ? iconButton = initIconButton() : iconButton;
    }

    protected abstract PrimaryColors initPrimaryColors();

    protected abstract AccentColors initAccentColors();

    protected abstract Style initStyle();

    protected abstract Horizontal initHorizontal();

    protected abstract Margin initMargin();

    protected abstract Padding initPadding();

    protected abstract IconSize initIconSize();

    protected abstract Labels initLabels();

    protected abstract TextButton initTextButton();

    protected abstract IconButton initIconButton();
}