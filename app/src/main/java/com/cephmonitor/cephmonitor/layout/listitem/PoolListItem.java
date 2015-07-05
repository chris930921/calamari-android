package com.cephmonitor.cephmonitor.layout.listitem;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cephmonitor.cephmonitor.R;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.model.logic.GenerateViewId;
import com.resourcelibrary.model.logic.RandomId;
import com.resourcelibrary.model.view.WH;

public class PoolListItem extends RelativeLayout {
    private Context context;
    private WH ruler;

    private View topFillView;
    private TextView nameText;
    private TextView idText;
    private RelativeLayout replicasContainer;
    private View replicasImage;
    private TextView replicasText;
    private TextView replicasValue;
    private RelativeLayout pgsContainer;
    private View pgsImage;
    private TextView pgsText;
    private TextView pgsValue;
    private View bottomFillView;
    private View bottomLine;

    public PoolListItem(Context context) {
        super(context);
        this.context = context;
        this.ruler = new WH(context);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        setId(RandomId.get());
        setLayoutParams(params);

        topFillView = topFillView();
        nameText = nameText(topFillView);
        idText = idText(nameText);
        replicasContainer = replicasContainer(idText);
        replicasImage = replicasImage();
        replicasValue = replicasValue(replicasImage);
        replicasText = replicasText(replicasValue);
        pgsContainer = pgsContainer(replicasContainer);
        pgsImage = pgsImage();
        pgsValue = pgsValue(pgsImage);
        pgsText = pgsText(pgsValue);
        bottomFillView = bottomFillView(pgsContainer);
        bottomLine = bottomLine(bottomFillView);

        addView(topFillView);
        addView(nameText);
        addView(idText);
        addView(replicasContainer);
        replicasContainer.addView(replicasImage);
        replicasContainer.addView(replicasValue);
        replicasContainer.addView(replicasText);
        addView(pgsContainer);
        pgsContainer.addView(pgsImage);
        pgsContainer.addView(pgsValue);
        pgsContainer.addView(pgsText);
        addView(bottomFillView);
        addView(bottomLine);
    }

    public void setData(String name, String id, int replicas, int pgs) {
        nameText.setText(name);
        idText.setText(context.getString(R.string.pool_list_id_ahead) + id);
        replicasValue.setText(String.valueOf(replicas));
        pgsValue.setText(String.valueOf(pgs));
    }

    private View topFillView() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ruler.getW(5));
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(ALIGN_PARENT_TOP);

        View v = new View(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    private TextView nameText(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(20));
        v.setTextColor(ColorTable._666666);
        v.setTypeface(null, Typeface.BOLD);
        v.setGravity(Gravity.CENTER_VERTICAL);

        return v;
    }

    private TextView idText(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(14));
        v.setTextColor(ColorTable._999999);
        v.setGravity(Gravity.CENTER_VERTICAL);

        return v;
    }

    private RelativeLayout replicasContainer(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View replicasImage() {
        LayoutParams params = new LayoutParams(ruler.getW(4), ruler.getW(4));
        params.addRule(CENTER_VERTICAL);
        params.rightMargin = ruler.getW(3);

        View v = new View(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setBackgroundResource(R.drawable.icon030);

        return v;
    }

    private TextView replicasValue(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, leftView.getId());
        params.addRule(RIGHT_OF, leftView.getId());
        params.rightMargin = ruler.getW(2);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(16));
        v.setTextColor(ColorTable._666666);
        v.setTypeface(null, Typeface.BOLD);
        v.setGravity(Gravity.CENTER_VERTICAL);

        return v;
    }

    private TextView replicasText(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, leftView.getId());
        params.addRule(RIGHT_OF, leftView.getId());

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(16));
        v.setTextColor(ColorTable._666666);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.pool_list_id_replicas);

        return v;
    }

    private RelativeLayout pgsContainer(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(BELOW, topView.getId());

        RelativeLayout v = new RelativeLayout(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View pgsImage() {
        LayoutParams params = new LayoutParams(ruler.getW(4), ruler.getW(4));
        params.addRule(CENTER_VERTICAL);
        params.rightMargin = ruler.getW(3);

        View v = new View(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setBackgroundResource(R.drawable.icon031);

        return v;
    }

    private TextView pgsValue(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, leftView.getId());
        params.addRule(RIGHT_OF, leftView.getId());
        params.rightMargin = ruler.getW(2);

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(16));
        v.setTextColor(ColorTable._666666);
        v.setTypeface(null, Typeface.BOLD);
        v.setGravity(Gravity.CENTER_VERTICAL);

        return v;
    }

    private TextView pgsText(View leftView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, leftView.getId());
        params.addRule(RIGHT_OF, leftView.getId());

        TextView v = new TextView(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setTextSize(ruler.getTextSize(16));
        v.setTextColor(ColorTable._666666);
        v.setGravity(Gravity.CENTER_VERTICAL);
        v.setText(R.string.pool_list_id_pgs);

        return v;
    }

    private View bottomFillView(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ruler.getW(5));
        params.addRule(BELOW, topView.getId());

        View v = new View(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);

        return v;
    }

    private View bottomLine(View topView) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ruler.getW(0.36));
        params.addRule(BELOW, topView.getId());

        View v = new View(context);
        v.setId(GenerateViewId.get());
        v.setLayoutParams(params);
        v.setBackgroundColor(ColorTable._EFEFEF);

        return v;
    }
}
