package com.test.viewpagedemo.Views.TestCoordinatorLayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

public class HeaderBehavior extends CoordinatorLayout.Behavior {

    float removeY;
    boolean flg = false;
    int marginTop;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        View tabView = parent.findViewById(R.id.tab);

        marginTop = tabView.getMeasuredHeight();
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //只关注竖直方向滑动
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (target instanceof RecyclerView) {
            int pos = ((LinearLayoutManager) ((RecyclerView) target).getLayoutManager()).findFirstCompletelyVisibleItemPosition();

//            int pos = 0;
            LoggerUtils.LOGD("child = " + child.getHeight() + ",removeY = " + removeY + ",dy = " + dy);
            if (removeY - dy <= 0 && -removeY + dy < child.getHeight()) {
                flg = false;
            }

            if (pos == 0 && !flg) {
                removeY -= dy;

                //heard能向下滑动的最大距离
                if (removeY > 0) {
                    removeY = 0;
                    flg = true;
                }
                //heard能向上滑动的最大距离
                if (-removeY >= child.getHeight() - marginTop) {
                    removeY = -child.getHeight() + marginTop;
                    flg = true;
                }
                //translation的最大可移动距离必须和layout的距离一致，否则会出现显示不完全
                child.setTranslationY(removeY);
                consumed[1] = dy;
            }
        }

    }
}
