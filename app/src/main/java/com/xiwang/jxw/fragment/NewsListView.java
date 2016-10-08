package com.xiwang.jxw.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.NewsDetailActivity;
import com.xiwang.jxw.adapter.HomeNewsListAdapter;
import com.xiwang.jxw.adapter.HomeNewsListAdapter2;
import com.xiwang.jxw.adapter.OnitemClicklistener;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.HomeBiz;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.SpUtil;
import java.util.List;

/**
 * Created by liangxg on 2016/7/19.
 */
public class NewsListView extends RelativeLayout {


    /** 栏目信息*/
    ColumnBean columnBean;
    /** 下拉刷新和上拉更多控件*/
    MaterialRefreshLayout refreshLayout;
    /** 新闻列表适配器*/
    HomeNewsListAdapter2 adapter;
    /** data列表控件*/
    RecyclerView listView;
    /** 当前页数*/
    int currentPage=1;
    Context context;
    View view_Parent;

    public ColumnBean getColumnBean() {
        return columnBean;
    }

    public void setColumnBean(ColumnBean columnBean) {
        this.columnBean = columnBean;
    }


    public NewsListView(Context context) {
        super(context);
        init(context);
    }

    public NewsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    private void init(Context mContex) {
        this.context=mContex;
        view_Parent  = View.inflate(context, R.layout.fragment_newslist, null);
        initGetData();
        findViews();
        widgetListener();
        addView(view_Parent);

    }

    /**
     * 添加头部广告
     */
    private void addHeadView(){

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void findViews() {

        //refreshLayout.setLoadMore(true);
        listView= (RecyclerView) view_Parent.findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(listView
                .getContext()));

        listView.setAdapter(adapter);



        refreshLayout = (MaterialRefreshLayout) view_Parent.findViewById(R.id.refresh_layout);
        refreshLayout.setWaveColor(0xffffffff);
        refreshLayout.setIsOverLay(false);
        refreshLayout.setWaveShow(true);
    }


    public void initGetData() {
        adapter=new HomeNewsListAdapter2(context);
    }


    protected void widgetListener() {

        adapter.setOnitemClicklistener(new OnitemClicklistener() {
            @Override
            public void onitemClick(View view, int position) {
                if (position <= adapter.getNewsBeanList().size() - 1) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(context.getString(R.string.send_news), adapter.getNewsBeanList().get(position));
                    bundle.putSerializable(context.getString(R.string.send_column), columnBean);
                    IntentUtil.gotoActivity(context, NewsDetailActivity.class, bundle);
                }
            }
        });
        refreshLayout
                .setMaterialRefreshListener(new MaterialRefreshListener() {
                    @Override
                    public void onRefresh(
                            final MaterialRefreshLayout materialRefreshLayout) {

                        // materialRefreshLayout.finishRefreshLoadMore();
                        loadData(1, false, false);
                    }

                    @Override
                    public void onfinish() {

                    }

                    @Override
                    public void onRefreshLoadMore(
                            final MaterialRefreshLayout materialRefreshLayout) {
                        loadData(currentPage+1, false, false);
                    }

                });

    }



    /**
     * 加载新闻数据
     * @param page 页数
     * @param isCache 是否缓存
     * @param startLoading 首次加载loading
     */
    public void loadData(final int page, final boolean isCache,boolean startLoading){



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
                            refreshLayout.setLoadMore(true);
                        }else{
                            refreshLayout.setLoadMore(false);
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
                            responseBean = (ResponseBean) SpUtil.getObject(context, context.getString(R.string.home_newslist) + columnBean.getDataUrl());
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
        refreshLayout.finishRefresh();
        refreshLayout.finishRefreshLoadMore();
        adapter.notifyDataSetChanged();
    }

}
