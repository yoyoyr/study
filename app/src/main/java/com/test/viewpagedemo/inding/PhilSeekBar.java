package com.test.viewpagedemo.inding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import com.test.viewpagedemo.LoggerUtils;

/**
 * Created by Phil on 2017/9/4.
 */

public class PhilSeekBar extends AppCompatSeekBar {
    private static InverseBindingListener mInverseBindingListener;

    public PhilSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @BindingAdapter(value = "philprogress", requireAll = false)
    public static void setPhilProgress(PhilSeekBar seekBar, int progress) {
        LoggerUtils.LOGV("progess = " + progress);
//        if (getPhilProgress(seekBar) != progress) {
        seekBar.setProgress(progress);
//        }
    }

    @InverseBindingAdapter(attribute = "philprogress", event = "philprogressAttrChanged")
    public static int getPhilProgress(PhilSeekBar seekBar) {
        LoggerUtils.LOGV("progess = " + seekBar.getProgress());
        return seekBar.getProgress();
    }

    @BindingAdapter(value = {"philprogressAttrChanged"}, requireAll = false)
    public static void setPhilProgressAttrChanged(PhilSeekBar seekBar, InverseBindingListener inverseBindingListener) {
        LoggerUtils.LOGV("progess = " + seekBar.getProgress());
        if (inverseBindingListener == null) {
            Log.e("错误！", "InverseBindingListener为空!");
        } else {
            seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    //触发反向数据的传递
                    if (mInverseBindingListener != null) {
                        mInverseBindingListener.onChange();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }
}