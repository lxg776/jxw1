package com.xiwang.jxw.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiwang.jxw.R;
import com.xiwang.jxw.activity.NewsDetailActivity;
import com.xiwang.jxw.adapter.HomeNewsListAdapter2;
import com.xiwang.jxw.adapter.OnitemClicklistener;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.base.BaseFragment3;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.HomeLunBoBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.HomeBiz;
import com.xiwang.jxw.config.TApplication;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.widget.refresh.JxwRefreshViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;



/**
 * 新闻列表部分
 * Created by sunshine on 15/11/7.
 */
@SuppressLint("ValidFragment")
public class NewsListFragment extends BaseFragment3 {
    /** 栏目信息*/
    ColumnBean columnBean;
    /** 下拉刷新和上拉更多控件*/
    BGARefreshLayout refreshLayout;
    /** 新闻列表适配器*/
    HomeNewsListAdapter2 adapter;
    /** data列表控件*/
    RecyclerView listView;
    /** 当前页数*/
    int currentPage=1;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    HomeLunBoBean lunboBean;


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
    protected View getViews(LayoutInflater inflater, ViewGroup container) {
         return inflater.inflate( R.layout.fragment_newslist, container,false);
    }

    @Override
    protected void findViews() {

        //refreshLayout.setLoadMore(true);
        listView= (RecyclerView) view_Parent.findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(listView
                .getContext()));
        listView.setAdapter(adapter);
        refreshLayout = (BGARefreshLayout) view_Parent.findViewById(R.id.refresh_layout);




        //设置刷新头部

        JxwRefreshViewHolder meiTuanRefreshViewHolder = new JxwRefreshViewHolder(TApplication.context, true);

        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_change_to_release_refresh_05);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);

        refreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);



//        refreshLayout.setWaveColor(0xffffffff);
//        refreshLayout.setIsOverLay(false);
//        refreshLayout.setWaveShow(true);

    }




    @Override
    public void initGetData() {

        adapter=new HomeNewsListAdapter2(context,columnBean);

    }

    @Override
    protected void widgetListener() {

        adapter.setOnitemClicklistener(new OnitemClicklistener() {
            @Override
            public void onitemClick(View view, int position) {
                if (position <= adapter.getNewsBeanList().size() - 1) {
                    Bundle bundle = new Bundle();
                    Object o =  adapter.getNewsBeanList().get(position);
                    if(o instanceof  NewsBean){
                        NewsBean bean = (NewsBean) o;
                        NewsDetailActivity.jumpNewsDetailActivity(context,bean,columnBean);
                    }
                }
            }
        });

        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                loadData(1, false, false);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                loadData(currentPage+1, false, false);
                return false;
            }
        });

//        refreshLayout
//                .setMaterialRefreshListener(new MaterialRefreshListener() {
//                    @Override
//                    public void onRefresh(
//                            final MaterialRefreshLayout materialRefreshLayout) {
//
//                        // materialRefreshLayout.finishRefreshLoadMore();
//                        loadData(1, false, false);
//                    }
//
//                    @Override
//                    public void onfinish() {
//
//                    }
//
//                    @Override
//                    public void onRefreshLoadMore(
//                            final MaterialRefreshLayout materialRefreshLayout) {
//                        loadData(currentPage+1, false, false);
//                    }
//
//                });

    }

    @Override
    protected void init() {
        if(null==adapter.getNewsBeanList()||adapter.getNewsBeanList().size()<=0){
            loadData(1, true, false);
        }
    }

    List<Object> objectList =new ArrayList<>();

    /**
     * 获取轮播布局
     */
    private void getHomeLunbo(){
        HomeBiz.getTopLunbo(columnBean.getFid(), new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                HomeLunBoBean boBean = (HomeLunBoBean) responseBean.getObject();
                if(boBean.getDataList()!=null&&boBean.getDataList().size()>0){
                    lunboBean =boBean;
                }else{
                    lunboBean=null;
                }
                updateLunboView();
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

    /**
     * 更新轮播布局
     */
    private void updateLunboView(){
        Object o=null;
        if(null!=objectList&&objectList.size()>0){
                o= objectList.get(0);
        }
        if(o!=null&&o instanceof HomeLunBoBean&&lunboBean!=null){
            objectList.remove(0);
            objectList.add(0,lunboBean);
        }else if(o!=null&&o instanceof HomeLunBoBean&&lunboBean==null){
            objectList.remove(0);
        }else if(o!=null&&o instanceof NewsBean&&lunboBean!=null){
            objectList.add(0,lunboBean);
        }else if(o==null){
            objectList.add(0,lunboBean);
        }
        adapter.setNewsBeanList(objectList);
        adapter.notifyDataSetChanged();
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
                if(page==1){
                    //获取轮播图
                    getHomeLunbo();
                }
                HomeBiz.getHomeNewsList(dataUrl, page, new BaseBiz.RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        ListBean listBean = (ListBean) responseBean.getObject();
                        currentPage = page;
                        if (currentPage == 1) {
                            if(objectList.size()>0){
                                Object o = objectList.get(0);
                                objectList.clear();
                                if(o instanceof HomeLunBoBean){
                                    objectList.add(0,o);
                                }
                            }
                            objectList.addAll(listBean.getModelList());
                            adapter.setNewsBeanList(objectList);
                        } else {
                            objectList.addAll(listBean.getModelList());
                            adapter.setNewsBeanList(objectList);
                        }
//                        if(currentPage!=listBean.getPages()){
//                            refreshLayout.setLoadMore(true);
//
//                        }else{
//                            refreshLayout.setLoadMore(false);
//                        }
                        finishLoad();
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        finishLoad();
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
                            objectList.clear();
                            objectList.addAll(listBean.getModelList());
                            adapter.setNewsBeanList(objectList);
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
        refreshLayout.endLoadingMore();
        refreshLayout.endRefreshing();
        adapter.notifyDataSetChanged();
    }

}
