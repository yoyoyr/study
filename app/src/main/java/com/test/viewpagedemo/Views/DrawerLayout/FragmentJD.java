package com.test.viewpagedemo.Views.DrawerLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.point.R;


public class FragmentJD extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jd, container, false);

        getChildFragmentManager().beginTransaction()
                .add(R.id.left, new FragmentLeft())
                .commit();

        return view;

    }

    public static String getName() {
        return "FragmentJD";
    }
}
