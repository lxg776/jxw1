package com.xiwang.jxw.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.xiwang.jxw.bean.ColumnBean;
import com.xiwang.jxw.fragment.NewsListFragment;
import com.xiwang.jxw.fragment.NewsListView;
import com.xiwang.jxw.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页页面适配器
 * Created by sunshine on 15/11/7.
 */
public class HomePagerViewAdapter extends PagerAdapter {

    //Map<ColumnBean, NewsListView> mapFragement = new HashMap<ColumnBean, NewsListView>();
    List<ColumnBean> columnBeanList;
    List<NewsListView> newsListViews;
    Context context;


    public HomePagerViewAdapter(Context context){
        this.context=context;
    }

    public void setColumnBeanList(List<ColumnBean> columnBeanList) {

        this.columnBeanList = columnBeanList;
        if(newsListViews==null){
            newsListViews =new ArrayList<NewsListView>() ;
        }else{
            newsListViews.clear();
        }
        if(null!=columnBeanList&&columnBeanList.size()>0){
            for(int i=0;i<columnBeanList.size();i++){
                NewsListView view =new NewsListView(context);
                view.setColumnBean(columnBeanList.get(i));
                newsListViews.add(view);
            }
        }
    }

    @Override
    public int getCount() {
        if(null!=columnBeanList){
            return  columnBeanList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    //是从ViewGroup中移出当前View
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(newsListViews.get(arg1));
    }

    //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
    public Object instantiateItem(View arg0, int arg1){
        NewsListView view= newsListViews.get(arg1);
        view.loadData(1,false,false);
        ((ViewPager)arg0).addView(view);
        return view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != columnBeanList) {
            return columnBeanList.get(position).getName();
        }
        return null;
    }
}
