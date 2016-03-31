package com.xiwang.jxw.activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.HomeNewsListAdapter;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.bean.ListBean;
import com.xiwang.jxw.bean.NewsBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.HomeBiz;
import com.xiwang.jxw.event.PickImageEvent;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.widget.PercentView;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by sunshine on 15/11/15.
 */
public class TestActivity extends BaseActivity{

    ListView listView;
    HomeNewsListAdapter adapter;
    int currentPage=1;

    ColumnBean columnBean=new ColumnBean();

    View headView;
    WebView webView;

    protected PercentView percentView;
    @Override
    protected String getPageName() {
        return "测试a";
    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test;
    }

    int per=0;


    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(per<100){
                per=per+1;
                percentView.setPercent(per);
                mHandler.removeCallbacks(runnable);
                mHandler.postDelayed(runnable,200);
            }else{
                mHandler.removeCallbacks(runnable);
                per=0;
            }

        }
    };
    @Override
    protected void findViews() {
        percentView= (PercentView) findViewById(R.id.percentView);
        mHandler.postDelayed(runnable,200);

//        listView= (ListView) findViewById(R.id.listView);
//        adapter =new HomeNewsListAdapter(this);
//
//        columnBean.setDataUrl("getapp.php?a=thread&fid=2");
//        columnBean.setName("新鲜事");
//
//       // headView=View.inflate(context,R.layout.view_news_detail,null);
//        webView= (WebView) headView.findViewById(R.id.webView);
//        listView.addHeaderView(headView);
//
//        //listView.addHeaderView(headView, null, false);
//        listView.setAdapter(adapter);
//        webView.loadUrl("http://www.qq.com");
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        loadData(1,false,false);
    }

    /**
     * 加载新闻数据
     * @param page 页数
     * @param isCache 是否缓存
     * @param startLoading 首次加载loading
     */
    private void loadData(final int page, final boolean isCache,boolean startLoading){

//        if(startLoading){
//            refreshLayout.setRefreshing(true);
//        }

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
                adapter.notifyDataSetChanged();
//                finishLoad();
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
