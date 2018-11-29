package com.test.viewpagedemo.Views.viewpage.fragmentstateadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.study.point.R;
import com.test.viewpagedemo.Views.fragment.Fragment1;
import com.test.viewpagedemo.Views.fragment.Fragment2;
import com.test.viewpagedemo.Views.fragment.Fragment3;
import com.test.viewpagedemo.Views.fragment.Fragment4;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class FragmentStateadapterActivity extends AppCompatActivity {


    List<Fragment> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_viewpage_adapter);

        data = new ArrayList<>();
        data.add(new Fragment1());
        data.add(new Fragment2());
        data.add(new Fragment3());
        data.add(new Fragment4());
        ViewPager viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(new BaseFragmentStateViewPageAdapter(getSupportFragmentManager(), data));

    }

    public void setBtn(String text) {
        ((Button) findViewById(R.id.btn)).setText(text);
    }


}
