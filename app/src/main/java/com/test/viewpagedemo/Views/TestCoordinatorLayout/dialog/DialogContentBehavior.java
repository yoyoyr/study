package com.test.viewpagedemo.Views.TestCoordinatorLayout.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;


public class DialogContentBehavior extends CoordinatorLayout.Behavior {

    int height;

    public DialogContentBehavior() {
    }

    public DialogContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        View headerView = parent.findViewById(R.id.clHeard);
        child.offsetTopAndBottom((int) (headerView.getMeasuredHeight()));
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof LinearLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //计算列表y坐标，最小为0
        float y = dependency.getTranslationY();
        if(height == 0 )
            height = child.getHeight();

        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        layoutParams.height = (int) (height - y);
//        LoggerUtils.LOGV("translation y = " + y + ",layoutParams.height " + layoutParams.height+",height = "+height);
        child.setLayoutParams(layoutParams);
        child.setTranslationY(y);
        return true;
    }

}
