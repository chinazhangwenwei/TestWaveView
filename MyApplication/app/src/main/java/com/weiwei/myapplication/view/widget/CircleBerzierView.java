package com.weiwei.myapplication.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wenwei on 2017/5/9.
 */

public class CircleBerzierView extends View {
    private int paintColor = Color.RED;
    private int paintWidth = 3;
    private Point points[] = new Point[12];
    private int centerX;
    private int centerY;
    private int radius;
    private Path mPath = new Path();
    private Paint mPaint = new Paint();
    private boolean flag = false;


    public CircleBerzierView(Context context) {
        this(context, null);
    }

    public CircleBerzierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBerzierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(paintColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        radius = Math.min(centerX, centerY) / 2;
        float ration = 0.551915024494f;
        int tempRadius = (int) (ration * radius);
        Point point0 = new Point();
        point0.set(0, radius);
        points[0] = point0;
        Point point1 = new Point();
        point1.set(tempRadius, radius);
        points[1] = point1;
        Point point2 = new Point();
        point2.set(radius, tempRadius);
        points[2] = point2;
        Point point3 = new Point();
        point3.set(radius, 0);
        points[3] = point3;


        Point point4 = new Point();
        point4.set(radius, -tempRadius);
        points[4] = point4;
        Point point5 = new Point();
        point5.set(tempRadius, -radius);
        points[5] = point5;
        Point point6 = new Point();
        point6.set(0, -radius);
        points[6] = point6;
        Point point7 = new Point();
        point7.set(-tempRadius, -radius);
        points[7] = point7;


        Point point8 = new Point();
        point8.set(-radius, -tempRadius);
        points[8] = point8;
        Point point9 = new Point();
        point9.set(-radius, 0);
        points[9] = point9;
        Point point10 = new Point();
        point10.set(-radius, tempRadius);
        points[10] = point10;
        Point point11 = new Point();
        point11.set(-tempRadius, radius);
        points[11] = point11;


    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(points[0].x, points[0].y + ((int) (bottomLineRation * radius)));
        int i = 1;
        for (; i < points.length - 3; i += 3) {
            if (i == 1) {
                mPath.cubicTo(points[i].x, points[i].y + ((int) (bottomRation * radius)),
                        points[i + 1].x + ((int) (leftRation * radius)),
                        points[i + 1].y, points[i + 2].x + ((int)
                                (leftLineRation * radius)), points[i + 2].y);
            }
            if (i == 4) {
                mPath.cubicTo(points[i].x + ((int) (leftRation * radius)), points[i].y, points[i + 1].x,
                        points[i + 1].y - ((int) (topRation * radius)), points[i + 2].x,
                        points[i + 2].y - ((int) (topLineRation * radius)));
            }
            if (i == 7) {
                mPath.cubicTo(points[i].x, points[i].y - ((int) (topRation * radius)), points[i + 1].x -
                                ((int) (rightRation * radius)),
                        points[i + 1].y, points[i + 2].x - ((int) (rightLineRation * radius)), points[i + 2].y);
            }

        }


        mPath.cubicTo(points[i].x - ((int) (rightRation * radius)), points[i].y, points[i + 1].x,
                points[i + 1].y + ((int) (bottomRation * radius)), points[0].x, points[0].y + ((int) (bottomLineRation * radius)));
        mPath.close();
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    private float leftRation = 0.0f;
    private float rightRation = 0.0f;
    private float topRation = 0.0f;
    private float bottomRation = 0.0f;
    private float leftLineRation = 0.0f;
    private float rightLineRation = 0.0f;
    private float topLineRation = 0.0f;
    private float bottomLineRation = 0.0f;


    public void setLeftRation(float ration) {
        this.leftRation = ration;
//        points[2].x += (int) (ration * radius / 2);
//        points[4].x = points[2].x;
//        leftRation =
        if (flag) {
            this.leftLineRation = ration;
        }
        invalidate();
    }


    public void setRightRation(float ration) {
        this.rightRation = ration;
//        points[8].x -= (int) (ration * radius / 2);
//        points[10].x = points[8].x;
//        rightRation = ration;
        if (flag) {
            this.rightLineRation = ration;
        }
        invalidate();
    }

    public void setTopRation(float ration) {
        this.topRation = ration;
//        points[5].y -= (int) (ration * radius / 2);
//        points[7].y = points[5].y;
        if (flag) {
            this.topLineRation = ration;
        }
        invalidate();

    }

    public void setBottomRation(float ration) {
        this.bottomRation = ration;
        if (flag) {
            this.bottomLineRation = ration;
        }
        invalidate();
    }

}
