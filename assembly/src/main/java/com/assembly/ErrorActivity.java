package com.assembly;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.ButterKnife;

@Route(path = "/error/errorActivity")
public class ErrorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aroute_error);
        ButterKnife.bind(this);

    }
}
