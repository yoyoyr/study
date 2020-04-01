package com.test.viewpagedemo.EventBus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.point.R;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class FragmentB extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentb, container, false);

        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = FragmentB.this;

                if (fragment.getActivity() != null) {
                    EventBus.getDefault().post(new FragmentB.Message("result for B"));
                    fragment.getFragmentManager().popBackStack();
                }
            }
        });

        //贵在空间效率
        Map map = new ArrayMap<>();

        return view;
    }

    public static String getName() {
        return "FragmentB";
    }


    public static class Message {

        String content;

        public Message(String content) {
            this.content = content;
        }
    }
}
