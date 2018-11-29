package com.test.viewpagedemo.Views.TestCoordinatorLayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

//https://www.jianshu.com/p/b987fad8fcb4
public class TitleBehavior extends CoordinatorLayout.Behavior {

    float deltaY;

    public TitleBehavior() {
    }

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (deltaY == 0) {
            deltaY = dependency.getHeight() - child.getHeight();
        }

        float dy = (-dependency.getY() / deltaY) * child.getHeight() - child.getHeight();
//        LoggerUtils.LOGD("dy = " + dy + ",top = " + dependency.getY() + ",height = " + child.getHeight() + ",deltay = " + deltaY);
        if (-dependency.getY() > deltaY) {
            return true;
        }
        //基于原始位置移动，不改变l，t的值
        child.setTranslationY(dy);
//        距离递增,直接改变l，t的值
//        child.offsetTopAndBottom((int) dy);
        return true;
    }
}

