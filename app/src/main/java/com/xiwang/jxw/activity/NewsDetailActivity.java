package com.xiwang.jxw.activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.CommentListAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.AuthorBean;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.DigOrFightBean;
import com.xiwang.jxw.bean.DigUserBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.NewsDetailBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.bean.ShareBean;
import com.xiwang.jxw.bean.SmileBean;
import com.xiwang.jxw.biz.NewsBiz;
import com.xiwang.jxw.biz.UserBiz;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.intf.OnShareListener;
import com.xiwang.jxw.util.DisplayUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.IntentUtil;
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
import com.xiwang.jxw.widget.pop.SharePopWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

/**
 * 帖子详情界面
 * Created by sunshine on 15/11/9.
 */
public class NewsDetailActivity extends BaseActivity implements RefreshLayout.OnLoadListener,RefreshLayout.OnRefreshListener,AbsListView.OnScrollListener {
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

    /**全布局监听键盘事件*/
    AutoRelativeLayout auto_rl;
    /**评论内容*/
    RichEditText comment_edt;
    EmojiView emoji_view;

    /**点赞按钮*/
    LinearLayout dianzan_ll;
    /**点赞次数*/
    TextView dianzan_count_tv;
    /**点赞名单列表*/
    TextView dianzan_users_tv;
    /**点赞详情*/
    LinearLayout dianzan_detail_ll;


    /**回复数*/
    TextView replies_tv;
    /**跳回回复第一条*/
    RelativeLayout show_replies_rl;

    /**点赞的列表*/
    ArrayList<String> diges;
    /**回复按钮*/
    TextView huifu_btn;
    /**是否转到评论列表*/
    boolean isRelpy=true;
    /**是否启动主界面*/
    boolean isStartMainActivity=false;


    /**分享popWindow*/
    SharePopWindow sharePopWindow;

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

        /**点赞部分*/
        dianzan_ll= (LinearLayout) headView.findViewById(R.id.dianzan_ll);
        dianzan_count_tv= (TextView) headView.findViewById(R.id.dianzan_count_tv);
        dianzan_users_tv= (TextView) headView.findViewById(R.id.dianzan_users_tv);
        dianzan_detail_ll= (LinearLayout) headView.findViewById(R.id.dianzan_detail_ll);

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
        showComment_btn= (TextView)findViewById(R.id.showComment_btn);
        comment_edt= (RichEditText) findViewById(R.id.comment_edt);
        emoji_view= (EmojiView) findViewById(R.id.emoji_view);

        replies_tv= (TextView) findViewById(R.id.replies_tv);
        show_replies_rl= (RelativeLayout) findViewById(R.id.show_replies_rl);
        huifu_btn= (TextView) findViewById(R.id.huifu_btn);

    }


    @Override
    protected void init() {
        loadNetData(1, true);
        sharePopWindow=new SharePopWindow(this);
        sharePopWindow.setOnShareListener(new OnShareListener() {
            @Override
            public void onShare(final ShareBean shareBean) {
                doShare(shareBean);
            }
        });

        sharePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                removeshadeView();
            }
        });
    }

    /**
     * 分享操作
     */
    private void doShare(final ShareBean shareBean){
        UMShareAPI   mShareAPI = UMShareAPI.get(NewsDetailActivity.this);
        if(mShareAPI.isInstall(NewsDetailActivity.this,shareBean.getPlatform())){
                    /*
                        已经安装
                     */
            if(mShareAPI.isAuthorize(NewsDetailActivity.this,shareBean.getPlatform())){
                    /*
                        已经授权
                     */
                shareContent(newsBean, detailBean.getShareurl(), shareBean);
            }else{


                    /*
                      未授权
                     */
                mShareAPI.doOauthVerify(NewsDetailActivity.this, shareBean.getPlatform(), new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        shareContent(newsBean, detailBean.getShareurl(), shareBean);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        ToastUtil.showToast(context,"授权失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        ToastUtil.showToast(context,"取消授权");
                    }
                });
            }
        }else{
            ToastUtil.showToast(context,"未安装"+shareBean.getAppName());
        }
    }

    /**
     * 成功回调
     */
    UMShareListener listener=new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.showToast(NewsDetailActivity.this,"分享成功!");


        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast(NewsDetailActivity.this,"分享失败!");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showToast(NewsDetailActivity.this,"分享取消!");
        }
    };

    public void shareContent(NewsBean newsBean, String shareUrl, ShareBean shareBean){
        if(SHARE_MEDIA.TENCENT.equals(shareBean.getPlatform())){
            //腾讯微博
            newsBean.setShareUrl(shareUrl);
            Intent intent=new Intent(this,ShareEditActivity.class);
            intent.putExtra(getString(R.string.send_data),newsBean);
            intent.putExtra("sharebean",shareBean);
            startActivity(intent);
        }else if(SHARE_MEDIA.SINA.equals(shareBean.getPlatform())){
            //新浪微博
            Intent intent=new Intent(this,ShareEditActivity.class);
            intent.putExtra(getString(R.string.send_data),newsBean);
            intent.putExtra("sharebean",shareBean);
            startActivity(intent);
        }else{
            UMImage image=null;
            if(TextUtils.isEmpty(newsBean.getImage())){
                image= new UMImage(this, newsBean.getImage());
            }
            ShareAction shareAction=new ShareAction(this);
            shareAction.setPlatform(shareBean.getPlatform());
            shareAction.setCallback(listener);
           // shareAction.withText(newsBean.getSubject());
        //    shareAction.withTitle(newsBean.getSubject());
            shareAction.withTargetUrl(shareUrl);
            ShareContent shareContent=new ShareContent();
            shareContent.mTitle=newsBean.getSubject();
            shareContent.mTargetUrl=shareUrl;
            shareContent.mMedia=image;
            shareAction.setShareContent(shareContent);
//            shareAction.set
//            if(image!=null){
//                shareAction.withMedia(image);
//            }
            shareAction.share();
        }
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
        isStartMainActivity=getIntent().getBooleanExtra(getString(R.string.send_fla),false);


        listAdapter=new CommentListAdapter(this);
        listView.setAdapter(listAdapter);
        diges=NewsBiz.getDiges(context);

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



        //设置回复数据
        if(!TextUtils.isEmpty(detailBean.getReplies())&&!"0".equals(detailBean.getReplies())){
            replies_tv.setText(detailBean.getReplies());
            replies_tv.setVisibility(View.VISIBLE);
        }else{
            replies_tv.setVisibility(View.GONE);
        }

        if(diges.contains(detailBean.getTid())){
            setDianzan(true);
        }else{
            setDianzan(false);
        }
        //设置点赞
        dianzan_count_tv.setText(detailBean.getDig());
        setDigesListText(detailBean.getDiglist());
    }

    /**
     * 设置点赞文本
     */
    private void setDigesListText(List<DigUserBean> digUserBeans){
        if(null==digUserBeans||digUserBeans.size()==0){
            dianzan_detail_ll.setVisibility(View.GONE);
        }else{
            dianzan_detail_ll.setVisibility(View.VISIBLE);
            StringBuffer sb=new StringBuffer();
            int j=0;
            for(int i=digUserBeans.size()-1;i>=0;i--){
                DigUserBean userBean=digUserBeans.get(i);
                sb.append(userBean.getUsername());
                if(i>0){
                    sb.append("、");
                }else{
                    sb.append("");
                }
                j++;
                if(j==2){
                    break;
                }
            }
            dianzan_users_tv.setText(sb.toString());
        }
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
            ImgLoadUtil.getInstance().displayImage(authorBean.getFace(), author_headimg_iv, options, loadingListener);
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

    /**
     * 跳转点赞列表
     */
    private void toDigList(String tid){
        Intent intent=new Intent(context,DigUsersActivity.class);
        intent.putExtra(IntentConfig.SEND_TID, tid);
        startActivity(intent);
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

    /**
     * 设置点赞按钮
     */
    private void setDianzan(boolean fla){
        if(fla){
            dianzan_ll.setBackground(getResources().getDrawable(R.drawable.yellow500_bg));
            ImageView iv= (ImageView) dianzan_ll.getChildAt(0);
            iv.setBackgroundDrawable(getResources().getDrawable(R.mipmap.dig_yellow));
            TextView textView= (TextView) dianzan_ll.getChildAt(1);
            textView.setTextColor(getResources().getColor(R.color.orange_700));
        }else{
            dianzan_ll.setBackground(getResources().getDrawable(R.drawable.white_bg));
            ImageView iv= (ImageView) dianzan_ll.getChildAt(0);
            iv.setBackgroundDrawable(getResources().getDrawable(R.mipmap.dig0));
            TextView textView= (TextView) dianzan_ll.getChildAt(1);
            textView.setTextColor(getResources().getColor(R.color.gray_500));
        }

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
                if (keyBoardHeight > 0) {
                    emoji_view.setContentHeight(keyBoardHeight);
                }
                if (comment_edt.hasFocus() && emoji_view.getVisibility() == View.GONE) {
                    onKeyShow();
                } else if (!comment_edt.hasFocus()) {
                    onEmojiHide();
                } else if (comment_edt.hasFocus() && emoji_view.content_ll.getVisibility() == View.VISIBLE) {
                    onKeyShow();
                }
            }

            @Override
            public void onKeyBoardHide() {
                if (emoji_view.getVisibility() == View.VISIBLE && emoji_view.content_ll.getVisibility() == View.GONE) {
                    hideCommentView();
                }
            }
        });

        /*
          跳至第一条评论
         */
        show_replies_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRelpy){
                    listView.setSelection(1);
                }else{
                    listView.setSelection(0);
                }
                isRelpy=!isRelpy;

            }
        });

        /*
        点赞
         */
        dianzan_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tid =newsBean.getTid();
                String pid="";
                String type= UserBiz.TYPE_DIG;
                UserBiz.digOrFight(type, tid, pid, new BaseBiz.RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        DigOrFightBean bean = (DigOrFightBean) responseBean.getObject();
                        String dig = bean.getDig();
                        setDianzan(true);
                        dianzan_ll.setBackground(getResources().getDrawable(R.drawable.yellow500_bg));
                        TextView textView = (TextView) dianzan_ll.getChildAt(1);
                        textView.setText(dig);
                        diges.add(newsBean.getTid());
                        NewsBiz.setDiges(context, diges);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {

                    }

                    @Override
                    public ResponseBean getRequestCache() {
                        return null;
                    }

                    @Override
                    public void onRequestCache(ResponseBean result) {

                    }
                });
            }
        });

        /**
         * 点赞详情
         */
        dianzan_detail_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toDigList(detailBean.getTid());
            }
        });

        /**
         *回复按钮
         */
        huifu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replyContent = comment_edt.getRichText();
                if (!CheckUtil.isEmpty(context, "回复内容", replyContent)) {
                    NewsBiz.reply(newsBean.getTid(), replyContent, null, new BaseBiz.RequestHandle() {
                        @Override
                        public void onSuccess(ResponseBean responseBean) {
                            showNewsDetail(responseBean);
                            listView.setSelection(1);
                        }

                        @Override
                        public void onFail(ResponseBean responseBean) {
                            ToastUtil.showToast(context, responseBean.getInfo());
                        }

                        @Override
                        public ResponseBean getRequestCache() {
                            return null;
                        }

                        @Override
                        public void onRequestCache(ResponseBean result) {

                        }
                    });
                }
            }
        });
        listView.setOnScrollListener(this);



    }

    @Override
    public void onLoad() {
        loadNetData(currentPage + 1, false);
    }

    @Override
    public void onRefresh() {
        loadNetData(1, true);
    }


    public void onSort(MenuItem item) {
        // Request a call to onPrepareOptionsMenu so we can change the sort icon
        invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            openCustemShare();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void openCustemShare(){
            if(sharePopWindow.isShowing()){
                sharePopWindow.dismiss();

            }else{
                sharePopWindow.showAtLocation(findViewById(R.id.content_rl), Gravity.BOTTOM, 0, 0);
                addShadeView();
            }
    }

    TextView shadetextView;
    private void addShadeView(){
        if(null==shadetextView){
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            shadetextView  = new TextView(context);
            shadetextView.setBackgroundColor(0x99000000);
            RelativeLayout content_rl= (RelativeLayout) findViewById(R.id.content_rl);
            content_rl.addView(shadetextView, layoutParams);
        }
    }

    private void removeshadeView(){
        WindowManager  mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(shadetextView!=null){
            RelativeLayout content_rl= (RelativeLayout) findViewById(R.id.content_rl);
            content_rl.removeView(shadetextView);
            shadetextView=null;
        }

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

    /**
     * 滚动事件监听
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState){
                case  AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        hideCommentView();
                    break;
            }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void finish() {
        super.finish();
        if(isStartMainActivity){
            /**启动主界面*/
            IntentUtil.gotoActivity(this,MainActivity.class);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}


