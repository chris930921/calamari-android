package com.cephmonitor.cephmonitor.model.app.theme.custom.template;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

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
import com.cephmonitor.cephmonitor.model.app.theme.custom.object.FontStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;

/**
 * Created by User on 2015/7/14.
 */
public class Ocean extends DesignSpec {

    public Ocean(Context context) {
        super(context);
    }

    @Override
    protected PrimaryColors initPrimaryColors() {

        return new PrimaryColors(this, getContext()) {
            @Override
            public int getBackgroundOne() {
                return Color.parseColor("#f9f9f9");
            }

            @Override
            public int getBackgroundTwo() {
                return Color.parseColor("#f3f3f3");
            }

            @Override
            public int getBackgroundThree() {
                return Color.parseColor("#ffffff");
            }

            @Override
            public int getPrimary() {
                return Color.parseColor("#2980b9");
            }

            @Override
            public int getSecondary() {
                return Color.parseColor("#20638f");
            }

            @Override
            public int getHorizontalOne() {
                return Color.parseColor("#efefef");
            }

            @Override
            public int getHorizontalTwo() {
                return Color.parseColor("#d9d9d9");
            }
        };
    }

    @Override
    protected AccentColors initAccentColors() {
        return new AccentColors(this, getContext()) {
            @Override
            public int getActive() {
                return Color.parseColor("#8dc41f");
            }

            @Override
            public int getNormal() {
                return Color.parseColor("#39c0ed");
            }

            @Override
            public int getError() {
                return Color.parseColor("#e63427");
            }

            @Override
            public int getWarning() {
                return Color.parseColor("#f7b500");
            }
        };
    }

    @Override
    protected Style initStyle() {
        return new Style(this, getContext()) {

            @Override
            public FontStyle getDisplayOne() {
                return new FontStyle() {
                    @Override
                    public int getSize() {
                        return 34;
                    }

                    @Override
                    public int getColor() {
                        return Color.parseColor("#000000");
                    }

                    @Override
                    public int getTypeface() {
                        return 0;
                    }
                };
            }

            @Override
            public FontStyle getHeadline() {
                return new FontStyle() {
                    @Override
                    public int getSize() {
                        return 24;
                    }

                    @Override
                    public int getColor() {
                        return Color.parseColor("#000000");
                    }

                    @Override
                    public int getTypeface() {
                        return Typeface.NORMAL;
                    }
                };
            }

            @Override
            public FontStyle getTitle() {
                return new FontStyle() {
                    @Override
                    public int getSize() {
                        return 20;
                    }

                    @Override
                    public int getColor() {
                        return Color.parseColor("#ffffff");
                    }

                    @Override
                    public int getTypeface() {
                        return Typeface.BOLD;
                    }
                };
            }

            @Override
            public FontStyle getSubhead() {
                return new FontStyle() {
                    @Override
                    public int getSize() {
                        return 16;
                    }

                    @Override
                    public int getColor() {
                        return Color.parseColor("#666666");
                    }

                    @Override
                    public int getTypeface() {
                        return Typeface.NORMAL;
                    }
                };
            }

            @Override
            public FontStyle getBodyTwo() {
                return new FontStyle() {
                    @Override
                    public int getSize() {
                        return 14;
                    }

                    @Override
                    public int getColor() {
                        return Color.parseColor("#666666");
                    }

                    @Override
                    public int getTypeface() {
                        return Typeface.BOLD;
                    }
                };
            }

            @Override
            public FontStyle getBodyOne() {
                return new FontStyle() {
                    @Override
                    public int getSize() {
                        return 14;
                    }

                    @Override
                    public int getColor() {
                        return Color.parseColor("#666666");
                    }

                    @Override
                    public int getTypeface() {
                        return Typeface.NORMAL;
                    }
                };
            }

            @Override
            public FontStyle getNote() {
                return new FontStyle() {
                    @Override
                    public int getSize() {
                        return 12;
                    }

                    @Override
                    public int getColor() {
                        return Color.parseColor("#999999");
                    }

                    @Override
                    public int getTypeface() {
                        return Typeface.NORMAL;
                    }
                };
            }

            @Override
            public FontStyle getDefaultButton() {
                return new FontStyle() {
                    @Override
                    public int getSize() {
                        return 14;
                    }

                    @Override
                    public int getColor() {
                        return Color.parseColor("#000000");
                    }

                    @Override
                    public int getTypeface() {
                        return Typeface.BOLD;
                    }
                };
            }

            @Override
            public FontStyle getLargeButton() {
                return new FontStyle() {
                    @Override
                    public int getSize() {
                        return 16;
                    }

                    @Override
                    public int getColor() {
                        return Color.parseColor("#000000");
                    }

                    @Override
                    public int getTypeface() {
                        return Typeface.BOLD;
                    }
                };
            }
        };
    }

    @Override
    protected Horizontal initHorizontal() {
        return new Horizontal(this, getContext()) {
            @Override
            public float getHorizontalOneHeight() {
                return 0.36F;
            }

            @Override
            public float getHorizontalTwoHeight() {
                return 0.36F;
            }
        };
    }

    @Override
    protected Margin initMargin() {
        return new Margin(this, getContext()) {
            @Override
            public float getLeftRightOne() {
                return 3;
            }

            @Override
            public float getLeftRightTwo() {
                return 6;
            }

            @Override
            public float getTopBottomOne() {
                return 3;
            }

            @Override
            public float getTopBottomTwo() {
                return 6;
            }
        };
    }

    @Override
    protected Padding initPadding() {
        return new Padding(this, getContext()) {
            @Override
            public float getLeftRightOne() {
                return 3;
            }

            @Override
            public float getLeftRightTwo() {
                return 6;
            }

            @Override
            public float getTopBottomOne() {
                return 3;
            }

            @Override
            public float getTopBottomTwo() {
                return 6;
            }
        };
    }

    @Override
    protected IconSize initIconSize() {
        return new IconSize(this, getContext()) {
            @Override
            public float getTitle() {
                return 4.4F;
            }

            @Override
            public float getSubhead() {
                return 3F;
            }

            @Override
            public float getBody() {
                return 2.8F;
            }

            @Override
            public float getNote() {
                return 2.6F;
            }

            @Override
            public float getDefaultButton() {
                return 2.8F;
            }

            @Override
            public float getLargeButton() {
                return 3F;
            }

            @Override
            public float getMessage() {
                return 30F;
            }
        };
    }

    @Override
    protected Labels initLabels() {
        return new Labels(this, getContext()) {
            @Override
            public int getSuccessColor() {
                return getDesignSpec().getAccentColors().getActive();
            }

            @Override
            public int getInfoColor() {
                return getDesignSpec().getAccentColors().getNormal();
            }

            @Override
            public int getWarningColor() {
                return getDesignSpec().getAccentColors().getWarning();
            }

            @Override
            public int getDangerColor() {
                return getDesignSpec().getAccentColors().getError();
            }

            @Override
            public int getBorderRadius() {
                return 10;
            }

            @Override
            public int getFontSize() {
                return getDesignSpec().getStyle().getBodyOne().getSize();
            }

            @Override
            public float getTopPadding() {
                return 2;
            }

            @Override
            public float getBottomPadding() {
                return 2;
            }

            @Override
            public float getLeftPadding() {
                return 3;
            }

            @Override
            public float getRightPadding() {
                return 3;
            }

            @Override
            public float getHorizonMargin() {
                return 3;
            }

            @Override
            public float getVerticalMargin() {
                return 3;
            }
        };
    }

    @Override
    protected TextButton initTextButton() {
        return new TextButton(this, getContext()) {
            @Override
            public float getDefaultHeight() {
                return 11.5F;
            }

            @Override
            public float getLargeHeight() {
                return 13F;
            }

            @Override
            public int getDefaultFontSize() {
                return getDesignSpec().getStyle().getDefaultButton().getSize();
            }

            @Override
            public int getLargeFontSize() {
                return getDesignSpec().getStyle().getLargeButton().getSize();
            }

            @Override
            public int getNormalBackground() {
                return Color.parseColor("#eeeeee");
            }

            @Override
            public int getNormalBorderSize() {
                return 1;
            }

            @Override
            public int getNormalBorderColor() {
                return Color.parseColor("#e2e2e2");
            }

            @Override
            public int getNormalTextColor() {
                return Color.parseColor("555555");
            }

            @Override
            public int getActiveBackground() {
                return Color.parseColor("d5d5d5");
            }

            @Override
            public int getActiveBorderSize() {
                return 1;
            }

            @Override
            public int getActiveBorderColor() {
                return Color.parseColor("c3c3c3");
            }

            @Override
            public int getActiveTextColor() {
                return Color.parseColor("555555");
            }
        };
    }

    @Override
    protected IconButton initIconButton() {
        return new IconButton(this, getContext()) {
            @Override
            public float getNormalWidth() {
                return getDesignSpec().getIconSize().getDefaultButton();
            }

            @Override
            public float getActiveWidth() {
                return getDesignSpec().getIconSize().getLargeButton();
            }

            @Override
            public float getNormalHeight() {
                return getDesignSpec().getIconSize().getDefaultButton();
            }

            @Override
            public float getActiveHeight() {
                return getDesignSpec().getIconSize().getLargeButton();
            }
        };
    }
}
