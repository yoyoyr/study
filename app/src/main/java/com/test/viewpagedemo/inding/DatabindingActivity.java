package com.test.viewpagedemo.inding;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.study.point.R;
import com.study.point.databinding.AcitivityBinBinding;
import com.test.viewpagedemo.Views.SelfView.MyView;


public class DatabindingActivity extends AppCompatActivity {

    int count = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AcitivityBinBinding binding = DataBindingUtil.setContentView(this, R.layout.acitivity_bin);

        final DataBindingVM dataBindingVM = ViewModelProviders.of(this).get(DataBindingVM.class);
        dataBindingVM.data.setValue("init");
        binding.setLifecycleOwner(this);
        binding.setDataBindingVM(dataBindingVM);

        binding.add.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBindingVM.data.setValue("btn click");
            }
        });
    }
}