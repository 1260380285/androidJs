package com.alog.connect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private TextView textView;
    private TextView evaluateByJavascriptView;
    private TextView jump;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        initView ( );
        initSetting ( );
        initListener ( );
    }

    private void initSetting ( ) {
        WebSettings webSettings = webView.getSettings ( );
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled ( true );
        // 设置允许JS弹窗
        // webSettings.setJavaScriptCanOpenWindowsAutomatically ( true );

        // 先载入JS代码
        // webView.loadUrl ( "https://www.baidu.com/" );
        webView.loadUrl ( "file:///android_asset/javascript.html" );
        webView.setWebViewClient ( new WebViewClient ( ) {
            @Override
            public boolean shouldOverrideUrlLoading ( WebView view , WebResourceRequest request ) {
                return super.shouldOverrideUrlLoading ( view , request );
            }
        } );
        //一定要写上这个才能弹alert，这个是承载体
        webView.setWebChromeClient ( new WebChromeClient ( ) {
           /* @Override
            public boolean onJsAlert ( WebView view , String url , String message , final JsResult result ) {
                AlertDialog.Builder b = new AlertDialog.Builder ( MainActivity.this );
                b.setTitle ( "Alert" );
                b.setMessage ( message );
                b.setPositiveButton ( android.R.string.ok , new DialogInterface.OnClickListener ( ) {
                    @Override
                    public void onClick ( DialogInterface dialog , int which ) {
                        result.confirm ( );
                    }
                } );
                b.setCancelable ( false );
                b.create ( ).show ( );
                return true;
            }*/

        } );
    }

    private void initListener ( ) {
        jump.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent = new Intent ( getApplicationContext ( ) , JsCallAndroidActivity.class );
                startActivity ( intent );
            }
        } );
        textView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                webView.loadUrl ( "javascript:callJS('hello')" );
            }
        } );
        evaluateByJavascriptView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                evaluateJavascript ( );
            }
        } );

    }

    private void evaluateJavascript ( ) {
        webView.evaluateJavascript ( "javascript:callJsByEvaluateJavascript()" , new ValueCallback < String > ( ) {
            @Override
            public void onReceiveValue ( String value ) {
                Toast.makeText ( getApplicationContext ( ) , value , Toast.LENGTH_LONG ).show ( );
            }
        } );
    }

    private void initView ( ) {
        webView = findViewById ( R.id.webView );
        textView = findViewById ( R.id.loadUrl );
        evaluateByJavascriptView = findViewById ( R.id.evaluateByJavascript );
        jump = findViewById ( R.id.jump );
    }
}
