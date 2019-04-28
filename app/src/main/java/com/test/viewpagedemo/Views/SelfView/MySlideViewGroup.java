package com.test.viewpagedemo.Views.SelfView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.test.viewpagedemo.LoggerUtils;


/**
 * 1.通过scrollBy方法，结合事件分发机制实现布局的滑动
 * view.updateDisplayListIfDirty()方法会调用canvas.translate(-mScrollX, -mScrollY);
 */
public class MySlideViewGroup extends ViewGroup {


    int childWidth, childHeight, contentHeight, totalHeight, totalWidth;
    static int marginTop = 20;
    Scroller scroller;
    float lastX, lastY;
    int countY, moveY;

    public MySlideViewGroup(Context context) {
        super(context);
        init(context);
    }

    public MySlideViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MySlideViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context);
        totalHeight = totalWidth = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //wrap_content = AT_MOST MATCH_PARENT = EXACTLY
//        LoggerUtils.LOGD(MeasureSpec.AT_MOST + "----" + MeasureSpec.EXACTLY);
//        LoggerUtils.LOGD("width = " + width + "width model =" + widthModel + ",height = " + height + ",height model = " + heightModel);


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
        contentHeight = resolveSize(childHeight, heightMeasureSpec);
        setMeasuredDimension(resolveSize(childWidth, widthMeasureSpec), resolveSize(childHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        totalHeight = t;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
//                LoggerUtils.LOGD("childWidth = " + childWidth + ",childHeight = " + childHeight + ",childTop = " + childTop);
                child.layout(0, totalHeight, childWidth, totalHeight + childHeight);
                totalHeight += childHeight + marginTop;
                totalWidth = childWidth;
            }
        }
        totalHeight -= contentHeight;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        mDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 1.滑动冲突外部拦截法典型。
     * 2.内部拦截发，重写子view的dispatchTouchEvent如下
     * <p>
     * public boolean dispatchTouchEvent(MotionEvent event) {
     * switch (event.getAction()) {
     * case MotionEvent.ACTION_DOWN:
     * if (!(event.getY() <= mTotalHeight / 5)) {
     * getParent().requestDisallowInterceptTouchEvent(true);
     * }
     * break;
     * default:
     * break;
     * }
     * return super.dispatchTouchEvent(event);
     * }
     * </p>
     * 并且父布局不能够拦截DOWN事件。因为处理DWON事件之前，会将requestDisallowInterceptTouchEvent设置的标志位重置
     * 导致设置无效。
     */
    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
//        LoggerUtils.LOGD(ev.getAction() + "==");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = ev.getX();
            lastY = ev.getY();
            //如果fill还没停止，则停止
            if (scroller != null && !scroller.isFinished()) {
                scroller.abortAnimation();
            }
            return false;

        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            moveY = (int) (lastY - ev.getY());
//            LoggerUtils.LOGD("move y =" + moveY);
            if (Math.abs(moveY) > 15) {
                lastY = ev.getY();
                return true;
            }
            return false;
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            lastY = lastX = 0;
            return false;
        }
        return false;
    }

    VelocityTracker velocityTracker;

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
//        LoggerUtils.LOGD(ev.getAction() + "==");
        boolean flg = super.onTouchEvent(ev);
        if (childHeight <= contentHeight) {
            return flg;
        }

        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(ev);
        boolean eventAddedToVelocityTracker = false;

        if (ev.getAction() == MotionEvent.ACTION_MOVE && countY >= 0 && (countY + contentHeight) <= (childHeight)) {

            moveY = (int) (lastY - ev.getY());
            if (countY + moveY <= 0) {//防止滑出顶部
                moveY = -countY;
            } else if (countY + moveY >= (childHeight - contentHeight)) {//防止滑出底部
                moveY = childHeight - contentHeight - countY;
            }
//            LoggerUtils.LOGD("move y =" + moveY);
            /**
             * 这里改变了mScrollX，mScrollY的值，并触发了重绘
             * 在ViewGroup的绘制方法中，会调用drawChild方法绘制子view，调用的是View.boolean draw(Canvas canvas, ViewGroup parent, long drawingTime)
             * ,这里面会调用
             * if (!hasDisplayList) {
             *  computeScroll();
             *  sx = mScrollX;
             *  sy = mScrollY;
             *  }
             *canvas.translate(mLeft - sx, mTop - sy);
             */
            scrollBy(0, moveY);
            countY += moveY;
            lastY = ev.getY();
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            eventAddedToVelocityTracker = true;
            velocityTracker.computeCurrentVelocity(1000, 8000);//8000
//            LoggerUtils.LOGD("velocity y = " + velocityTracker.getYVelocity(0) + ",x = " + velocityTracker.getXVelocity(0));
            scroller.fling(0, getScrollY(), 0, (int) -velocityTracker.getYVelocity(0),
                    -100, totalWidth + 100, -100, totalHeight + 100);
            invalidate();
            velocityTracker.clear();
        }
        if (!eventAddedToVelocityTracker) {
            velocityTracker.addMovement(ev);
        }
//        vtev.recycle();
        return flg;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
//            LoggerUtils.LOGD(scroller.getCurrX() + "---" + scroller.getCurrY());
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }
}
