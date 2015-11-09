package com.xiwang.jxw.activity;

import android.view.View;
import android.webkit.WebView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.widget.LoadingLayout;

/**
 * 帖子详情界面
 * Created by sunshine on 15/11/9.
 */
public class NewsDetailActivity extends BaseActivity{
    /** webview用于显示html内容*/
    private WebView webView;
    /** 内容布局*/
    private LoadingLayout content_rl;

    /** 传进来的 newsitem数据*/
    NewsBean newsBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void findViews() {
        content_rl= (LoadingLayout) findViewById(R.id.content_rl);
        content_rl.setLoadView(true);
        content_rl.setContentLayout(View.inflate(context, R.layout.view_news_detail, null));
        webView= (WebView) findViewById(R.id.webView);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                content_rl.setLoadView(false);
            }
        }, 3000);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }
}
