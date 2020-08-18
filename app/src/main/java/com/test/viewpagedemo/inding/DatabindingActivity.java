package com.test.viewpagedemo.inding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.study.point.R;
import com.study.point.databinding.AcitivityBinBinding;


public class DatabindingActivity extends AppCompatActivity {

    int count = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AcitivityBinBinding binding = DataBindingUtil.setContentView(this, R.layout.acitivity_bin);

        final Progress progress = new Progress();
        binding.setProgress(progress);

        //设置一个初始值作为演示 数据 -> View
        //这是最常见的进度设置。
        progress.porgress.set(21);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.seekBar.setProgress(++count);
                progress.porgress.set(count);
            }
        });
    }
}