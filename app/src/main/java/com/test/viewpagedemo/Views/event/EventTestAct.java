package com.test.viewpagedemo.Views.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

public class EventTestAct extends AppCompatActivity implements View.OnTouchListener {
    private LinearLayout mLayout;
    private TestButton mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_event_test);

        mLayout = (LinearLayout) this.findViewById(R.id.mylayout);
        mButton = (TestButton) this.findViewById(R.id.my_btn);

        mLayout.setOnTouchListener(this);
        mButton.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        LoggerUtils.LOGV("action=" + event.getAction() + " --" + v);
        return false;
    }

}
