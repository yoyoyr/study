package com.test.viewpagedemo.Views.TestCoordinatorLayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

public class RecyclerViewBehavior extends CoordinatorLayout.Behavior {

    int leastY = 0;
    CoordinatorLayout.LayoutParams lp;
    boolean init = true;
    float marginTop;
    int maxTranslateY = 0;
    float offsetY;
    boolean flg = false;

    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, View child, int layoutDirection) {

        marginTop = parent.findViewById(R.id.title).getMeasuredHeight();
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//        layoutParams.topMargin = (int) (layoutParams.topMargin + marginTop);
//        child.setLayoutParams(layoutParams);
        parent.onLayoutChild(child, layoutDirection);
        View headerView = parent.findViewById(R.id.heard);
        View tabView = parent.findViewById(R.id.tab);

        child.offsetTopAndBottom((int) (headerView.getMeasuredHeight() - marginTop));
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //计算列表y坐标，最小为0
        float y = dependency.getTranslationY();
//        if (y < 0) {
//            y = 0;
//        }

        LoggerUtils.LOGV("margintop = " + marginTop + ",translation y = " + y);
        child.setTranslationY(y);
        return true;
    }

}
