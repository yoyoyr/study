package com.test.viewpagedemo.Views.SelfView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import com.test.viewpagedemo.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

public class LineChar extends View {

    Paint paint, shaderPaint;
    Paint linkPaint;//画链接xy轴的画笔
    float x, y;
    float width, height;
    List<Point> xPoint, yPoint;
    List<LineBean> points;//point集合
    int selectPoint;//点击的点所在索引
    float percent;
    int xPointCount;
    Path path;
    int max = 0;
    float moveLeft;
    //折线最右端的坐标
    float maxRight;
    //到达最大左右边距之后，还能拉的距离
    float externLen;
    int upMotionEvent;
    ValueAnimator valueAnimator;
    VelocityTracker velocityTracker;
    /**
     * View滑动相关的方法
     * scrollTo()   滑动到某个点
     * scrollBy()   相对当前位置滑动一段距离
     * <p>
     * Scroller滑动相关方法
     * fill()
     * startScroll()
     * 原理还是interpolator不断的计算当时应该所在位置
     */
    OverScroller scroller, scrollerS;
    int maxVelocity;

    public LineChar(Context context) {
        super(context);
        init(context, null);
    }

    public LineChar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        linkPaint = new Paint();
        linkPaint.setColor(Color.GRAY);
        linkPaint.setAntiAlias(true);
        linkPaint.setStyle(Paint.Style.FILL);
        linkPaint.setStrokeWidth(5);
        shaderPaint = new Paint();
        shaderPaint.setColor(Color.BLACK);
        shaderPaint.setAntiAlias(true);
        shaderPaint.setStyle(Paint.Style.FILL);


        path = new Path();

        xPoint = new ArrayList<>();
        yPoint = new ArrayList<>();
        points = new ArrayList<>();

        selectPoint = -1;
        percent = 0;
        xPointCount = 8;
        moveLeft = 0;
        maxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        scroller = new OverScroller(getContext());
        scrollerS = new OverScroller(getContext());
        externLen = 100;

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();

                /**
                 * requestLayout() measure,layout,draw.调用viewrootImpl.performTraversals(),
                 * postInvalidate  子线程调用,onDraw() 调用viewrootImpl.performTraversals()
                 * invalidate  主线程调用，onDraw() 调用viewrootImpl.performTraversals()
                 *
                 *
                 */
                requestLayout();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        if (!scroller.isFinished()) {
            scroller.abortAnimation();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        LoggerUtils.LOGD("onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        //无论什么模式，都填充满父控件
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        LoggerUtils.LOGD("onLayout");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        LoggerUtils.LOGD("onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        x = 50;
        y = getHeight() - 50;
        width = getWidth();
        height = getHeight();
        float deviX = (getWidth() - x) / xPointCount;
        float deviY = (y) / xPointCount;
        float offsetX = 0;
        float offsetY = 0;
        int lineLen = 5;
        //折线阴影
        shaderPaint.setShader(new LinearGradient(0, 0, width, height, Color.BLACK, Color.WHITE,
                Shader.TileMode.CLAMP));
        xPoint.clear();
        yPoint.clear();
        for (int i = 0; i < points.size(); ++i) {
            xPoint.add(new Point((int) (x + offsetX), (int) y));
            offsetX += deviX;

            yPoint.add(new Point((int) x, (int) (offsetY)));
            offsetY += deviY;

            float yPoint = (1 - (float) points.get(i).value / max) * y;
            points.get(i).x = xPoint.get(i).x;
            points.get(i).y = yPoint;
//            LoggerUtils.LOGD("lineBean = " + points.get(i).toString());
        }
        maxRight = xPoint.get(xPoint.size() - 1).x;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
//        LoggerUtils.LOGD("onDraw");
        super.onDraw(canvas);

        drawXLine(canvas);
        drawYLine(canvas);
        drawPoint(canvas);
    }

    private void drawPoint(@NonNull Canvas canvas) {
        int drawPointCount = (int) (percent * points.size());
        paint.setStrokeWidth(5);
        if (drawPointCount > 0) {
            path.reset();
            paint.setStyle(Paint.Style.FILL);
            float deviX = (getWidth() - x) / 5;
            for (int i = 0; i < drawPointCount; ++i) {
                if (i == 0) {
//                    LoggerUtils.LOGD("first point = " + (points.get(i).x - moveLeft) +
//                            "point x = " + points.get(i).x + ",left = " + moveLeft);
                    path.moveTo(points.get(i).x - moveLeft, points.get(i).y);
                } else {
                    //三阶贝塞尔曲线
                    path.cubicTo(points.get(i - 1).x + deviX / 2 - moveLeft, points.get(i - 1).y,
                            points.get(i).x - moveLeft - deviX / 2, points.get(i).y,
                            points.get(i).x - moveLeft, points.get(i).y);
                }

                if (selectPoint == i) { //选择的点
                    LineBean point = points.get(i);
                    String text = point.text;
                    float len = paint.measureText(text);
                    canvas.drawCircle(points.get(i).x - moveLeft, points.get(i).y, 10, paint);
                    canvas.drawLine(0, point.y, getWidth(), point.y, linkPaint);
                    canvas.drawLine(point.x, 0, point.x, getHeight(), linkPaint);
                    canvas.drawText(text, points.get(i).x - len / 2 - moveLeft, points.get(i).y - 10, paint);
                }
            }
            path.lineTo(points.get(drawPointCount - 1).x - moveLeft, y);
            path.lineTo(x - moveLeft, y);
            path.close();
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);
        } else {
//            LoggerUtils.LOGE("point count = " + drawPointCount);
        }
    }

    private void drawYLine(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        float endX = x;
        float endY = 0 + 10;

        canvas.drawLine(x, y, endX, endY, paint);

        path.reset();
        path.moveTo(x - 10, endY);
        path.lineTo(x, 0);
        path.lineTo(x + 10, endY);
        canvas.drawPath(path, paint);

        for (int i = 0; i < yPoint.size(); ++i) {
            canvas.drawLine(yPoint.get(i).x, yPoint.get(i).y, yPoint.get(i).x + 5, yPoint.get(i).y, paint);
        }
    }

    private void drawXLine(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        float endX, endY;
        endX = getWidth() - 10;
        endY = y;
        canvas.drawLine(x, y, endX, endY, paint);

        //画x轴末端的三角箭头
        path.reset();
        path.moveTo(endX, endY - 10);
        path.lineTo(endX + 10, endY);
        path.lineTo(endX, endY + 10);
        canvas.drawPath(path, paint);


        paint.setTextSize(25);
        for (int i = 0; i < xPoint.size(); ++i) {
            //画线上的点
            canvas.drawLine(xPoint.get(i).x, xPoint.get(i).y - 5, xPoint.get(i).x, xPoint.get(i).y, paint);

            //写文字
            String text = "第" + i + "个";
            float txLen = paint.measureText(text);
            canvas.drawText(text, xPoint.get(i).x - txLen / 2, xPoint.get(i).y + 20, paint);
        }

    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getY() >= y / 5) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    float oldTouchX, oldTouchY, downX, downY, lastPointX;

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            selectPoint = -1;
            downX = oldTouchX = event.getX();
            downY = oldTouchY = event.getY();
            //关闭动画
            if (!scroller.isFinished()) {
                scroller.abortAnimation();
            }
            scrollerS.abortAnimation();
            if (velocityTracker == null) {
                velocityTracker = VelocityTracker.obtain();
            }
            velocityTracker.addMovement(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.abs(oldTouchX - event.getX()) <= 20) {
                return true;
            }
            velocityTracker.addMovement(event);
            float move = oldTouchX - event.getX();
            //左边界
            if (moveLeft + move > -externLen && moveLeft + move + width <= maxRight + externLen) {
                moveLeft += move;
                //右边界
            } else if (moveLeft + move + width > maxRight + externLen) {
                moveLeft = maxRight - width + externLen;
            } else {
                moveLeft = -externLen;
            }
            oldTouchX = event.getX();
            invalidate();

            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (Math.abs(downX - event.getX()) <= 20 && Math.abs(downY - event.getY()) <= 20) {//点击事件，选择出点击的点
                upMotionEvent = 0;
                for (int i = 0; i < points.size() - 1; ++i) {
                    LineBean left = points.get(i);
                    LineBean right = points.get(i + 1);
                    float pointX = event.getX();

                    if (Math.abs(left.x - pointX) <= 5) {
                        selectPoint = i;
                        postInvalidate();
                        return true;
                    } else if (Math.abs(right.x - pointX) <= 5) {
                        selectPoint = i + 1;
                        postInvalidate();
                        return true;
                    } else if (left.x < pointX && right.x > pointX) {
                        if (Math.abs(left.x - pointX) <= Math.abs(right.x - pointX)) {
                            selectPoint = i;
                            postInvalidate();
                            return true;
                        } else {
                            selectPoint = i + 1;
                            postInvalidate();
                            return true;
                        }

                    }

                }
            } else if (moveLeft < 0) {
//                LoggerUtils.LOGD("move left = " + moveLeft);
                upMotionEvent = 1;
                scrollerS.startScroll(0, 0, (int) -moveLeft, 0);
                postInvalidate();
            } else {
                upMotionEvent = 3;
                velocityTracker.computeCurrentVelocity(1000, maxVelocity);
                float speed = velocityTracker.getXVelocity();
//                LoggerUtils.LOGD("speed = " + speed);
                velocityTracker.clear();
                lastPointX = event.getX();
                scroller.fling((int) event.getX(), (int) event.getY(), (int) (speed),
                        0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0, 50, 0);
                postInvalidate();
            }
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset() && upMotionEvent == 3) {
            float move = (lastPointX - scroller.getCurrX());
//            LoggerUtils.LOGD("move = " + move + ",move left = " + moveLeft + ",width = " + width
//                    + ",max x = " + xPoint.get(xPoint.size() - 1).x);
            if (moveLeft + move > 0 && moveLeft + move + width <= maxRight) {
                moveLeft += move;
                lastPointX = scroller.getCurrX();
//                //右边界
            } else if (moveLeft + move + width > maxRight) {
                moveLeft = maxRight - width;
                scroller.abortAnimation();
            } else {
                moveLeft = 0;
                scroller.abortAnimation();
            }
            postInvalidate();
        } else if (scrollerS.computeScrollOffset() && upMotionEvent == 1) {
//            LoggerUtils.LOGD("currx = " + scrollerS.getCurrX());
            moveLeft = scrollerS.getCurrX() - 100;
            postInvalidate();
        }
    }

    public void setData(@NonNull List<Integer> values, @NonNull List<String> datas) {

        selectPoint = -1;
        for (int i = 0; i < values.size(); ++i) {
            max = Math.max(max, values.get(i));
        }
//        LoggerUtils.LOGD("max = " + max);
        int dev = max / 5;
        float maxY = y;

        for (int i = 0; i < values.size(); ++i) {
            LineBean lineBean = new LineBean();
            lineBean.value = values.get(i);
            lineBean.text = datas.get(i);

            points.add(lineBean);
        }

        valueAnimator.start();
    }

}
