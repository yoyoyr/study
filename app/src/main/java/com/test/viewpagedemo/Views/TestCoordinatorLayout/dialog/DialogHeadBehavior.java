package com.test.viewpagedemo.Views.TestCoordinatorLayout.dialog;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import org.greenrobot.eventbus.EventBus;

public class DialogHeadBehavior extends CoordinatorLayout.Behavior {

    float removeY;
    boolean flg = false;

    boolean isAnima;
    float downX, downY;
    static final int MIN_MOVE = 5;
    boolean isMoving;
//    boolean isUp;
//    boolean isDown;

    ValueAnimator valueAnimator;
    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            isAnima = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnima = false;
//            isDown = isUp = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

            isAnima = false;
//            isDown = isUp = false;
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
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    public void onDetachedFromLayoutParams() {

        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }


    public boolean onInterceptTouchEvent(CoordinatorLayout parent, final View child, MotionEvent ev) {
        LoggerUtils.LOGV("DialogHeadBehavior ev.getAction() = " + ev.getAction() + "，removeY = " + removeY
                + ",isAnima = " + isAnima + ",child.getHeight() = " + child.getHeight() + ",ev.getActionMasked() = " + ev.getActionMasked());

        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            isMoving = true;
            if (!isAnima) {
                if (-removeY > MIN_MOVE && downY > ev.getY()) {
                    //向上滑动了removeY，滑动到child.getHeight()
                    valueAnimator = ValueAnimator.ofFloat(-removeY, child.getHeight());
                    valueAnimator.setDuration(50);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                            removeY = -(float) animation.getAnimatedValue();
                            LoggerUtils.LOGV("DialogHeadBehavior removeY = " + removeY);
                            child.setTranslationY(removeY);
                        }
                    });
                    valueAnimator.addListener(animatorListener);
                    valueAnimator.start();
                } else if (child.getHeight() + removeY > MIN_MOVE && downY < ev.getY()) {

                    valueAnimator = ValueAnimator.ofFloat(removeY, 0);
                    valueAnimator.setDuration(50);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                            removeY = -(float) animation.getAnimatedValue();
                            LoggerUtils.LOGV("DialogHeadBehavior removeY = " + removeY);
                            child.setTranslationY(removeY);
                        }
                    });
                    valueAnimator.addListener(animatorListener);
                    valueAnimator.start();
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

        if (isAnima || !isMoving) {
            return;
        }


        if (removeY - dy <= 0 //向下移动的最大距离不能大于0，否则heard不能在顶部
                && -removeY + dy < child.getHeight()/**向上移动的最大距离不能大于header的大小**/) {
            removeY -= dy;
            LoggerUtils.LOGD("DialogHeadBehavior offsetY child = " + child.getHeight() + ",removeY = " + removeY + ",dy = " + dy);

            //heard能向下滑动的最大距离
            if (removeY > 0) {
                removeY = 0;
            }
            //heard能向上滑动的最大距离
            if (-removeY >= child.getHeight()) {
                removeY = -child.getHeight();
            }
            //translation的最大可移动距离必须和layout的距离一致，否则会出现显示不完全
            child.setTranslationY(removeY);
            consumed[1] = dy;


        }

    }
}
