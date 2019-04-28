package com.test.viewpagedemo.Views.SelfView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.test.viewpagedemo.LoggerUtils;


public class DragViewHelperGroup extends FrameLayout {
    private static final String TAG = "TestViewGroup";

    private ViewDragHelper mDragHelper;

    public DragViewHelperGroup(@NonNull Context context) {
        this(context, null);
        init();
    }

    public DragViewHelperGroup(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public DragViewHelperGroup(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            int origX, origY;
            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                LoggerUtils.LOGD("onViewCaptured");
                super.onViewCaptured(capturedChild, activePointerId);
                origX = capturedChild.getLeft();
                origY = capturedChild.getTop();
            }

            // 手指释放时的回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                /**
                 * 1. settleCapturedViewAt() 方法调用的目的的将 child 定位到 (left,top) 位置，但它不是瞬间到达，有一个动画的过程。
                 * 2. 需要动画过程的每一帧调用 continueSettling() 方法，直到它返回 false。
                 * 3. 如果 continureSettling() 返回 false 表明此次动画结束。
                 */
                mDragHelper.settleCapturedViewAt(origX, origY);
                invalidate();
            }

            // 决定了是否需要捕获这个 child，只有捕获了才能进行下面的拖拽行为
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            // 修整 child 水平方向上的坐标，left 指 child 要移动到的坐标，dx 相对上次的偏移量
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            // 修整 child 垂直方向上的坐标，top 指 child 要移动到的坐标，dy 相对上次的偏移量
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            //返回对应监听的view
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                LoggerUtils.LOGD("onEdgeDragStarted");
                super.onEdgeDragStarted(edgeFlags, pointerId);
                if (edgeFlags == ViewDragHelper.EDGE_LEFT) {
                    mDragHelper.captureChildView(getChildAt(getChildCount() - 1), pointerId);
                } else if (edgeFlags == ViewDragHelper.EDGE_TOP) {
                    mDragHelper.captureChildView(getChildAt(getChildCount() - 2), pointerId);
                } else {
                    mDragHelper.captureChildView(getChildAt(getChildCount() - 3), pointerId);
                }

            }

            /**
             *对于可响应点击事件的控件，需要设置这个两个回调函数
             * @param child
             * @return 大于0则相应对应方向滑动
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
                return 1;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return 1;
            }
        });

        //设置可以监听的边缘
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
        /** 是否应该拦截 children 的触摸事件，
         *只有拦截了 ViewDragHelper 才能进行后续的动作
         *将它放在 ViewGroup 中的 onInterceptTouchEvent() 方法中就好了
         **/
        boolean flg = mDragHelper.shouldInterceptTouchEvent(ev);
        LoggerUtils.LOGD("event = " + ev.getAction() + ",flg = " + flg);
        return flg;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        /** 处理 ViewGroup 中传递过来的触摸事件序列
         * 在ViewGroup 中的 onTouchEvent() 方法中处理
         */
        LoggerUtils.LOGD("onTouchEvent"+event.getAction());
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}