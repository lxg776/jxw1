package com.xiwang.jxw.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.CommentListAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.AuthorBean;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.NewsDetailBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.SmileBean;
import com.xiwang.jxw.biz.NewsBiz;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.util.CheckUtil;
import com.xiwang.jxw.util.ToastUtil;
import com.xiwang.jxw.util.keyboardUtil;
import com.xiwang.jxw.widget.AutoRelativeLayout;
import com.xiwang.jxw.widget.EmojiView;
import com.xiwang.jxw.widget.HorizontalRadioView;
import com.xiwang.jxw.widget.LoadingLayout;
import com.xiwang.jxw.widget.RefreshLayout;
import com.xiwang.jxw.widget.RichEditText;
import com.xiwang.jxw.widget.pop.CommentPopWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

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
    /** 底部加载更多 布局*/
    View foot_view;
    /** 底部加载更多 文本*/
    TextView text_more;
    /** 底部加载更多 ProgressBar*/
    ProgressBar indeterminate_progress_library;

    /** 图片的异步显示的选项*/
    DisplayImageOptions options=ImgLoadUtil.getUserOptions();
    /** 加载监听*/
    ImageLoadingListener loadingListener=ImgLoadUtil.defaultLoadingListener();
    /** -1表示没点赞和点差/0表示点赞/1表示点差*/
    int likefla=-1;
    /**图片列表*/
    ArrayList<String> imagesUrlList=new ArrayList<String>();

    /**评论按钮*/
    TextView showComment_btn;
    /**评论的popwindow*/
    CommentPopWindow commentPopWindow;
    /**全布局监听键盘事件*/
    AutoRelativeLayout auto_rl;
    /**评论内容*/
    RichEditText comment_edt;
    EmojiView emoji_view;

    /**
     * 点赞
     */
    /**点赞按钮*/
    LinearLayout dianzan_ll;
    /**点赞次数*/
    TextView dianzan_count_tv;
    /**点赞名单列表*/
    TextView dianzan_users_tv;
    /**点赞详情*/
    LinearLayout dianzan_user_btn;

    @Override
    protected String getPageName() {
        if(null!=columnBean){
            return columnBean.getName()+"帖子详情a";
        }
        return "帖子详情页面";
    }

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
        auto_rl= (AutoRelativeLayout) findViewById(R.id.auto_rl);

        headView=View.inflate(context,R.layout.view_news_detail_head, null);
        refreshLayout= (RefreshLayout) findViewById(R.id.refreshLayout);
        listView= (ListView) findViewById(R.id.listView);
        webView= (WebView) headView.findViewById(R.id.webView);
        author_tv= (TextView) headView.findViewById(R.id.author_tv);
        publish_tv= (TextView) headView.findViewById(R.id.publish_tv);
        author_headimg_iv= (ImageView) headView.findViewById(R.id.author_headimg_iv);
        listView.addHeaderView(headView, null, false);
        like_rv= (HorizontalRadioView) findViewById(R.id.like_rv);
        not_like_rv= (HorizontalRadioView) findViewById(R.id.not_like_rv);
        message_rv= (HorizontalRadioView) findViewById(R.id.message_rv);

        foot_view=View.inflate(context, R.layout.listview_footer_view, null);
        text_more= (TextView) foot_view.findViewById(R.id.text_more);
        if(TApplication.sdk>android.os.Build.VERSION_CODES.HONEYCOMB){
            indeterminate_progress_library = (ProgressBar) foot_view.findViewById(R.id.load_progress_bar);
            IndeterminateProgressDrawable drawable=new IndeterminateProgressDrawable(context);
            drawable.setTint(context.getResources().getColor(R.color.orange_500));
            indeterminate_progress_library.setIndeterminateDrawable(drawable);
        }
        foot_view.setVisibility(View.GONE);
        listView.addFooterView(foot_view);
        refreshLayout.setChildView(listView);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange_500));
        /**添加js调用activity方法 用于展现图片列表*/
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsObject(), "jsObject");
        /**评论部分*/
        showComment_btn= (TextView) findViewById(R.id.showComment_btn);
        commentPopWindow=new CommentPopWindow(this);
        commentPopWindow.setOutsideTouchable(true);
        comment_edt= (RichEditText) findViewById(R.id.comment_edt);
        emoji_view= (EmojiView) findViewById(R.id.emoji_view);
        /**点赞部分*/
        dianzan_ll= (LinearLayout) findViewById(R.id.dianzan_ll);
        dianzan_count_tv= (TextView) findViewById(R.id.dianzan_count_tv);
        dianzan_users_tv= (TextView) findViewById(R.id.dianzan_users_tv);
        dianzan_user_btn= (LinearLayout) findViewById(R.id.dianzan_users_tv);
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



        if(page>1){
            text_more.setText(R.string.more_loading);
            indeterminate_progress_library.setVisibility(View.VISIBLE);
        }

        NewsBiz.getNewsDetail(newsBean.getTid(), currentPage, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                currentPage = page;
                if (page == 1) {
                    //如果是第一页加载显示详情
                    showNewsDetail(responseBean);
                } else {
                    NewsDetailBean bean = (NewsDetailBean) responseBean.getObject();
                    showCommentList(bean);
                }
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                ToastUtil.showToast(context, responseBean.getInfo());
            }

            @Override
            public ResponseBean getRequestCache() {
                if (loadCache && page == 1) {
                    return (ResponseBean) SpUtil.getObject(context, getString(R.string.cache_newsdetail) + newsBean.getTid());
                }
                return null;
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
            htmlStr = CheckUtil.inputStream2String(getAssets().open("news_temp.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlStr=htmlStr.replace("#content#", detailBean.getContent());
        htmlStr=htmlStr.replace("#subject#", detailBean.getSubject());
        htmlStr=  htmlStr.replaceAll("href=.*?\"", "");
        //设置图片列表
        htmlStr=setImagesListByHtml(imagesUrlList,htmlStr);
        webView.getSettings().setDefaultTextEncodingName("utf-8") ;
        webView.loadData(htmlStr, "text/html", "utf-8");
        //webView.loadUrl("http://www.sznews.com");


        showAuthor();
        showCommentList(detailBean);

        like_rv.setText(detailBean.getDig());
        not_like_rv.setText(detailBean.getPoor());
        message_rv.setText(detailBean.getReplies());
    }
    /**正则抽去<img>标签的内容*/
    private static Pattern IMAGE_TAG_PATTERN = Pattern
            .compile("\\<img (.*?)\\ />");
    /**正则抽去<img src= >的内容*/
    private static Pattern SRC_TAG_PATTERN = Pattern.compile("src=\"(.*?)\"");

    /**
     * 显示作者信息
     */
    private void showAuthor(){
        AuthorBean authorBean=detailBean.getUserinfo();
        if(null!=authorBean){
            author_tv.setText(authorBean.getAuthor());
            publish_tv.setText(detailBean.getPostdate());
            ImgLoadUtil.getInstance().displayImage(CommonUtil.getAboutAbsoluteImgUrl(authorBean.getFace()), author_headimg_iv, options, loadingListener);
        }
    }

    /**
     * 显示评论布局
     */
    private void showCommentView(){
        findViewById(R.id.comment_before_ll).setVisibility(View.GONE);
        findViewById(R.id.commenting_ll).setVisibility(View.VISIBLE);
        comment_edt.requestFocus();

        keyboardUtil.showKeyBoard(this, comment_edt);
        emoji_view.setVisibility(View.VISIBLE);
        emoji_view.onKeyBoard();

    }


    /**
     * 显示评论布局
     */
    private void hideCommentView(){
        findViewById(R.id.comment_before_ll).setVisibility(View.VISIBLE);
        findViewById(R.id.commenting_ll).setVisibility(View.GONE);
        onEmojiHide();
    }


    /**
     * 设置图片列表的方法
     * @param imagesUrlList
     * @param html
     * @return
     */
    private String setImagesListByHtml(List<String> imagesUrlList, String html) {
        Matcher matcher = IMAGE_TAG_PATTERN.matcher(html);
        while (matcher.find()) {
            String src = matcher.group(1);
            System.out.println(src);
            Matcher urlMatcher = SRC_TAG_PATTERN.matcher(src);
            while (urlMatcher.find()) {
                String onClickEvent = "  onclick=\"toImages('src')\"  ";
                String url = urlMatcher.group(1);
                if (!imagesUrlList.contains(url)) {
                    imagesUrlList.add(url);
                    onClickEvent = onClickEvent.replace("src", url);
                    html = html.replaceAll(src, src + onClickEvent);
                }
            }
        }
        return html;
    }


    /**attachment/upload/middle/44/2044.jpg?1451383209
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


        text_more.setText(R.string.loadmore);
        indeterminate_progress_library.setVisibility(View.GONE);

    }


    /**
     *  显示表情
     */
    private void onEShow(){


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                emoji_view.setVisibility(View.VISIBLE);
            }
        }, 500);

        keyboardUtil.hideKeyBoard(context, comment_edt);
    }

    /**
     * 监听返回按钮
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if(emoji_view.isContentFla()){
                hideCommentView();
                return  true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 隐藏表情以及键盘
     */
    private void  onEmojiHide(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                emoji_view.onHide();
                emoji_view.setVisibility(View.GONE);
            }
        }, 500);
    }

    /**
     *  显示键盘
     */
    private void onKeyShow(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                emoji_view.setVisibility(View.VISIBLE);
                emoji_view.onKeyBoard();
                 showCommentView();
            }
        }, 500);
    }


    @Override
    protected void widgetListener() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);
        webView.getSettings().setSupportZoom(false);
        /** 屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件*/
        webView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        showComment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onKeyShow();
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




        emoji_view.setEmojiListener(new EmojiView.EmojiListener() {
            @Override
            public void onClickEmojiView(SmileBean bean) {
                if (bean.isDeleteSimile()) {
                    comment_edt.deleteEmoji();
                } else {
                    comment_edt.addEmoji(bean);
                }
            }

            @Override
            public void onEmojiShow() {
                onEShow();
            }

            @Override
            public void onKeyBoard() {
                if (!auto_rl.isKeyBoard) {
                    keyboardUtil.showKeyBoard(context, comment_edt);
                }
            }
        });


        auto_rl.setKeyboardListener(new AutoRelativeLayout.OnKeyBoardListener() {
            @Override
            public void onKeyboardShow(int keyBoardHeight) {
                if(keyBoardHeight>0){
                    emoji_view.setContentHeight(keyBoardHeight);
                }
                if(comment_edt.hasFocus()&&emoji_view.getVisibility()==View.GONE) {
                    onKeyShow();
                }else if(!comment_edt.hasFocus()){
                    onEmojiHide();
                }else if(comment_edt.hasFocus()&&emoji_view.content_ll.getVisibility()==View.VISIBLE){
                    onKeyShow();
                }
            }

            @Override
            public void onKeyBoardHide() {
                if(emoji_view.getVisibility()==View.VISIBLE&&emoji_view.content_ll.getVisibility()==View.GONE){
                    hideCommentView();
                }
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


    /**
     * 网页调用android接口
     */
    class JsObject {
        @JavascriptInterface
        public void toImages(String url) {
            Intent intent=new Intent(NewsDetailActivity.this,NewsImagesActivity.class);
            intent.putExtra(IntentConfig.SEND_URL,url);
            intent.putStringArrayListExtra(IntentConfig.SEND_URL_LIST, imagesUrlList);
            intent.putExtra(IntentConfig.SEND_TITLE, newsBean.getSubject());
            startActivity(intent);
        }
    }
}


