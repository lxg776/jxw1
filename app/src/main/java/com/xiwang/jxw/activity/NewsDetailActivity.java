package com.xiwang.jxw.activity;

import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.CommentListAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.NewsDetailBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.NewsBiz;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.util.StringUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.widget.HorizontalRadioView;
import com.xiwang.jxw.widget.LoadingLayout;
import com.xiwang.jxw.widget.RefreshLayout;
import java.io.IOException;

/**
 * 帖子详情界面
 * Created by sunshine on 15/11/9.
 */
public class NewsDetailActivity extends BaseActivity implements RefreshLayout.OnLoadListener,RefreshLayout.OnRefreshListener{
    /** webview用于显示html内容*/
    private WebView webView;
    /** 内容布局*/
    private LoadingLayout content_rl;
    /** 传进来的 newsitem数据*/
    NewsBean newsBean;
    /** 传进来的栏目数据 */
    ColumnBean columnBean;
    /** 详情实体*/
    NewsDetailBean detailBean;
    /** 作者tv*/
    TextView author_tv;
    /** 发布时间*/
    TextView publish_tv;
    /** 作者头像iv*/
    ImageView author_headimg_iv;
    /** 下拉刷新和上来更多控件*/
    RefreshLayout refreshLayout;

    /** 点赞 按钮*/
    HorizontalRadioView like_rv;
    /** 点不赞 按钮*/
    HorizontalRadioView not_like_rv;
    /** 评论 按钮*/
    HorizontalRadioView message_rv;

    /** 评论列表*/
    ListView listView;
    /** 发布人布局*/
    View headView;
    /** 评论列表 适配器*/
    CommentListAdapter listAdapter;
    /** 当前评论的页数*/
    int currentPage=1;
    /** 顶部导航*/
    Toolbar toolbar;

    /** 图片的异步显示的选项*/
    DisplayImageOptions options=ImgLoadUtil.getUserOptions();
    /** 加载监听*/
    ImageLoadingListener loadingListener=ImgLoadUtil.defaultLoadingListener();
    /** -1表示没点赞和点差/0表示点赞/1表示点差*/
    int likefla=-1;

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(columnBean.getName());
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
        return R.layout.activity_news_detail;
    }

    @Override
    protected void findViews() {
        content_rl= (LoadingLayout) findViewById(R.id.content_rl);
        content_rl.setLoadView(true);
        content_rl.setContentLayout(View.inflate(context, R.layout.view_news_detail_content, null));
        headView=View.inflate(context,R.layout.view_news_detail_head, null);
        refreshLayout= (RefreshLayout) findViewById(R.id.refreshLayout);
        listView= (ListView) findViewById(R.id.listView);
        webView= (WebView) headView.findViewById(R.id.webView);
        author_tv= (TextView) headView.findViewById(R.id.author_tv);
        publish_tv= (TextView) headView.findViewById(R.id.publish_tv);
        author_headimg_iv= (ImageView) headView.findViewById(R.id.author_headimg_iv);
        listView.addHeaderView(headView, null, false);
        refreshLayout.setChildView(listView);

        like_rv= (HorizontalRadioView) findViewById(R.id.like_rv);
        not_like_rv= (HorizontalRadioView) findViewById(R.id.not_like_rv);
        message_rv= (HorizontalRadioView) findViewById(R.id.message_rv);


    }

    @Override
    protected void init() {
        loadNetData(1,true);
    }

    /**
     * 加载网络数据
     * @param page 页数
     */
    private void loadNetData(final int page, final boolean loadCache){
        NewsBiz.getNewsDetail(newsBean.getTid(),currentPage, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                currentPage=page;
                if(page==1){
                    //如果是第一页加载显示详情
                    showNewsDetail(responseBean);
                }else{
                    NewsDetailBean bean= (NewsDetailBean) responseBean.getObject();
                    showCommentList(bean);
                }
            }
            @Override
            public void onFail(ResponseBean responseBean) {
                ToastUtil.showToast(context,responseBean.getInfo());
            }

            @Override
            public ResponseBean getRequestCache() {
                if(loadCache&&page==1){
                    return  (ResponseBean)SpUtil.getObject(context,getString(R.string.cache_newsdetail)+newsBean.getTid());
                }
                return  null;
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                showNewsDetail(result);
            }
        });
    }

    @Override
    protected void initGetData() {
        newsBean= (NewsBean) getIntent().getSerializableExtra(getString(R.string.send_news));
        columnBean=(ColumnBean)getIntent().getSerializableExtra(getString(R.string.send_column));
        listAdapter=new CommentListAdapter(this);
        listView.setAdapter(listAdapter);
    }

    /**
     * 显示新闻详情
     */
    private void showNewsDetail(ResponseBean responseBean){
        content_rl.setLoadView(false);
        detailBean= (NewsDetailBean) responseBean.getObject();
        String htmlStr="";
        try {
            htmlStr = StringUtil.inputStream2String(getAssets().open("news_temp.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlStr=htmlStr.replace("#content#", detailBean.getContent());
        htmlStr=htmlStr.replace("#subject#", detailBean.getSubject());
        htmlStr=  htmlStr.replaceAll("href=.*?\"", "");

        webView.getSettings().setDefaultTextEncodingName("utf-8") ;
        webView.loadData(htmlStr, "text/html", "utf-8");
        //webView.loadUrl("http://www.sznews.com");

        author_tv.setText(detailBean.getAuthor());
        publish_tv.setText(detailBean.getPostdate());
        author_headimg_iv.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        ImgLoadUtil.getInstance().displayImage(detailBean.getFace(), author_headimg_iv, options, loadingListener);
        showCommentList(detailBean);

        like_rv.setText(detailBean.getDig());
        not_like_rv.setText(detailBean.getPoor());
        message_rv.setText(detailBean.getReplies());
    }
    /**
     * 显示新闻评论
     */
    private void showCommentList(NewsDetailBean newsDetailBean){
        if(Integer.parseInt(newsDetailBean.getPage())==1){
            listAdapter.setCommentBeanList(newsDetailBean.getCommentList());
        }else{
            listAdapter.getCommentBeanList().addAll(newsDetailBean.getCommentList());
        }
        finishLoad();
    }


    /**
     * 加载完成
     */
    private void finishLoad(){
        listAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        refreshLayout.setLoading(false);
        webView.getSettings().setSupportZoom(false);



    }


    @Override
    protected void widgetListener() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);

        /** 屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件*/
        webView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {
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
    public void onLoad() {
        loadNetData(currentPage+1,false);
    }

    @Override
    public void onRefresh() {
        loadNetData(currentPage+1,true);
    }
}


