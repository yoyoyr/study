package com.test.viewpagedemo.Views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       LoggerUtils.LOGD("onCreateView");
        View view = inflater.inflate(R.layout.fragment2, container, false);
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        LoggerUtils.LOGD("INITIALIZING  step1 onAttach");
        super.onAttach(context);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
//        LoggerUtils.LOGD("INITIALIZING  step2  如果有子fragment，下发子fragment");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LoggerUtils.LOGD("INITIALIZING  step3 onCreate" );
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LoggerUtils.LOGD("CREATED  step2 onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LoggerUtils.LOGD("hidden = " + hidden);
        super.onHiddenChanged(hidden);
    }
    @Override
    public void onStart() {
        LoggerUtils.LOGD("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LoggerUtils.LOGD("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LoggerUtils.LOGD("onPause");
        super.onPause();
    }

    @Override
    public void onDetach() {
        LoggerUtils.LOGD("onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        LoggerUtils.LOGD("onDestroy");
        super.onDestroy();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LoggerUtils.LOGD("isVisibleToUser = " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static String getFragmentTag() {
        return "fragment2";
    }

}
