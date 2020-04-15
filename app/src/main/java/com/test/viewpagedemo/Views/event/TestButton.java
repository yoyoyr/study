package com.test.viewpagedemo.Views.event;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.test.viewpagedemo.LoggerUtils;

public class TestButton extends AppCompatButton {

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LoggerUtils.LOGV("action = " + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LoggerUtils.LOGV("action = " + event.getAction());
        return super.onTouchEvent(event);
    }
}
