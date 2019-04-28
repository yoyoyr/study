package com.test.viewpagedemo.Views.SelfView;

import android.support.annotation.NonNull;

public class LineBean {
    int value;
    float x;
    float y;
    String text;

    @NonNull
    @Override
    public String toString() {
        return "LineBean{" +
                "value=" + value +
                ", x=" + x +
                ", y=" + y +
                ", text='" + text + '\'' +
                '}';
    }
}
