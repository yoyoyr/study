package com.test.viewpagedemo.Views.DragHelper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.test.viewpagedemo.LoggerUtils;

public class DragViewGroup extends LinearLayout {


    ViewDragHelper viewDragHelper;

    public DragViewGroup(Context context) {
        super(context);
    }

    public DragViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                LoggerUtils.LOGD("view = " + child);
                return true;
            }

            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }
        });
    }
}
