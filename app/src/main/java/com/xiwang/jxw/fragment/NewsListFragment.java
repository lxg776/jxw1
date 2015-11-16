package com.xiwang.jxw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.NewsDetailActivity;
import com.xiwang.jxw.adapter.HomeNewsListAdapter;
import com.xiwang.jxw.adapter.OnitemClicklistener;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseFragment;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.HomeBiz;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.widget.RefreshLayout;

import java.util.List;

/**
 * 新闻列表部分
 * Created by sunshine on 15/11/7.
 */
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

    View foot_view;

    public NewsListFragment(){

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
         refreshLayout.setColorSchemeColors(R.color.orange_500,R.color.orange_700);

         listView=findViewById(R.id.listView);
         adapter=new HomeNewsListAdapter(context);
         foot_view=View.inflate(context,R.layout.listview_footer_view,null);
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
                if (position <= adapter.getNewsBeanList().size()-1) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(getString(R.string.send_news), adapter.getItem(position));
                    IntentUtil.gotoActivity(context, NewsDetailActivity.class, bundle);
                }}
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
        loadData(currentPage+1,false,false);
    }
    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        loadData(1,false,false);
    }

    /**
     * 加载新闻数据
     * @param page 页数
     * @param isCache 是否缓存
     * @param startLoading 首次加载loading
     */
    private void loadData(final int page, final boolean isCache,boolean startLoading){

        if(startLoading){
            refreshLayout.setRefreshing(true);
        }

        HomeBiz.getHomeNewsList(columnBean.getDataUrl(), page, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                ListBean listBean = (ListBean) responseBean.getObject();
                currentPage = page;
                if (currentPage == 1) {
                    adapter.setNewsBeanList((List<NewsBean>) listBean.getModelList());
                } else {
                    adapter.getNewsBeanList().addAll((List<NewsBean>) listBean.getModelList());
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

    /**
     * 加载完成
     */
    private void finishLoad(){
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        refreshLayout.setLoading(false);
    }


}
