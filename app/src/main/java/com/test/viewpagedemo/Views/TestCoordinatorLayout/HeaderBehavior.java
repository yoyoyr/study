package com.test.viewpagedemo.Views.TestCoordinatorLayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class HeaderBehavior extends CoordinatorLayout.Behavior {

    float removeY;
    boolean flg = false;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
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

            if (removeY - dy <= 0 && -removeY + dy < child.getHeight()) {
                flg = false;
            }

            if (pos == 0 && !flg) {
                removeY -= dy;
                if (removeY > 0) {
                    removeY = 0;
                    flg = true;
                }
                if (-removeY >= child.getHeight()) {
                    removeY = -child.getHeight();
                    flg = true;
                }
//                LoggerUtils.LOGD("child = " + child.toString() + ",removeY = " + removeY);
                child.setTranslationY(removeY);
                consumed[1] = dy;
            }
        }

    }
}
