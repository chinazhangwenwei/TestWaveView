package com.weiwei.myapplication.view.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.weiwei.myapplication.R;

/**
 * Created by wenwei on 2017/5/9.
 */

public class RaderView extends View {
    private int raderViewColor = Color.RED;
    private int raderViewLineColor = Color.BLACK;
    private int paintWidth = 5;
    private int radius;
    private int raderViewCircleCount = 5;
    private Paint mPaint;
    private SweepGradient sweepGradient;
    private Paint sweepPaint;
    private Matrix matrix;
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
//            super.dispatchMessage(msg);
            invalidate();
            if (msg.what == 1) {
                handler.sendEmptyMessageDelayed(1, 350);
            }
        }
    };

    public RaderView(Context context) {
        this(context, null);
    }

    public RaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RaderView);
        paintWidth = typedArray.getDimensionPixelOffset(R.styleable.RaderView_rader_paint_width, paintWidth);
        raderViewColor = typedArray.getColor(R.styleable.RaderView_rader_color, raderViewColor);
        raderViewCircleCount = typedArray.getInt(R.styleable.RaderView_rader_circle_count, raderViewCircleCount);
        raderViewLineColor = typedArray.getColor(R.styleable.RaderView_rader_line_colro, raderViewLineColor);

        typedArray.recycle();
        matrix = new Matrix();
        sweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sweepPaint.setDither(true);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(paintWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(raderViewLineColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                break;
            default:
                heightSize = Math.min(heightSize, 200);
        }
        return heightSize;
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                break;
            default:
                widthSize = Math.min(widthSize, 200);
        }
        return widthSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(getMeasuredHeight(), getMeasuredWidth()) / 2;
        radius = radius - getPaddingLeft() - paintWidth / 2;
        sweepGradient = new SweepGradient(getMeasuredWidth() / 2,
                getMeasuredHeight() / 2,
                Color.TRANSPARENT, raderViewColor);
        sweepPaint.setShader(sweepGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawCircleAndLine(canvas);
        drawShaderCircle(canvas);

    }

    private void drawCircleAndLine(Canvas canvas) {
        mPaint.setColor(raderViewLineColor);
        canvas.save();
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        float tempRadius = radius * 1.0f / raderViewCircleCount;
        for (int i = 1; i <= raderViewCircleCount; i++) {
            canvas.drawCircle(0, 0, tempRadius * i, mPaint);
        }
        canvas.drawLine(0, -radius, 0, radius, mPaint);
        canvas.drawLine(-radius, 0, radius, 0, mPaint);
        canvas.restore();
    }

    int temp = 0;

    private void drawShaderCircle(Canvas canvas) {
        temp++;
        if(temp>360){
            temp=0;
        }
        matrix.preRotate(temp, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        sweepGradient.setLocalMatrix(matrix);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, radius, sweepPaint);

    }

    private static final String TAG = "RaderView";
    public void startRader() {
        handler.sendEmptyMessageDelayed(1, 200);
        Log.d(TAG, "startRader: start");
    }

    public void stopRader() {
        handler.sendEmptyMessageDelayed(2, 200);
    }


}
