package com.xiwang.jxw.fragment;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.NewsDetailActivity;

import com.xiwang.jxw.adapter.HomeNewsListAdapter;
import com.xiwang.jxw.adapter.OnitemClicklistener;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseFragment2;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.HomeBiz;
import com.xiwang.jxw.util.IntentUtil;
import com.xiwang.jxw.util.SpUtil;
import java.util.List;



/**
 * 新闻列表部分
 * Created by sunshine on 15/11/7.
 */
@SuppressLint("ValidFragment")
public class NewsListFragment extends BaseFragment2{
    /** 栏目信息*/
    ColumnBean columnBean;
    /** 下拉刷新和上拉更多控件*/
    MaterialRefreshLayout refreshLayout;
    /** 新闻列表适配器*/
    HomeNewsListAdapter adapter;
    /** data列表控件*/
    ListView listView;
    /** 当前页数*/
    int currentPage=1;



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
        return columnBean.getName()+"f";

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

        //refreshLayout.setLoadMore(true);
        listView= (ListView) view_Parent.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        refreshLayout = (MaterialRefreshLayout) view_Parent.findViewById(R.id.refresh_layout);
        refreshLayout.setWaveColor(0xffffffff);
        refreshLayout.setIsOverLay(false);
        refreshLayout.setWaveShow(true);
    }

    @Override
    public void initGetData() {
        adapter=new HomeNewsListAdapter(context);
    }

    @Override
    protected void widgetListener() {

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

    @Override
    protected void init() {
        if(null==adapter.getNewsBeanList()||adapter.getNewsBeanList().size()<=0){
            loadData(1, true, false);
        }
    }




    /**
     * 加载新闻数据
     * @param page 页数
     * @param isCache 是否缓存
     * @param startLoading 首次加载loading
     */
    private void loadData(final int page, final boolean isCache,boolean startLoading){



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
        refreshLayout.finishRefresh();
        refreshLayout.finishRefreshLoadMore();
        adapter.notifyDataSetChanged();
    }


}
