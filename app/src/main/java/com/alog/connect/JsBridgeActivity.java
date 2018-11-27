package com.alog.connect;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

/**
 * Author: 10067835.
 * Create date: 2018/11/15
 * mail: bingyuan@alog.com
 * description:
 */

public class JsBridgeActivity extends AppCompatActivity {
    private EditText editText;
    private BridgeWebView bridgeWebView;
    private TextView btnSend;
    private TextView btnReset;
    private MyHandlerCallBack.OnSendDataListener mOnSendDataListener;

    @Override
    protected void onCreate ( @Nullable Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.jsbridge );
        initView ( );
        initListener ( );
        initWebView ( );
    }

    private void initWebView ( ) {
        //辅助WebView设置处理关于页面跳转，页面请求等操作
        bridgeWebView.setWebViewClient ( new MyWebViewClient ( bridgeWebView , JsBridgeActivity.this ) );
        //Handler做为通信桥梁的作用，接收处理来自H5数据及回传Native数据的处理，当h5调用send()发送消息的时候，调用MyHandlerCallBack
        bridgeWebView.setDefaultHandler ( new MyHandlerCallBack ( mOnSendDataListener ) );
        //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等比等，不过它还能处理文件上传操作
        // bridgeWebView.setWebChromeClient ( new MyChromeWebClient ( ) );
        // 如果不加这一行，当点击界面链接，跳转到外部时，会出现net::ERR_CACHE_MISS错误
        // 需要在androidManifest.xml文件中声明联网权限
        // <uses-permission android:name="android.permission.INTERNET"/>
        if ( Build.VERSION.SDK_INT >= 19 ) {
            bridgeWebView.getSettings ( ).setCacheMode ( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }

        //加载网页地址
        bridgeWebView.loadUrl ( "file:///android_asset/web.html" );

        //有方法名的都需要注册Handler后使用
        bridgeWebView.registerHandler ( "submitFromWeb" , new BridgeHandler ( ) {
            @Override
            public void handler ( String data , CallBackFunction function ) {
                if ( ! TextUtils.isEmpty ( data ) ) {
                    editText.setText ( "通过调用Native方法submitFromWeb接收数据：\n" + data );
                }
                function.onCallBack ( "Native已经接收到数据：" + data + "，请确认！" );
            }
        } );

        //初始化就调用
        bridgeWebView.send ( "初始化 native 向js发送的消息" , new CallBackFunction ( ) {
            @Override
            public void onCallBack ( String data ) {
              //  Toast.makeText ( JsBridgeActivity.this , "bridge.init初始化数据成功" + data , Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }

    private void initListener ( ) {
        btnSend.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                String text = editText.getText ( ).toString ( );
                //应用启动后初始化数据调用，js处理方法connectWebViewJavascriptBridge(function(bridge)
                bridgeWebView.callHandler ( "functionInJs" , text , new CallBackFunction ( ) {
                    @Override
                    public void onCallBack ( String data ) {

                        editText.setText ( "向h5发送初始化数据成功，接收h5返回值为：\n" + data );
                    }
                } );
            }
        } );

        btnReset.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                bridgeWebView.loadUrl ( "file:///android_asset/web.html" );
            }
        } );
        mOnSendDataListener = new MyHandlerCallBack.OnSendDataListener ( ) {
            @Override
            public void sendData ( String data ) {
                editText.setText ( "通过webview发消息接收到数据：\n" + data );
            }
        };
    }

    private void initView ( ) {
        editText = findViewById ( R.id.edittext );
        bridgeWebView = findViewById ( R.id.webview );
        btnSend = findViewById ( R.id.submit );
        btnReset = findViewById ( R.id.reset );
    }
}
