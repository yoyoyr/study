package com.test.viewpagedemo.Views.SelfView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.OverScroller;

import com.test.viewpagedemo.LoggerUtils;


/***
 * Android Matrix  安卓控件移动 ？？？
 *
 * 滑动相关
 * offsetLeftAndRight(int offset)	水平方向挪动View。offset为正则x轴正向移动，移动的是整个View，getLeft()会变的
 * scrollTo(int x, int y)	将View中内容（不是整个View）滑动到对应的位置，參考坐标原点为ParentView左上角，
 * x，y为正则向xy轴反方向移动，反之同理。x y会调用canvas.translate(x,y)设置
 *
 * 点击事件传递链需要深入了解？？？？
 */
public class MyViewGroup extends ViewGroup {


    int childWidth, childHeight;
    static int marginTop = 20;
    float lastX, lastY, mSlop, origX, origY;
    OverScroller scroller;
    @Nullable
    View touchView;

    public MyViewGroup(Context context) {
        super(context);
        init(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        scroller = new OverScroller(context);
        mSlop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        ViewConfiguration.getWindowTouchSlop();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        LoggerUtils.LOGV("onMeasure");
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //wrap_content = AT_MOST MATCH_PARENT = EXACTLY
        LoggerUtils.LOGD(MeasureSpec.AT_MOST + "----" + MeasureSpec.EXACTLY);
        LoggerUtils.LOGD("width = " + width + "width model =" + widthModel + ",height = " + height + ",height model = " + heightModel);


        childWidth = 0;//容器的宽高
        childHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                /**
                 *测量子View大小
                 */
                measureChild(child, widthMeasureSpec,
                        heightMeasureSpec);
                childWidth = child.getMeasuredWidth();
                childHeight += child.getMeasuredHeight();
            }
        }
//        LoggerUtils.LOGD("childWidth = " + childWidth + ",childHeight = " + childHeight);

        /**
         * resolveSize（）调整ViewGroup的大小
         */
        setMeasuredDimension(resolveSize(childWidth, widthMeasureSpec), resolveSize(childHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LoggerUtils.LOGV("onLayout");
        int childTop = t;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
//                LoggerUtils.LOGD("childWidth = " + childWidth + ",childHeight = " + childHeight + ",childTop = " + childTop);
                child.layout(0, childTop, childWidth, childTop + childHeight);
                childTop += childHeight + marginTop;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        LoggerUtils.LOGV("onDraw");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LoggerUtils.LOGV("onDraw");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean r = super.dispatchTouchEvent(ev);
        LoggerUtils.LOGV("event = " + ev.getAction() + "r = " + r);
        return r;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        LoggerUtils.LOGV("onTouchEvent");
        boolean r = false;
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) {
            r = true;
        }
        LoggerUtils.LOGV("event = " + ev.getAction() + "r = " + r);
        return r;


//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
////            LoggerUtils.LOGD("move " + Math.abs(ev.getX() - lastX));
//            if (touchView != null && (Math.abs(ev.getX() - lastX) >= mSlop
//                    || Math.abs(ev.getY() - lastY) >= mSlop)) {
//                touchView.offsetLeftAndRight((int) (ev.getX() - lastX));
//                touchView.offsetTopAndBottom((int) (ev.getY() - lastY));
////                LoggerUtils.LOGD("lastX = " + lastX);
//                lastX = ev.getX();
//                lastY = ev.getY();
//            }
//            return true;
//        } else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
//
//            if (touchView != null) {
//                lastX = touchView.getLeft();
//                lastY = touchView.getTop();
////                LoggerUtils.LOGD("origX = " + origX + ",ev.getX() = " + ev.getX() + ",left = " + touchView.getLeft());
//                ValueAnimator xAnimator = ValueAnimator.ofInt((int) touchView.getLeft(), (int) origX);
//
//                xAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(@NonNull ValueAnimator animation) {
//
//                        int x = (Integer) animation.getAnimatedValue();
////                        LoggerUtils.LOGD("x = " + x + ",move = " + (x - lastX));
//                        touchView.offsetLeftAndRight((int) (x - lastX));
//                        lastX = x;
//                    }
//                });
//
//                ValueAnimator yAnimator = ValueAnimator.ofInt(touchView.getTop(), (int) origY);
//                yAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(@NonNull ValueAnimator animation) {
//                        int y = (int) animation.getAnimatedValue();
//                        touchView.offsetTopAndBottom((int) (y - lastY));
//                        lastY = y;
//                    }
//                });
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.play(xAnimator).with(yAnimator);
//                animatorSet.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        touchView = null;
//                        origX = origY = lastX = lastY = 0;
//                    }
//                });
//                animatorSet.start();
//            }
//
//            return false;
//        }
//        return false;
    }

    /**
     * down  move up事件都会调用方法判断是否拦截。如果拦截，调用ontouchevent。否则调用子View的
     * dispatchTouchEvent。如果子view不接受，这返回父控件的ontouchevent方法
     * <p>
     * 如果上一次down事件被子view拦截，并且这次move事件viewgroup的oninterrceptTouchEvent返回true。则本次事件会
     * 回调子view的ontouch方法，mFirstTouchTarget被置为null，并调用viewgroup的outouchevent方法，但是后续事件只会调用viewgroup的ontouchevent方法
     * <p>
     * 如果down时间没有人处理，这后续同一事件序列交给activity处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {

//        boolean r = super.onInterceptTouchEvent(ev);
        boolean r = false;
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_MOVE) {
            r = true;
        }
        LoggerUtils.LOGV("event = " + ev.getAction() + "r = " + r);
        return r;
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//
//            lastX = ev.getX();
//            lastY = ev.getY();
//            touchView = getTouchView(ev.getX(), ev.getY());
//            return false;
//        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            if (touchView != null && (Math.abs(ev.getX() - lastX) >= mSlop
//                    || Math.abs(ev.getY() - lastY) >= mSlop)) {
////                LoggerUtils.LOGD("group  true");
//                return true;
//            }
//            return false;
//
//        } else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
//            return false;
//        } else {
//            return false;
//        }

    }

    private View getTouchView(float x, float y) {
        RectF rectF = new RectF();

        for (int i = getChildCount() - 1; i >= 0; --i) {
            View child = getChildAt(i);
            rectF.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());

            if (rectF.contains(x, y)) {
                origX = child.getLeft();
                origY = child.getTop();
                return child;
            }
        }

        return null;
    }


}
