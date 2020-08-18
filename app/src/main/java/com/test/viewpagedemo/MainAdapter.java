package com.test.viewpagedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lyl.animationtest.base.ShareAnimActivity;
import com.study.point.R;
import com.test.viewpagedemo.AOP.AOPActivity;
import com.test.viewpagedemo.Bitmap.BitmapActivity;
import com.test.viewpagedemo.DaggerNew.NewDaggerActivity;
import com.test.viewpagedemo.Views.DrawerLayout.DrawerActivity;
import com.test.viewpagedemo.inding.DatabindingActivity;
import com.test.viewpagedemo.EventBus.EventBusActivity;
import com.test.viewpagedemo.Glide.GlideActivity;
import com.test.viewpagedemo.GreenDao.GreenDaoActivity;
import com.test.viewpagedemo.LeakCanary.TestLeakCanaryActivity;
import com.test.viewpagedemo.Lifecycle.ObserableActivity;
import com.test.viewpagedemo.OkHttp.OkHttpActivity;
import com.test.viewpagedemo.Retrofit.RetrofitAndRxJava;
import com.test.viewpagedemo.RxJavaNew.RxJavaNew;
import com.test.viewpagedemo.SVG.SVGActivity;
import com.test.viewpagedemo.Views.RecyclerView.RecyclerViewAct;
import com.test.viewpagedemo.Views.ScrollTest.ScrollAtivity;
import com.test.viewpagedemo.Views.SelfView.MySlideActivity;
import com.test.viewpagedemo.Views.TestCoordinatorLayout.TestCoordinatorLayoutActivity;
import com.test.viewpagedemo.Views.event.EventTestAct;
import com.test.viewpagedemo.Views.fragment.FragmentBaseActivity;
import com.test.viewpagedemo.Views.motionlayout.MotionLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import com.test.viewpagedemo.WebView.WebViewActivity;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.VH> {

    static {
        LoggerUtils.LOGD("init static main adapter!");
    }

    @NonNull
    public List<String> titles = new ArrayList<>();
    @NonNull
    List<Intent> intents = new ArrayList<>();
    MainActivity context;

    public MainAdapter(MainActivity context) {
        this.context = context;

        titles.add("组件化");
        intents.add(new Intent());
        titles.add("hook");
        intents.add(new Intent());
        titles.add("AOP");
        intents.add(new Intent(context, AOPActivity.class));
        titles.add("fragment");
        intents.add(new Intent(context, FragmentBaseActivity.class));
        titles.add("touch event");
        intents.add(new Intent(context, EventTestAct.class));
        titles.add("DrawerActivity");
        intents.add(new Intent(context, DrawerActivity.class));
        titles.add("ViewPage");
        intents.add(new Intent(context, com.test.viewpagedemo.Views.viewpage.fragmentadapter.FragmentadapterActivity.class));
//        titles.add("fragmentadapter 和懒加载机制实现");
//        intents.add(new Intent(context, FragmentadapterActivity.class));
//        titles.add("fragmentStateadapter");
//        intents.add(new Intent(context, FragmentStateadapterActivity.class));
//        titles.add("侧滑菜单1");
//        intents.add(new Intent(context, DrawerActivity.class));
        titles.add("CoordinatorLayout");
        intents.add(new Intent(context, TestCoordinatorLayoutActivity.class));
        titles.add("Databinding");
        intents.add(new Intent(context, DatabindingActivity.class));
        titles.add("RecyclerView");
        intents.add(new Intent(context, RecyclerViewAct.class));
        titles.add("self view");
        intents.add(new Intent(context, MySlideActivity.class));
        titles.add("scroll view");
        intents.add(new Intent(context, ScrollAtivity.class));
        titles.add("RxJava");
        intents.add(new Intent(context, RxJavaNew.class));
        titles.add("OkHttp");
        intents.add(new Intent(context, OkHttpActivity.class));
        titles.add("retrofit");
        intents.add(new Intent(context, RetrofitAndRxJava.class));
        titles.add("GreenDao");
        intents.add(new Intent(context, GreenDaoActivity.class));
        titles.add("NewDagger");
        intents.add(new Intent(context, NewDaggerActivity.class));
        titles.add("LeakCanary");
        intents.add(new Intent(context, TestLeakCanaryActivity.class));
        titles.add("bitmap");
        intents.add(new Intent(context, BitmapActivity.class));
        titles.add("Glide");
        intents.add(new Intent(context, GlideActivity.class));
        titles.add("EventBus");
        intents.add(new Intent(context, EventBusActivity.class));
        titles.add("svg");
        intents.add(new Intent(context, SVGActivity.class));
        titles.add("lifecycle");
        intents.add(new Intent(context, ObserableActivity.class));
        titles.add("shareAnim");
        intents.add(new Intent(context, ShareAnimActivity.class));
        titles.add("MotionLyout");
        intents.add(new Intent(context, MotionLayoutActivity.class));
        titles.add("WebView");
        intents.add(new Intent(context, WebViewActivity.class));
//        titles.add("SPI");
//        intents.add(new Intent(context, SPIActivity.class));
//        titles.add("MVVM");
//        intents.add(new Intent(context, MVVMActivity.class));

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.textView.setText(titles.get(position));
        if (position == 0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoggerUtils.LOGD("route");
                    ARouter.getInstance().build(RouteBean.modularize).navigation();
                }
            });
        } else if (position == 1) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoggerUtils.LOGD("route");
//                    ARouter.getInstance().build(RouteBean.hook).navigation();
                    while (true) {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 10000; ++i) {
                            list.add("" + i);
                        }
                    }

                }
            });

        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(intents.get(position));
                }
            });
        }


//        try {
//            throw new NullPointerException("yoyo");
//        } catch (NullPointerException e) {
//            LoggerUtils.LOGE(e);
//        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class VH extends RecyclerView.ViewHolder {


        TextView textView;

        public VH(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text);
        }
    }
}
