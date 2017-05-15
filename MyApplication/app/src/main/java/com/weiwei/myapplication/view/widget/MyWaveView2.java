package com.weiwei.myapplication.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.weiwei.myapplication.R;

/**
 * Created by wenwei on 2017/5/8.
 */

public class MyWaveView2 extends View {

    private BitmapShader bitmapShader;
    private int waveColor1 = Color.RED;
    private int waveColor2 = Color.RED;
    private int paintWidth = 2;
    private int waveHeight = 20;
    private float rationHeight = 0.5f;

    private int measureWidth;
    private int measureHeight;
    private Paint mPaint;
    private boolean isCircle = true;
    private int radius;
    private Bitmap bitmap;
    private Paint mBordPaint;


    private Matrix shaderMatrix;


//    private int


    public MyWaveView2(Context context) {
        this(context, null);
    }

    public MyWaveView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWaveView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyWaveView2);
        waveColor1 = typedArray.getColor(R.styleable.MyWaveView2_wave_color1, waveColor1);
        waveColor2 = typedArray.getColor(R.styleable.MyWaveView2_wave_color2, waveColor2);
        paintWidth = typedArray.getDimensionPixelOffset(R.styleable.MyWaveView2_paint_width, paintWidth);
        waveHeight = typedArray.getDimensionPixelOffset(R.styleable.MyWaveView2_wave_height, waveHeight);
        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBordPaint.setDither(true);
        mBordPaint.setStrokeWidth(paintWidth);
        mBordPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureHeight(heightMeasureSpec);
        measureWidth(widthMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    private void measureHeight(int heightMeasureSpec) {
        int measureMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (measureMode) {
            case MeasureSpec.EXACTLY:
                measureHeight = MeasureSpec.getSize(heightMeasureSpec);
                break;

            default:
                measureHeight = 10 * waveHeight;
        }

//        return  0;
    }

    private void measureWidth(int widthMeasureSpec) {
//        return 0;
        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (measureMode) {
            case MeasureSpec.EXACTLY:
                measureWidth = MeasureSpec.getSize(widthMeasureSpec);
                break;
            default:
                measureWidth = 10 * waveHeight;

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        createBitmapShader();
        if (isCircle) {
            radius = Math.min(measureHeight, measureWidth) / 2;
        }
    }

    private static final String TAG = "MyWaveView2";

    private void createBitmapShader() {
        bitmap = Bitmap.createBitmap(measureWidth, measureHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // y = sinx+y
        int endY = measureHeight;
        int startX = 0;
        int endX = measureWidth;
        int tempHeight = (int) (rationHeight * measureHeight);
//        mPaint.setAlpha(80);
        mPaint.setColor(waveColor1 & 0x66ffffff);
        int[] waveY = new int[endX+1];
        for (; startX <=endX; startX++) {
            double sinX = Math.sin(2 * Math.PI * (1.0f * startX / endY));
            Log.d(TAG, "createBitmapShader: sinX" + sinX);
            int startY = (int) (waveHeight * sinX) + tempHeight;
            canvas.drawLine(startX, startY, startX, endY, mPaint);
            waveY[startX] = startY;
        }
        mPaint.setColor(waveColor2 & 0x99ffffff);
//
        startX = 0;
        int tranX = measureWidth / 3;
        for (; startX <= endX; startX++) {
//            double sinX = Math.sin(2 * Math.PI * (1.0f * startX / endY) + Math.PI / 2);
//            int startsY = (int) (waveHeight * sinX) + tempHeight;
            int tempStartY = waveY[(startX + tranX) % endX];
            Log.d(TAG, "createBitmapShader: startY" + tempStartY + (startX + 20) % endX);
            canvas.drawLine(startX, tempStartY, startX, endY, mPaint);
        }
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmapShader == null) {
            createBitmapShader();
        }
//        canvas.drawBitmap(bitmap, 0, 0, mPaint);
//        super.onDraw(canvas);
        if (mPaint.getShader() == null) {
            mPaint.setShader(bitmapShader);
            shaderMatrix = new Matrix();


        }
        shaderMatrix.preTranslate(10,0);
//        shaderMatrix.setScale(1.0001f,1.0001f);
        bitmapShader.setLocalMatrix(shaderMatrix);
        if (isCircle) {
//            canvas.drawCircle()
            drawCircle(canvas);

        } else {
            drawRect(canvas);
        }
    }

    private float waveShiftRation;

    public float getWaveShiftRation(float waveShiftRation) {
        return waveShiftRation;
    }

    public void setWaveShiftRation(float waveShiftRatio) {
        this.waveShiftRation = waveShiftRatio;
        Log.d(TAG, "setWaveShiftRation: waveShiftRation" + waveShiftRation);
//        postInvalidate();
        invalidate();
    }

    private void drawCircle(Canvas canvas) {
//        canvas.save();

//        canvas.translate(measureWidth / 2, measureHeight / 2);
        if (paintWidth > 0) {
            canvas.drawCircle(measureWidth / 2, measureHeight / 2, measureHeight / 2 - paintWidth / 2, mBordPaint);
        }
//        mPaint.setStrokeWidth(paintWidth);
//        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(waveColor1);
        canvas.drawCircle(measureHeight / 2, measureHeight / 2, measureHeight / 2, mPaint);
//        canvas.translate(-measureWidth / 2, -measureHeight / 2);
//        canvas.restore();
    }

    private void drawRect(Canvas canvas) {
//        canvas.save();
//        canvas.translate(measureWidth / 2, measureHeight / 2);
        mPaint.setStrokeWidth(paintWidth);
        canvas.drawRect(0, 0, measureWidth, measureHeight, mPaint);
//        canvas.restore();
    }
}
