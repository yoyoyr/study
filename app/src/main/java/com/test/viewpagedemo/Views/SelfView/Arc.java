package com.test.viewpagedemo.Views.SelfView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.study.point.R;

import java.util.ArrayList;
import java.util.List;

public class Arc extends View {

    Paint paint;
    Path path;
    int color;
    int location;
    int defLen;
    List<ArcBean> arcs;
    float total;
    float x, y;//圆心的坐标
    RectF out;
    RectF in;
    float percent;
    ValueAnimator valueAnimator;

    public Arc(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {

        defLen = 200;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        location = array.getInt(R.styleable.MyView_location, 0);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);

        arcs = new ArrayList<>();
        path = new Path();
        x = y = 0;

        /**
         * view动画
         * 帧动画
         * 属性动画
         * AnimatorUpdateListener 监听每一帧
         * AnimatorListener  监听特定阶段
         * ObjectAnimator 要求改变的属性必须有对应的setXXX,getXXX方法
         *  如果没有这些方法，可以用装饰方式提供set get方法或者使用valueAnimator
         */

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
//                LoggerUtils.LOGD("requestLayout");
                postInvalidate();
                //只会调用onDraw()
//                invalidate();
            }
        });
    }

    public Arc(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 记得关闭动画
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        out = new RectF(0, 0, getWidth(), getHeight());
        in = new RectF(getWidth() / 8, getHeight() / 8, getWidth() * 7 / 8, getHeight() * 7 / 8);
        x = (getWidth() / 2);
        y = getHeight() / 2;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        if (widthModel == MeasureSpec.AT_MOST || heightModel == MeasureSpec.AT_MOST) {
//            LoggerUtils.LOGD("len = " + defLen);
            setMeasuredDimension(defLen, defLen);
            return;
        }

        int len = Math.max(width, height);
//        LoggerUtils.LOGD("len = " + len);
        setMeasuredDimension(len, len);
        return;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        LoggerUtils.LOGD("onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
//        LoggerUtils.LOGD("onDraw");
        super.onDraw(canvas);
        //画圆弧
        if (arcs.size() <= 0) {
        path.arcTo(in, 0, 180);
        path.arcTo(out, 180, -180);
        path.arcTo(in, 180, 180);
        path.arcTo(out, 360, -180);
//        canvas.drawRect(out, paint);
//        canvas.drawRect(in, paint);
        canvas.drawPath(path, paint);
        } else {
            for (int i = 0; i < arcs.size(); ++i) {
                ArcBean arcBean = arcs.get(i);
                path.arcTo(in, arcBean.startAngle * percent, arcBean.switchAngle * percent);
                path.arcTo(out, arcBean.endAngle * percent, -arcBean.switchAngle * percent);
                paint.setColor(arcBean.color);
                canvas.drawPath(path, paint);
                path.reset();
            }
    }

}

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (Math.pow(Math.abs(event.getY() - y), 2) +
                    Math.pow(Math.abs(event.getX() - x), 2) <=
                    Math.pow(getWidth() * 3 / 8, 2)) {
//                LoggerUtils.LOGD("down point on inner circle!");
                return false;
            } else if (Math.pow(Math.abs(event.getY() - y), 2) +
                    Math.pow(Math.abs(event.getX() - x), 2) >
                    Math.pow(getWidth() / 2, 2)) {
//                LoggerUtils.LOGD("down point out outter circle!");
                return false;

            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 坐标已经转换为子控件的
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float clickX = event.getX();
            float clickY = event.getY();
            //计算 弧度
            double atan2 = Math.atan2(clickY - y, clickX - x);
            //根据弧度算出角度
            double angle = atan2 * 180 / Math.PI;
            if (angle < 0) {
                angle = 360 + angle;
            }

            for (int i = 0; i < arcs.size(); ++i) {
                if (arcs.get(i).startAngle < angle && arcs.get(i).endAngle >= angle) {
//                    LoggerUtils.LOGD("click arc" + i);
                }
            }
        }
        return true;
    }

    public void setData(@Nullable List<Integer> data, @NonNull List<Integer> colors) {
        if (data != null && data.size() > 0) {
            arcs.clear();
            total = 0;
            float startAngle = 0;
            for (Integer i : data) {
                total += i;
            }
            for (int i = 0; i < data.size(); ++i) {
                ArcBean arcBean = new ArcBean();
                arcBean.startAngle = startAngle;
                arcBean.endAngle = arcBean.startAngle + (data.get(i) / total) * 360;
                startAngle = arcBean.endAngle;
                arcBean.switchAngle = arcBean.endAngle - arcBean.startAngle;
                arcBean.value = data.get(i);
                arcBean.color = colors.get(i);
//                LoggerUtils.LOGD("arc = " + arcBean.toString());
                arcs.add(arcBean);
            }

            arcs.get(data.size() - 1).endAngle = 360;
            valueAnimator.start();
            return;
        }
//        LoggerUtils.LOGE("data is null!");
    }

}
