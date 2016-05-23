package com.carlosolmedo.badgeview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by Carlos Olmedo on 19/5/16.
 */
public class BadgeView extends View {


    private static class SavedState extends BaseSavedState {

        String text;
        int badgeColor;
        int textColor;
        int textSize;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            text = in.readString();
            badgeColor = in.readInt();
            textColor = in.readInt();
            textSize = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(text);
            out.writeInt(badgeColor);
            out.writeInt(textColor);
            out.writeInt(textSize);

        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }


    private static final int BADGE_INNER_PADDING = 6;
    private static final int DEFAULT_TEXT_SIZE = 14;

    private Paint textPaint;
    private Paint rectanglePaint;

    private String text;
    private int badgeColor;
    private int textColor;
    private int textSize;

    public BadgeView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(attrs);

        setupTextPaint();
        setupRectanglePaint();

    }

    private void setupTextPaint() {
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(convertDpToPixel(textSize, getContext()));
    }

    private void setupRectanglePaint() {
        rectanglePaint = new Paint();
        rectanglePaint.setColor(badgeColor);
        rectanglePaint.setAntiAlias(true);
    }

    public void init(AttributeSet attrs) {

        setSaveEnabled(true);

        // Go through all custom attrs.
        TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.badge);

        badgeColor = attrsArray.getColor(R.styleable.badge_badge_fillColor, Color.RED);
        textColor = attrsArray.getColor(R.styleable.badge_badge_textColor, Color.WHITE);
        text = attrsArray.getString(R.styleable.badge_badge_text);
        if (text==null) {
            text = "";
        }
        textSize = attrsArray.getInteger(R.styleable.badge_badge_textSize, DEFAULT_TEXT_SIZE);

        // Google tells us to call recycle.
        attrsArray.recycle();
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.text = text;
        ss.textColor = textColor;
        ss.textSize = textSize;
        ss.badgeColor = badgeColor;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        text = ss.text;
        textColor = ss.textColor;
        textSize = ss.textSize;
        badgeColor = ss.badgeColor;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        //drawBadgeCircle(canvas);
        drawBadgeRectangle(canvas);
        drawBadgeText(canvas);

    }

    private void drawBadgeText(Canvas canvas) {

        float x = getWidth()/2;
        //the y coordinate marks the bottom of the text, so we need to factor in the height
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        float y = getHeight()  - getPaddingBottom() - convertDpToPixel(BADGE_INNER_PADDING, getContext());

        canvas.drawText(text, x, y, textPaint);
    }

    private void drawBadgeRectangle(Canvas canvas) {

        float left = getPaddingLeft();
        float top = getPaddingTop();
        float right = getWidth() - getPaddingRight();
        float bottom = getHeight() - getPaddingBottom();

        float rX = getHeight();
        float rY = getHeight();

        RectF bounds = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(bounds, rX, rY, rectanglePaint);

        //canvas.drawRect(left, top, right, bottom, rectanglePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        /*
        int max = Math.max(width, height);
        setMeasuredDimension(max, max);
        */
        setMeasuredDimension(Math.max(width, height), height);
    }

    private int measureHeight(int measureSpec) {
        //determine height
        int size = getPaddingTop() + getPaddingBottom();
        //size += textPaint.getFontSpacing();
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        size += bounds.height();

        //2dp of padding top and bottom
        size += convertDpToPixel(BADGE_INNER_PADDING, getContext()) * 2;

        return resolveSizeAndState(size, measureSpec, 0);
    }

    private int measureWidth(int measureSpec) {
        //determine width
        int size = getPaddingLeft() + getPaddingRight();
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        size += bounds.width();

        //2dp of padding left and right
        size += convertDpToPixel(BADGE_INNER_PADDING, getContext()) * 2;

        return resolveSizeAndState(size, measureSpec, 0);
    }

    /**
     * Change badge text
     * @param text Text to show
     */
    public void setBadgeText(@Nullable String text) {
        if (text==null) {
            text = "";
        }
        this.text = text;
        invalidate();
        requestLayout();
    }

    public void setBadgeTextSize(int textSize) {
        this.textSize = textSize;
        setupTextPaint();
        invalidate();
        requestLayout();
    }

    public void setBadgeColor(int color) {
        this.badgeColor = color;
        setupRectanglePaint();
        invalidate();
    }

    public void setTextColor(int color) {
        this.textColor = color;
        setupTextPaint();
        invalidate();
    }


    private static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    private static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }



}
