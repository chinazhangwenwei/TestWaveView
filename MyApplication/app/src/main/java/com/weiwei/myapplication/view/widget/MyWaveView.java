package com.weiwei.myapplication.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.weiwei.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenwei on 2017/5/7.
 */

public class MyWaveView extends View {
    private int waveColor;
    private int waveHeight;
    private int waveSpeed;
    private int waveType;
    private int radius;
    private static final String TAG = "MyVaveView";
    private Paint mPiant;
    private Path mPath;
    private List<Point> mPoints;
    private float progress = 0.2f;
    private int measureHeight;
    private int measureWidth;
    private Handler mHandler;
    private int tempSpeed = 0;
    private int paintWidth = 2;
    private float myTextSize = 14;

    public MyWaveView(Context context) {
        this(context, null);
    }

    public MyWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs, context);

    }

    @TargetApi(21)
    public MyWaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initAttrs(AttributeSet attrs, Context context) {
        mHandler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
//        super.dispatchMessage(msg);
                tempSpeed += waveSpeed;
                if (tempSpeed > measureWidth) {
                    tempSpeed = tempSpeed - measureWidth;
                }
                postInvalidate();
            }
        };
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyWaveView);
        waveSpeed = typedArray.getInteger(R.styleable.MyWaveView_WaveSpeed, 10);
        waveHeight = typedArray.getDimensionPixelOffset(R.styleable.MyWaveView_WaveHeight, 10);
        waveColor = typedArray.getColor(R.styleable.MyWaveView_WaveColor, 0x00ffff00);
        waveType = typedArray.getInteger(R.styleable.MyWaveView_WaveViewType, 1);
        paintWidth = typedArray.getDimensionPixelSize(R.styleable.MyWaveView_PaintWidth, paintWidth);//都乘以密度
        myTextSize = typedArray.getDimension(R.styleable.MyWaveView_TextSize, myTextSize);
        mPiant = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiant.setDither(true);
        mPiant.setStrokeWidth(paintWidth);
        mPiant.setTextSize(myTextSize);
        mPath = new Path();
        mPath.reset();
        Log.d(TAG, "initAttrs:WaveHeight " + waveHeight);
        Log.d(TAG, "initAttrs: myTextSize" + myTextSize);
        Log.d(TAG, "initAttrs: paintWidth" + paintWidth);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measureWidth = getMeasuredWidth();
        measureHeight = getMeasuredHeight();
        radius = Math.min(measureWidth, measureHeight) / 2;

        mPoints = new ArrayList<>();
        int x = 0, y = 0;
        for (int i = 0; i < 9; i++) {
            int type = i % 4;
            switch (type) {
                case 0:
                    y = (int) ((1.0 - progress) * measureHeight);
                    break;

                case 2:

                    y = (int) ((1.0 - progress) * measureHeight);
                    break;
                case 1:

                    y = (int) ((1.0 - progress) * measureHeight) + waveHeight;
                    break;

                case 3:

                    y = (int) ((1.0 - progress) * measureHeight) - waveHeight;
                    break;
            }
            Point point = new Point(-measureWidth + i * measureWidth / 4, y);
            mPoints.add(point);
        }


    }

    private int measureHeight(int heightMeasureSpec) {
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (heightMeasureMode) {
            case MeasureSpec.EXACTLY:
                return MeasureSpec.getSize(heightMeasureSpec);
        }
        return 10 * waveHeight;
    }

    private int measureWidth(int widthMeasureSpec) {

        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (widthMeasureMode) {
            case MeasureSpec.EXACTLY:
                return MeasureSpec.getSize(widthMeasureSpec);
        }
        return 10 * waveHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawAngleQue(canvas);
        drawText(canvas);
        switch (waveType) {
            case 1:
                drawCircle(canvas);
                break;
            case 2:
                drawRect(canvas);
                break;
        }

    }

    //画覆盖圆环，画覆盖圆
    private void drawCircle(Canvas canvas) {
//measureHeight/2+measureWidth/2
        canvas.save();
        double suqLine = Math.pow(measureHeight / 2, 2) + Math.pow(measureWidth / 2, 2);
        int tempRaduis = (int) Math.sqrt(suqLine + 0.5);
        Log.d(TAG, "drawCircle: tempRaduis" + tempRaduis);
        Log.d(TAG, "drawCircle: radius" + radius);
        Log.d(TAG, "drawCircle: measureHeight" + measureHeight / 2);
        Log.d(TAG, "drawCircle: measureWidth" + measureWidth / 2);
        mPiant.setColor(Color.WHITE);
        canvas.translate(measureWidth / 2, measureHeight / 2);
        mPiant.setStrokeWidth((tempRaduis - radius) * 2);//记录 线的宽度会用均分
        mPiant.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, tempRaduis, mPiant);
        mPiant.setStrokeWidth(paintWidth);
        mPiant.setColor(waveColor);
        canvas.drawCircle(0, 0, radius, mPiant);
        canvas.restore();
    }

    //画方形框
    private void drawRect(Canvas canvas) {
        mPiant.setColor(waveColor);
        mPiant.setStrokeWidth(paintWidth);
        mPiant.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, measureWidth, measureHeight, mPiant);
    }

    //画波浪线
    private void drawAngleQue(Canvas canvas) {
        mPath.reset();
        mPiant.setColor(waveColor);
        mPiant.setStyle(Paint.Style.FILL);
        int i = 0;
        int tempHeight = (int) (progress * measureHeight);
        mPath.moveTo(mPoints.get(i).x + tempSpeed, mPoints.get(i).y - tempHeight);
        for (; i < mPoints.size() - 2; i += 2) {
            mPath.quadTo(mPoints.get(i + 1).x + tempSpeed, mPoints.get(i + 1).y - tempHeight,
                    mPoints.get(i + 2).x + tempSpeed, mPoints.get(i + 2).y - tempHeight);
        }
        mPath.lineTo(mPoints.get(i).x + tempSpeed, measureHeight);
        mPath.lineTo(mPoints.get(0).x + tempSpeed, measureHeight);
        mPath.close();
        canvas.drawPath(mPath, mPiant);
        mHandler.sendEmptyMessageDelayed(1, 50);

    }

    private void drawText(Canvas canvas) {
        canvas.save();
        int temp = (int) (progress * 100);
        String stringProgress = String.format("%d%%", temp);
        String text = Math.round(temp * 100) / 100 + "%";
//        String.f
//        mPiant.measureText()
        Rect rectf = new Rect();
        mPiant.getTextBounds(text, 0, text.length(), rectf);
        canvas.translate(measureWidth / 2, measureHeight / 2);
        if (progress > 0.5) {
            mPiant.setColor(Color.WHITE);
        } else {
            mPiant.setColor(waveColor);
        }
        canvas.drawText(text, -rectf.width() / 2, rectf.height() / 2, mPiant);
//        canvas.drawText();
        canvas.restore();
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
