package com.test.viewpagedemo.Views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

/**
 * =====add
 * D: onAttach
 * D: onCreate
 * D: onCreateView
 * D: onActivityCreated
 * D: onStart
 * D: onResume
 * =====remove
 * D: onPause
 * D: onDestroyView
 * D: onDestroy
 * D: onDetach
 * =====detach
 * D: onPause
 * D: onDestroyView
 * ===attch
 * D: onCreateView
 * D: onActivityCreated
 * D: onStart
 * D: onResume
 * ====hide
 * onHiddenChanged hidden = true
 * ====show
 * onHiddenChanged hidden = false
 * ====replace
 * D/Fragment2.onAttach(L:31): INITIALIZING  step1 onAttach
 * D/Fragment2.onCreate(L:43): INITIALIZING  step3 onCreate
 * D/Fragment1.onPause(L:144): onPause
 * D/Fragment1.onDestroyView(L:138): onDestroyView
 * D/Fragment1.onDestroy(L:151): onDestroy
 * D/Fragment1.onDetach(L:158): onDetach
 * D/Fragment2.onCreateView(L:19): onCreateView
 * D/Fragment2.onActivityCreated(L:48): CREATED  step2 onActivityCreated
 * D/Fragment2.onStart(L:59): onStart
 * D/Fragment2.onResume(L:65): onResume
 */
public class Fragment1 extends Fragment {


    /**
     * 所有的bundle都是f.mSavedFragmentState
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LoggerUtils.LOGD("onCreateView");

        if (savedInstanceState != null) {
            LoggerUtils.LOGD("fragment onRestoreInstanceState " + savedInstanceState.getString("key"));
        }
        if (getView() != null) {
            LoggerUtils.LOGD("getView()");
            return getView();
        }

        View view = inflater.inflate(R.layout.fragment1, container, false);

//        final Button btn1 = view.findViewById(R.id.tv1);
//        final Button btn2 = view.findViewById(R.id.tv2);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn1.setBackground(getResources().getDrawable(R.drawable.stake_button_pressed));
//                btn2.setBackground(getResources().getDrawable(R.drawable.stake_button_normal));
//            }
//        });
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn2.setBackground(getResources().getDrawable(R.drawable.stake_button_pressed));
//                btn1.setBackground(getResources().getDrawable(R.drawable.stake_button_normal));
//            }
//        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        LoggerUtils.LOGD("onAttach");
        super.onAttach(context);
    }

    /**
     * 当使用add()+show()，hide()跳转新的Fragment时，旧的Fragment回调onHiddenChanged()，
     * 不会回调onStop()等生命周期方法，而新的Fragment在创建时是不会回调onHiddenChanged()
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        LoggerUtils.LOGD("onHiddenChanged hidden = " + hidden);
        super.onHiddenChanged(hidden);
    }

    /**
     * isVisibleToUser为true表明是当前ViewPage加载页面
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LoggerUtils.LOGD("isVisibleToUser = " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        LoggerUtils.LOGD("onAttachFragment INITIALIZING  step2  如果有子fragment，下发子fragment");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LoggerUtils.LOGD("onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LoggerUtils.LOGD("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
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

    /**
     * f.mContainer.removeView(f.mView);
     */
    @Override
    public void onDestroyView() {
        LoggerUtils.LOGD("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        LoggerUtils.LOGD("onPause");
        super.onPause();
    }


    @Override
    public void onDestroy() {
        LoggerUtils.LOGD("onDestroy");
        super.onDestroy();
    }

    //最后调用
    @Override
    public void onDetach() {
        LoggerUtils.LOGD("onDetach");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        LoggerUtils.LOGD("fragment onSaveInstanceState");
        outState.putString("key", "resetf1");
        super.onSaveInstanceState(outState);
    }

    public static String getFragmentTag() {
        return "fragment1";
    }


}
