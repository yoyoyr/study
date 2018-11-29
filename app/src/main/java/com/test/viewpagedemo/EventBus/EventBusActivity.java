package com.test.viewpagedemo.EventBus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusActivity extends AppCompatActivity {

    final String FRAGMENT = "fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);

        ButterKnife.bind(this);

        Fragment fragment = null;
        if (savedInstanceState != null) {
            fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT);
        }

        if (fragment == null) {
            fragment = new FragmentA();
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment, FRAGMENT)
                .commit();
    }

    @OnClick({R.id.sendEvent})
    void sendEvent() {
        LoggerUtils.LOGD("send event");
        EventBus.getDefault().post(new Event("yoyo", "event"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
