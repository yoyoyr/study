package com.test.viewpagedemo.Views.TestCoordinatorLayout.dialog;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.test.viewpagedemo.EventBus.Event;
import com.test.viewpagedemo.LoggerUtils;
import com.test.viewpagedemo.Views.DeviceUtils;

import org.greenrobot.eventbus.EventBus;

public class DialogHeadBehavior extends CoordinatorLayout.Behavior {

    float removeY;
    boolean flg = false;
    int measuredHeight;

    boolean isAnima;
    float downY;
    static final int MIN_MOVE = 5;
    static final int ANIMATOR_TIME = 50;
    boolean isMoving;

    ValueAnimator valueAnimator;
    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            isAnima = true;

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnima = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            isAnima = false;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public DialogHeadBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, View child, int layoutDirection) {
        measuredHeight = DeviceUtils.getScreenHeight(parent.getContext()) - DeviceUtils.dip2px(parent.getContext(), 430);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        layoutParams.height = measuredHeight;
        child.setLayoutParams(layoutParams);
        parent.onLayoutChild(child, layoutDirection);
        return true;
    }

    public void onDetachedFromLayoutParams() {

        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }


    public boolean onInterceptTouchEvent(CoordinatorLayout parent, final View child, MotionEvent ev) {
//        LoggerUtils.LOGV("DialogHeadBehavior ev.getAction() = " + ev.getAction() + "，removeY = " + removeY
//                + ",isAnima = " + isAnima + ",ev.getActionMasked() = " + ev.getActionMasked());

        if (ev.getAction() == MotionEvent.ACTION_UP ) {
            isMoving = true;
            if (!isAnima) {
                if (-removeY > MIN_MOVE && downY > ev.getY()) {//上拉
                    //向上滑动了removeY，滑动到child.getHeight()
                    valueAnimator = ValueAnimator.ofFloat(-removeY, measuredHeight);
                    valueAnimator.setDuration(ANIMATOR_TIME);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                            removeY = -(float) animation.getAnimatedValue();
//                            LoggerUtils.LOGV("DialogHeadBehavior removeY = " + removeY);
                            child.setTranslationY(removeY);
                        }
                    });
                    valueAnimator.addListener(animatorListener);
                    valueAnimator.start();
                    EventBus.getDefault().post(new Page2Visibily(true));
                } else if (measuredHeight + removeY > MIN_MOVE && downY < ev.getY()) {

                    valueAnimator = ValueAnimator.ofFloat(removeY, 0);
                    valueAnimator.setDuration(ANIMATOR_TIME);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                            removeY = -(float) animation.getAnimatedValue();
//                            LoggerUtils.LOGV("DialogHeadBehavior removeY = " + removeY);
                            child.setTranslationY(removeY);
                        }
                    });
                    valueAnimator.addListener(animatorListener);
                    valueAnimator.start();
                    EventBus.getDefault().post(new Page2Visibily(false));
                }
            }
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downY = ev.getY();
            isMoving = false;
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            isMoving = true;
        }
        return false;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //只关注竖直方向滑动
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull final CoordinatorLayout coordinatorLayout, @NonNull final View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

        LoggerUtils.LOGD("DialogHeadBehavior ,removeY = " + removeY + ",dy = " + dy);

        if (isAnima || !isMoving) {
            return;
        }


        if (removeY - dy <= 0 //向下移动的最大距离不能大于0，否则heard不能在顶部
                && -removeY + dy < measuredHeight/**向上移动的最大距离不能大于header的大小**/) {
            removeY -= dy;
            //heard能向下滑动的最大距离
            if (removeY > 0) {
                removeY = 0;
            }
            //heard能向上滑动的最大距离
            if (-removeY >= measuredHeight) {
                removeY = -measuredHeight;
            }
            //translation的最大可移动距离必须和layout的距离一致，否则会出现显示不完全
            child.setTranslationY(removeY);
            consumed[1] = dy;


        }

    }
}
