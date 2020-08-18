package com.test.viewpagedemo.inding;

import android.databinding.BaseObservable;
import android.databinding.ObservableInt;

/**
 * Created by Phil on 2017/9/4.
 */

public class Progress extends BaseObservable {
    public final ObservableInt porgress = new ObservableInt();
}