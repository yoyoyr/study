package main.java.com.test.viewpagedemo.WebView;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.study.point.R;
import com.study.point.databinding.ActivityWebviewBinding;

public class WebViewActivity extends AppCompatActivity {
    ActivityWebviewBinding bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        bind.setLifecycleOwner(this);
        bind.webView.setWebViewClient(new MyWebviewClient());
        setWebView();
        bind.webView.loadUrl("https://www.baidu.com/");

        bind.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.webView.reload();
            }
        });
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {
        WebSettings webSettings = bind.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }
}
