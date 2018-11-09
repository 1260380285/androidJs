package com.alog.connect;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Author: 10067835.
 * Create date: 2018/11/8
 * mail: bingyuan@alog.com
 * description:
 */

public class AndroidJs {

    @JavascriptInterface
    public void hello(String msg) {
        System.out.println(msg);
    }

}
