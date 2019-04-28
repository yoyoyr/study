package com.test.viewpagedemo.Views.SelfView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Line extends View {

    Paint paint, fillPaint;
    int defWidth, defHeight;
    float x, y;
    float width, height;
    List<Point> xPoint, yPoint;
    @Nullable
    List<LineB> points;
    int selectPoint;
    int xPointCount;
    Path path;
    //折线最右端的坐标
    float maxRight;
    //到达最大左右边距之后，还能拉的距离
    float externLen;
    int upMotionEvent;
    int maxVelocity;
    onPointClickListener onPointClickListener;

    public void setOnPointClickListener(Line.onPointClickListener onPointClickListener) {
        this.onPointClickListener = onPointClickListener;
    }

    public void setData(@Nullable List<LineB> values) {
        selectPoint = -1;
        if (values == null || values.size() <= 0) {
//            LoggerUtils.LOGE("value is null!");
            return;
        }
        for (int i = 0; i < values.size(); ++i) {
            LineB line = values.get(i);
            LineB lineB = new LineB();
            lineB.bottomLeftText = line.bottomLeftText;
            lineB.bottomRightText = line.bottomRightText;
            lineB.value = line.value;

            if (getWidth() >= 0) {
                lineB.x = (1 + i) * getWidth() / values.size();
            }
        }
        points = values;
        postInvalidate();
    }

    public void setTextSize(int size) {
        paint.setTextSize(size);
        postInvalidate();
    }


    public Line(Context context) {
        super(context);
        init(context, null);
    }

    public Line(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setTextSize(26);
        paint.setStyle(Paint.Style.STROKE);
        fillPaint = new Paint();
        fillPaint.setColor(Color.RED);
        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.FILL);

        path = new Path();

        xPoint = new ArrayList<>();
        yPoint = new ArrayList<>();
        points = new ArrayList<>();

        selectPoint = -1;
        xPointCount = 8;
        maxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        externLen = 100;
        defWidth = 500;
        defHeight = 100;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        LoggerUtils.LOGD("onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        if (heightModel == MeasureSpec.AT_MOST) {
            height = defHeight;
        }
        if (widthModel == MeasureSpec.AT_MOST) {
            height = defWidth;
        }
        //无论什么模式，都填充满父控件
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        y = getHeight() / 2;
        for (int i = 0; i < points.size(); ++i) {
            LineB line = points.get(i);
            line.x = (1 + i) * getWidth() / (points.size() + 1);
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
//        LoggerUtils.LOGD("onDraw");
        super.onDraw(canvas);
        drawPoint(canvas);
    }

    private void drawPoint(@NonNull Canvas canvas) {
        if (points.size() > 0) {
            path.reset();
            int radius = 15;
            int padding = 20;
            for (int i = 0; i < points.size(); ++i) {
                String text = points.get(i).value;
                float len = paint.measureText(text);
                canvas.drawText(text, points.get(i).x - len / 2, y - 40, paint);
                if (i == 0) {
                    canvas.drawLine(0, y, points.get(i).x - radius, y, paint);
                } else {
                    canvas.drawLine(points.get(i - 1).x + radius, y, points.get(i).x - radius, y, paint);
                }
                if (selectPoint == i) {
                    canvas.drawCircle(points.get(i).x, y, radius, fillPaint);

                    len = paint.measureText(points.get(i).getBottomRightText());
                    canvas.drawText(points.get(i).getBottomLeftText(), padding, getHeight() - 20, paint);
                    canvas.drawText(points.get(i).getBottomRightText(), getWidth() - len - padding, getHeight() - 20, paint);

                } else {
                    canvas.drawCircle(points.get(i).x, y, radius, paint);
                }
            }
            canvas.drawLine(points.get(points.size() - 1).x + radius, y, getWidth(), y, paint);
        } else {
//            LoggerUtils.LOGE("point count = " + points.size());
        }
    }

    float oldTouchX, oldTouchY, downX, downY, lastPointX;

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = oldTouchX = event.getX();
            downY = oldTouchY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (Math.abs(downX - event.getX()) <= 50 && Math.abs(downY - event.getY()) <= 50) {
                upMotionEvent = 0;
                for (int i = 0; i < points.size(); ++i) {
                    if (points.get(i).x + 50 >= event.getX() && points.get(i).x - 50 <= event.getX()) {
                        selectPoint = i;
//                        LoggerUtils.LOGD("click " + selectPoint);
                        if (onPointClickListener != null) {
                            onPointClickListener.onClick(points.get(selectPoint));
                        }
                        postInvalidate();
                        return true;
                    }
                }
            }
        }
        return true;
    }


    public interface onPointClickListener {
        void onClick(LineB lineB);
    }
}
