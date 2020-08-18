package com.test.viewpagedemo.WebView;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.study.point.R;
import com.study.point.databinding.ActivityWebviewBinding;
import com.test.viewpagedemo.LoggerUtils;
import com.test.viewpagedemo.MainApp;

public class WebViewActivity extends AppCompatActivity {
    ActivityWebviewBinding bind;

    public static WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        bind.setLifecycleOwner(this);

        webView = new WebView(getApplicationContext());
        setWebView(webView);
        webView.setWebViewClient(new MyWebviewClient());
        LoggerUtils.LOGV("init web = " + webView);
        bind.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Debug.startMethodTracing(getCacheDir() + "/webview.trace", 1024 * 1024 * 100);
                LoggerUtils.LOGV("webView start");
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                bind.llContent.addView(webView, layoutParams);
                LoggerUtils.LOGV("init web = " + webView);
                webView.loadUrl("https://pinyin.sogou.com/mac");

            }
        });
//        bind.memory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Runtime rt = Runtime.getRuntime();
//                long maxMemory = rt.maxMemory();
//                LoggerUtils.LOGV("maxMemory:" + Long.toString(maxMemory / (1024 * 1024)));
//                long totalMemory = rt.totalMemory();
//                LoggerUtils.LOGV("totalMemory:" + Long.toString(totalMemory / (1024 * 1024)));
//                long freeMemory = rt.freeMemory();
//                LoggerUtils.LOGV("freeMemory:" + Long.toString(freeMemory / (1024 * 1024)));
//            }
//        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.llParent.removeView(webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }
}
