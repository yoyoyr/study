package com.test.viewpagedemo.WebView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.test.viewpagedemo.LoggerUtils;

public class OnePxActivity extends AppCompatActivity {

    public static WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);

        webView = new WebView(getApplicationContext());
        setWebView(webView);
        webView.setWebViewClient(new MyWebviewClient());
        webView.loadUrl("https://pinyin.sogou.com/mac");
        LoggerUtils.LOGV("init web = " + webView);

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
