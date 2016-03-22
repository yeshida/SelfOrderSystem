package com.centerm.selforder.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;

/**
 * Created by caiqingyuan on 16/3/11.
 */
public class EmailWebviewActivity  extends SubpageActivity{
    private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_webview);

        setSubTitle("注册");

        webview = (WebView) findViewById(R.id.webView);

        webview.loadUrl("http://www.baidu.com/");
    }
}
