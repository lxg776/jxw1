package com.xiwang.jxw.activity;

import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.config.ServerConfig;

import java.net.ServerSocket;

/**
 * Created by sunshine on 15/12/22.
 */
public class ServiceProtocolActivity extends BaseActivity {
    /** 网页控件*/
    WebView mWebView;

    @Override
    protected String getPageName() {
        return "注册协议a";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("西网服务协议");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_service_protocol;
    }

    @Override
    protected void findViews() {
        mWebView= (WebView) findViewById(R.id.mWebView);
        mWebView.getSettings().setSupportZoom(false);
        /** 屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件*/
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {
       // mWebView.loadUrl(ServerConfig.PROTOCOL_URL);
    }

    @Override
    protected void widgetListener() {

    }
}
