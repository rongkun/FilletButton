package com.kun.filletbuttondemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kun.filletbuttondemo.R;

/**
 * Created by RK on 2017/6/13
 * 自定义圆角Button
 */

public class FilletButton extends View {

    private int textColor = Color.WHITE;//字体颜色
    private int bgColor = Color.YELLOW;//背景颜色
    private int bgColor_press = Color.GRAY;//按下时背景颜色
    private int color_current = Color.GRAY;//当前背景颜色
    private Rect mBound;
    private Paint p;
    private String text;
    private float textSize;
    private RectF rectF;
    private Context context;
    private float radius = 0;

    public FilletButton(Context context) {
        this(context,null);
    }

    public FilletButton(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FilletButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FilletButton, defStyleAttr, 0);
        for (int i = 0; i < array.getIndexCount(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.FilletButton_textsize_f:
                    textSize = array.getDimension(attr, 20);
                    break;
                case R.styleable.FilletButton_textcolor_f:
                    textColor = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.FilletButton_bgcolor_f:
                    bgColor = array.getColor(attr, Color.YELLOW);
                    color_current = bgColor;
                    break;
                case R.styleable.FilletButton_bgcolor_press_f:
                    bgColor_press = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.FilletButton_text_f:
                    text = array.getString(attr);
                    break;
                case R.styleable.FilletButton_radius_f:
                    radius = array.getDimension(attr,0f);
                    break;
            }
        }
        array.recycle();
        Init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else {
            p.setTextSize(textSize);
            p.getTextBounds(text, 0, text.length(), mBound);
            float textWidth = mBound.width();
            width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else {
            p.setTextSize(textSize);
            p.getTextBounds(text, 0, text.length(), mBound);
            float textHeight = mBound.height();
            height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
        }

        setMeasuredDimension(width, height);
    }

    private void Init(){
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        p = new Paint();
        mBound = new Rect();
        rectF = new RectF();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                color_current = bgColor_press;
                this.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_POINTER_UP:
                color_current =bgColor;
                this.invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawText(canvas);
    }

    private void drawBackground(Canvas canvas){
        float mRadius = radius;
        if (mRadius == 0.0){
            mRadius = getHeight()/2;
        }else {
            mRadius = radius;
        }
        rectF.set(0,0,getWidth(),getHeight());
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(color_current);
        canvas.drawRoundRect(rectF,mRadius,mRadius,p);
    }
    private void drawText(Canvas canvas){
        if (TextUtils.isEmpty(text)) return;
        p.setColor(textColor);
        p.setTextSize(textSize);
        p.setStyle(Paint.Style.FILL);
        Paint.FontMetricsInt fontMetrics = p.getFontMetricsInt();
        int baseline = (getHeight() - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text,getWidth()/2, baseline, p);
    }

    public FilletButton setBackgroundColor(@ColorRes int bgColorId, @ColorRes int bgColor_pressId){
        this.bgColor = ContextCompat.getColor(context, bgColorId);
        this.bgColor_press = ContextCompat.getColor(context, bgColor_pressId);
        color_current = this.bgColor;
        return this;
    }

    public FilletButton setText(@StringRes int strId){
        text = context.getString(strId);
        return this;
    }
    public FilletButton setText(String textStr){
        text = textStr;
        return this;
    }
    public FilletButton setTextColor(@ColorRes int colorId){
        textColor = ContextCompat.getColor(context, colorId);
        return this;
    }

    /**
     * 更改样式时需刷新控件
     */
    public void refresh(){
        this.invalidate();
    }
}
