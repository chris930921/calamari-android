package com.resourcelibrary.model.view.button;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.CompoundButton;

import com.resourcelibrary.model.logic.GestureAdapter;


/**
 * Created by User on 1/22/2015.
 */
public class IosSwitchNew extends CompoundButton {
    private Context context;
    private float trackRadius;
    private float thumbRadius;
    private float thumbPadding;
    private float thumbY;
    private float thumbX;
    private float thumbHorizonLimit;
    private boolean status;

    private float startX;
    private RectF trackBound;
    private Paint trackPen;
    private Paint thumbPen;
    private int trackFalseColor;
    private int trackTrueColor;

    private Bitmap thumbImage;
    private Bitmap trackImage;
    private Bitmap trackFalseImage;
    private Bitmap trackTrueImage;

    private int trackFalseResource;
    private int trackTrueResource;
    private int thumbResource;

    private boolean isCustomImage;
    private OnCheckedChangeListener event;

    public IosSwitchNew(Context context) {
        super(context);
        this.context = context;
        this.trackFalseColor = Color.parseColor("#C9CBCA");
        this.trackTrueColor = Color.parseColor("#35ED69");

        trackPen = new Paint();
        trackPen.setColor(trackFalseColor);
        trackPen.setStyle(Paint.Style.FILL);
        trackPen.setAntiAlias(true);

        thumbPen = new Paint();
        thumbPen.setColor(Color.parseColor("#FFFFFF"));
        thumbPen.setStyle(Paint.Style.FILL);
        thumbPen.setAntiAlias(true);

        thumbX = 0;
        isCustomImage = false;
    }

    public void setImage(int trackFalseResource, int trackTrueResource, int thumbResource) {
        isCustomImage = true;

        this.trackFalseResource = trackFalseResource;
        this.trackTrueResource = trackTrueResource;
        this.thumbResource = thumbResource;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        trackRadius = (w > h) ? h / 2 : w / 2;
        thumbRadius = trackRadius * 0.8f;
        thumbPadding = trackRadius * 0.1f;
        thumbHorizonLimit = w - trackRadius * 2;
        trackBound = new RectF(0, 0, w, h);
        thumbX = (status) ? thumbHorizonLimit : 0;
        thumbY = h / 2;

        if (isCustomImage) {
            thumbImage = getThumbImage(h);
            trackFalseImage = getTrackImage(w, h, trackFalseResource);
            trackTrueImage = getTrackImage(w, h, trackTrueResource);
            trackImage = (status) ? trackTrueImage : trackFalseImage;
        }
    }

    private Bitmap getThumbImage(int h) {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), thumbResource);
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale((float) h / imageWidth, (float) h / imageHeight);

        image = Bitmap.createBitmap(image, 0, 0, (int) imageWidth, (int) imageHeight, matrix, true);
        return image;
    }

    private Bitmap getTrackImage(int w, int h, int resourceId) {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), resourceId);
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale((float) w / imageWidth, (float) h / imageHeight);

        image = Bitmap.createBitmap(image, 0, 0, (int) imageWidth, (int) imageHeight, matrix, true);
        return image;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isCustomImage) {
            canvas.drawBitmap(trackImage, 0, 0, trackPen);
            canvas.drawBitmap(thumbImage, thumbX, 0, thumbPen);

        } else {
            canvas.drawRoundRect(trackBound, trackRadius, trackRadius, trackPen);
            float thumbOriginX = thumbX + thumbPadding + thumbPadding / 2 + thumbRadius;
            canvas.drawCircle(thumbOriginX, thumbY, thumbRadius, thumbPen);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gesture.onTouchEvent(event, super.onTouchEvent(event));
    }

    private GestureAdapter gesture = new GestureAdapter() {

        @Override
        public void onDown(MotionEvent event) {
        }

        @Override
        public void onMoveStart(MotionEvent event) {
            startX = event.getX();
        }

        @Override
        public void onMoving(MotionEvent event) {
            thumbX += (event.getX() - startX) * 2;
            thumbX = (thumbX > thumbHorizonLimit) ? thumbHorizonLimit : thumbX;
            thumbX = (thumbX < 0) ? 0 : thumbX;
            invalidate();
            startX = event.getX();
        }

        @Override
        public void onClick(MotionEvent event) {
            setChecked(!status);
            invalidate();
        }

        @Override
        public void onMoveEnd(MotionEvent event) {
            setChecked(thumbX > thumbHorizonLimit / 2);
            invalidate();
        }
    };

    @Override
    public void setChecked(boolean checked) {
        status = checked;
        thumbX = (status) ? thumbHorizonLimit : 0;
        trackImage = (status) ? trackTrueImage : trackFalseImage;

        if (trackPen != null) {
            trackPen.setColor((status) ? trackTrueColor : trackFalseColor);
        }
        if (event != null) {
            event.onCheckedChanged(this, status);
        }
    }

    @Override
    public boolean isChecked() {
        return status;
    }

    @Override
    public void toggle() {
        setChecked(!status);
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        event = l;
    }
}