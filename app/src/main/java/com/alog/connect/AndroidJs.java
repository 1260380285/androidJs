package com.alog.connect;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Author: 10067835.
 * Create date: 2018/11/8
 * mail: bingyuan@alog.com
 * description:
 */

public class AndroidJs {
    private Context context;

    public AndroidJs ( Context context ) {
        this.context = context;
    }

    @JavascriptInterface
    public void hello ( String msg ) {
        Toast.makeText ( context , msg , Toast.LENGTH_LONG ).show ( );
    }

}
