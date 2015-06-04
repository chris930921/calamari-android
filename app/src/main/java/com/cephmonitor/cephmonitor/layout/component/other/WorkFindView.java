package com.cephmonitor.cephmonitor.layout.component.other;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

/**
 * Created by User on 5/26/2015.
 */
public class WorkFindView extends RelativeLayout {
    private Context context;
    private WH ruler;

    public View workFineImage;
    public TextView workFineLineOne;
    public TextView workFineLineTwo;

    public WorkFindView(Context context) {
        super(context);
        this.context = context;
        ruler = new WH(context);

        workFineImage = workFineImage();
        workFineLineOne = workFineLineOne(workFineImage);
        workFineLineTwo = workFineLineTwo(workFineLineOne);

        addView(workFineImage);
        addView(workFineLineOne);
        addView(workFineLineTwo);
    }

    private View workFineImage() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ruler.getW(30), ruler.getW(30));
        params.addRule(CENTER_HORIZONTAL);

        View v = new View(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setBackgroundResource(R.drawable.icon026);

        return v;
    }

    private TextView workFineLineOne(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());
        params.setMargins(0, ruler.getH(5), 0, 0);

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextColor(ColorTable._666666);
        v.setTextSize(14);

        return v;
    }

    private TextView workFineLineTwo(View topView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(context);
        v.setId(RandomId.get());
        v.setLayoutParams(params);
        v.setTextColor(ColorTable._666666);
        v.setTextSize(14);

        return v;
    }

    public void setText(String lineOne, String lineTwo) {
        workFineLineOne.setText(lineOne);
        workFineLineTwo.setText(lineTwo);
    }

    public void showWorkFind(View hideView) {
        hideView.setVisibility(GONE);
        setVisibility(VISIBLE);
    }

    public void hideWorkFind(View hideView) {
        hideView.setVisibility(VISIBLE);
        setVisibility(GONE);
    }
}
