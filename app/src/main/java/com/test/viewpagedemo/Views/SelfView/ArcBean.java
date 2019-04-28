package com.test.viewpagedemo.Views.SelfView;

import android.support.annotation.NonNull;

public class ArcBean {
    int color;
    float startAngle;
    float endAngle;

    @NonNull
    @Override
    public String toString() {
        return "ArcBean{" +
                "color=" + color +
                ", startAngle=" + startAngle +
                ", endAngle=" + endAngle +
                ", switchAngle=" + switchAngle +
                ", value=" + value +
                '}';
    }

    float switchAngle;
    int value;

}
