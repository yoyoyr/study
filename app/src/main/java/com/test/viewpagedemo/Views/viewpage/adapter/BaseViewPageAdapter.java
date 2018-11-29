package com.test.viewpagedemo.Views.viewpage.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.test.viewpagedemo.LoggerUtils;

import java.util.List;

public class BaseViewPageAdapter extends PagerAdapter {


    List<View> data;

    public BaseViewPageAdapter(List<View> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
//        LoggerUtils.LOGD("getCount");
        return data.size();
    }

    /**
     * childer view是否等于ItemInfo.object
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
//        LoggerUtils.LOGD("view = " + view + ", object " + object);
        return view == object;
    }

    /**
     * 当前显示的View
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        LoggerUtils.LOGD("position = " + position);
        super.setPrimaryItem(container, position, object);
    }

    /**
     * Viewpage需要新的view时候调用
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LoggerUtils.LOGD(" position = " + position);
        View view = data.get(position);
        container.addView(view);
        return view;
    }

    /**
     * view从ViewPage移除的时候调用
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LoggerUtils.LOGD(" position = " + position);
        container.removeView(data.get(position));
    }

    /**
     * 保存一些状态
     *
     * @return
     */
    @Override
    public Parcelable saveState() {
        return super.saveState();
    }
}
