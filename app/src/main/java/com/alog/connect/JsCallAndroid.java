package com.alog.connect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Author: 10067835.
 * Create date: 2018/11/8
 * mail: bingyuan@alog.com
 * description:
 */

public class JsCallAndroid extends AppCompatActivity {
    private WebView webView;
    private TextView textView;
    private TextView jump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jscallandroid);
        initView();
        initSetting();
    }

    private void initSetting() {
        WebSettings webSettings = webView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        webView.addJavascriptInterface(new AndroidJs(), "test");//AndroidtoJS类对象映射到js的test对象

        // 加载JS代码
        webView.loadUrl("file:///android_asset/jsCallAndroid.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                System.out.print("Url--->" + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);

            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                System.out.print("Url--->" + url+":message-->"+message);
                result.confirm("js调用了Android的方法成功啦");
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }
        });

    }

    private void initView() {
        webView = findViewById(R.id.webView);
        textView = findViewById(R.id.btn);
        jump = findViewById(R.id.jump);
    }
}
