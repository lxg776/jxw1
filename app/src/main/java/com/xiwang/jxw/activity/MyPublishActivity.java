package com.xiwang.jxw.activity;

import com.xiwang.jxw.base.BaseActivity;

/**
 * 我的发布
 * Created by liangxg on 2016/10/18.
 */
public class MyPublishActivity extends BaseActivity{
    @Override
    protected String getPageName() {
        return null;
    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected int getContentViewId() {
        return 0;
    }

    @Override
    protected void findViews() {

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

//    /**我的发布列表*/
//     RecyclerView mlistView;
//
//    /** 新闻列表适配器*/
//    HomeNewsListAdapter2 mAdapter;
//    /**刷新控件*/
//    MaterialRefreshLayout mRefreshLayout;
//
//
//    List<Object> objectList =new ArrayList<>();
//    int currentPage=1;
//
//
//    ColumnBean columnBean =new ColumnBean();
//
//
//    {
//        columnBean.setName("我的发布");
//        columnBean.setFid("-999");
//    }
//
//
//    @Override
//    protected String getPageName() {
//        return "我的发布";
//    }
//
//    @Override
//    protected void initActionBar() {
//        toolbar= (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("我的发布");
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//
//
//
//    /**
//     * 加载新闻数据
//     * @param page 页数
//     * @param isCache 是否缓存
//     * @param startLoading 首次加载loading
//     */
//    private void loadData(final int page, final boolean isCache,boolean startLoading){
//
//
//                UserBiz.getMyPublish(context, page, new BaseBiz.RequestHandle() {
//                    @Override
//                    public void onSuccess(ResponseBean responseBean) {
//                        ListBean listBean = (ListBean) responseBean.getObject();
//                        currentPage = page;
//                        if (currentPage == 1) {
//                            if(objectList.size()>0){
//                                Object o = objectList.get(0);
//                                objectList.clear();
//                                if(o instanceof HomeLunBoBean){
//                                    objectList.add(0,o);
//                                }
//                            }
//                            objectList.addAll(listBean.getModelList());
//                            mAdapter.setNewsBeanList(objectList);
//                            SpUtil.setObject(context,getString(R.string.my_publish) + UserBiz.getUserBean(context).getUid(),responseBean);
//                        } else {
//                            objectList.addAll(listBean.getModelList());
//                            mAdapter.setNewsBeanList(objectList);
//                        }
//                        if(currentPage!=listBean.getPages()){
//                            mRefreshLayout.setLoadMore(true);
//                        }else{
//                            mRefreshLayout.setLoadMore(false);
//                        }
//                        finishLoad();
//                    }
//
//                    @Override
//                    public void onFail(ResponseBean responseBean) {
//
//
//                    }
//                    @Override
//                    public ResponseBean getRequestCache() {
//                        ResponseBean responseBean = null;
//                        if (isCache && page == 1) {
//                            responseBean = (ResponseBean) SpUtil.getObject(context, getString(R.string.my_publish) + UserBiz.getUserBean(context).getUid());
//                        }
//                        return responseBean;
//                    }
//                    @Override
//                    public void onRequestCache(ResponseBean result) {
//                        if (currentPage == 1) {
//                            ListBean listBean = (ListBean) result.getObject();
//                            objectList.clear();
//                            objectList.addAll(listBean.getModelList());
//                            mAdapter.setNewsBeanList(objectList);
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//    }
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_mypublish;
//    }
//
//    @Override
//    protected void findViews() {
//        mlistView = (RecyclerView) findViewById(R.id.listView);
//         mRefreshLayout= (MaterialRefreshLayout) findViewById(R.id.refresh_layout);
//        mRefreshLayout.setWaveColor(0xffffffff);
//        mRefreshLayout.setIsOverLay(false);
//        mRefreshLayout.setWaveShow(true);
//    }
//
//    /**
//     * 加载完成
//     */
//    private void finishLoad(){
//        mRefreshLayout.finishRefresh();
//        mRefreshLayout.finishRefreshLoadMore();
//        mAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    protected void init() {
//        if(null==mAdapter.getNewsBeanList()||mAdapter.getNewsBeanList().size()<=0){
//            loadData(1, true, false);
//        }
//    }
//
//
//    /**
//     * 跳转我的发布
//     * @param context
//     */
//    public  static void jumpActivity(Context context){
//        Bundle bundle = new Bundle();
//        IntentUtil.gotoActivity(context, MyPublishActivity.class, bundle);
//    }
//
//    @Override
//    protected void initGetData() {
//        mAdapter=new HomeNewsListAdapter2(context,columnBean);
//        mlistView.setLayoutManager(new LinearLayoutManager(mlistView
//                .getContext()));
//        mlistView.setAdapter(mAdapter);
//
//    }
//
//    @Override
//    protected void widgetListener() {
//
//
//        mAdapter.setOnitemClicklistener(new OnitemClicklistener() {
//            @Override
//            public void onitemClick(View view, int position) {
//                if (position <= mAdapter.getNewsBeanList().size() - 1) {
//                    Bundle bundle = new Bundle();
//                    Object o =  mAdapter.getNewsBeanList().get(position);
//                    if(o instanceof NewsBean){
//                        NewsBean bean = (NewsBean) o;
//                        NewsDetailActivity.jumpNewsDetailActivity(context,bean,columnBean);
//                    }
//                }
//            }
//        });
//
//        mRefreshLayout
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
//
//    }
}
