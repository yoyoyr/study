package com.test.viewpagedemo.Glide;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

public class MyLinearLayout extends LinearLayout {

    ViewTarget<MyLinearLayout, GlideDrawable> viewTarget;

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        viewTarget = new ViewTarget<MyLinearLayout, GlideDrawable>(this) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                getView().setBackground(resource);
            }
        };
    }

    public ViewTarget<MyLinearLayout, GlideDrawable> getViewTarget() {
        return viewTarget;
    }
}
