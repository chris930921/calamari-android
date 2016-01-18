package com.cephmonitor.cephmonitor.layout.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.component.button.SelectLanguageButton;
import com.cephmonitor.cephmonitor.layout.component.edittext.BorderAutoCompleteEditText;
import com.cephmonitor.cephmonitor.layout.component.edittext.BorderEditText;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.TextViewStyle;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.logic.DpToPx;
import com.cephmonitor.cephmonitor.model.tool.RefreshViewManager;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;
import com.resourcelibrary.model.view.button.RoundFillColorButton;

public class LoginLayout extends RelativeLayout {
    public ImageView inwinLogo;

    public BorderAutoCompleteEditText host;
    public BorderAutoCompleteEditText port;
    public BorderAutoCompleteEditText name;
    public BorderEditText password;
    public SelectLanguageButton language;

    public RelativeLayout footer;
    public TextView versionText;
    public RelativeLayout footerBottomLineCenterContainer;
    public TextView fromText;
    public ImageView inwinTextLogo;
    public TextView developerText;

    public Button signIn;

    private Context context;
    private WH ruler;
    private DpToPx equalizer;
    private String versionName;

    private DesignSpec designSpec;
    private int backgroundColor;
    private int inputBackgroundColor;
    private int buttonBackgroundColor;
    private int buttonPressColor;
    private TextViewStyle inputTextStyle;
    private TextViewStyle buttonTextStyle;
    private TextViewStyle footerTextStyle;
    private int imageTopMargin;
    private int imageBottomMargin;
    private int inputTopMargin;
    private int buttonTopMargin;
    private int textBottomMargin;
    private int inputLeftRightMargin;
    private RefreshViewManager.Interface intermediary;

    public LoginLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        this.equalizer = new DpToPx(context);
        this.intermediary = (RefreshViewManager.Interface) context;
        this.designSpec = ThemeManager.getStyle(context);
        backgroundColor = designSpec.getPrimaryColors().getBackgroundOne();
        inputBackgroundColor = designSpec.getPrimaryColors().getBackgroundThree();
        buttonBackgroundColor = designSpec.getPrimaryColors().getPrimary();
        buttonPressColor = designSpec.getPrimaryColors().getSecondary();
        inputTextStyle = new TextViewStyle(designSpec.getStyle().getBodyOne());
        buttonTextStyle = new TextViewStyle(designSpec.getStyle().getLargeButton());
        footerTextStyle = new TextViewStyle(designSpec.getStyle().getNote());
        imageTopMargin = ruler.getW(designSpec.getMargin().getTopBottomFour());
        imageBottomMargin = ruler.getW(designSpec.getMargin().getLeftRightFour());
        inputTopMargin = ruler.getW(designSpec.getMargin().getTopBottomOne());
        buttonTopMargin = ruler.getW(designSpec.getMargin().getTopBottomOne());
        textBottomMargin = ruler.getW(designSpec.getMargin().getTopBottomOne());
        inputLeftRightMargin = ruler.getW(designSpec.getMargin().getLeftRightOne());

        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        setId(RandomId.get());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        setBackgroundColor(backgroundColor);
        setPadding(inputLeftRightMargin, 0, inputLeftRightMargin, 0);

        inwinLogo = inwinLogo();
        host = host(inwinLogo);
        port = port(host);
        name = name(port);
        password = password(name);
        language = language(password);
        signIn = signIn(language);
        footer = footer();
        versionText = versionText();
        footerBottomLineCenterContainer = footerBottomLineCenterContainer(versionText);
        fromText = fromText();
        inwinTextLogo = inwinTextLogo(fromText);
        developerText = developerText(inwinTextLogo);

        addView(inwinLogo);
        addView(host);
        addView(port);
        addView(name);
        addView(password);
        addView(language);
        addView(signIn);
        addView(footer);
        footer.addView(versionText);
        footer.addView(footerBottomLineCenterContainer);
        footerBottomLineCenterContainer.addView(fromText);
        footerBottomLineCenterContainer.addView(inwinTextLogo);
        footerBottomLineCenterContainer.addView(developerText);

        setTextInput();
        recoverInputStatus();
    }

    private ImageView inwinLogo() {
        LayoutParams params = new LayoutParams(
                220,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_TOP);
        params.topMargin = imageTopMargin;

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon01);
        v.setAdjustViewBounds(true);
        v.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return v;
    }

    private BorderAutoCompleteEditText host(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, equalizer.change(44));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.topMargin = imageBottomMargin;

        BorderAutoCompleteEditText v = addAutoInput(R.string.login_host);
        v.setLayoutParams(params);

        return v;
    }


    private BorderAutoCompleteEditText port(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,equalizer.change(44));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.topMargin = inputTopMargin;

        BorderAutoCompleteEditText v = addAutoInput(R.string.login_port);
        v.setLayoutParams(params);

        return v;
    }

    private BorderAutoCompleteEditText name(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, equalizer.change(44));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.topMargin = inputTopMargin;

        BorderAutoCompleteEditText v = addAutoInput(R.string.login_name);
        v.setLayoutParams(params);

        return v;
    }

    private BorderEditText password(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, equalizer.change(44));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.topMargin = inputTopMargin;

        BorderEditText v = addInput(R.string.login_password);
        v.setLayoutParams(params);

        return v;
    }

    private SelectLanguageButton language(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, equalizer.change(44));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.topMargin = inputTopMargin;

        SelectLanguageButton v = new SelectLanguageButton(getContext());
        v.setId(RandomId.get());
        v.setLayoutParams(params);

        return v;
    }

    private BorderEditText addInput(final int hintId) {
        final BorderEditText v = new BorderEditText(context);
        v.setId(RandomId.get());
        v.setSingleLine(true);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setPadding(ruler.getW(6.12), 0, ruler.getW(6.12), 0);
        v.setHint(hintId);
        v.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        v.setTypeface(null, Typeface.BOLD);
        v.setBackgroundColor(inputBackgroundColor);
        inputTextStyle.style(v);
        intermediary.refreshViewManager.addTask(new Runnable() {
            @Override
            public void run() {
                v.setHint(hintId);
            }
        });
        v.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                v.setSelection(v.getText().length());
            }
        });

        return v;
    }

    private BorderAutoCompleteEditText addAutoInput(final int hintId) {
        final BorderAutoCompleteEditText v = new BorderAutoCompleteEditText(context);
        v.setId(RandomId.get());
        v.setSingleLine(true);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setPadding(ruler.getW(6.12), 0, ruler.getW(6.12), 0);
        v.setHint(hintId);
        v.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        v.setTypeface(null, Typeface.BOLD);
        v.setBackgroundColor(inputBackgroundColor);
        inputTextStyle.style(v);
        intermediary.refreshViewManager.addTask(new Runnable() {
            @Override
            public void run() {
                v.setHint(hintId);
            }
        });
        v.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                v.setSelection(v.getText().length());
            }
        });
        v.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        return v;
    }

    private Button signIn(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, equalizer.change(44));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.topMargin = buttonTopMargin;

        final RoundFillColorButton v = new RoundFillColorButton(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        v.setPadding(0, 0, 0, 0);
        v.setFillAndPressColor(buttonBackgroundColor, buttonPressColor);
        v.setText(R.string.login_sign_in);
        buttonTextStyle.style(v);
        v.setTextColor(Color.WHITE);
        intermediary.refreshViewManager.addTask(new Runnable() {
            @Override
            public void run() {
                v.setText(R.string.login_sign_in);
            }
        });

        return v;
    }

    private RelativeLayout footer() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.bottomMargin = textBottomMargin;

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);

        return v;
    }

    private TextView versionText() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        final TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(getContext().getString(R.string.login_version) + versionName);
        footerTextStyle.style(v);
        intermediary.refreshViewManager.addTask(new Runnable() {
            @Override
            public void run() {
                v.setText(getContext().getString(R.string.login_version) + versionName);
            }
        });

        return v;
    }

    private RelativeLayout footerBottomLineCenterContainer(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);

        return v;
    }

    private TextView fromText() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(CENTER_VERTICAL);

        final TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.login_from);
        footerTextStyle.style(v);
        intermediary.refreshViewManager.addTask(new Runnable() {
            @Override
            public void run() {
                v.setText(R.string.login_from);
            }
        });

        return v;
    }

    private ImageView inwinTextLogo(final View leftView) {
        LayoutParams params = new LayoutParams(1, 1);
        params.addRule(RelativeLayout.ALIGN_TOP, leftView.getId());
        params.addRule(RelativeLayout.ALIGN_BOTTOM, leftView.getId());
        params.addRule(RIGHT_OF, leftView.getId());

        ImageView v = new ImageView(context) {
            private int height = 1;

            @Override
            protected void onSizeChanged(int w, int h, int oldw, int oldh) {
                super.onSizeChanged(w, h, oldw, oldh);

                int viewHeight = leftView.getHeight();
                if (height == viewHeight) return;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(context.getResources(), R.drawable.icon034, options);
                float width = options.outWidth;
                float height = options.outHeight;
                float multiple = viewHeight / height;
                float viewWidth = width * multiple;

                final ViewGroup.LayoutParams params = getLayoutParams();
                params.width = (int) viewWidth;
                height = viewHeight;

                post(new Runnable() {
                    @Override
                    public void run() {
                        setLayoutParams(params);
                    }
                });
            }
        };
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setAdjustViewBounds(true);
        v.setScaleType(ImageView.ScaleType.FIT_XY);
        v.setImageResource(R.drawable.icon034);

        return v;
    }

    private TextView developerText(View leftView) {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RIGHT_OF, leftView.getId());
        params.addRule(CENTER_VERTICAL);

        final TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.login_developer);
        footerTextStyle.style(v);
        intermediary.refreshViewManager.addTask(new Runnable() {
            @Override
            public void run() {
                v.setText(R.string.login_developer);
            }
        });

        return v;
    }

    private void setTextInput() {
        host.setRawInputType(InputType.TYPE_CLASS_TEXT);
        port.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void recoverInputStatus() {
        host.recoverColor();
        port.recoverColor();
        name.recoverColor();
        password.recoverColor();
    }

    public void setNoValueInput(View v) {
        recoverInputStatus();
        if (v instanceof BorderEditText) {
            BorderEditText input = ((BorderEditText) v);
            input.warningColor();
        } else if (v instanceof BorderAutoCompleteEditText) {
            BorderAutoCompleteEditText input = ((BorderAutoCompleteEditText) v);
            input.warningColor();
        }
    }
}
