package com.test.viewpagedemo.Views.DrawerLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.study.point.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 修改布局文件即可改变侧滑栏
 */
public class DrawerActivity extends AppCompatActivity {

    FragmentContent fragmentContent;
    FragmentJD fragmentJD;
    Unbinder unbinder;

    @Nullable
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @Nullable
    @BindView(R.id.fl_container)
    FrameLayout content;
    @Nullable
    @BindView(R.id.btn1)
    Button btn;

    @Nullable
    @BindView(R.id.nav_view)
    RelativeLayout navView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        unbinder = ButterKnife.bind(this);


//        fragmentContent =
//                new FragmentContent();
        fragmentJD = new FragmentJD();
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fl_container, fragmentContent)
//                .addToBackStack(FragmentContent.getName())
//                .commit();
//
//        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.fl_container, fragmentJD)
//                        .hide(fragmentContent)
//                        .addToBackStack(FragmentContent.getName())
//                        .commit();
//            }
//        });

    }

    @OnClick({R.id.btn1})
    public void switchContent() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, fragmentJD)
                .addToBackStack(FragmentContent.getName())
                .commit();

        drawerLayout.closeDrawer(navView,false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
