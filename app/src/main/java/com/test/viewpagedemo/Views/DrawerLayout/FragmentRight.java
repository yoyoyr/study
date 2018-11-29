package com.test.viewpagedemo.Views.DrawerLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

public class FragmentRight extends Fragment {

    String name;

    public String getName() {
        return name;
    }

    public static FragmentRight newInstance(String content) {
        FragmentRight fragmentRight = new FragmentRight();
        Bundle bundle = fragmentRight.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("content", content);
        fragmentRight.name = content;
        fragmentRight.setArguments(bundle);
        return fragmentRight;
    }

//    public FragmentRight() {
//        EventBus.getDefault().register(this);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ((TextView) view.findViewById(R.id.tv)).setText((CharSequence) getArguments().get("content"));

        return view;

    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void reflashContent(String content) {
//        if (getActivity() != null && getView() != null) {
//            ((TextView) getView().findViewById(R.id.tv)).setText(content);
//        }
//    }

    @Override
    public void onDestroy() {
        LoggerUtils.LOGD("destory " + name);
        super.onDestroy();
    }

}
