package com.cephmonitor.cephmonitor.layout.component.osdhealthboxes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resourcelibrary.model.logic.GestureAdapter;
import com.resourcelibrary.model.view.WH;

import java.util.ArrayList;

/**
 * Created by User on 5/18/2015.
 */
public class OsdHealthBoxes extends View {
    public static final int ALL = 0;
    public static final int WARN = 1;
    public static final int ERROR = 2;

    public static final int columnCount = 4;
    private int radius = 20;
    public int rowCount;
    public Context context;
    public ArrayList<OsdBox> boxes;
    public ArrayList<OsdBox> warnBoxes;
    public ArrayList<OsdBox> errorBoxes;
    public ArrayList<OsdBox> drawBoxes;
    public int dividerSize;
    public int horizonPadding;
    public int verticalPadding;
    public int boxSize;
    public WH ruler;
    public Paint boxPaint;
    public Paint textPaint;
    public RectF anyBoxBounds;
    public Rect anyTextBounds;
    public int showStatus;
    public OsdBox touchedBox;
    private OnOsdBoxClickListener clickEvent;

    public OsdHealthBoxes(Context context) {
        super(context);
        this.context = context;
        this.dividerSize = 0;
        this.ruler = new WH(context);

        this.boxPaint = new Paint();
        boxPaint.setAntiAlias(true);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView v = new TextView(context);
        v.setTextSize(14);

        this.textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(v.getTextSize());

        anyBoxBounds = new RectF();
        anyTextBounds = new Rect();
        showStatus = ALL;

        dividerSize = ruler.getW(5);
        horizonPadding = ruler.getW(5);
        verticalPadding = ruler.getH(5);

        int totalSpace = dividerSize * (columnCount - 1) + horizonPadding * 2;
        int totalBoxSpace = ruler.getW(100) - totalSpace;
        boxSize = totalBoxSpace / 4;
    }

    public void setData(ArrayList<OsdBox> boxes) {
        this.boxes = new ArrayList<>(boxes);
        this.warnBoxes = new ArrayList<>();
        this.errorBoxes = new ArrayList<>();

        for (int i = 0; i < this.boxes.size(); i++) {
            OsdBox box = this.boxes.get(i);
            if (box.status == OsdBox.WARN_STATUS) {
                warnBoxes.add(box);
            } else if (box.status == OsdBox.ERROR_STATUS) {
                errorBoxes.add(box);
            }
        }
        drawBoxes = boxes;
        rowCount = getRow(drawBoxes);
        changeHeight(rowCount);
    }

    protected void changeHeight(int rowCount) {
        int viewHeight = boxSize * rowCount + dividerSize * (rowCount - 1) + verticalPadding * 2;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = viewHeight;
        setLayoutParams(params);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBox(canvas, drawBoxes);
    }

    protected void drawBox(Canvas canvas, ArrayList<OsdBox> boxes) {
        int itemCount = boxes.size();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                int index = i * columnCount + j;
                if (index >= itemCount) return;

                OsdBox box = boxes.get(index);
                int left = getBoxLeft(j);
                int top = getBoxTop(i);

                anyBoxBounds.set(left, top, left + boxSize, top + boxSize);
                boxPaint.setColor(box.getColor());
                canvas.drawRoundRect(anyBoxBounds, radius, radius, boxPaint);

                String text = box.value + "";

                textPaint.getTextBounds(text, 0, text.length(), anyTextBounds);
                int exactWidth = (int) textPaint.measureText(text, 0, text.length());
                int behindTextWidth = boxSize - exactWidth;
                int leftPadding = behindTextWidth / 2;
                int centerTextLeft = left + leftPadding;

                int behindTextHeight = boxSize - anyTextBounds.height();
                int topPadding = behindTextHeight / 2;
                int centerTextBottom = top + topPadding + anyTextBounds.height();

                canvas.drawText(text, centerTextLeft, centerTextBottom, textPaint);
            }
        }
    }

    public void setShowStatus(int status) {
        this.showStatus = status;
        if (showStatus == ALL) {
            drawBoxes = boxes;
        } else if (showStatus == WARN) {
            drawBoxes = warnBoxes;
        } else if (showStatus == ERROR) {
            drawBoxes = errorBoxes;
        }
        changeHeight(getRow(drawBoxes));
        invalidate();
    }

    protected int getRow(ArrayList<OsdBox> array) {
        int surplus = (array.size() % columnCount == 0) ? 0 : 1;
        return array.size() / columnCount + surplus;
    }

    protected int getBoxTop(int row) {
        if (row >= rowCount) return -1;

        return verticalPadding + boxSize * row + dividerSize * row;
    }

    protected int getBoxLeft(int column) {
        if (column >= columnCount) return -1;

        return horizonPadding + boxSize * column + dividerSize * column;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gesture.onTouchEvent(event, super.onTouchEvent(event));
    }

    private GestureAdapter gesture = new GestureAdapter() {
        @Override
        public void onDown(MotionEvent event) {
            touchedBox = null;

            if (event.getX() < horizonPadding) return;
            if (event.getX() > getMeasuredWidth() - horizonPadding) return;
            if (event.getY() < verticalPadding) return;
            if (event.getY() > getMeasuredHeight() - verticalPadding) return;

            float touchX = event.getX() - horizonPadding;
            float touchY = event.getY() - verticalPadding;

            float touchSize = boxSize + dividerSize;

            int clickColumn = (int) (touchX / touchSize);
            int clickRow = (int) (touchY / touchSize);
            int touchIndex = clickRow * columnCount + clickColumn;

            if (touchIndex < drawBoxes.size()) {
                touchedBox = drawBoxes.get(touchIndex);
            } else {
                touchedBox = null;
            }
        }

        @Override
        public void onMoveStart(MotionEvent event) {

        }

        @Override
        public void onMoving(MotionEvent event) {

        }

        @Override
        public void onClick(MotionEvent event) {
            if (touchedBox == null) return;

            if (event != null) {
                clickEvent.onClick(OsdHealthBoxes.this, touchedBox);
            }
        }

        @Override
        public void onMoveEnd(MotionEvent event) {

        }
    };

    public void setOnOsdBoxClickListener(OnOsdBoxClickListener event) {
        clickEvent = event;
    }
}
