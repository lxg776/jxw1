package com.xiwang.jxw.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.CommentListAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.NewsDetailBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.NewsBiz;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.util.StringUtil;
import com.xiwang.jxw.util.ToastUtil;
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
    /** 评论列表*/
    ListView listView;
    /** 发布人布局*/
    View headView;
    /** 评论列表 适配器*/
    CommentListAdapter listAdapter;

    int currentPage=1;

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
        webView= (WebView) findViewById(R.id.webView);
        author_tv= (TextView) findViewById(R.id.author_tv);
        publish_tv= (TextView) findViewById(R.id.publish_tv);
        author_headimg_iv= (ImageView) findViewById(R.id.author_headimg_iv);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                content_rl.setLoadView(false);
            }
        }, 3000);

        listView.addHeaderView(headView,null,false);
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
        listAdapter=new CommentListAdapter(this);
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
        htmlStr=htmlStr.replace("#content#",detailBean.getContent());
        htmlStr=htmlStr.replace("#subject#",detailBean.getSubject());
        webView.getSettings().setDefaultTextEncodingName("utf-8") ;
        webView.loadData(htmlStr, "text/html", "utf-8");
        author_tv.setText(detailBean.getAuthor());
        publish_tv.setText(detailBean.getPostdate());
        author_headimg_iv.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        showCommentList(detailBean);
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
    }


    @Override
    protected void widgetListener() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);
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