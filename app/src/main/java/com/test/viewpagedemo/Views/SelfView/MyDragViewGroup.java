package com.test.viewpagedemo.Views.SelfView;

import android.content.Context;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.test.viewpagedemo.LoggerUtils;

import static android.support.v4.widget.ViewDragHelper.EDGE_LEFT;

/**
 * 1.findTopChildUnder  获取点击区域的view，如果view重合，根据Callback#getOrderedChildIndex(int)返回
 * <p>
 * 2.checkTouchSlop(View child（点击区域所对应的的view）, float dx（滑动的x轴距离）, float dy（滑动的y轴距离）)
 * 如果（getViewHorizontalDragRange || getViewVerticalDragRange）&&mTouchSlop  返回true
 * 用于判断是否处理该滑动事件
 * <p>
 * 3.reportNewEdgeDrags(float dx, float dy, int pointerId)
 * move事件调用，判断是否是边界拖拽，如果是，判断是否响应，如果响应，则回调onEdgeDragStarted，
 * 在回调调用captureChildView处理事件。如果调用了captureChildView，会setDragState(STATE_DRAGGING);
 * <p>
 * 4.tryCaptureViewForDrag(View toCapture, int pointerId)
 * mCallback.tryCaptureView(toCapture, pointerId)判断是否该view是否响应拖拽事件，
 * 如果响应， mCallback.onViewCaptured(childView, activePointerId); setDragState(STATE_DRAGGING);
 * <p>
 * 5.onEdgeTouched
 * down事件回调
 * <p>
 * 6.dragTo(int left, int top, int dx, int dy)
 * 正在移动的方法，调用 mCapturedView.offsetLeftAndRight  mCapturedView.offsetTopAndBottom
 * 并通过 mCallback.onViewPositionChanged通知位置改变
 * <p>
 * 7.
 */
public class MyDragViewGroup extends FrameLayout {

    float mSlop;

    View touchView;

    int lastTouchX, lastTouchY, downX, downY, scrollX, scrollY;

    ViewDragHelper viewDragHelper;

    public MyDragViewGroup(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MyDragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyDragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mSlop = ViewConfiguration.get(context).getScaledWindowTouchSlop();

        touchView = null;
        scrollX = scrollY = downX = downY = lastTouchY = lastTouchX = 0;

        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            int origX, origY;

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                LoggerUtils.LOGD("child = " + child);
                origX = child.getLeft();
                origY = child.getTop();
                return true;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                LoggerUtils.LOGD("child = " + child);
                return top;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                LoggerUtils.LOGD("child = " + child);
                return left;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                viewDragHelper.settleCapturedViewAt(origX, origY);
                postInvalidate();
            }

            //            @Override
//            public int getViewVerticalDragRange(View child) {
//                LoggerUtils.LOGD("child = " + child);
//                return 1;
//            }
//
//            @Override
//            public int getViewHorizontalDragRange(View child) {
//                LoggerUtils.LOGD("child = " + child);
//                return 1;
//            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                LoggerUtils.LOGD("drag start = " + edgeFlags);
                if (edgeFlags == EDGE_LEFT) {
                    viewDragHelper.captureChildView(MyDragViewGroup.this.getChildAt(0), pointerId);
                }
            }
        });

        viewDragHelper.setEdgeTrackingEnabled(EDGE_LEFT);
    }

    /**
     * 1.如果oninterceptTouchEvent返回true，这事件序列都会回调到ontouchevent
     * 2.如果onintercepttouchevent返回false，子view也没有消耗事件，这回调用ontouchevent查看是否消耗事件
     * 如果事件未被消耗，则再也不会接收到这个事件的剩余事件序列
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        viewDragHelper.processTouchEvent(event);
        return true;
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
////            lastTouchX = (int) event.getX();
////            downY = lastTouchY = (int) event.getY();
////            if (getTouchView(lastTouchX, lastTouchY)) {
////                downX = (int) touchView.getLeft();
////                downY = (int) touchView.getTop();
////                return true;
////            } else {
////                return false;
////            }
////        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
////
////            float moveX = event.getX() - lastTouchX;
////            float moveY = event.getY() - lastTouchY;
////            if (touchView != null && (Math.abs(moveX) > mSlop || Math.abs(moveY) > mSlop)) {
////                touchView.offsetLeftAndRight((int) moveX);
////                touchView.offsetTopAndBottom((int) moveY);
////                lastTouchY = (int) event.getY();
////                lastTouchX = (int) event.getX();
////            }
////            return true;
////        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
////
////            lastTouchY = (int) touchView.getTop();
////            lastTouchX = (int) touchView.getLeft();
////            ValueAnimator valueAnimatorX = ValueAnimator.ofInt((int) touchView.getLeft(), (int) downX);
////
////            valueAnimatorX.setDuration(3000);
////            valueAnimatorX.addUpdateListener((ValueAnimator animation) -> {
////                if (touchView != null) {
////                    touchView.offsetLeftAndRight((int) ((int) animation.getAnimatedValue() - lastTouchX));
////                    LoggerUtils.LOGD("x= " + touchView.getLeft() + ",down x = " + downX);
////                    lastTouchX = (int) animation.getAnimatedValue();
////                }
////            });
////            valueAnimatorX.start();
////
////
////            ValueAnimator valueAnimatorY = ValueAnimator.ofInt((int) touchView.getTop(), (int) downY);
////            valueAnimatorY.setDuration(3000);
////            valueAnimatorY.addUpdateListener((ValueAnimator valueAnimator) -> {
////                touchView.offsetTopAndBottom((int) ((int) valueAnimator.getAnimatedValue() - lastTouchY));
////                lastTouchY = (int) valueAnimator.getAnimatedValue();
////            });
////            valueAnimatorY.start();
////            return true;
////
////        } else {
////            return super.onTouchEvent(event);
////        }
    }

    private boolean getTouchView(float lastTouchX, float lastTouchY) {

        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            RectF rectF = new RectF(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
            if (rectF.contains(lastTouchX, lastTouchY)) {
                touchView = child;
                return true;
            }
        }
        return false;
    }

    /**
     * 1.如果viewgroup和子view都没有消耗down事件，这事件不会在传到这里
     * 2.如果viewgroup或者子view消耗了down事件，则剩余事件序列都会调用viewgroup的onintercepttouchevent事件
     * 3.
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flg = viewDragHelper.shouldInterceptTouchEvent(ev);
        LoggerUtils.LOGD("flg = " + flg + ",action = " + MotionEventCompat.getActionMasked(ev));
        return flg;
    }

    @Override
    public void computeScroll() {
        if(viewDragHelper.continueSettling(true)){
            invalidate();
        }
    }
}
