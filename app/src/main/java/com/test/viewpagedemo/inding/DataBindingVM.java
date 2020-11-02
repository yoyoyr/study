package com.test.viewpagedemo.inding;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class DataBindingVM extends ViewModel {

    public MutableLiveData<String> data = new MutableLiveData();

}
