package com.test.viewpagedemo.Views.TestCoordinatorLayout.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.point.R;
import com.test.viewpagedemo.EventBus.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

public class BottomDialog extends BaseBottomDialog {

    private static final String KEY_LAYOUT_RES = "bottom_layout_res";
    private static final String KEY_HEIGHT = "bottom_height";
    private static final String KEY_DIM = "bottom_dim";
    private static final String KEY_CANCEL_OUTSIDE = "bottom_cancel_outside";

    private FragmentManager mFragmentManager;

    private boolean mIsCancelOutside = super.getCancelOutside();

    private String mTag = super.getFragmentTag();

    private float mDimAmount = super.getDimAmount();

    private int mHeight = super.getHeight();

    @LayoutRes
    private int mLayoutRes;

    private ViewListener mViewListener;
    private ViewListener2 listenerV2;
    private onDialogDisMissListener listener;
    private Map<String, Object> map;
    private DialogInterface.OnCancelListener cancelListener;

    public void setData(Map<String, Object> map) {
        this.map = map;
    }

    public static BottomDialog create(FragmentManager manager) {
        BottomDialog dialog = new BottomDialog();
        dialog.setFragmentManager(manager);
        return dialog;
    }

    public static BottomDialog create(Context context) {
        BottomDialog dialog = new BottomDialog();
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.BOTTOM);
            slide.setDuration(1000);
            setExitTransition(slide);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_dapp_transfer_confirm,container,false);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLayoutRes = savedInstanceState.getInt(KEY_LAYOUT_RES);
            mHeight = savedInstanceState.getInt(KEY_HEIGHT);
            mDimAmount = savedInstanceState.getFloat(KEY_DIM);
            mIsCancelOutside = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_RES, mLayoutRes);
        outState.putInt(KEY_HEIGHT, mHeight);
        outState.putFloat(KEY_DIM, mDimAmount);
        outState.putBoolean(KEY_CANCEL_OUTSIDE, mIsCancelOutside);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void bindView(View v) {
        if (mViewListener != null) {
            mViewListener.bindView(v);
        } else if (listenerV2 != null) {
            listenerV2.bindView(v, map);
        }
    }

    @Override
    public int getLayoutRes() {
        return mLayoutRes;
    }

    public BottomDialog setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    public BottomDialog setViewListener(ViewListener listener) {
        mViewListener = listener;
        return this;
    }

    public BottomDialog setViewListenerV2(ViewListener2 listener) {
        this.listenerV2 = listener;
        return this;
    }

    public BottomDialog setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    public BottomDialog setCancelOutside(boolean cancel) {
        mIsCancelOutside = cancel;
        return this;
    }

    public BottomDialog setTag(String tag) {
        mTag = tag;
        return this;
    }

    public BottomDialog setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public BottomDialog setHeight(int heightPx) {
        mHeight = heightPx;
        return this;
    }

    @Override
    public float getDimAmount() {
        return mDimAmount;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public boolean getCancelOutside() {
        return mIsCancelOutside;
    }

    @Override
    public String getFragmentTag() {
        return mTag;
    }

    public void setOnDisMissListener(onDialogDisMissListener listener) {
        this.listener = listener;
    }

    public interface ViewListener {
        void bindView(View v);
    }

    public interface ViewListener2 {
        void bindView(View v, Map<String, Object> dataMap);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (listener != null) {
            listener.onOnDisMiss();
        }
        super.onDismiss(dialog);
    }

    public void setCancelListener(DialogInterface.OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (cancelListener != null) {
            cancelListener.onCancel(dialog);
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        Window window =getDialog().getWindow();
//        window.setBackgroundDrawableResource(R.color.black_60);
//
//    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public BaseBottomDialog show() {
        show(mFragmentManager);
        return this;
    }

    public interface onDialogDisMissListener {
        void onOnDisMiss();
    }

}