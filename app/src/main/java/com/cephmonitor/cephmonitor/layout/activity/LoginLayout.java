package com.cephmonitor.cephmonitor.layout.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;
import com.resourcelibrary.model.view.button.RoundFillColorButton;
import com.resourcelibrary.model.view.edittext.RoundCornerEditText;

public class LoginLayout extends RelativeLayout {
    public ImageView inwinLogo;
    public TextView titleOne;
    public TextView titleTwo;
    public TextView titleThree;

    public RoundCornerEditText host;
    public RoundCornerEditText port;
    public RoundCornerEditText name;
    public RoundCornerEditText password;

    public Button signIn;

    private Context context;
    private WH ruler;

    public LoginLayout(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);
        int padding = ruler.getW(6.12);

        setId(RandomId.get());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        setBackgroundColor(Color.WHITE);
        setPadding(padding, padding, padding, padding);

        addView(inwinLogo = inwinLogo());
        addView(titleOne = titleOne(inwinLogo));
        addView(titleThree = titleThree(inwinLogo, titleOne));
        addView(titleTwo = titleTwo(titleThree));
        addView(host = host(titleThree));
        addView(port = port(host));
        addView(name = name(port));
        addView(password = password(name));
        addView(signIn = signIn(password));

        setTextInput();
        recoverInputStatus();
    }

    private ImageView inwinLogo() {
        LayoutParams params = new LayoutParams(ruler.getW(36.7), ruler.getH(16.8));
        params.addRule(ALIGN_PARENT_TOP);
        params.addRule(ALIGN_PARENT_LEFT);

        ImageView v = new ImageView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setImageResource(R.drawable.icon01);

        return v;
    }

    private TextView titleOne(View relativeView) {
        LayoutParams params = new LayoutParams(ruler.getW(51.02), LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, relativeView.getId());
        params.addRule(RIGHT_OF, relativeView.getId());
        params.setMargins(ruler.getW(2.04), 0, 0, 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(30));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setTypeface(null, Typeface.BOLD);
        v.setText(R.string.login_title_one);

        return v;
    }

    private TextView titleThree(View alignBottom, View alignLeft) {
        LayoutParams params = new LayoutParams(ruler.getW(80), LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_LEFT, alignLeft.getId());
        params.addRule(ALIGN_BOTTOM, alignBottom.getId());
        params.setMargins(0, 0, 0, 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.login_title_three);
        return v;
    }

    private TextView titleTwo(View relativeView) {
        LayoutParams params = new LayoutParams(ruler.getW(80), LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_LEFT, relativeView.getId());
        params.addRule(ABOVE, relativeView.getId());
        params.setMargins(0, 0, 0, 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.login_title_two);
        return v;
    }


    private RoundCornerEditText host(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(7.22));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.setMargins(0, ruler.getH(6.02), 0, 0);

        RoundCornerEditText v = addInput(R.string.login_host);
        v.setLayoutParams(params);

        return v;
    }

    private RoundCornerEditText port(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(7.22));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.setMargins(0, ruler.getH(4.21), 0, 0);

        RoundCornerEditText v = addInput(R.string.login_port);
        v.setLayoutParams(params);

        return v;
    }

    private RoundCornerEditText name(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(7.22));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.setMargins(0, ruler.getH(4.21), 0, 0);

        RoundCornerEditText v = addInput(R.string.login_name);
        v.setLayoutParams(params);

        return v;
    }

    private RoundCornerEditText password(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(7.22));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.setMargins(0, ruler.getH(4.21), 0, 0);

        RoundCornerEditText v = addInput(R.string.login_password);
        v.setLayoutParams(params);

        return v;
    }

    private RoundCornerEditText addInput(int hintId) {
        RoundCornerEditText input = new RoundCornerEditText(context);
        input.setId(RandomId.get());
        input.setTextSize(ruler.getTextSize(22));
        input.setSingleLine(true);
        input.setGravity(Gravity.CENTER_VERTICAL);
        input.setPadding(ruler.getW(6.12), 0, ruler.getW(6.12), 0);
        input.setHint(hintId);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        input.setTypeface(null, Typeface.BOLD);
        input.setBorderWidth(ruler.getW(1.02));

        return input;
    }

    private Button signIn(View relativeView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ruler.getH(7.22));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, relativeView.getId());
        params.setMargins(0, ruler.getH(4.81), 0, 0);

        RoundFillColorButton v = new RoundFillColorButton(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setTextColor(Color.argb(255, 255, 255, 255));
        v.setGravity(Gravity.CENTER);
        v.setPadding(0, 0, 0, 0);
        v.setFillAndPressColor(Color.parseColor("#e63427"), Color.parseColor("#942119"));
        v.setText(R.string.login_sign_in);

        return v;
    }

    private void setTextInput() {
        host.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        port.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void recoverInputStatus() {
        String color = "#666666";
        host.setBorderColor(Color.parseColor(color));
        port.setBorderColor(Color.parseColor(color));
        name.setBorderColor(Color.parseColor(color));
        password.setBorderColor(Color.parseColor(color));
    }

    public void setNoValueInput(View v) {
        recoverInputStatus();
        ((RoundCornerEditText) v).setBorderColor(Color.parseColor("#e63427"));
    }
}
