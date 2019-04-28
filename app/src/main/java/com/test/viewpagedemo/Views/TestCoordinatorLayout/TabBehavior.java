package com.test.viewpagedemo.Views.TestCoordinatorLayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

public class TabBehavior extends CoordinatorLayout.Behavior {


    int titleHeight = 0;
    int marginTop = 0;


    public TabBehavior() {
    }

    public TabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        View heard = parent.findViewById(R.id.heard);
        marginTop = heard.getMeasuredHeight();
        child.offsetTopAndBottom(marginTop);
        child.invalidate();
        return true;
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        //计算列表y坐标，最小为0
//        float y = heardHeight + dependency.getTranslationY();
        float y = dependency.getTranslationY() + marginTop;
        //childe的最小高度是title的高度
        if (y < titleHeight) {
            y = titleHeight;
        }
        LoggerUtils.LOGV("y = " + y + "dependency = " + dependency.getTranslationY() + ",marginTop = " + marginTop);
        child.setY(y);
        return true;
    }

}
