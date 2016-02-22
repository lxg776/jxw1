package com.xiwang.jxw.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xiwang.jxw.R;
import com.xiwang.jxw.base.BaseActivity;
import com.xiwang.jxw.base.BaseBiz;
import com.xiwang.jxw.bean.DigUserBean;
import com.xiwang.jxw.bean.ResponseBean;
import com.xiwang.jxw.biz.NewsBiz;
import com.xiwang.jxw.config.IntentConfig;
import com.xiwang.jxw.util.CommonUtil;
import com.xiwang.jxw.util.ImgLoadUtil;
import com.xiwang.jxw.util.SpUtil;
import com.xiwang.jxw.widget.LoadingLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * 点赞列表
 * Created by liangxg on 2016/2/22.
 */
public class DigUsersActivity extends BaseActivity {


    /**列表控件*/
    ListView listView;
    /**加载中*/
    LoadingLayout content_rl;

    View contentLayout;
    /**帖子id*/
    String tid;

    DigAdapter adapter;
    @Override
    protected String getPageName() {
        return "点赞列表a";
    }

    @Override
    protected void initActionBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("点赞用户");
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
        return R.layout.activity_digusers;
    }

    @Override
    protected void findViews() {
        content_rl = (LoadingLayout) findViewById(R.id.content_rl);
        contentLayout = View.inflate(context, R.layout.c_digusers, null);
        content_rl.setContentLayout(contentLayout);
        listView= (ListView) findViewById(R.id.listView);
    }


    private void showListView( ArrayList<DigUserBean> list){
        if(list==null||list.size()==0){
            return;
        }
        adapter.list=list;
        adapter.notifyDataSetChanged();
        content_rl.setLoadView(true);
    }

    @Override
    protected void init() {
        content_rl.setLoadView(false);
        NewsBiz.getDigUsers(tid, new BaseBiz.RequestHandle() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                ArrayList<DigUserBean> list = (ArrayList<DigUserBean>) responseBean.getObject();
                showListView(list);
                SpUtil.setObject(context, tid + getResources().getString(R.string.cache_digusers), responseBean);
            }

            @Override
            public void onFail(ResponseBean responseBean) {
                content_rl.setLoadView(true);
            }

            @Override
            public ResponseBean getRequestCache() {
                return null;
            }

            @Override
            public void onRequestCache(ResponseBean result) {
                showListView((ArrayList<DigUserBean>) result.getObject());
            }
        });
    }

    @Override
    protected void initGetData() {
        tid=getIntent().getStringExtra(IntentConfig.SEND_TID);
        adapter=new DigAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    protected void widgetListener() {

    }


    /** 图片的异步显示的选项*/
    DisplayImageOptions options=ImgLoadUtil.getUserOptions();
    /** 加载监听*/
    ImageLoadingListener loadingListener=ImgLoadUtil.defaultLoadingListener();

    class DigAdapter extends BaseAdapter{

        List<DigUserBean> list;

        @Override
        public int getCount() {
            if(list!=null){
                return  list.size();
            }
            return 0;
        }

        @Override
        public DigUserBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(null==convertView){
                convertView=View.inflate(context,R.layout.item_dig_user,null);
            }
            DigUserBean digUserBean=getItem(position);
            /**头像*/
            ImageView author_headimg_iv= (ImageView) convertView.findViewById(R.id.author_headimg_iv);
            /**名称*/
            TextView author_tv= (TextView) convertView.findViewById(R.id.author_tv);
            ImgLoadUtil.getInstance().displayImage(CommonUtil.getAboutAbsoluteImgUrl(digUserBean.getFace()),author_headimg_iv, options, loadingListener);
            author_tv.setText(digUserBean.getUsername());
            return convertView;
        }
    }
}
