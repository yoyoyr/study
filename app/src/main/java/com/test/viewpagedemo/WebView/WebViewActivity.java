package com.test.viewpagedemo.WebView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.study.point.R;
import com.study.point.databinding.ActivityWebviewBinding;
import com.test.viewpagedemo.LoggerUtils;
import com.test.viewpagedemo.MainApp;

public class WebViewActivity extends AppCompatActivity {
    ActivityWebviewBinding bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        bind.setLifecycleOwner(this);

        bind.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Debug.startMethodTracing(getCacheDir() + "/webview.trace", 1024 * 1024 * 100);
                LoggerUtils.LOGV("webView start");
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                OnePxActivity.mWebView.scrollToTop();
                bind.llContent.addView(OnePxActivity.mWebView, layoutParams);
                OnePxActivity.mWebView.loadUrl("https://more.ethte.com/web/uniswap-info/#/pair/0xc5be99a02c6857f9eac67bbce58df5572498f40c");

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        LoggerUtils.LOGE("remove");
        ((ViewGroup) OnePxActivity.mWebView.getParent()).removeView(OnePxActivity.mWebView);
    }

}
