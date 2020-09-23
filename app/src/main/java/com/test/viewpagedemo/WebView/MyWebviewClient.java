package com.test.viewpagedemo.WebView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Debug;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.viewpagedemo.LoggerUtils;

import org.greenrobot.eventbus.EventBus;

public class MyWebviewClient extends WebViewClient {


    /**
     * 当加载的网页需要重定向的时候就会回调这个函数告知我们应用程序是否需要接管控制网页加载，如果应用程序接管，
     * 并且return true意味着主程序接管网页加载，如果返回false让webview自己处理。
     */
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        String url = request.getUrl().toString();
//        if (url.contains("uniswap-info")) {
//            view.loadUrl(request.getUrl().toString());
//            return true;
//        }
//        return false;
//
//    }

    //页面开始加载
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        LoggerUtils.LOGV("start page " + url);
        super.onPageStarted(view, url, favicon);
    }

    //页面加载结束
    @Override
    public void onPageFinished(WebView view, String url) {
        LoggerUtils.LOGV("page end");
        Debug.stopMethodTracing();
    }


    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
            error) {
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
    }


}
