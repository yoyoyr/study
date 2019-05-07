package com.test.viewpagedemo.SVG;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.study.point.R;

public class SVGActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        ImageView imageview= (ImageView) findViewById(R.id.img);
        ((AnimatedVectorDrawable) imageview.getDrawable()).start();
    }
}
