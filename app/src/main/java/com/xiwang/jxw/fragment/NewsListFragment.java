package com.xiwang.jxw.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.NewsDetailActivity;
import com.xiwang.jxw.activity.TestActivity;
import com.xiwang.jxw.adapter.HomeNewsListAdapter;
import com.xiwang.jxw.adapter.OnitemClicklistener;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseFragment;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.HomeBiz;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.widget.RefreshLayout;

import java.util.List;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

/**
 * 新闻列表部分
 * Created by sunshine on 15/11/7.
 */
@SuppressLint("ValidFragment")
public class NewsListFragment extends BaseFragment implements RefreshLayout.OnLoadListener,RefreshLayout.OnRefreshListener{
    /** 栏目信息*/
    ColumnBean columnBean;
    /** 下拉刷新和上拉更多控件*/
    RefreshLayout refreshLayout;
    /** 新闻列表适配器*/
    HomeNewsListAdapter adapter;
    /** data列表控件*/
    ListView listView;
    /** 当前页数*/
    int currentPage=1;
    /** 加载更多*/
    View foot_view;
    /** 加载更多 progressbar*/
    ProgressBar indeterminate_progress_library;
    /** 加载更多 文本*/
    TextView text_more;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String param1;

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        //fragment.setColumnBean(columnBean);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "param1");
        args.putString(ARG_PARAM2, "param2");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getPageName() {
        if(null!=columnBean)
        return columnBean.getName()+"页面";

        return "newsList";
    }

    public NewsListFragment(){
        super();
    }


    public NewsListFragment(ColumnBean columnBean){
        this.columnBean=columnBean;

    }

    public ColumnBean getColumnBean() {
        return columnBean;
    }

    public void setColumnBean(ColumnBean columnBean) {
        this.columnBean = columnBean;
    }

    @Override
    protected View getViews() {
         return View.inflate(context, R.layout.fragment_newslist, null);
    }

    @Override
    protected void findViews() {
         refreshLayout=findViewById(R.id.refreshLayout);
         refreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange_500));

         listView=findViewById(R.id.listView);
         adapter=new HomeNewsListAdapter(context);
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
         listView.setAdapter(adapter);
         refreshLayout.setChildView(listView);

    }

    @Override
    public void initGetData() {

    }

    @Override
    protected void widgetListener() {
        refreshLayout.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        adapter.setOnitemClicklistener(new OnitemClicklistener() {
            @Override
            public void onitemClick(View view, int position) {
                if (position <= adapter.getNewsBeanList().size() - 1) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(getString(R.string.send_news), adapter.getItem(position));
                    bundle.putSerializable(getString(R.string.send_column), columnBean);
                    IntentUtil.gotoActivity(context, NewsDetailActivity.class, bundle);
                }
            }
        });

        foot_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(currentPage + 1, false, false);
            }
        });



    }

    @Override
    protected void init() {
        loadData(1, true, true);
    }

    /**
     * 上拉更多方法
     */
    @Override
    public void onLoad() {
        loadData(currentPage + 1, false, false);
    }
    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        loadData(1, false, false);
    }

    /**
     * 加载新闻数据
     * @param page 页数
     * @param isCache 是否缓存
     * @param startLoading 首次加载loading
     */
    private void loadData(final int page, final boolean isCache,boolean startLoading){

        if(startLoading&&page==1){
            refreshLayout.setRefreshing(true);
        }
        if(page>1){
            text_more.setText(R.string.more_loading);
            indeterminate_progress_library.setVisibility(View.VISIBLE);
        }
        if(null!=columnBean){
            String dataUrl=columnBean.getDataUrl();
            if(!TextUtils.isEmpty(dataUrl)){
                HomeBiz.getHomeNewsList(dataUrl, page, new BaseBiz.RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        ListBean listBean = (ListBean) responseBean.getObject();
                        currentPage = page;
                        if (currentPage == 1) {
                            adapter.setNewsBeanList((List<NewsBean>) listBean.getModelList());
                        } else {
                            adapter.getNewsBeanList().addAll((List<NewsBean>) listBean.getModelList());
                        }
                        if(currentPage!=listBean.getPages()){
                            refreshLayout.setOnLoadListener(null);
                            foot_view.setVisibility(View.VISIBLE);

                        }else{
                            foot_view.setVisibility(View.GONE);
                            //listView.removeFooterView(foot_view);
                        }

                        finishLoad();
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {

                    }

                    @Override
                    public ResponseBean getRequestCache() {
                        ResponseBean responseBean = null;
                        if (isCache && page == 1) {
                            responseBean = (ResponseBean) SpUtil.getObject(context, getString(R.string.home_newslist) + columnBean.getDataUrl());
                        }
                        return responseBean;
                    }

                    @Override
                    public void onRequestCache(ResponseBean result) {
                        if (currentPage == 1) {
                            ListBean listBean = (ListBean) result.getObject();
                            adapter.setNewsBeanList((List<NewsBean>) listBean.getModelList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }
    }

    /**
     * 加载完成
     */
    private void finishLoad(){

        refreshLayout.setRefreshing(false);
        refreshLayout.setLoading(false);
        text_more.setText(R.string.loadmore);
        indeterminate_progress_library.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }


}
