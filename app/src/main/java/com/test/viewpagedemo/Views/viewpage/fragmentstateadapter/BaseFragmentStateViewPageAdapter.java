package com.test.viewpagedemo.Views.viewpage.fragmentstateadapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.test.viewpagedemo.LoggerUtils;

import java.util.List;

public class BaseFragmentStateViewPageAdapter extends FragmentStatePagerAdapter {


    List<Fragment> data;

    public BaseFragmentStateViewPageAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * object为当前的fragment
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
        LoggerUtils.LOGD("restoreState");
    }

    @Override
    public Parcelable saveState() {
        LoggerUtils.LOGD("saveState");
        return super.saveState();
    }
}
