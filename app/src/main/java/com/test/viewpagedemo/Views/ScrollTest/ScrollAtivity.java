package com.test.viewpagedemo.Views.ScrollTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

public class ScrollAtivity extends AppCompatActivity {

    Button test;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoggerUtils.LOGD("test = " + v.toString());
            }
        });

        /**
         * ViewGroup调用canvas.translate(-mScrollX, -mScrollY);
         *
         * test = android.support.v7.widget.AppCompatButton{4d9f994 VFED..C.. ...P.... 0,0-1080,131 #7f080120 app:id/scroll}
         * top = 0,translate y = 0.0,scroll y = 0,parent scroll y = -300
         */
        ((Button) findViewById(R.id.scroll)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((View) test.getParent()).scrollBy(0, -100);
                LoggerUtils.LOGD("test = " + test.toString());
                LoggerUtils.LOGD("top = " + test.getTop() + ",translate y = " + test.getTranslationY()
                        + ",scroll y = " + test.getScrollY() + ",y = " + test.getY() +
                        ",parent scroll y = " + ((View) test.getParent()).getScrollY());
            }
        });

        /**
         * test = android.support.v7.widget.AppCompatButton{16ab185 VFED..C.. ........ 0,0-656,131 #7f080152 app:id/test}
         * top = 0,translate y = 200.0,scroll y = 0,y = 200.0,parent scroll y = 0
         */
        ((Button) findViewById(R.id.translate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 100;
                test.setTranslationY(count);
                LoggerUtils.LOGD("test = " + test.toString());
                LoggerUtils.LOGD("top = " + test.getTop() + ",translate y = " + test.getTranslationY()
                        + ",scroll y = " + test.getScrollY() + ",y = " + test.getY() +
                        ",parent scroll y = " + ((View) test.getParent()).getScrollY());
            }
        });


        /**
         * test = android.support.v7.widget.AppCompatButton{4d9f994 VFED..C.. ........ 0,200-656,331 #7f080152 app:id/test}
         * top = 200,translate y = 0.0,scroll y = 0,y = 200.0,parent scroll y = 0
         */
        ((Button) findViewById(R.id.offset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接设置子view的l，r
                test.offsetTopAndBottom(100);
                LoggerUtils.LOGD("test = " + test.toString());
                LoggerUtils.LOGD("top = " + test.getTop() + ",translate y = " + test.getTranslationY()
                        + ",scroll y = " + test.getScrollY() + ",y = " + test.getY() +
                        ",parent scroll y = " + ((View) test.getParent()).getScrollY());
            }
        });
    }
}
