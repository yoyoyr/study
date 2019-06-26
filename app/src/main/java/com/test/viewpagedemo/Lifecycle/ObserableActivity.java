package com.test.viewpagedemo.Lifecycle;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class ObserableActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //为activity添加一个lifecycle观察者
        getLifecycle().addObserver(new Observer());
    }
}
