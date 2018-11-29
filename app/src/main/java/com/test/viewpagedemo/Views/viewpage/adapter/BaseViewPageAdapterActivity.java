package com.test.viewpagedemo.Views.viewpage.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;
import com.study.point.R;

import java.util.ArrayList;
import java.util.List;

public class BaseViewPageAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_viewpage_adapter);

        ViewPager viewPager = findViewById(R.id.vp);
        List<View> data = new ArrayList<>();

        ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(this.getApplicationContext());

        data.add(getLayoutInflater().inflate(R.layout.viewpage1, viewPager, false));
        data.add(getLayoutInflater().inflate(R.layout.viewpage2, viewPager, false));
        data.add(getLayoutInflater().inflate(R.layout.viewpage3, viewPager, false));

        viewPager.setAdapter(new BaseViewPageAdapter(data));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //            页面滑动期间
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //            滑动结束
            @Override
            public void onPageSelected(int position) {

            }

            //页面滑动状态变化
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
